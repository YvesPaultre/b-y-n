import Navbar from "./Navbar"
import { Outlet, useNavigate, useOutletContext } from 'react-router-dom'
import { useState, useContext } from "react"
import UserContext from '../context/UserContext'


import { Stack } from "react-bootstrap"

const Root = () => {
    const navigate = useNavigate()

    const [user, setUser] = useState(null);

    const login = (userData) => {
        setUser(userData);
    };

    const logout = () => {
        setUser(null);
        navigate('/')
    };


    const [isOpen, setIsOpen] = useState(false)

    const handleSidebarSet = () => {
        setIsOpen(!isOpen)
    }

    return (
        <UserContext.Provider value={{ user, login, logout }}>
            <Stack fluid='true'>
                <Navbar openSetter={handleSidebarSet} />
                <div id='detail'>
                    <Outlet/>
                    {/* <Outlet context={[user, login, logout]}/> */}
                </div>
            </Stack>
        </UserContext.Provider>
    )
}

export default Root