import { useState, useContext, useEffect } from "react"
import { Container, Row, Col, Form, Nav } from "react-bootstrap"
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
        // console.log(user.sub)
        // fetch(`http://localhost:8080/api/user/username/${user.sub}`)
        // .then(response=>{
        //     if (response.status === 200) {
        //         return response.json();
        //     } else {
        //         return Promise.reject(`Unexpected Status Code: ${response.status}`);
        //     }
        // })
        // .then(data=>console.log(data))
        fetch("http://localhost:8080/api/routine")
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected Status Code: ${response.status}`);
                }
            })
            .then(data => {
                setRoutines(data.filter(routine=>routine.routine_author_name === user.sub))})
            .catch(console.log)

    }

    const renderRoutines = ()=>{
        if(routines.length===0){
            return <h4>You have created no Routines</h4>
        } else{
            return routines.map((routine) => {
                return (
                    <Row key={routine.routine_id}>
                        <Col className="routine-name-col">
                            <Nav.Link className="routine-name" href={`routines/${routine.routine_id}`}>
                                {routine.routine_name}
                            </Nav.Link>
                        </Col>
                        <Col className="routine-author-col">
                            <p className="routine-author">{routine.routine_author}</p>
                        </Col>
                        <Col className="routine-diff-col">
                            <p className="routine-diff">{routine.difficulty}</p>
                        </Col>
                        <Col className="routine-duration-col">
                            <p className="routine-duration">{routine.routine_duration}</p>
                        </Col>
                    </Row>
                )
            })
        }

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
                {
                    renderRoutines()
                }


            </Container>

        </Container>
    )
}

export default Dashboard