import { Container } from "react-bootstrap"

const Workout = (workout) => {
    console.log(workout)
    return (
        <Container>
            {workout.workout_name}
        </Container>
    )
}

export default Workout