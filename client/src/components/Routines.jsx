const Routines = () => {
    const [search, setSearch] = useState('')
    const [trainerFilter, setTrainerFilter] = useState('')
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

    useEffect(() => { filterRoutines() }, [filter])

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
                    <Col className="routine-mg-col">
                        <p className="routine-mg">{routine.muscle_group}</p>
                    </Col>
                    <Col className="routine-muscle-col">
                        <p className="routine-muscle">{routine.muscle_name}</p>
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

    const handleFilterChange = (event) => {
        setFilter(event.target.value)
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
        if (filter != '') {
            setFilteredRoutines(routines.filter(routine => routine.muscle_group === filter))
            makeCards(filteredRoutines)
        }
        else {
            makeCards(routines)
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        console.log(search, filter)
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
                                <Form.Text className="text-muted">Search by routine Name</Form.Text>
                            </Form.Group>
                            <Form.Select aria-label="routine muscle group" onChange={handleFilterChange}>
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
                {routineCards}
            </Row>
            <Button onClick={() => console.log(routineCards)}>Debugging</Button>
            <Button onClick={() => console.log(routines)}>routines</Button>
        </Container>
    )
}

export default Routines