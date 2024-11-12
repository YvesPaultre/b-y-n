import { Container, Form, Button } from "react-bootstrap"

const Login = ()=>{
    // TODO: Add Error Handling
    
    const handleSubmit = (event)=>{
        event.preventDefault()
        console.log('Submitted form')
    }
    return (
        <Container>
            <h2 className="login-title">Log In</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group className='mb-3' controlId="username">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="text"/>
                </Form.Group>
                <Form.Group className='mb-3' controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" />
                </Form.Group>
                <Button variant="primary" type="submit">
                    Log In
                </Button>
                <Button variant="secondary" href="/register">
                    Register
                </Button>
            </Form>
        </Container>
    )
}

export default Login