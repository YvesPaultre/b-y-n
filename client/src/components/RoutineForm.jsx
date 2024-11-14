import { useEffect, useState } from "react";
import { useNavigate, useParams, Link} from "react-router-dom";
import User from "../context/UserContext";
import { Alert, ListGroup, ListGroupItem, Dropdown, Container, Form } from "react-bootstrap";

const ROUTINE_DEFAULT = {
    routine_name: '',
    routine_description: '',
    routine_duration: 0,
    routine_difficulty: '',
    routine_author_name: '',
    workouts: []
}

function RoutineForm(){
    const [routine, setRoutine] = useState(ROUTINE_DEFAULT);
    const [errors, setErrors] = useState([]);
    const [workouts, setWorkouts] = useState([]);
    const [routineWorkouts, setRoutineWorkouts] = useState([]);
    const {id} = useParams();
    const navigate = useNavigate();
    const url = 'http://localhost:8080/api/routine';

    useEffect(() => {
        fetch("http://localhost:8080/api/workout")
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
        if(id){
            fetch(`${url}/id/${id}`)
            .then(response => {
                if(response.status === 200){
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            }).then(data => setRoutine(data))
            .catch(console.log);
        } else {
            setRoutine(ROUTINE_DEFAULT);
        }
    }, [id]);

    const handleSubmit = (event) => {
        event.preventDefault();
        if(id){
            updateRoutine();
        } else {
            addRoutine();
        }
    }

    const handleChange = (event) => {
        const newRoutine = {...routine};
        newRoutine[event.target.name] = event.target.value;
        setRoutine(newRoutine);
    }

    const addRoutine = () => {
        const init = {
            method: 'POST',
            headers: {
                'content-Type': 'application/json',
                Authorization: User
            },
            body: JSON.stringify(routine)
        };

        fetch(url, init)
        .then(response => {
            if(response.status === 201 || response.status === 403 || response.status === 400){
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        }).then(data => {
            if(data.routine_id){
                navigate('/routines');
            } else {
                setErrors(data);
            }
        }).catch(console.log);
    }

    const updateRoutine = () => {
        routine.routine_id = id;
        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: User
            },
            body: JSON.stringify(routine)
        };

        fetch(`${url}/${id}`, init)
        .then(response => {
            if(response.status === 204){
                return null;
            } else if(response.status === 403 || response.status === 400){
                return response.json();
            } else{
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        }).then(data => {
            if(!data){
                navigate('/routines');
            } else {
                setErrors(data);
            }
        }).catch(console.log);
    }

    const toggleWorkout = (workout) =>{
        let newRoutineWorkouts = routineWorkouts;
        if(newRoutineWorkouts.includes(workout)){
            newRoutineWorkouts.splice(newRoutineWorkouts.indexOf(workout), 1);
        } else {
            newRoutineWorkouts.push(workout);
        }

        setRoutineWorkouts(newRoutineWorkouts);

        const newRoutine = {...routine};
        newRoutine['workouts'] = newRoutineWorkouts;
        setRoutine(newRoutine);
    }

    return (<>
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
                    <Form.Control type="text" name="routine_name"/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="routine_description" onChange={handleChange}>
                    <Form.Label>Description</Form.Label>
                    <Form.Control type="text" name="routine_description"/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="routine_duration" onChange={handleChange}>
                    <Form.Label>Duration (Min)</Form.Label>
                    <Form.Control type="number" name="routine_duration"/>
                </Form.Group>
                <Form.Select controlId="routine_difficulty" onChange={handleChange}>
                    <option value="">Select Difficulty</option>
                    <option value="Easy">Easy</option>
                    <option value="Medium">Medium</option>
                    <option value="Hard">Hard</option>
                </Form.Select>
                <Form.Group className="mb-3" controlId="routine_author_name" onChange={handleChange}>
                    <Form.Label>Your Name</Form.Label>
                    <Form.Control type="text" name="routine_author_name"/>
                </Form.Group>
                <Dropdown>
                    <Dropdown.Toggle variant="success" id="dropdown-basic">Select Workouts</Dropdown.Toggle>
                    <Dropdown.Menu>
                        {workouts.map((workout) => (
                            <Dropdown.Item key={workout}
                            onClick={() => toggleWorkout(workout)}
                            active={routineWorkouts.includes(workout)}
                            >{workout.name}</Dropdown.Item>
                        ))}
                    </Dropdown.Menu>
                </Dropdown>
                <Button variant="success" type="submit">
                    {id > 0 ? 'Update Routine' : 'Add Routine'}
                </Button>
            </Form>
        </Container>
    </> )
}

export default RoutineForm;