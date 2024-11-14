import { useState, useContext, useEffect } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"
import Goals from "./Goals"
import UserContext from '../context/UserContext'
// import UserContext from './Root'
import { jwtDecode } from "jwt-decode"

const Dashboard = ()=>{
    const [logs,setLogs] = useState([])
    const [routines, setRoutines] = useState([])

    const {user} = useContext(UserContext)
    let isAdmin

    useEffect(()=>{
        // console.log(localStorage.getItem('user'))
        try{
            console.log(jwtDecode(localStorage.getItem('user')))
            // console.log(jwtDecode(user))

            isAdmin = jwtDecode(localStorage.getItem('user')).authorities.includes("ADMIN")
            console.log(isAdmin)
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