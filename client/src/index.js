import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";

import Home from "./components/Home";
import ErrorPage from "./components/ErrorPage";
import Root from "./components/Root";
import Workouts from "./components/Workouts";
import Workout from "./components/Workout";
import Routines from "./components/Routines";
import Routine from "./components/Routine";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import UserContext from './context/UserContext'

import { useContext, useState } from "react";

import {
  createBrowserRouter,
  RouterProvider,
  Route,
  createRoutesFromElements,
} from "react-router-dom";

// const [user, setUser] = useState(null);

// const login = (userData) => {
//   setUser(userData);
// };

// const logout = () => {
//   setUser(null);
// };



import "bootstrap/dist/css/bootstrap.min.css";
const router = createBrowserRouter(
  createRoutesFromElements(
   
      <Route path="/" element={<Root />} errorElement={<ErrorPage />}>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/workouts" element={<Workouts />} />
        <Route path="/workouts/:id" element={<Workout />} />
        <Route path="/routines" element={<Routines />} />
        <Route path="/routines/:id" element={<Routine />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Route>
    
  )
);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
