package com.springdisplay.springwebdisplay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@PropertySource("classpath:application.properties")
public class DisplayController {

	@Value("${pets.list.uri}")
	private String petsListUri;

	@RequestMapping(value="/getPetsList", method=RequestMethod.GET)
	private String getPets(Model model) {
		
		model.addAttribute("pets", new Pets());
		RestTemplate restTemplate = new RestTemplate();
//		Pets[] result = restTemplate.getForObject(petsListUri, Pets[].class);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		System.out.println("petsListUri: "+petsListUri);
		ResponseEntity<Pets[]> response = restTemplate.exchange(petsListUri, HttpMethod.GET, request, Pets[].class);
		model.addAttribute("response", response.getBody());
		System.out.println(response.getBody());
		return "pets";
	}

	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	private String removePets(@PathVariable String id, Model model) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		System.out.println("petsListUri: "+petsListUri);
		ResponseEntity<String> response = restTemplate.exchange(petsListUri+id, HttpMethod.DELETE, request, String.class);
		//model.addAttribute("message", "Record deleted successfully");
		System.out.println(response.getBody());
		return "redirect:/getPetsList";
	}
	
	@RequestMapping(value="/savePets", method=RequestMethod.POST)
	private String editPets(Pets pets, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/getPetsList";
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pets> request = new HttpEntity<Pets>(pets, headers);
		System.out.println("petsListUri: "+petsListUri);
		ResponseEntity<String> response = restTemplate.exchange(petsListUri, HttpMethod.POST, request, String.class);
		//model.addAttribute("message", "Record added successfully");
		System.out.println(response.getBody());
		return "redirect:/getPetsList";
	}
}
