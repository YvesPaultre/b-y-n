import Navbar from "./Navbar"
import { Outlet } from 'react-router-dom'
import { useState } from "react"

import { Stack } from "react-bootstrap"

const Root = () => {
    const [isOpen, setIsOpen] = useState(false)

    const handleSidebarSet = () => {
        setIsOpen(!isOpen)
    }

    return (
        <Stack fluid>
            <Navbar openSetter={handleSidebarSet} />
            <div id='detail'>
                <Outlet />
            </div>
        </Stack>
    )
}

export default Root