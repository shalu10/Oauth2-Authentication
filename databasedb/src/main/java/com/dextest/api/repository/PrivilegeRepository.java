package com.dextest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dextest.api.model.Privilege;



public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
