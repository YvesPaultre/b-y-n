
// // Update with authentication token from authenticate path
// const User = createContext(null);

// export default User;

// UserContext.js
import React, { createContext, useState, useEffect } from 'react';

// Create the context
const UserContext = createContext(null);

// Create the provider component
export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(() => {
        const savedUser = localStorage.getItem('user');
        return savedUser  ? JSON.parse(savedUser) : null;
    })

    // useEffect(() => {
    //     console.log("UserContext setting local user")
    //         const savedUser = localStorage.getItem("user");
    //         console.log(savedUser)
    //         if (savedUser) {
    //             console.log('Saving User')
    //             setUser(JSON.parse(savedUser)); // Set user data into state
    //         }
    // }, []);

    // useEffect(() => {
    //     setUser(localStorage.getItem('user'))
    //     if (user === null) {
    //         // Trigger reload if user is not found
    //         window.location.reload();
    //     }
    // }, []);

    // Function to log in and set the user data
    const login = async (userData) => {
        // setUser(userData);
        await setUser(localStorage.setItem('user', JSON.stringify(userData)))
    };

    // Function to log out and clear the user data
    const logout = () => {
        // setUser(null);
        localStorage.removeItem('user')
        setUser(null)
    };

    return (
        <UserContext.Provider value={{ user, login, logout }}>
            {children} {/* This renders all child components wrapped in UserProvider */}
        </UserContext.Provider>
    );
};

export default UserContext