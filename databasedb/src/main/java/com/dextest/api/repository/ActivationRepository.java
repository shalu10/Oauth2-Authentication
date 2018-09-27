package com.dextest.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.dextest.api.model.Activation;




public interface ActivationRepository extends PagingAndSortingRepository<Activation,String>{
	
}
