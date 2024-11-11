drop database if exists byn_fitness_test;
create database byn_fitness_test;
use byn_fitness_test;

CREATE TABLE `user` (
  `user_id` int auto_increment PRIMARY KEY,
  `username` varchar(50) NOT NULL,
  `hashed_pw` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `isAdmin` boolean NOT NULL
);

CREATE TABLE `muscle_group` (
  `mg_id` int auto_increment PRIMARY KEY,
  `mg_name` varchar(50) NOT NULL
);

CREATE TABLE `muscle` (
  `muscle_id` int auto_increment PRIMARY KEY,
  `muscle_name` varchar(50) NOT NULL,
  `mg_id` int NOT NULL,
  constraint fk_muscle_mg_mg_id
	foreign key(mg_id)
    references muscle_group(mg_id)
);

CREATE TABLE `workout` (
  `workout_id` int auto_increment PRIMARY KEY,
  `workout_name` varchar(50) NOT NULL,
  `workout_description` varchar(500) NOT NULL,
  `workout_duration` int NOT NULL,
  `muscle_id` int NOT NULL,
  constraint fk_workout_muscle_id
	foreign key(muscle_id)
    references muscle(muscle_id)
);

CREATE TABLE `goal` (
  `goal_id` int auto_increment PRIMARY KEY,
  `goal_name` varchar(50) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `complete` boolean NOT NULL,
  `user_id` int NOT NULL,
  constraint fk_goal_user_id
	foreign key(user_id)
    references `user`(user_id)
);

CREATE TABLE `goal_workout` (
  `gw_id` int auto_increment PRIMARY KEY,
  `workout_id` int,
  `goal_id` int,
  constraint fk_gw_workout_id
	foreign key(workout_id)
    references workout(workout_id),
  constraint fk_gw_goal_id
	foreign key(goal_id)
    references goal(goal_id)
);

CREATE TABLE `log` (
  `log_id` int auto_increment PRIMARY KEY,
  `user_id` int NOT NULL,
  `finished` timestamp NOT NULL,
  `goal_id` int,
  `workout_id` int NOT NULL,
  constraint fk_log_goal_id
	foreign key(goal_id)
    references goal(goal_id),
  constraint fk_log_workout_id
	foreign key(workout_id)
    references workout(workout_id)
);

CREATE TABLE `routine` (
  `routine_id` int auto_increment PRIMARY KEY,
  `routine_name` varchar(50) NOT NULL,
  `routine_description` varchar(2000) NOT NULL,
  `routine_duration` int NOT NULL,
  `difficulty` varchar(25) NOT NULL,
  `routine_author_id` int NOT NULL,
  constraint fk_routine_author_id
	foreign key(routine_author_id)
    references user(user_id)
);

CREATE TABLE `routine_workout` (
  `rw_id` int auto_increment PRIMARY KEY,
  `workout_id` int NOT NULL,
  `routine_id` int NOT NULL,
  constraint fk_rw_workout_id
	foreign key(workout_id)
    references workout(workout_id),
  constraint fk_rw_routine_id
	foreign key(routine_id)
    references routine(routine_id)
);

-- make seed data