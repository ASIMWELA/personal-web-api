INSERT  INTO roles(name) VALUES('ROLE_USER');
INSERT  INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO user_entity(uid, first_name, last_name, user_name, email, password,sex) VALUES("yk7jgKlmnRh67Jk", "Augustine","Simwela","Asimwela","augastinesimwela@gmail.com", "$2y$12$KTBkUnamnje8cVLI73PTReEo0hjMn2H.8zx.ZhsDwABESaI2RtpRC", "male");
INSERT INTO user_entity(uid, first_name, last_name, user_name, email, password,sex) VALUES("yk7jgKlmnRK67Jk", "Nelson","Simwela","Nsimwela","Nsimwela@gmail.com", "$2y$12$KTBkUnamnje8cVLI73PTReEo0hjMn2H.8zx.ZhsDwABESaI2RtpRC", "male");

INSERT INTO user_roles(user_id, role_id) VALUES(1,1);
INSERT INTO user_roles(user_id, role_id) VALUES(1,2);
INSERT INTO user_roles(user_id, role_id) VALUES(2,1);