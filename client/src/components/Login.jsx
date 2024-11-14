import { createContext, useState, useContext } from "react";
import {
  Container,
  Form,
  Button,
  ListGroup,
  ListGroupItem,
  Alert,
} from "react-bootstrap";
import User from "../context/UserContext";
import { redirect, useNavigate } from "react-router-dom";
import UserContext from "../context/UserContext";

const USER_DEFAULT = {
  username: "",
  password: "",
};

const Login = () => {
  // TODO: Add Error Handling
  const [credentials, setCredentials] = useState(USER_DEFAULT);
  const [errors, setErrors] = useState([]);
  const url = "http://localhost:8080/api/user/authenticate";

  const { user, login } = useContext(UserContext);

  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    // console.log(credentials)

    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(credentials),
    };

    fetch(url, init)
      .then((response) => {
        if (response.status === 200 || response.status === 403) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {
        if (data.jwt_token) {
          console.log(data);
          login(data);
          // setTimeout(() => { navigate('/dashboard') }, 1000)
          navigate("/dashboard");
        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };

  const handleChange = (event) => {
    // console.log(event.target.name, event.target.value)
    const newCredentials = { ...credentials };
    newCredentials[event.target.name] = event.target.value;
    setCredentials(newCredentials);
  };

  const renderErrors = (err) => {};

  return (
    <Container>
      <h2 className="login-title">Log In</h2>
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
        <Form.Group
          className="mb-3"
          controlId="password"
          onChange={handleChange}
        >
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" name="password" />
        </Form.Group>
        <Button variant="primary" type="submit">
          Log In
        </Button>
        {/* <Button variant="secondary" href="/register">
          Register
        </Button> */}
      </Form>
    </Container>
  );
};

export default Login;
