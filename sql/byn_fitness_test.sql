drop database if exists byn_fitness_test;
create database byn_fitness_test;
use byn_fitness_test;

CREATE TABLE `user` (
  `user_id` int auto_increment PRIMARY KEY,
  `username` varchar(50) NOT NULL,
  `hashed_pw` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `isAdmin` boolean NOT NULL,
  `disabled` boolean NOT NULL
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

-- Initial seed data (not dependent on state)
insert into muscle_group(mg_id, mg_name) values
	(1, 'upper back'), 
	(2, 'lower back'), 
	(3, 'arm'),
	(4, 'core'),
	(5, 'leg'),	
	(6, 'chest'),
	(7, 'shoulder'),
	(8, 'buttock');	
    
insert into muscle(muscle_id, muscle_name, mg_id ) values 
	(1, 'bicep',3),				-- 1
	(2, 'tricep',3),			-- 2
	(3, 'deltoid',7),			-- 3
	(4, 'pectoral',6),			-- 4
	(5, 'calf',5),				-- 5
	(6, 'glutes',8),			-- 6
	(7, 'trapezius',1),			-- 7
	(8, 'rhomboid',1),			-- 8
	(9, 'abdominal',4);			-- 9

insert into workout(workout_id, workout_name, workout_description, workout_duration, muscle_id) values
		(1, 'bicep curl','Hold dumbells in both hands while standing. Bring one of the dumbells to your shoulder, and then slowly back down. Repeat for the other dumbell. Keep your back straight to focus the effort on your biceps',10,1),
		(2, 'tricep dip','Firmly grasp the handles on the dip machine and raise your body up to the starting position. Slowly lower your body downwards. You should lower yourself down so that your arms are in a 90 degree position. Slowly raise your body back up to the starting position.',10,2),
		(3, 'dumbbell press','With your feet shoulder-width apart, take a dumbbell in each hand. Raise the dumbbells to eye level with your elbows bent to about 90 degrees. Brace your core and drive the dumbbells up and together, extending the elbows to 180 degrees. Pause, and slowly return the weight to the starting position.',15,3),
		(4, 'bench press','Grasp the barbell using an overhand grip. Your arms are slightly wider than shoulder-width apart and the angle of your upper arms is about 45 degrees to the body. Remove the barbell from the rack, locking your elbows. Inhale while slowly lowering the bar just above your chest. Exhale as you press the bar above your chest. Lower the bar so it is just above your chest.',20,4),
		(5, 'heel raise','Stand with your feet together, flat on the floor. Raise your heels, shifting your weight onto the balls of your feet. Hold for a second, then slowly bring your heels back to the ground.',5,5),
		(6, 'glute kickback (cable)','Place a strap attached to the cable around the ankle. Keep your back in a neutral position with your abs engaged. Tilt your body forward and kick your leg behind you while maintaining a slight knee bend. Raise your leg until you fully contract your working glute while relaxing your non-working leg.',10,6),
		(7, "farmer's carry",'Hold a weight in each hand at your sides. Stand upright. Take even, measured steps, controlling the weight and maintaining your posture for either a set time or distance.',6,7),
		(8, 'side lying reverse dumbbell fly','Lying on your side with your elbow fixed and body still, raise the dumbbell from floor until vertical. Lower back down in a reverse motion while inhaling. Repeat on the opposite side.',5,8),
		(9, 'leg lifts','Lie faceup on the floor with your legs together. Using only your core, lift your feet a few inches off the ground. Slowly, move your legs apart and back together. Hold your feet in the air for a few moments, and then slowly bring back them back down',2,9);


insert into user(user_id, username, hashed_pw, email, isAdmin, disabled) values
	(1, 'testMctestface','$2y$10$zz4DMA.lu.Cm3ppl39VriO3Xzao37w7wVeuH8JbLJ2gU0QIu4Bxoe','test@example.com',false,false), -- password
	(2, 'novicegymrat90','$2y$10$XQRDrU4yN0hX6vgTFo5Mluto6brBE0ZSB1WmA6vNeJmeePvZIQYPS','spamgoeshere@gmail.com',false,false), -- easy2hack
	(3, 'gymbro95','$2y$10$LE2DpMV1ezpdpMBcMIGutu4CXd9RMF/8EmQOL6FfyFegTd76iQQ8C','gainzfordayz@gmail.com',false,false), -- DonutStealMyPassword
	(4, 'wheresTheSauna','$2y$10$Njh0tfLg0xC4.gGL0QkAXOAgp/zpxBEwGPagwWam/R/gNuw0DDy92','example@yahoo.com',false,false), -- extrasecretlogincode
	(5, 'TrainerYves','$2y$10$JGoZXkBgG/m3ZjdkbrwPsOIghRsl1MYM2ONDz36DoXcGKMEHsjX6i','yves@bynfitness.com',true,false), -- traineryves
	(6, 'GovernatorSchwarzenegger','$2y$10$TSmKD5esfeFTUN7Ogdo0dO2PAgG9i4ZuyWLPLBq9bR/UmFuHT0L8C','aschwarz@california.gov',true,false); -- thegovernatorhasarrived


-- Known good state procedure
delimiter //
create procedure set_known_good_state()
begin
	set SQL_SAFE_UPDATES = 0;
    
    delete from goal_workout;
    alter table goal_workout auto_increment = 1;
    delete from routine_workout;
    alter table routine_workout auto_increment = 1;
    delete from `routine`;
    alter table `routine` auto_increment = 1;
    delete from log;
    alter table log auto_increment = 1;
	delete from goal;
    alter table goal auto_increment = 1;
	
    set SQL_SAFE_UPDATES = 1;

	insert into routine(routine_id, routine_name, routine_description, routine_duration, difficulty,routine_author_id) values
		(1, 'Upper Body','General Workout for your arms and shoulders',60,'Easy',5),								-- 1
		(2, 'All Around Fitness','A well rounded routine that targets your whole body',120,'Medium',6),				-- 2
		(3, 'Leg Day',"Hope you don't have a long walk home.",30,'Easy',5),											-- 3
		(4, 'Intense Bodybuilding','A grueling all around workout designed to maximize your gains.',60,'Hard',6);	-- 4

	insert into routine_workout(rw_id, workout_id, routine_id) values
		(1,1,1), (2,2,1), (3,8,1),
		(4,2,2), (5,3,2), (6,5,2), (7,9,2),
		(8,5,3), (9,6,3),
		(10,3,4), (11,4,4), (12,7,4), (13,8,4);
		
	insert into goal(goal_id, goal_name, description, complete, user_id) values
		(1,'Get Stronk','Bench press at least 250 lbs',false,1),
		(2,'Build Core','Stengthen up my abs.',true,3),
		(3,'Gym 101','Pick up the weights, and put them down.',true,1);
		
	insert into log(log_id,user_id, finished, goal_id, workout_id) values
		(1,1,'2024-10-11 14:23:38',1,4),
		(2,1,'2024-10-11 15:01:42',null,2),
		(3,2,'2024-10-31 11:05:15',null,8),
		(4,3,'2024-11-01 09:54:22',null,7);
        
end //
delimiter ;