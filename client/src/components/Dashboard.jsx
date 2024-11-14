import { useState, useContext, useEffect } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"
import Goals from "./Goals"
import UserContext from '../context/UserContext'
// import UserContext from './Root'
import { jwtDecode } from "jwt-decode"

const Dashboard = ()=>{
    const [logs,setLogs] = useState([])
    const [routines, setRoutines] = useState([])

    // const {user} = useContext(UserContext)
    const user = jwtDecode(localStorage.getItem('user'))
    let isAdmin

    useEffect(()=>{
        // console.log(localStorage.getItem('user'))
        try{
         getRoutines()   
        }
        catch{
            console.log('User is null')
        }
    },[])

    const getRoutines = () =>{
        console.log(user.sub)
        fetch(`http://localhost:8080/api/user/username/${user.sub}`)
        .then(response=>{
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected Status Code: ${response.status}`);
            }
        })
        // .then(data=>console.log(data))

    }

    const renderRoutines = ()=>{

    }
   
    return (
        <Container className="dashboard-container">
            {/* <Row className="goals">
                <Goals />

            </Row>
            <Row className="logs-routines">
                <Col className="logs">

                </Col>

            </Row> */}
            <Container className="Routines">
                <h3 className="dash-routines-title">
                    Routines
                </h3>


            </Container>

        </Container>
    )
}

export default Dashboard