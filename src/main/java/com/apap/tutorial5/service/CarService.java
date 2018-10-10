package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.CarModel;

/**
 * @author DELL
 * CarService
 */
public interface CarService {
	void addCar(CarModel car);
	void deleteCar(Long id);
	void updateCar(Long id, String amount, String brand, String price, String type);
}
