package com.exercise.nisum.repository;

import com.exercise.nisum.model.PhoneModel;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<PhoneModel, Long> {
}
