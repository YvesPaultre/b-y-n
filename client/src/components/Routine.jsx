import { useEffect, useState } from "react"
import { Container, Row, Col, ListGroup } from "react-bootstrap"
import { useParams } from "react-router-dom"

const Routine = () => {
    const { id } = useParams()
    const [routine, setRoutine] = useState({})

    useEffect(() => {
        fetch(`http://localhost:8080/api/routine/id/${id}`)
            .then(response => {
                if (response.status === 200) { return response.json() }
                else { return Promise.reject(`Unexpected Status Code: ${response.status}`) }
            })
            .then(data => { setRoutine(data) })
            .catch(console.log)
    }, [])

    console.log(routine)
    return (
        <Container>
            <Row>
                <h3 className="routine-name">{routine.routine_name}</h3>
            </Row>
            <Row className="routine-auth-diff-duration">
                <Col>
                    <h5 className="routine-auth">{routine.routine_author_name}</h5>
                </Col>
                <Col>
                    <h5 className="routine-diff">{routine.difficulty}</h5>
                </Col>
                <Col>
                    <h5 className="routine-duration">{routine.routine_duration}</h5>
                </Col>
            </Row>
            <Row children='routine-description-workouts'>
                <Col>
                <p>{routine.routine_description}</p>
                </Col>
                <Col>
                <h5 className="routine-workouts">Workouts</h5>
                    <ListGroup>
                        
                    </ListGroup>
                </Col>
            </Row>
        </Container>
    )
}

export default Routine