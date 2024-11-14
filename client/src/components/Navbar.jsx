import React from "react"
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
import { useContext } from "react";
import UserContext from '../context/UserContext'
// import UserContext from './Root'

import { useOutletContext } from "react-router-dom";

const NavBar = () => {
    // TODO: Dynamically Render Signup or Log out
    const { user, logout } = useContext(UserContext)

    // const [user, logout] = useOutletContext()

    const loggedOut = <>
        <Nav.Link href="/login">
            <h4 className='navLink'>Log In</h4></Nav.Link>
        <Nav.Link href="/register">
            <h4 className='navLink'>Sign Up</h4></Nav.Link>
    </>

    const loggIn = <>
        <Nav.Link href="/dashboard">
            <h4 className='navLink'>Dashboard</h4></Nav.Link>

        <Nav.Link href="/" onClick={logout}>
            <h4 className='navLink'>Log Out</h4></Nav.Link>
    </>

    return (
        <Container id="narbar-container">
            <Navbar expand='sm' className="mb-3">
                <Container className="navBar">
                    <Navbar.Brand href={user  ? '/dashboard': '/'}>
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
                                {user  ? loggIn : loggedOut}
                            </Nav>
                        </Offcanvas.Body>
                    </Navbar.Offcanvas>
                </Container>
            </Navbar>

        </Container>
    )

}

export default NavBar