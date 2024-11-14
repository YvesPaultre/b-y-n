import { useState, useContext } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"
import UserContext from '../context/UserContext'


const Goals = () => {
    const [compltedFilter, setCompletedFilter] = useState(false)
    const handleSwitchChange = () => {
        setCompletedFilter(!compltedFilter)
    }
    
    const { user } = useContext(UserContext)
    // console.log(user)

    
    return (
        <>
            <h3>
                Goals
            </h3>
            <Form>
                <Form.Check type='switch' label='Hide Completed' onChange={handleSwitchChange} />
            </Form>
        </>
    )
}

export default Goals