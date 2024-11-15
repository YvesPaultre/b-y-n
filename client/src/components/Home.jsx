import Container from "react-bootstrap/Container";
import { Stack } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import Workouts from "./Workouts";
import Routines from "./Routines";

const Home = () => {
  return (
    <>
      <Container>
        <header>
          <h1 className="home-banner">BYN Fitness</h1>
        </header>
      </Container>
      <Workouts />
      <Routines />
    </>
  );
};

export default Home;
