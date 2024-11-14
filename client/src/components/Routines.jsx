import { useEffect, useState } from "react";
import { Container, Row, Col, Form, Button, Nav } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

const Routines = () => {
  const [search, setSearch] = useState("");

  // TODO: set up UserController to get all trainers
  // const [trainerFilter, setTrainerFilter] = useState('')
  const [diffFilter, setDiffFilter] = useState("");
  const [routines, setRoutines] = useState([]);
  // const [filteredRoutines, setFilteredRoutines] = useState([])
  // const [routineCards, setroutineCards] = useState([])

  useEffect(() => {
    getAllRoutines();
  }, []);

  useEffect(() => {
    filterRoutines();
  }, [diffFilter]);

  const getAllRoutines = () => {
    fetch("http://localhost:8080/api/routine")
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => setRoutines(data))
      .catch(console.log);
    // makeCards(routines)
  };

  // copy implementation from workouts to fix cards not loading on page load

  const renderRoutines = () => {
    if (routines.length === 0) {
      return <h4>Loading...</h4>;
    } else {
      return filterRoutines().map((routine) => {
        return (
          <tr key={routine.routine_id}>
            <td className="routine-name-col">
              <Nav.Link
                className="routine-name"
                href={`routines/${routine.routine_id}`}
              >
                {routine.routine_name}
              </Nav.Link>
            </td>
            <td className="routine-diff-col">{routine.difficulty}</td>
            <td className="routine-duration-col">{routine.routine_duration}</td>
          </tr>
        );
      });
    }
  };

  // const makeCards = (data) => {
  //     setroutineCards(data.map((routine) => {
  //         return (
  //             <Row key={routine.routine_id}>
  //                 <Col className="routine-name-col">
  //                     <Nav.Link className="routine-name" href={`routines/${routine.routine_id}`}>
  //                         {routine.routine_name}
  //                     </Nav.Link>
  //                 </Col>
  //                 <Col className="routine-author-col">
  //                     <p className="routine-author">{routine.routine_author}</p>
  //                 </Col>
  //                 <Col className="routine-diff-col">
  //                     <p className="routine-diff">{routine.difficulty}</p>
  //                 </Col>
  //                 <Col className="routine-duration-col">
  //                     <p className="routine-duration">{routine.routine_duration}</p>
  //                 </Col>
  //             </Row>
  //         )
  //     }))
  // }

  const handleChange = (event) => {
    setSearch(event.target.value);
  };

  const handleDiffFilterChange = (event) => {
    setDiffFilter(event.target.value);
  };

  const searchForRoutines = () => {
    fetch(`http://localhost:8080/api/routine/search/${search}`)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        setRoutines(data);
        filterRoutines();
      })
      .catch(console.log);
  };

  const filterRoutines = () => {
    //TODO: refactor to filter by trainer name too

    if (diffFilter != "") {
      // setFilteredRoutines(routines.filter(routine => routine.difficulty === diffFilter))
      return routines.filter((routine) => routine.difficulty === diffFilter);
    } else {
      return routines;
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(search, diffFilter);
    searchForRoutines();
  };

  return (
    <Container>
      <div className="routine-search-container">
        <div className="grid-item-1">
          <h2 className="routine-title">Routines</h2>
        </div>

        <div className="searchbar grid-item-2">
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="routineName" onChange={handleChange}>
              {/* <div className="search-input">
                <Form.Control type="text" placeholder="Enter Routine Name..." />

                <Button variant="secondary" type="submit">
                  <FontAwesomeIcon icon={faMagnifyingGlass} />
                </Button>
              </div> */}
            </Form.Group>

            <Form.Select
              aria-label="routine difficulty"
              onChange={handleDiffFilterChange}
            >
              <option value="">Select Difficulty</option>
              <option value="Easy">Easy</option>
              <option value="Medium">Medium</option>
              <option value="Hard">Hard</option>
            </Form.Select>
          </Form>
        </div>
      </div>

      <table className="table table-striped">
        <thead className="thead-dark">
          <tr>
            <th className="table-dark">RoutineName</th>
            <th className="table-dark">Difficulty</th>
            <th className="table-dark">Duration</th>
          </tr>
        </thead>
        <tbody>{renderRoutines()}</tbody>
      </table>
      <Button onClick={() => console.log(routines)}>routines</Button>
    </Container>
  );
};

export default Routines;
