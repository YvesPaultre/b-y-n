import { useState } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"

const Dashboard = ()=>{
    const [logs,setLogs] = useState([])
    const [routines, setRoutines] = useState([])
    
    return (
        <Container className="dashboard-container">
            <Form>
                <Form.Check type='checkbox' label='Hide Completed'/>
            </Form>
            <Row className="goals">

            </Row>
            <Row className="logs-routines">
                <Col className="logs">

                </Col>

            </Row>

        </Container>
    )
}

export default Dashboard