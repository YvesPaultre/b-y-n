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
│               │
│               │
│               ├───data
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
│               │       UserService                                 -- user validation/rules. implements UserDetailsService
│               │       WorkoutService.java       
│               │       GoalService.java
│               │       RoutineService.java
│               └───models
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
                │        UserFileRepositoryTest.java     -- UserFileRepository tests
                │        WorkoutRepositoryTest.java      -- WorkoutRepository Tests
                │        WorkoutLogRepositoryTest.java   -- WorkoutLogRepository Tests
                │        GoalRepositoryTest.java         -- GoalRepository Tests
                │        RoutineRepositoryTest.java      -- RoutineRepository Tests
                └───domain
                        PanelServiceTest.java           -- PanelService tests
```