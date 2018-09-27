package com.dextest.api.service;

import java.util.UUID;

import com.dextest.api.model.Activation;



public interface ActivationService {
	void createActivation(Activation activation);
	void updateActivation(Activation activation);
}
