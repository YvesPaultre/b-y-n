import { useState, useContext } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"
import UserContext from '../context/UserContext'
import Goals from "./Goals"

const Dashboard = ()=>{
    const [logs,setLogs] = useState([])
    const [routines, setRoutines] = useState([])

   
    return (
        <Container className="dashboard-container">
            <Row className="goals">
                <Goals user={useContext(UserContext)} />

            </Row>
            <Row className="logs-routines">
                <Col className="logs">

                </Col>

            </Row>

        </Container>
    )
}

export default Dashboard