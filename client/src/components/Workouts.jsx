import { useEffect, useState } from "react";
import {
  Container,
  Row,
  Col,
  Form,
  Button,
  Nav,
  Table,
  NavLink,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";

const Workouts = () => {
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("");
  const [workouts, setWorkouts] = useState([]);

  // TODO: configure api path to GET muscle groups, and dynamically create options
  // const [muscleGroups, setMuscleGroups] = useState()
  const url = `${process.env.REACT_APP_AWS_SERVER_HOST_BASE_URL}/api/workout`

  useEffect(() => {
    getAllWorkouts();
    // makeCards(workouts)
  }, []);

  useEffect(() => {
    filterWorkouts();
  }, [filter]);

  const getAllWorkouts = () => {
    console.log(url)
    fetch(url)
      .then((response) => {
        // console.log(response)
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => setWorkouts(data))
      .catch(console.log);
    // makeCards(workouts)
  };

  const renderCards = () => {
    if (workouts.length === 0) {
      return (
        <tr>
          <td>
            <h4>Loading...</h4>
          </td>
        </tr>
      )
    } else {
      return filterWorkouts().map((workout) => {
        return (
          <tr key={workout.id}>
            <td className="workout-name-col">
              <NavLink href={`/workouts/${workout.id}`}>{workout.name}</NavLink>
            </td>
            <td className="workout-mg-col">{workout.muscleGroup}</td>
            <td className="workout-muscle-col">{workout.muscle}</td>
            <td className="workout-duration-col">{workout.duration}</td>
          </tr>
        );
      });
    }
  };

  const handleChange = (event) => {
    setSearch(event.target.value);
  };

  const handleFilterChange = (event) => {
    setFilter(event.target.value);
  };

  const searchForWorkouts = () => {
    fetch(`${url}/${search}`)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        setWorkouts(data);
        filterWorkouts();
      })
      .catch(console.log);
  };

  const filterWorkouts = () => {
    if (filter != "") {
      return workouts.filter((workout) => workout.muscleGroup === filter);
    } else {
      return workouts;
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // console.log(search, filter)
    searchForWorkouts();
  };

  return (
    <Container>
      <div className="workout-search-container">
        <div className="grid-item-1">
          <h2 className="workout-title">Workouts</h2>
        </div>
        <div className="searchbar grid-item-2">
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="workoutName" onChange={handleChange}>
              {/* <div className="search-input">
                <Form.Control
                  className="form-control"
                  type="text"
                  placeholder="Enter Workout Name..."
                />
                <Button variant="secondary" type="submit">
                  <FontAwesomeIcon icon={faMagnifyingGlass} />
                </Button>
              </div> */}
            </Form.Group>
            <Form.Select
              aria-label="workout muscle group"
              onChange={handleFilterChange}
            >
              <option value="">Select Muscle Group</option>
              <option value="arm">Arms</option>
              <option value="buttock">Buttocks</option>
              <option value="chest">Chest</option>
              <option value="core">Core</option>
              <option value="leg">Legs</option>
              <option value="lower back">Lower Back</option>
              <option value="shoulder">Shoulders</option>
              <option value="upper back">Upper Back</option>
            </Form.Select>
          </Form>
        </div>
      </div>

      <table className="table table-striped">
        <thead className="thead-dark">
          <tr>
            <th className="table-dark">Workout Name</th>
            <th className="table-dark">Muscle Group</th>
            <th className="table-dark">Muscle</th>
            <th className="table-dark">Duration</th>
          </tr>
        </thead>
        <tbody>{renderCards()}</tbody>
      </table>
    </Container>
  );
};

export default Workouts;
