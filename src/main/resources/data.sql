INSERT  INTO roles(name) VALUES('ROLE_USER');
INSERT  INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO user_entity(uid, first_name, last_name, user_name, email, password,sex, dob,is_online, age) VALUES("AugagKAugaRh67k", "Augustine","Simwela","Asimwela","augastinesimwela@gmail.com", "$2y$12$KTBkUnamnje8cVLI73PTReEo0hjMn2H.8zx.ZhsDwABESaI2RtpRC", "male", "1996-01-28", false, 24);
INSERT INTO user_entity(uid, first_name, last_name, user_name, email, password,sex, dob, is_online,age) VALUES("NeljgKNelRK67Jk", "Nelson","Simwela","Nsimwela","Nsimela@gmail.com", "$2y$12$KTBkUnamnje8cVLI73PTReEo0hjMn2H.8zx.ZhsDwABESaI2RtpRC", "male","1996-01-28", false,24);

INSERT INTO experience(exp_name, began_on,years,months,user_id) VALUES('Java','2015-10-15',6,12,1),('Javascript','2014-10-15',7,12,1),('c++','2017-10-23',4,16,1),('php','2018-10-15',3,12,1),('python','2015-10-15',6,24,1);

INSERT INTO user_roles(user_id, role_id) VALUES(1,1);
INSERT INTO user_roles(user_id, role_id) VALUES(1,2);
INSERT INTO user_roles(user_id, role_id) VALUES(2,1);

INSERT INTO project_details(description,location_link,name,role)VALUES('INTRODUCTION TO JAVA','www.learnjava.com','Java','lead developer'),('MOBILE APPLICATION DEVELOPEMNT','www.learnANDROID.com','android','lead developer'),('INTRODUCTION TO django','www.learnpython.com','pythno','lead developer');
