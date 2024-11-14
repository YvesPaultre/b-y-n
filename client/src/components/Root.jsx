import Navbar from "./Navbar"
import { Outlet } from 'react-router-dom'
import { useState, useContext } from "react"
import UserContext from '../context/UserContext'


import { Stack } from "react-bootstrap"

const Root = () => {

    const [user, setUser] = useState(null);

    const login = (userData) => {
        setUser(userData);
        console.log(user)
    };

    const logout = () => {
        setUser(null);
    };


    const [isOpen, setIsOpen] = useState(false)

    const handleSidebarSet = () => {
        setIsOpen(!isOpen)
    }

    return (
        <Stack fluid>
            <UserContext.Provider value={{ user, login, logout }}>
            <Navbar openSetter={handleSidebarSet} />
            <div id='detail'>
                <Outlet />
            </div>
            </UserContext.Provider>
        </Stack>
    )
}

export default Root