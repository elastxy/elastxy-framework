package org.elastxy.web.security.repository;

import org.elastxy.web.security.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
