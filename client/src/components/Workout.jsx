import { useEffect } from "react"
import { Container } from "react-bootstrap"
import { useParams } from "react-router-dom"

const Workout = () => {
    const {id} = useParams()
    
    return (
        <Container>
            <p>
                {workout.workout_name}
                </p>
        </Container>
    )
}

export default Workout