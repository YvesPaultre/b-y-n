import { useEffect, useState } from "react";
import { useNavigate, useParams, Link, } from "react-router-dom";
import { Alert, ListGroup, ListGroupItem, Dropdown, Container, Form, Button } from "react-bootstrap";
import { jwtDecode } from "jwt-decode"

const ROUTINE_DEFAULT = {
    routine_name: "",
    routine_description: "",
    routine_duration: 0,
    difficulty: "",
    routine_author_name: "",
    workouts: [],
};

function RoutineForm() {
    const [routine, setRoutine] = useState(ROUTINE_DEFAULT);
    const [errors, setErrors] = useState([]);
    const [routineWorkouts, setRoutineWorkouts] = useState([]);
    const [workouts, setWorkouts] = useState([])
    const { id } = useParams();
    const navigate = useNavigate();
    // const url = 'http://localhost:8080/api/routine';
    const url = `${process.env.REACT_APP_AWS_SERVER_HOST_BASE_URL}/api/routine`
    const user = jwtDecode(localStorage.getItem('user'));


    useEffect(() => {
        // fetch("http://localhost:8080/api/workout")
        fetch(`${process.env.REACT_APP_AWS_SERVER_HOST_BASE_URL}/api/workout`)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then(data => setWorkouts(data))
            .catch(console.log)
    }, []);

    useEffect(() => {
        if (id) {
            fetch(`${url}/id/${id}`)
                .then((response) => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        return Promise.reject(`Unexpected status code: ${response.status}`);
                    }
                })
                .then((data) => setRoutine(data))
                .catch(console.log);
        } else {
            setRoutine(ROUTINE_DEFAULT);
        }
    }, [id]);

    const handleSubmit = (event) => {
        event.preventDefault();

        routine['routine_author_name'] = user.sub;
        if (id) {
            updateRoutine();
        } else {
            addRoutine();
        }
    };

    const handleChange = (event) => {
        // console.log(event.target.name, ':', event.target.value)
        let newRoutine = { ...routine };
        newRoutine[event.target.name] = event.target.value;
        setRoutine(newRoutine);
    };

    const addRoutine = () => {
        console.log(routine)
        const token = JSON.parse(localStorage.getItem('user'))

        routine.workouts = routine.workouts.join(',')
        console.log(routine)


        const init = {
            method: "POST",
            headers: {
                'content-Type': 'application/json',
                Authorization: `Bearer ${token.jwt_token}`
            },
            body: JSON.stringify(routine),
        };

        fetch(url, init)
            .then((response) => {
                if (
                    response.status === 201 ||
                    response.status === 403 ||
                    response.status === 400
                ) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then((data) => {
                if (data.routine_id) {
                    navigate("/routines");
                } else {
                    setErrors(data);
                }
            })
            .catch(console.log);
    };

    const updateRoutine = () => {
        const token = JSON.parse(localStorage.getItem('user'))
        routine.routine_id = id;
        
        const init = {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token.jwt_token}`
            },
            body: JSON.stringify(routine),
        };

        fetch(`${url}/${id}`, init)
            .then((response) => {
                if (response.status === 204) {
                    return null;
                } else if (response.status === 403 || response.status === 400) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then((data) => {
                if (!data) {
                    navigate("/routines");
                } else {
                    setErrors(data);
                }
            })
            .catch(console.log);
    };

    const toggleWorkout = (workout) => {
        console.log(workout)
        const workoutString = `${workout.id}:"${workout.name}"`
        let newRoutineWorkouts = routineWorkouts;
        if (newRoutineWorkouts.includes(workoutString)) {
            newRoutineWorkouts.splice(newRoutineWorkouts.indexOf(workoutString), 1);
        } else {
            newRoutineWorkouts.push(workoutString);
        }

        setRoutineWorkouts(newRoutineWorkouts);

        const newRoutine = { ...routine };
        newRoutine["workouts"] = newRoutineWorkouts;
        setRoutine(newRoutine);
    };

    const handleDelete = () => {
        if (window.confirm(`Delete Routine: ${routine.routine_name}?`)) {
            const token = JSON.parse(localStorage.getItem('user'))
            
            // console.log(token)
            
            const init = {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token.jwt_token}`
                }
            }
            fetch(`${url}/${id}`, init)
                .then(response => {
                    if (response.status === 204) {
                        navigate('/dashboard')
                    }
                    else { return Promise.reject(`Unexpected Status Code: ${response.status}`) }
                })
                .catch(console.log)
        }

    }

    const handleCancel = () => {
        if (window.confirm('Cancel?')) {
            navigate('/dashboard')
        }
    }

    return (
        <>
            <Container>
                <h2 className="routine-form-title">{id > 0 ? 'Update Routine' : 'Add Routine'}</h2>
                {errors.length > 0 && (
                    <Alert className="alert-danger">
                        <p>The following errors were found: </p>
                        <ListGroup>
                            {errors.map(error => (
                                <ListGroupItem key={error}>{error}</ListGroupItem>
                            ))}
                        </ListGroup>
                    </Alert>
                )}
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="routine_name" onChange={handleChange}>
                        <Form.Label>Routine Name</Form.Label>
                        <Form.Control type="text" name="routine_name" defaultValue={routine.routine_name} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="routine_description" onChange={handleChange}>
                        <Form.Label>Description</Form.Label>
                        <Form.Control type="text" name="routine_description" defaultValue={routine.routine_description} />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="routine_duration" onChange={handleChange}>
                        <Form.Label>Duration (Min)</Form.Label>
                        <Form.Control type="number" name="routine_duration" defaultValue={routine.routine_duration} />
                    </Form.Group>
                    <Form.Select name="difficulty" onChange={handleChange} defaultValue={routine.routine_difficulty}>
                        <option value="">Select Difficulty</option>
                        <option value="Easy">Easy</option>
                        <option value="Medium">Medium</option>
                        <option value="Hard">Hard</option>
                    </Form.Select>
                    <Dropdown autoClose='outside'>
                        <Dropdown.Toggle variant="success" id="dropdown-basic">Select Workouts</Dropdown.Toggle>
                        <Dropdown.Menu >
                            {workouts.map((workout) => (
                                <Dropdown.Item key={workout.id}
                                    onClick={() => toggleWorkout(workout)}
                                    active={routineWorkouts.includes(workout)}
                                >{workout.name}</Dropdown.Item>
                            ))}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Container className="button-box">
                        <Button variant="success" type="submit">
                            {id > 0 ? 'Update Routine' : 'Add Routine'}
                        </Button>
                        <Button variant='secondary' onClick={handleCancel}>Cancel</Button>
                        {
                            id > 0 ? <Button variant='danger' onClick={handleDelete}>Delete</Button> : <></>
                        }
                    </Container>
                </Form>
            </Container>
            {/* <Button onClick={()=>console.log(routine)}>BUTTON</Button> */}
        </>)
}

export default RoutineForm;
