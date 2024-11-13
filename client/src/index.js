import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import Home from './components/Home';
import ErrorPage from './components/ErrorPage';
import Root from './components/Root';
import Workouts from './components/Workouts'
import Workout from './components/Workout';
import Routines from './components/Routines'
import Routine from './components/Routine'
import Login from './components/Login';
import Register from './components/Register'
import Dashboard from './components/Dashboard'

import reportWebVitals from './reportWebVitals';
import {
  createBrowserRouter,
  RouterProvider,
  Route,
  createRoutesFromElements
} from "react-router-dom"

import 'bootstrap/dist/css/bootstrap.min.css'


const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<Root />} errorElement={<ErrorPage />}>
      <Route path="/" element={<Home />} />
      <Route path="/home" element={<Home />} />
      <Route path="/workouts" element={<Workouts />}/>
      <Route path="/workouts/:id" element={<Workout />} />
      <Route path="/routines" element={<Routines />}/>
      <Route path="/routines/:id" element={<Routine />} />
      <Route path="/register" element={<Register />} />
      <Route path="/login" element={<Login />} />
      <Route path="/dashboard" element={<Dashboard />} />
    </ Route>

  )
)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
