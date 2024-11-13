import { useEffect, useState } from "react"
import { Container, Row, Col } from "react-bootstrap"
import { useParams } from "react-router-dom"

const Workout = () => {
    const { id } = useParams()
    const [workout, setWorkout] = useState({})

    useEffect(() => {
        fetch(`http://localhost:8080/api/workout/id/${id}`)
            .then(response => {
                if (response.status === 200) { return response.json() }
                else { return Promise.reject(`Unexpected Status Code: ${response.status}`) }
            })
            .then(data => { setWorkout(data) })
            .catch(console.log)
    }, [])

    return (
        <Container>
            <Row>
                <h3 className="workout-name">{workout.name}</h3>
            </Row>
            <Row className="workout-mg-muscle-duration">
                <Col>
                    <Col>
                        <h5 className="workout-mg">{workout.muscleGroup}</h5>
                    </Col>
                    <Col>
                        <h5 className="workout-muscle">{workout.muscle}</h5>
                    </Col>
                </Col>
                <Col>
                    <h5 className="workout-duration">{workout.duration}</h5>
                </Col>
            </Row>
            <Row children='workout-description'>
                <p>{workout.description}</p>
            </Row>
        </Container>
    )
}

export default Workout