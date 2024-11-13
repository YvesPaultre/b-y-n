import { useEffect, useState } from "react"
import { Container, Row, Col } from "react-bootstrap"
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
                <h3 className="routine-name">{routine.name}</h3>
            </Row>
            <Row className="routine-mg-muscle-duration">
                <Col>
                    <Col>
                        <h5 className="routine-mg">{routine.muscleGroup}</h5>
                    </Col>
                    <Col>
                        <h5 className="routine-muscle">{routine.muscle}</h5>
                    </Col>
                </Col>
                <Col>
                    <h5 className="routine-duration">{routine.duration}</h5>
                </Col>
            </Row>
            <Row children='routine-description'>
                <p>{routine.description}</p>
            </Row>
        </Container>
    )
}

export default Routine