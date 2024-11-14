import Container from 'react-bootstrap/Container'
import { Stack } from 'react-bootstrap'
import Nav from 'react-bootstrap/Nav'


const Home = ()=>{

    return(
        <Container id='home-container'>
            <Stack>
                <h1 className="home-banner">BYN Fitness</h1>
                <h4 className="tagline">Put a catchy tagline here!</h4>
                <Container className="about">
                    <p className="about-text">
                        Lorem ipsum dolor, sit amet consectetur adipisicing elit. Harum accusantium suscipit, quidem ipsa, autem molestias tempore neque enim iusto molestiae iure animi modi, doloremque amet voluptatum qui pariatur aut alias!
                    </p>
                </Container>
                {/* <Nav variant='pills'>
                    <Nav.Item>
                        <Nav.Link href='/register'>Sign Up</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link href='/login'>Log In</Nav.Link>
                    </Nav.Item>
                </Nav> */}
            </Stack>


        </Container>
    )
}

export default Home