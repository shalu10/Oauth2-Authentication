package com.dextest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dextest.api.model.Role;




public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
