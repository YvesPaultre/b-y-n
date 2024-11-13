import { useEffect, useState } from "react"
import { Container, Row, Col, Form, Button } from "react-bootstrap"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'
import Workout from "./Workout"

const exampleWorkouts = [
    {
        "workout_id": 1,
        "workout_name": "test",
        "workout_description": "testing",
        "workout_duration": 10,
        "muscle_name": "bicep",
        "muscle_group": "arm"
    },
    {
        "workout_id": 2,
        "workout_name": "test2",
        "workout_description": "another testing",
        "workout_duration": 20,
        "muscle_name": "calf",
        "muscle_group": "leg"
    }
]

const Workouts = () => {
    // TODO: move searchbar into its own component to reuse with Routines
    const [search, setSearch] = useState('')
    const [filter, setFilter] = useState('')
    const [workouts, setWorkouts] = useState([])
    const [filteredWorkouts, setFilteredWorkouts] = useState([])
    const [workoutCards, setWorkoutCards] = useState([])

    // TODO: configure api path to GET muscle groups, and dynamically create options
    // const [muscleGroups, setMuscleGroups] = useState()
    // let workoutCards


    useEffect(() => {
        getAllWorkouts()
        // setWorkouts(exampleWorkouts)
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
        // setWorkouts(exampleWorkouts)
        makeCards(workouts)
    }

    const makeCards = (data) => {
        // setWorkoutCards(data.map((workout) => <Workout key={workout.workout_id} workout={workout} />))
        setWorkoutCards(data.map((workout) => {
            return (
                //TODO: update name to link to individual page
                <Row key={workout.id}>
                    <Col className="workout-name-col">
                        <h4 className="workout-name">
                            {workout.name}
                        </h4>
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
        }))
    }

    const handleChange = (event) => {
        setSearch(event.target.value)
    }

    const handleFilterChange = (event) => {
        setFilter(event.target.value)
    }


    // Where filter and search, get mg workouts, then filter by name


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
            // setFilteredWorkouts(workouts.filter(workout => workout.muscleGroup === filter))
            makeCards(workouts.filter(workout => workout.muscleGroup === filter))
        }
        else {
            makeCards(workouts)
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log(search, filter)
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
                workoutCards}
            </Row>
            <Button onClick={() => console.log(workoutCards)}>Debugging</Button>
            <Button onClick={() => console.log(workouts)}>Workouts</Button>
        </Container>
    )
}

export default Workouts