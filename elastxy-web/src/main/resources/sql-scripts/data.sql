/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
INSERT INTO app_role (id, role_name, description) VALUES (1, 'APP_USER', 'Standard Application End User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- USER
-- non-encrypted password: secretxy
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'AppUser', 'AppUser', '0533fb5abc04e6a46189314e480452e79b1b668e25a1079edf1d14bb0cdb0687', 'appuser');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'AdminUser', 'AdminUser', '0533fb5abc04e6a46189314e480452e79b1b668e25a1079edf1d14bb0cdb0687', 'adminuser');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
