package com.dextest.api.repository;



import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.data.jpa.repository.Query;

import com.dextest.api.model.User;




public interface UserRepository extends JpaRepository<User, String> {
	@Query("SELECT u from User u where u.username=?1")	
	User findOneByUsername(String username);

	@Query("SELECT c.userId from Contact c WHERE c.type='email' AND c.data=?1")
	User findByEmail(String email);

	@Query("SELECT c.userId from Contact c WHERE c.type='mobile' AND c.data=?1")
	User findByMobile(String mobile);
	
	
	/*@Query("SELECT u from User u where u.principalId=?1")	
	User findByPrincipalId(String principalId);*/
}
