import React from "react"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
import { useContext } from "react";
import UserContext from '../context/UserContext'


const NavBar = ()=>{
    // TODO: Dynamically Render Signup or Log out
    const currentUser = useContext(UserContext)

    const signUp = <Nav.Link href="/register">
        <h4 className='navLink'>Sign Up</h4></Nav.Link>

    const logout = <Nav.Link href="/logout">
        <h4 className='navLink'>Log Out</h4></Nav.Link>

    return(
        <Container id="narbar-container">
            <Navbar expand='sm' className="mb-3">
                <Container className="navBar">
                    <Navbar.Brand href='/'>
                    <h3 className="brand">BYN Fitness</h3>
                    </Navbar.Brand>
                    <Navbar.Toggle 
                        aria-controls={`offcanvasNavbar-expand-'sm'`} 
                        className="navBar-toggle" />
                    <Navbar.Offcanvas
                        id={`offcanvasNavbar-expand-'sm'`}
                        aria-labelledby={`offcanvasNavbarLabel-expand-'sm'`}
                        placement="end"
                    >
                        <Offcanvas.Header closeButton>
                            <Offcanvas.Title id={`offcanvasNavbarLabel-expand-'sm'`}>
                                BYN Fitness
                            </Offcanvas.Title>
                        </Offcanvas.Header>
                        <Offcanvas.Body>
                            <Nav className="justify-content-end flex-grow-1 pe-3">
                                <Nav.Link href="/" >
                                    <h4 className='navLink'>Home</h4>
                                </Nav.Link>
                                <Nav.Link href="/workouts">
                                    <h4 className='navLink'>Workouts</h4></Nav.Link>
                                <Nav.Link href="/routines">
                                    <h4 className='navLink'>Routines</h4></Nav.Link>
                                {currentUser ? logout : signUp }
                            </Nav>
                        </Offcanvas.Body>
                    </Navbar.Offcanvas>
                </Container>
            </Navbar>

        </Container>
    )

}

export default NavBar