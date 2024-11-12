import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import Home from './components/Home';
import ErrorPage from './components/ErrorPage';
import Root from './components/Root';
import Workouts from './components/Workouts'
import Routines from './components/Routines'


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
      <Route path="/routines" element={<Routines />}/>
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
