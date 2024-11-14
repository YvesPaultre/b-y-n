import { useEffect, useState } from "react"
import { Container, Row, Col, Form, Button, Nav } from "react-bootstrap"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'

const Routines = () => {
    const [search, setSearch] = useState('')

    // TODO: set up UserController to get all trainers
    // const [trainerFilter, setTrainerFilter] = useState('') 
    const [diffFilter, setDiffFilter] = useState('')
    const [routines, setRoutines] = useState([])
    const [filteredRoutines, setFilteredRoutines] = useState([])
    const [routineCards, setroutineCards] = useState([])


    useEffect(() => {
        getAllRoutines()
    }, [])

    useEffect(() => { filterRoutines() }, [diffFilter])

    const getAllRoutines = () => {
        fetch("http://localhost:8080/api/routine")
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then(data => setRoutines(data))
            .catch(console.log)
        makeCards(routines)
    }


    // copy implementation from workouts to fix cards not loading on page load
    
    const makeCards = (data) => {
        setroutineCards(data.map((routine) => {
            return (
                <Row key={routine.routine_id}>
                    <Col className="routine-name-col">
                        <Nav.Link className="routine-name" href={`routines/${routine.routine_id}`}>
                            {routine.routine_name}
                        </Nav.Link>
                    </Col>
                    <Col className="routine-author-col">
                        <p className="routine-author">{routine.routine_author}</p>
                    </Col>
                    <Col className="routine-diff-col">
                        <p className="routine-diff">{routine.difficulty}</p>
                    </Col>
                    <Col className="routine-duration-col">
                        <p className="routine-duration">{routine.routine_duration}</p>
                    </Col>
                </Row>
            )
        }))
    }

    const handleChange = (event) => {
        setSearch(event.target.value)
    }

    const handleDiffFilterChange = (event) => {
        setDiffFilter(event.target.value)
    }

    const searchForRoutines = () => {
        fetch(`http://localhost:8080/api/routine/search/${search}`)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then(data => {
                setRoutines(data)
                filterRoutines()
            }
        )
            .catch(console.log)
    }

    const filterRoutines = () => {
        //TODO: refactor to filter by trainer name too

        if (diffFilter != '') {
            // setFilteredRoutines(routines.filter(routine => routine.difficulty === diffFilter))
            makeCards(routines.filter(routine => routine.difficulty === diffFilter))
        }
        else {
            makeCards(routines)
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log(search, diffFilter)
        searchForRoutines()
    }


    return (
        <Container>
            <Row>
                <Col>
                    <h2 className="routine-title">routines</h2>
                </Col>
                <Col>
                    <Container className="searchbar">
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3" controlId='routineName' onChange={handleChange}>
                                <Form.Control type="text" placeholder="routine Name" />
                                <Form.Text className="text-muted">Search by Routine Name</Form.Text>
                            </Form.Group>
                            <Form.Select aria-label="routine difficulty" onChange={handleDiffFilterChange}>
                                <option value="">Select Difficulty</option>
                                <option value="Easy">Easy</option>
                                <option value="Medium">Medium</option>
                                <option value="Hard">Hard</option>
                            </Form.Select>
                            <Button variant="secondary" type="submit">
                                <FontAwesomeIcon icon={faMagnifyingGlass} />
                            </Button>
                        </Form>
                    </Container>
                </Col>
            </Row>
            <Row>
                {routineCards}
            </Row>
            <Button onClick={() => console.log(routines)}>routines</Button> 
        </Container>
    )
}

export default Routines