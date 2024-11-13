import { useEffect, useState } from "react"
import { Container, Row, Col, Form, Button } from "react-bootstrap"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'

const exampleRoutines = [
    {
        "routine_id":1,
        "routine_name":"testing",
        "routine_description": "a run of the mill test",
        "difficulty": "easy",
        "routine_duration":60,
        "routine_author": "testerino"
    },
    {
        "routine_id": 2,
        "routine_name": "test 2",
        "routine_description": "a harder test",
        "difficulty": "medium",
        "routine_duration": 90,
        "routine_author": "McTesterton"
    },
    {
        "routine_id": 3,
        "routine_name": "Third test",
        "routine_description": "The hardest Test",
        "difficulty": "hard",
        "routine_duration": 300,
        "routine_author": "Retset"
    }
]

const Routines = () => {
    const [search, setSearch] = useState('')
    // TODO: set up UserController to get all trainers
    // const [trainerFilter, setTrainerFilter] = useState('') 
    const [diffFilter, setDiffFilter] = useState('')
    const [routines, setRoutines] = useState([])
    const [filteredRoutines, setFilteredRoutines] = useState([])
    const [routineCards, setroutineCards] = useState([])

    // TODO: configure api path to GET muscle groups, and dynamically create options
    // const [muscleGroups, setMuscleGroups] = useState()
    // let routineCards


    useEffect(() => {
        getAllRoutines()
        // setRoutines(exampleRoutines)
    }, [])

    useEffect(() => { filterRoutines() }, [diffFilter])

    const getAllRoutines = () => {
        // fetch("http://localhost:8080/api/routine")
        //     .then(response => {
        //         if (response.status === 200) {
        //             return response.json();
        //         } else {
        //             return Promise.reject(`Unexpected Status Code: ${response.status}`);
        //         }
        //     })
        //     .then(data => setRoutines(data))
        //     .catch(console.log)
        setRoutines(exampleRoutines)
        makeCards(routines)
    }

    const makeCards = (data) => {
        // setroutineCards(data.map((routine) => <routine key={routine.routine_id} routine={routine} />))
        setroutineCards(data.map((routine) => {
            return (
                <Row key={routine.routine_id}>
                    <Col className="routine-name-col">
                        <h4 className="routine-name">
                            {routine.routine_name}
                        </h4>
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


    // Where filter and search, get mg routines, then filter by name


    const searchForRoutines = () => {
        // fetch(`http://localhost:8080/api/routine/${search}`)
        //     .then(response => {
        //         if (response.status === 200) {
        //             return response.json();
        //         } else {
        //             return Promise.reject(`Unexpected Status Code: ${response.status}`);
        //         }
        //     })
        //     .then(data => setRoutines(filterRoutines(data)))
        //     .catch(console.log)
        filterRoutines()
    }

    const filterRoutines = () => {
        //TODO: refactor to filter by trainer name too

        if (diffFilter != '') {
            setFilteredRoutines(routines.filter(routine => routine.difficulty === diffFilter))
            makeCards(filteredRoutines)
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
                                <option value="easy">Easy</option>
                                <option value="medium">Medium</option>
                                <option value="hard">Hard</option>
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
            {/* <Button onClick={() => console.log(routineCards)}>Debugging</Button>
            <Button onClick={() => console.log(routines)}>routines</Button> */}
        </Container>
    )
}

export default Routines