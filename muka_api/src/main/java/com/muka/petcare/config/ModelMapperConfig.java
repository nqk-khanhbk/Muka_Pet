package com.muka.petcare.config;

import com.muka.petcare.dto.response.info_alert.InfoAlertResponse;
import com.muka.petcare.entity.Alert;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		return modelMapper;
	}
}
