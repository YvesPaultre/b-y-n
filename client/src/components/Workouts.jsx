import { useEffect, useState } from "react"
import { Container, Row, Col, Form, Button } from "react-bootstrap"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'

const Workouts = () => {
    // TODO: move searchbar into its own component to reuse with Routines
    const [search, setSearch] = useState()
    const [filter, setFilter] = useState()
    const [workouts, setWorkouts] = useState()
    
    // TODO: configure api path to GET muscle groups, and dynamically create options
    // const [muscleGroups, setMuscleGroups] = useState()

    useEffect(()=>{
        fetch("http://localhost:8080/api/workout")
            .then.then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then(data => setWorkouts(data))
            .catch(console.log)
    })

    const handleChange = (event) => {
        setSearch(event.target.value)
    }

    const handleFilterChange = (event) => {
        setFilter(event.target.value)
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log(search, filter)
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

            </Row>
        </Container>
    )
}

export default Workouts