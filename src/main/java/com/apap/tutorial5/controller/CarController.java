package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * @author DELL
 * CarController
 */

@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		car.setDealer(dealer);
		model.addAttribute("title", "Add Car");
		model.addAttribute("car", car);
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	@RequestMapping(value = "/car/update/{dealerId}/{carId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId,@PathVariable(value = "carId") Long carId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		car.setDealer(dealer);
		car.setId(carId);
		model.addAttribute("car", car);
		model.addAttribute("title", "Update Car");
		return "updateCar";
	}
	@RequestMapping(value = "/car/remove/{dealerId}/{carId}", method = RequestMethod.GET)
	private String deleteCar(@PathVariable(value = "dealerId") Long dealerId,@PathVariable(value = "carId") Long carId, Model model) {
		carService.deleteCar(carId);
		model.addAttribute("title", "remove car");
		return "removeCar";
}
	
	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car,Model model) {
		carService.addCar(car);
		model.addAttribute("title", "Add Car");
		return "add";
	}
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.POST)
	private String update(@ModelAttribute CarModel car,@PathVariable(value = "carId") Long carId, Model model) {
		carService.updateCar(carId, car.getAmount(), car.getBrand(), car.getPrice(), car.getType());
		model.addAttribute("title", "Update Car");
		return "updateCarBerhasil";
	}
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String deleteCar(Model model, @ModelAttribute DealerModel dealer) {
		for(CarModel car: dealer.getListCar()) {
			carService.deleteCar(car.getId());
		}
		model.addAttribute("title", "delete Car");
		return "removeCar";
	}
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addRow"})
	public String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
		model.addAttribute("title", "Add Car");
		if (dealer.getListCar() == null) {
            dealer.setListCar(new ArrayList<CarModel>());
        }
		dealer.getListCar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
	public String removeRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
		model.addAttribute("title", "Add Car");
	    final Integer row = Integer.valueOf(req.getParameter("removeRow"));
	    dealer.getListCar().remove(row.intValue());
	    
	    model.addAttribute("dealer", dealer);
	    return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params={"save"})
	private String addCarSubmit(@ModelAttribute DealerModel dealer, Model model) {
		model.addAttribute("title", "Add Car");
		DealerModel tambahDealer  = dealerService.getDealerDetailById(dealer.getId()).get();
		for(CarModel car: dealer.getListCar()) {
			car.setDealer(tambahDealer);
			carService.addCar(car);
		}
		return "add";
	}
	
}
