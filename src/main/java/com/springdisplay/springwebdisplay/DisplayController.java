package com.springdisplay.springwebdisplay;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
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

import com.springdisplay.springwebdisplay.model.Pets;
//import com.springdisplay.springwebdisplay.service.PetsService;

@Controller
@PropertySource("classpath:application.properties")
public class DisplayController {

//	ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/dispatcher-servlet.xml");
//    PetsService petsService = context.getBean("petsService",PetsService.class);
    
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
		System.out.println("petsListUri: "+ petsListUri);
		System.out.println("petsListUri: "+ System.getenv("PETS_SERVICE"));
		ResponseEntity<Pets[]> response = restTemplate.exchange(getPetsApiURL(), HttpMethod.GET, request, Pets[].class);
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
		System.out.println("petsListUri: "+getPetsApiURL());
		ResponseEntity<String> response = restTemplate.exchange(getPetsApiURL()+id, HttpMethod.DELETE, request, String.class);
		//model.addAttribute("message", "Record deleted successfully");
		return "redirect:/getPetsList";
	}
	
	@RequestMapping(value="/savePets", method=RequestMethod.POST)
	private String addPets(Pets pets, BindingResult bindingResult, Model model) {
		System.out.println("Inside addPets");
		if (bindingResult.hasErrors()) {
			return "redirect:/getPetsList";
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pets> request = new HttpEntity<Pets>(pets, headers);
		ResponseEntity<String> response = restTemplate.exchange(getPetsApiURL(), HttpMethod.POST, request, String.class);
		System.out.println("Inside addPets"+response);
		//model.addAttribute("message", "Record added successfully");
		return "redirect:/getPetsList";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	private String editPets(@PathVariable String id, Model model) {
		System.out.println("Inside editPets");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		ResponseEntity<Pets> responsePets = restTemplate.exchange(getPetsApiURL()+id, HttpMethod.GET, request, Pets.class);
		model.addAttribute("pets", responsePets.getBody());
		
		ResponseEntity<Pets[]> response = restTemplate.exchange(getPetsApiURL(), HttpMethod.GET, request, Pets[].class);
		model.addAttribute("response", response.getBody());
		return "pets";
	}
	
	@RequestMapping(value="/edit/save", method=RequestMethod.POST)
	private String editSavePets(Pets pets, BindingResult bindingResult, Model model) {
		System.out.println("Inside editSavePets: "+pets);
		if (bindingResult.hasErrors()) {
			return "redirect:/getPetsList";
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pets> request = new HttpEntity<Pets>(pets, headers);
		ResponseEntity<String> response = restTemplate.exchange(getPetsApiURL()+pets.get_id(), HttpMethod.PUT, request, String.class);
		//model.addAttribute("message", "Record added successfully");
		return "redirect:/getPetsList";
	}
	
	private String getPetsApiURL() {
		String petsApiURL = petsListUri;
		if(System.getenv("PETS_SERVICE") != null) {
			petsApiURL = petsListUri.replaceFirst("localhost", System.getenv("PETS_SERVICE"));
		}
		return petsApiURL;
	}
}
