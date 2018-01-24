INSERT INTO app_role (id, role_name, description) VALUES (1, 'APP_USER', 'Standard Application End User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: secretxy
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'AppUser', 'AppUser', '0533fb5abc04e6a46189314e480452e79b1b668e25a1079edf1d14bb0cdb0687', 'appuser');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'AdminUser', 'AdminUser', '0533fb5abc04e6a46189314e480452e79b1b668e25a1079edf1d14bb0cdb0687', 'adminuser');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
