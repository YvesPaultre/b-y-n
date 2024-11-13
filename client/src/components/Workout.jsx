import { Container } from "react-bootstrap"

const Workout = (workout) => {
    console.log(workout)
    return (
        <Container>
            <p>
                {workout.workout_name}
                </p>
        </Container>
    )
}

export default Workout