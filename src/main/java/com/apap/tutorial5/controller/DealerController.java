package com.apap.tutorial5.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

/**
 * @author DELL
 * DealerController
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService CarService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title","home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		model.addAttribute("title", "Add Dealer");
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer,Model model) {
		dealerService.addDealer(dealer);
		model.addAttribute("title", "Add dealer");
		return "add";
}
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		model.addAttribute("dealer", new DealerModel());
		model.addAttribute("title", "Update Dealer");
		return "updatedealer";
}
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.POST)
	private String updateDealer(@PathVariable(value = "dealerId") Long dealerId,@ModelAttribute DealerModel dealer,Model model) {
		dealerService.updateDealer(dealerId, dealer.getAlamat(), dealer.getNoTelp());
		model.addAttribute("title", "update dealer");
		return "updatedealerberhasil";
}
	
	@RequestMapping(value = "/dealer/remove/{dealerId}", method = RequestMethod.GET)
	private String deleteDealer(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealerService.deleteDealer(dealer);
		model.addAttribute("title", "remove dealer");
		return "remove";
}
	@RequestMapping(value = "/dealer/viewall", method = RequestMethod.GET)
	private String viewDealer(Model model) {
		List<DealerModel> banyakdealer = dealerService.getAllDealer();
		for(int x= 0;x<banyakdealer.size();x++) {
			List<CarModel> listCar  = banyakdealer.get(x).getListCar();
			Collections.sort(listCar, new SortCar());
		}
		model.addAttribute("listdealer",banyakdealer);
		model.addAttribute("title", "view all");
		return "viewall";
}
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealerSubmit(@RequestParam("dealerId") Long dealerId, Model model) {
		try {
			DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
			model.addAttribute("dealer", dealer);
			model.addAttribute("dealerId", dealerId);
			List<CarModel> listCar  = dealerService.getDealerDetailById(dealerId).get().getListCar();
			Collections.sort(listCar, new SortCar());
			dealer.setListCar(listCar);
			model.addAttribute("listCar", listCar);
			model.addAttribute("title", "view dealer");
			return "view-dealer";
		}
		catch(NoSuchElementException e) {
			model.addAttribute("title", "view dealer");
			return "notfound";
		}
}
	class SortCar implements Comparator<CarModel> 
	{ 
	    public int compare(CarModel a, CarModel b) 
	    { 
	    	Integer priceA = Integer.parseInt(a.getPrice());
	    	Integer priceB = Integer.parseInt(b.getPrice());
	    	
	        return priceA - priceB; 
	    } 
	} 
}
