import { useState, useContext } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"
import Goals from "./Goals"
import User from "../context/UserContext"

const Dashboard = ()=>{
    const [logs,setLogs] = useState([])
    const [routines, setRoutines] = useState([])

    console.log(User)

   
    return (
        <Container className="dashboard-container">
            <Row className="goals">
                <Goals user={useContext(User)} />

            </Row>
            <Row className="logs-routines">
                <Col className="logs">

                </Col>

            </Row>

        </Container>
    )
}

export default Dashboard