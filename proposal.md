# BYN Proposal

## Problem Statement:
### Establishing and tracking workout performance and fitness goals, and identifying workouts for specific muscles and muscle groups.

## Technical Solution
### Create an application that tracks user's fitness goals and individual workouts, and supplies various workouts for selected muscle groups.
### Example 1: Bob just started a membership at a gym. He wants to improve his general physical fitness, but doesn't know quite where to start. He wants to make sure that he gets at least 30 minutes of exercise per day, and to work out his whole body over the course of the week. He does not have concrete numerical goals, such as weight; he is more focused on the "feel" of his body and performance. Bob uses the BYN Fitness app to check different workouts to do each day, and to keep track of what workouts he's done on which days to make sure that he gives himself enough recovery time between muscle workouts.
### Example 2: Laura is a frequent attendee at her local gym. She wants to have a consistent and easy-to-use tracker for her fitness that allows her to set multiple, separate benchmark goals for herself, and tracks which workouts she performs and the specific goals they apply to. She has specific numerical targets for her goals. Laura uses the BYN Fitness app to organize her daily workouts according to her goals and keep track of how close she is to meeting those goals.

## Glossary
### User: Anyone who uses the BYN Fitness app. A user may or may not have specific goals set; they may or may not record their workout logs.
### Trainer: A user with the administrator role. They have more privileges, such as organizing workouts into routines and making changes to user information.
### Workout: A pre-determined exercise. Includes details on form and execution, as well as muscles and muscle groups they affect. Workouts can only be created and edited by Trainers, but can be viewed by any User.
### Workout Log: A record of a workout session. Can include multiple Workouts and is tied to the User that creates them. They can be edited by the User that creates them, or by a Trainer.
### Routine: A compiled list of Workouts with an attached time frame for each Workout. Can be viewed by anyone, but can only be created or edited by a Trainer. Each routine has a Trainer that created it.
### Goal: A milestone created by a User to track their fitness goals. Can be as specific or as abstract as the user likes. Can be optionally linked to the workout logs that apply to the goal. A user can have multiple goals.
### Muscle: A specific muscle in the body. Belongs to a muscle group. Workouts reference the muscles they affect.
### Muscle Group: A group of muscles categorized by name.

## High-Level Requirements
### Browse workouts (anyone).
### Create a workout log (authenticated).
### Browse workout logs (authenticated creator/ADMIN).
### Update a workout log (authenticated creator/ADMIN).
### Delete a workout log (authenticated creator/ADMIN).
### Browse routines (anyone).
### Create a routine (ADMIN).
### Update a routine (ADMIN).
### Delete a routine (ADMIN).
### Browse goals (authenticated creator/ADMIN).
### Create a goal (authenticated).
### Update a goal (authenticated creator/ADMIN).
### Delete a goal (authenticated creator/ADMIN).

## User Stories/Scenarios
### Browse workouts:
### Display a list of workouts. Can be filtered by muscle group or search description.
### Suggested data:
### Workout name
### Workout description
### Target muscle/muscle group
### Demonstration pic/video/animation

### Browse goals:
### Browse the user's created goals. Filter by completion status.
### Precondition: User must be logged in. User must have created at least one goal.

### Create a goal:
### Create a new goal. User provides a short name and a description (500 char?)
### Precondition: User must be logged in.

### Update a goal:
### Update an existing goal.
### Precondition: User must be logged in.

### Delete a goal:
### Delete an existing goal.
### Precondition: User must be logged in.
### Post-condition: Workout logs attached to the goal have the attachment cleared (logs are not deleted).

### Browse logs:
### Browse the user's workout logs. Filter by timestamp or workout.
### Precondition: User must be logged in. User must have created at least one workout log.

### Create a log:
### Create a workout log. User logs identifies workout(s) completed, a completion timestamp, and optionally selects goals that log applies to (if any exist).
### Precondition: User must be logged in. Timestamp cannot be set to the future.
### Post-condition: Log is added to the user's profile. Goals related to the workout log are updated with the log attached.

### Update a log:
### Update a workout log.
### Precondition: User must be logged in. Timestamp cannot be set to the future.

### Delete a log:
### Delete a workout log.
### Precondition: User must be logged in.
### Post-condition: Goals with the log attached are updated, removing the log.

### Browse routines:
### Display a list of workout routines. Filter by difficulty or search description, or the trainer that created it.
### Suggested data:
### List of workouts contained in the routine
### Cycle/duration of routine

### Add a routine:
### Add a new routine.
### Precondition: User must be logged in and have the Trainer(ADMIN) role. Workouts selected must exist.

### Update a routine:
### Update an existing routine.
### Precondition: User must be logged in and have the Trainer(ADMIN) role. User must be the Trainer that created the routine.

### Delete a routine:
### Delete an existing routine.
### Precondition: User must be logged in and have the Trainer(ADMIN) role. User must be the Trainer that created the routine.

## Package/Class Overview

```
src
├───main
│   └───java
│       └───learn
│           └───byn-fitness
│               │   App.java                                        -- app entry point
│               │
│               ├───controllers
│               │       ExceptionHandler.java                       -- global exception handler
│               │       ResponseBuilder.java                        -- streamlined response builder
│               │       AuthController.java                         -- user authentication handler
│               │       WorkoutController.java                      -- workout controller
│               │       WorkoutLogController.java                   -- workout log controller
│               │       GoalController.java                         -- goal controller
│               │       RoutineController.java                      -- routine controller
│               ├───data
│               ├───────mappers
│               │       │       WorkoutMapper                       -- maps workout obtained from database
│               │       │       WorkoutLogMapper                    -- maps Workout Log obtained from database
│               │       DataException.java                          -- data layer custom exception
│               │       UserRepository.java                         -- user repository interface
│               │       UserJdbcTemplateRepository.java             -- concrete user repository
│               │       WorkoutRepository.java                      -- workout repository interface
│               │       WorkoutJdbcTemplateRepository.java          -- concrete workout repository
│               │       WorkoutLogRepository.java                   -- workout log repository interface
│               │       WorkoutLogJdbcTemplateRepository.java       -- concrete workout log repository
│               │       GoalRepository.java                         -- goal repository interface
│               │       GoalJdbcTemplateRepository.java             -- concrete goal repository
│               │       RoutineRepository.java                      -- routine repository interface
│               │       RoutineJdbcTemplateRepository.java          -- concrete routine repository
│               ├───domain
│               │       UserService.java                            -- user validation/rules. implements UserDetailsService
│               │       WorkoutService.java                         -- workout validation/rules
│               │       WorkoutLogService.java                      -- workout log validation/rules
│               │       GoalService.java                            -- goal validation/rules
│               │       RoutineService.java                         -- routine validaiton/rules
│               └───models
│                       Result.java                                 -- custom result model
│                       User.java                                   -- base user model
│                       Workout.java                                -- base workout model
│                       WorkoutLog.java                             -- base workout log model
│                       Goal.java                                   -- base goal model
│                       Routine.java                                -- base routine model
│                       
│
└───test
    └───java
        └───learn
            └───solar
                ├───data
                │       UserFileRepositoryTest.java     -- UserFileRepository tests
                │       WorkoutRepositoryTest.java      -- WorkoutRepository Tests
                │       WorkoutLogRepositoryTest.java   -- WorkoutLogRepository Tests
                │       GoalRepositoryTest.java         -- GoalRepository Tests
                │       RoutineRepositoryTest.java      -- RoutineRepository Tests
                └───domain
                        PanelServiceTest.java           -- PanelService tests
```

## Class Details
### data.DataException
- `public DataException(String, Throwable)` -- constructor, Throwable arg is root cause exception
### data.UserRepository.java (interface)
Contract for UserJdbcTemplateRepository
- `User getUserByUsername(String)` -- finds user by username, passes to UserService for authentication
- User add(User)
- boolean update(User)
- boolean delete(int)
### data.UserJdbcTemplateRepository.java
- private final JdbcTemplate
- public User add(User)
- public boolean update(User)
- public boolean delete(User)
- public User findById(int)
- private String serialize(User user) -- converts object to SQL string
- private User deserialize(String line) -- converts SQL row to object
### data.WorkoutRepository.java (interface)
Contract for WorkoutJdbcTemplateRepository
 - `List<Workout> findAll()` -- returns list of all workouts
 - `List<Workout> findByMuscleGroup(String muscleGroup)` -- returns list of workouts affecting muscles in the specified muscle group
 - `List<Workout> findByDescContent(String searchTerm)` -- returns list of workouts with descriptions containing the specified search term
### data.WorkoutJdbcTemplateRepository.java
 - `List<Workout> findAll()`
 - `List<Workout> findByMuscleGroup(String muscleGroup)`
 - `List<Workout> findByDescContent(String searchTerm)`
 - private Workout deserialize(String line) -- converts SQL row to object
### data.WorkoutLogRepository.java (interface)
Contract for WorkoutLogJdbcTemplateRepository
 - `List<WorkoutLog> findByUser(int userId)` -- returns list of workouts created by the specified (current) user
 - `WorkoutLog add(WorkoutLog log)` -- adds workout log to repository, returns log with attached id
 - `boolean update(WorkoutLog log)` -- updates workout log to repository, returns success status
 - `int delete(int logId)` -- deletes workout log from repository, returns success status (-1 not allowed, 0 not found, 1 success)
### data.WorkoutLogJdbcTemplateRepository.java
 - `List<WorkoutLog> findByUser(int userId)` 
 - `WorkoutLog add(WorkoutLog log)`
 - `boolean update(WorkoutLog log)`
 - `int delete(int logId)`
 - private String serialize(WorkoutLog log) -- converts object to SQL string
 - private WorkoutLog deserialize(String line) -- converts SQL row to object
### data.GoalRepository.java (interface)
Contract for GoalJdbcTemplateRepository
 - `List<Goal> findByUser(int userId)` -- returns list of goals created by the specified (current) user
 - `Goal add(Goal goal)` -- adds goal to repository, returns goal with attached id
 - `boolean update(Goal goal)` -- updates goal to repository, returns success status
 - `boolean delete(int goalId)` -- deletes goal from repository, returns success status
### data.GoalJdbcTemplateRepository.java
 - `List<Goal> findByUser(int userId)`
 - `Goal add(Goal goal)`
 - `boolean update(Goal goal)`
 - `boolean delete(int goalId)`
 - private String serialize(Goal goal) -- converts object to SQL string
 - private Goal deserialize(String line) -- converts SQL row to object
### data.RoutineRepository.java (interface)
Contract for RoutineJdbcTemplateRepository
 - `List<Routine> findAll()` -- returns list of all routines
 - `List<Routine> findByTrainer(int trainerId)` -- returns list of all routines created by a specific trainer
 - `List<Routine> findByDifficulty(String difficulty)` -- returns list of all routines with specified difficulty
 - `List<Routine> findByDescContent(String searchTerm)` -- returns list of all routines with descriptions containing the specified search term
 - `Routine findById(int routineId)` -- returns specific routine
 - `Routine add(Routine routine)` -- adds routine to repository
 - `boolean update(Routine routine)` -- updates routine in repository, returns success status
 - `boolean delete(Routine routine)` -- deletes routine from repository, returns success status
### data.RoutineJdbcTemplateRepository.java
 - `List<Routine> findAll()`
 - `List<Routine> findByTrainer(int trainerId)`
 - `List<Routine> findByDifficulty(String difficulty)`
 - `List<Routine> findByDescContent(String searchTerm)`
 - private String serialize(Routine routine) -- converts object to SQL string
 - private Routine deserialize(String line) -- converts SQL row to object

### domain.UserService.java
Implements UserDetailsService for security authentication
 - private final UserRepository repository
 - public UserService() -- constructor, instantiates repository and, if no admin user is found, creates one and logs to console
 - public UserDetails loadUserByUsername(String username) -- @Override, throws UsernameNotFoundException
 - public User add(User user)
 - private void validate(User user)
 - private void ensureAdmin() -- run once on instantiation to ensure an admin user exists; creates one if not
### domain.WorkoutService.java
 - private WorkoutRepository repository
 - `public Result<List<Workout>> findAll()`
 - `public Result<List<Workout>> findByMuscleGroup(String muscleGroup)`
 - `public Result<List<Workout>> findByDescContent(String searchTerm)`
### domain.WorkoutLogService.java
 - private WorkoutLogRepository repository
 - `public Result<List<WorkoutLog>> findByUser(int userId)`
 - `public Result<WorkoutLog> add(WorkoutLog log)`
 - `public Result<WorkoutLog> update(WorkoutLog log)`
 - `public Result<WorkoutLog> delete(int logId)`
### domain.GoalService.java
 - private GoalRepository repository
 - `public Result<List<Goal>> findByUser(int userId)`
 - `public Result<Goal> add(Goal goal)`
 - `public Result<Goal> update(Goal goal)`
 - `public Result<Goal> delete(int goalId)`
### domain.RoutineService.java
 - private RoutineRepository repository
 - `public Result<List<Routine>> findAll()`
 - `public Result<List<Routine>> findByTrainer(int trainerId)`
 - `public Result<List<Routine>> findByDifficulty(String difficulty)`
 - `public Result<List<Routine>> findByDescContent(String searchTerm)`
 - `public Result<Routine> findById(int routineId)`
 - `public Result<Routine> update(Routine routine)`
 - `public Result<Routine> delete(int routineId)`

### models.Result.java
- `private final ArrayList<String> messages`
- private ResultType type
- private T payload
- Getters for type, messages, and payload
- Setter for payload and type
- public boolean isSuccess()
- public void addMessage(String, ResultType)
### models.User.java
- private int user_id
- private String username
- private String email
- private String password
- private boolean isAdmin
- Full Getters and Setters
### models.Workout.java
- private int workout_id
- private String workout_name
- private String workout_description
- private int workout_duration
- private String muscle
- Full Getters and Setters
### models.WorkoutLog.java
- private int log_id
- private int user_id
- private int goal_id -- optional
- private Datetime finished
- Full Getters and Setters
### models.Goal.java
- private int goal_id
- private String goal_name
- private String goal_description
- private boolean complete
- private int user_id
- Full Getters and Setters
### models.Routine.java
- private int routine_id
- private String routine_name
- private String routine_description
- private int routine_duration
- private String difficulty
- private int routine_author_id -- Alias for a trainer's user_id
- Full Getters and Setters
### models.ResultType.java
- Enum with three values: SUCCESS, INVALID, and NOT_FOUND

## Steps
1. Create Maven Project
2. Add jUnit 5, Jupiter as Maven dependency and refresh 
3. Create packages
4. Create Models
5. Create MySQL production database
6. Create MySQL test database
    1. Populate test values
    2. Create testing schema
7. Create Data Layer
    1. Interfaces
    2. Repositories
8. Test Data Layer
9. Create Domain Layer
    1. Services
10. Test Domain Layer using mocked repositories
11. Create Controllers
12. Configure Database Security features
13. Create React UI
    1. Run npx create-react-app to create client directory
    2. Import Bootstrap
    2. Create components
14. Host database on online service, such as AWS
    1. Create AWS account
    2. Research DB hosting on AWS
    3. Configure hosted production database
    4. Populate online database
15. Host front-end on online service, such as AWS
    1. Research application hosting on AWS
    2. Configure hosted application
    3. Connect application to database