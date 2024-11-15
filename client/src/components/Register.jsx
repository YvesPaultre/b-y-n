import { useState, useContext } from "react";
import {
  Container,
  Form,
  Button,
  ListGroup,
  Alert,
  ListGroupItem,
} from "react-bootstrap";

import UserContext from "../context/UserContext";
import { redirect, useNavigate } from "react-router-dom";

const USER_DEFAULT = {
  username: "",
  password: "",
};

let passwordVerify = "";

const Register = () => {
  const [credentials, setCredentials] = useState(USER_DEFAULT);
  const [errors, setErrors] = useState([]);
  const url = "http://localhost:8080/api/user/register";
  // const url = `${process.env.REACT_APP_AWS_SERVER_HOST_BASE_URL}/api/user/register`

  const {user, login} = useContext(UserContext)

  const navigate = useNavigate()


  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(credentials);

    if (credentials.password !== credentials.passwordVerify) {
      setErrors(["Passwords do not match."]);
      return;
    }

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(credentials),
    };

    fetch(url, init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {
        if (data.appUserId) {
          console.log(data.appUserId);
          login(data)
          //TODO: navigate to dashboard
          navigate('/dashboard')

        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };

  const handleChange = (event) => {
    const newCredentials = { ...credentials };
    newCredentials[event.target.name] = event.target.value;
    setCredentials(newCredentials);
  };

  return (
    <Container>
      <h2 className="login-title">Sign Up</h2>
      {errors.length > 0 && (
        <Alert key={errors}>
          <p>The following errors were found:</p>
          <ListGroup>
            {errors.map((error) => (
              <ListGroupItem key={error}>{error}</ListGroupItem>
            ))}
          </ListGroup>
        </Alert>
      )}
      <Form onSubmit={handleSubmit}>
        <Form.Group
          className="mb-3"
          controlId="username"
          onChange={handleChange}
        >
          <Form.Label>Username</Form.Label>
          <Form.Control type="text" name="username" />
        </Form.Group>
        <Form.Group className="mb-3" controlId="email" onChange={handleChange}>
          <Form.Label>Email</Form.Label>
          <Form.Control type="text" name="email" />
        </Form.Group>
        <Form.Group
          className="mb-3"
          controlId="password"
          onChange={handleChange}
        >
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" name="password" />
        </Form.Group>
        <Form.Group
          className="mb-3"
          controlId="passwordVerify"
          onChange={handleChange}
        >
          <Form.Label>Verify Password</Form.Label>
          <Form.Control type="password" name="passwordVerify" />
        </Form.Group>
        <Button variant="primary" type="submit">
          Register
        </Button>
        <Button variant="secondary" href="/login">
          Sign In
        </Button>
      </Form>
    </Container>
  );
};

export default Register;
