package ma.ensa.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ma.ensa.model.AchatLocation;
import ma.ensa.model.Bien;
import ma.ensa.model.Client;
import ma.ensa.model.Contact;
import ma.ensa.model.ContactForm;
import ma.ensa.model.CritereRecherche;
import ma.ensa.repository.ClientRepository;
import ma.ensa.repository.ContactRepository;
import ma.ensa.service.AchatService;
import ma.ensa.service.BienService;

@Controller
@SessionAttributes("name")
@RequestMapping("/biens")
public class BienController {

	@Autowired
	private BienService bienService;
	
	@Autowired
	private ContactRepository contactRepo;
	
	@Autowired
	private AchatService achatService;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@GetMapping("/{bienId}/voir+")
	public String getBienById(Model model,@PathVariable long bienId) {
		Bien bien = bienService.getBienById(bienId);
		model.addAttribute("contactForm",new ContactForm());
		model.addAttribute("bien",bien);
		return "vue-bien";
	}
	
	@GetMapping("/{bienId}/voir-mon-bien")
	public String getBienClientById(Model model,@PathVariable long bienId) {
		Bien bien = bienService.getBienById(bienId);
		List<Contact> c = contactRepo.findByBien(bien);
		model.addAttribute("msg",c);
		model.addAttribute("contactForm",new ContactForm());
		model.addAttribute("bien",bien);
		return "vue-bien-client";
	}
	
	@PostMapping("/{bienId}/contact")
	public String contactBien(@Valid @ModelAttribute("contactForm") ContactForm cf, BindingResult
			result,@PathVariable long bienId) {
		
		Contact msg = new Contact();
		msg.setNom(cf.getNom());
		msg.setEmail(cf.getEmail());
		msg.setMessage(cf.getMessage());
		msg.setBien(bienService.getBienById(bienId));
		contactRepo.save(msg);
		System.out.println("sauvegarde du contact");
		
		return "redirect:/biens/{bienId}/voir+";
	}
	
	@GetMapping("/{bienId}/acheter")
	public String acheterBien(@PathVariable long bienId, Model model,@ModelAttribute("name") String name) {
			Bien bien = bienService.getBienById(bienId);
			AchatLocation monAchatOuLocation = new AchatLocation();
			Client c = clientRepo.findByName(name);
			monAchatOuLocation.setBienAcheteLocation(bien);
			monAchatOuLocation.setDateAchatLocation(new Date());
			achatService.save(monAchatOuLocation);
			c.getMesAchatsLocations().add(monAchatOuLocation);
			//mise à jour du client dans la BDD
			clientRepo.save(c);
			bien.setStatus("Acheté");
			//mise à jour du statut dans la base de données
			bienService.save(bien);
			//model.addAttribute("newStatus","Acheté");
			return "redirect:/biens/{bienId}/voir+";
		
	}
	
	@GetMapping("/{bienId}/louer")
	public String louerBien(@PathVariable long bienId, @ModelAttribute("name") String name) {

			Bien bien = bienService.getBienById(bienId);
			AchatLocation monAchat = new AchatLocation();
			Client c = clientRepo.findByName(name);
			monAchat.setBienAcheteLocation(bien);
			monAchat.setDateAchatLocation(new Date());
			achatService.save(monAchat);
			c.getMesAchatsLocations().add(monAchat);
			//mise à jour du client dans la BDD
			clientRepo.save(c);
			bien.setStatus("Loué");
			//mise à jour du statut dans la base de données
			bienService.save(bien);
			//model.addAttribute("newStatus","Acheté");
			return "redirect:/biens/{bienId}/voir+";
		
		
	}
	
	
}
