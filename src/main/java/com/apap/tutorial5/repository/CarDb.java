package com.apap.tutorial5.repository;
import com.apap.tutorial5.model.CarModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DELL
 * DealerDb
 */
@Repository
public interface CarDb extends JpaRepository<CarModel, Long> {
	CarModel findByType(String type);
}
