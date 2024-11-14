import { useState, useContext, useEffect } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"
import Goals from "./Goals"
import UserContext from '../context/UserContext'
import { jwtDecode } from "jwt-decode"

const Dashboard = ()=>{
    const [logs,setLogs] = useState([])
    const [routines, setRoutines] = useState([])

    const {user} = useContext(UserContext)
    let isAdmin

    useEffect(()=>{
        try{
            console.log(user)
            console.log(jwtDecode(user.jwt_token))

            isAdmin = jwtDecode(user.jwt_token).authorities.includes("ADMIN")
        }
        catch{
            console.log('User is null')
        }
    },[])
   
    return (
        <Container className="dashboard-container">
            <Row className="goals">
                <Goals />

            </Row>
            <Row className="logs-routines">
                <Col className="logs">

                </Col>

            </Row>

        </Container>
    )
}

export default Dashboard