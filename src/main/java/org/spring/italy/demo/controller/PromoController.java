package org.spring.italy.demo.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Pizza;
import org.spring.italy.demo.pojo.Promozione;
import org.spring.italy.demo.serv.PizzaService;
import org.spring.italy.demo.serv.PromozioneServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/promo")
public class PromoController {

	@Autowired
	private PromozioneServ promoServ;
	@Autowired
	private PizzaService pizzaServ;
	
	@GetMapping
	public String index(Model model) {
		
		List<Promozione> promos = promoServ.findAll();
		model.addAttribute("promos", promos);
		
		
		return "promo/index";
	}
	
	
	@GetMapping("/create")
	public String createPromo(Model model) {
		
		Promozione promo = new Promozione();		
		model.addAttribute("promo", promo);
		List<Pizza> pizze = pizzaServ.findAll();
		model.addAttribute("pizze", pizze);
		
		return "promo/promo-create";
	}
	
	
	@PostMapping("/create")
	public String storeBook(
				@Valid Promozione promo
			) {
	
		List<Pizza> listOfPizzas = promo.getListOfPizzas();
		
		for(Pizza pizza : listOfPizzas) {
			
			pizza.setPromo(promo);
		}
		
		promoServ.save(promo);
		
		return "redirect:/promo";
	}
}
