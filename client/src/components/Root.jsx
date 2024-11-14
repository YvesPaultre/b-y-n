import Navbar from "./Navbar"
import { Outlet, useNavigate, useOutletContext } from 'react-router-dom'
import { useState, useContext, createContext } from "react"
import { UserProvider} from '../context/UserContext'


import { Stack } from "react-bootstrap"

// export const UserContext = createContext(null)

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
        <UserProvider>
            <Stack fluid='true'>
                <Navbar openSetter={handleSidebarSet} />
                <div id='detail'>
                    <Outlet/>
                    {/* <Outlet context={[user, login, logout]}/> */}
                </div>
            </Stack>
        </UserProvider>
    )
}

export default Root