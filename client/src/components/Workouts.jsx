import { useEffect, useState } from "react"
import { Container, Row, Col, Form, Button } from "react-bootstrap"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'
import Workout from "./Workout"

const exampleWorkouts = [
    {
        "workout_id":1,
        "workout_name":"test",
        "workout_description":"testing",
        "workout_duration":10,
        "muscle_name":"bicep",
        "muscle_group":"arm"
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
    const [filter, setFilter] = useState()
    const [workouts, setWorkouts] = useState([])

    // TODO: configure api path to GET muscle groups, and dynamically create options
    // const [muscleGroups, setMuscleGroups] = useState()
    let workoutCards


    useEffect(() => {
        getAllWorkouts()
        makeCards()
    }, [])

    useEffect(()=>{filterWorkouts(workouts)},[filter])

    const getAllWorkouts = () => {
        // fetch("http://localhost:8080/api/workout")
        //     .then(response => {
        //         if (response.status === 200) {
        //             return response.json();
        //         } else {
        //             return Promise.reject(`Unexpected Status Code: ${response.status}`);
        //         }
        //     })
        //     .then(data => setWorkouts(data))
        //     .catch(console.log)
        setWorkouts(exampleWorkouts)
        makeCards()
    }

    const makeCards = ()=>{
        workoutCards = workouts.map((workout) => <Workout key={workout.workout_id} workout={workout} />)
        // console.log(workoutCards)
    }

    const handleChange = (event) => {
        setSearch(event.target.value)
    }

    const handleFilterChange = (event) => {
        setFilter(event.target.value)
    }


    // Where filter and search, get mg workouts, then filter by name


    const searchForWorkouts = () => {
        // fetch(`http://localhost:8080/api/workout/${search}`)
        //     .then(response => {
        //         if (response.status === 200) {
        //             return response.json();
        //         } else {
        //             return Promise.reject(`Unexpected Status Code: ${response.status}`);
        //         }
        //     })
        //     .then(data => setWorkouts(filterWorkouts(data)))
        //     .catch(console.log)
        filterWorkouts(exampleWorkouts)
    }

    const filterWorkouts = (data) => {
        // console.log(data)
        if(filter){
            data = data.filter(workout => workout.mg_name == filter)
        }
        setWorkouts(data)        
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
                {workoutCards}
            </Row>
            <Button onClick={()=>console.log(workoutCards)}>Debugging</Button>
        </Container>
    )
}

export default Workouts