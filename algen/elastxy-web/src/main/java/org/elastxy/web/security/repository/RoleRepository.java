package org.elastxy.web.security.repository;

import org.elastxy.web.security.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nydiarra on 06/05/17.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
	
	// Marker interface for Role Repository
}
