import { useEffect, useState } from "react"
import { Container, Row, Col, Form, Button, Nav } from "react-bootstrap"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'

const Workouts = () => {
    const [search, setSearch] = useState('')
    const [filter, setFilter] = useState('')
    const [workouts, setWorkouts] = useState([])

    // TODO: configure api path to GET muscle groups, and dynamically create options
    // const [muscleGroups, setMuscleGroups] = useState()


    useEffect(() => {
        getAllWorkouts()
        // makeCards(workouts)
    }, [])

    useEffect(() => { filterWorkouts() }, [filter])

    const getAllWorkouts = () => {
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
        // makeCards(workouts)
    }

    const renderCards = ()=>{
        if(workouts.length === 0){
            return <h4>Loading...</h4>
        } else{
            return filterWorkouts().map((workout) => {
                return (
                    <Row key={workout.id}>
                        <Col className="workout-name-col">
                            <Nav.Link href={`/workouts/${workout.id}`}>
                                <h4 className="workout-name">
                                    {workout.name}
                                </h4>
                            </Nav.Link>
                        </Col>
                        <Col className="workout-mg-col">
                            <p className="workout-mg">{workout.muscleGroup}</p>
                        </Col>
                        <Col className="workout-muscle-col">
                            <p className="workout-muscle">{workout.muscle}</p>
                        </Col>
                        <Col className="workout-duration-col">
                            <p className="workout-duration">{workout.duration}</p>
                        </Col>
                    </Row>
                )
            })
        }
    }

    const handleChange = (event) => {
        setSearch(event.target.value)
    }

    const handleFilterChange = (event) => {
        setFilter(event.target.value)
    }

    const searchForWorkouts = () => {
        fetch(`http://localhost:8080/api/workout/search/${search}`)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then(data => {
                setWorkouts(data)
                filterWorkouts()
            }
            )
            .catch(console.log)
    }

    const filterWorkouts = () => {
        if (filter != '') {
            return workouts.filter(workout => workout.muscleGroup === filter)
        }
        else {
            return workouts
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        // console.log(search, filter)
        searchForWorkouts()
    }


    return (
        <Container>
            <Row>
                <Col>
                    <h2 className="workout-title">Workouts</h2>
                </Col>
                <Col>
                    <Container className="searchbar">
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3" controlId='workoutName' onChange={handleChange}>
                                <Form.Control type="text" placeholder="Workout Name" />
                                <Form.Text className="text-muted">Search by Workout Name</Form.Text>
                            </Form.Group>
                            <Form.Select aria-label="workout muscle group" onChange={handleFilterChange}>
                                <option value="">Select Muscle Group</option>
                                <option value="arm">Arms</option>
                                <option value="buttock">Buttocks</option>
                                <option value="chest">Chest</option>
                                <option value="core">Core</option>
                                <option value="leg">Legs</option>
                                <option value="lower back">Lower Back</option>
                                <option value="shoulder">Shoulders</option>
                                <option value="upper back">Upper Back</option>
                            </Form.Select>
                            <Button variant="secondary" type="submit">
                                <FontAwesomeIcon icon={faMagnifyingGlass} />
                            </Button>
                        </Form>
                    </Container>
                </Col>
            </Row>
            <Row>
                { //TODO: Fix cards not rerendering when workouts is updated.
                renderCards()
                }
            </Row>
        </Container>
    )
}

export default Workouts