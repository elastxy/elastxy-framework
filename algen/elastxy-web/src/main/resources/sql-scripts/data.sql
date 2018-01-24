INSERT INTO app_role (id, role_name, description) VALUES (1, 'APP_USER', 'Standard Application End User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: secretxy
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'AppUser', 'AppUser', '0533FB5ABC04E6A46189314E480452E79B1B668E25A1079EDF1D14BB0CDB0687', 'appuser');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'AdminUser', 'AdminUser', '0533FB5ABC04E6A46189314E480452E79B1B668E25A1079EDF1D14BB0CDB0687', 'adminuser');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
