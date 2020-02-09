package com.veera.ppm.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ValidationService {
    ResponseEntity<? extends Object> getErrorsIfExist(BindingResult result);
}
