import { Container, Form, Button } from "react-bootstrap"

const Register = ()=>{
    const handleSubmit = (event) => {
        event.preventDefault()
        console.log('Submitted form')
    }
    return (
        <Container>
            <h2 className="login-title">Sign Up</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group className='mb-3' controlId="username">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="text" />
                </Form.Group>
                <Form.Group className='mb-3' controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" />
                </Form.Group>
                <Form.Group className='mb-3' controlId="passwordVerify">
                    <Form.Label>Verify Password</Form.Label>
                    <Form.Control type="password" />
                </Form.Group>
                <Button variant="primary" type="submit">
                    Register
                </Button>
                <Button variant="secondary" href="/login">
                    Sign In
                </Button>
            </Form>
        </Container>
    )
}

export default Register