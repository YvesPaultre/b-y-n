import { useState } from "react"
import { Container, Row, Col, Form } from "react-bootstrap"

const Goals = (user) => {
    const [compltedFilter, setCompletedFilter] = useState(false)
    const handleSwitchChange = () => {
        setCompletedFilter(!compltedFilter)
    }

    
    return (
        <>
            <Form>
                <Form.Check type='switch' label='Hide Completed' onChange={handleSwitchChange} />
            </Form>
            Goals
        </>
    )
}

export default Goals