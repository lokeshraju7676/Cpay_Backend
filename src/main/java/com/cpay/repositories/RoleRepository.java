package com.cpay.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cpay.entities.ERole;
import com.cpay.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}