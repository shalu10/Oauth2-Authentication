package com.dextest.api.service;

import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dextest.api.model.Activation;
import com.dextest.api.repository.ActivationRepository;



@Service
@Transactional
public class ActivationServiceImpl implements ActivationService{
	
	@Autowired private ActivationRepository activationRepository; 
	
	@Override
	public void createActivation(Activation activation) {
		activationRepository.save(activation);
	}

	@Override
	public void updateActivation(Activation activation) {
		activationRepository.save(activation);	
	}

}
