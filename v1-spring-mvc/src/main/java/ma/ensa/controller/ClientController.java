package ma.ensa.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ma.ensa.model.AchatLocation;
import ma.ensa.model.Bien;
import ma.ensa.model.Client;
import ma.ensa.model.CritereRecherche;
import ma.ensa.model.LoginForm;
import ma.ensa.repository.BienRepository;
import ma.ensa.repository.ClientRepository;
import ma.ensa.repository.CritèreRechercheRepository;
import ma.ensa.service.BienService;

@Controller
@SessionAttributes("name")

public class ClientController {
	
	@Autowired
	private BienService bienService;
	
	@Autowired
	CritèreRechercheRepository criteria;
	
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private BienRepository bienRepo;
	
	@Autowired
	 JobLauncher jobLauncher;

	 @Autowired
	 Job job4;
	 
	 public JobParameters params = new JobParametersBuilder()
             .addString("client", "client")
             .addString("ville", "ville")
             .addLong("montant", (long) 1200)
             .toJobParameters();
	
	private static final Logger logger= Logger.getLogger(ClientController.class);
	
	@GetMapping("/inscription")
	public String inscription(Model model) {
		model.addAttribute("client",new Client());
		return "inscription";
	}
	
	@GetMapping("/accueil")
	public String getAccueil(Model model, HttpServletRequest req) {
		
		int  page=0;
		int size=6;
		if(req.getParameter("page")!=null && !req.getParameter("page").isEmpty()) {
			page = Integer.parseInt(req.getParameter("page"))-1;
		}
		if(req.getParameter("size")!=null && !req.getParameter("size").isEmpty()) {
			size = Integer.parseInt(req.getParameter("size"));
		}
		model.addAttribute("client",new Client());
		model.addAttribute("biens",bienRepo.findAll(PageRequest.of(page, size)));
		return "accueil";
	}
	
	@GetMapping(value= {"/","/connexion"})
	public String seConnecter(Model model) {
		model.addAttribute("loginForm",new LoginForm());
		return "connexion";
	}
	
	@GetMapping("/deconnexion")
	public String seDeconecter(HttpServletRequest req,Model model) {
		HttpSession session = req.getSession(false);
		if(session !=null)
			session.invalidate();
		System.out.println("Déconnexion de l'application");
		model.addAttribute("name",null);
		model.addAttribute("loginForm",new LoginForm());
		return "connexion";
		
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginForm") LoginForm l,BindingResult result,Model model,
			HttpServletRequest req) {
		String email =  clientRepo.findByEmail(l.getEmail()).getEmail();
		String password =  clientRepo.findByEmail(l.getEmail()).getPassword();
		String name=clientRepo.findByEmail(l.getEmail()).getName();
		
		if(l!=null && l.getEmail().equals(email) && l.getPassword().equals(password) && result.hasErrors()!=true){
			logger.info("Connexion réussie");
			model.addAttribute("name",name);
			model.addAttribute("biens",bienRepo.findAll());
			return "redirect:/accueil";
		}
		else {
			return "connexion";
		}
		
	}
	
	@PostMapping("/addClient")
	public String ajoutClient(@Valid @ModelAttribute("client") Client c,BindingResult result, Model model, HttpServletRequest req ) {
		if(result.hasErrors())
			return "inscription";
		
		//save the client
		clientRepo.save(c);
		model.addAttribute("name",c.getName());
		return "redirect:accueil";
	}
	
	
	@GetMapping("/mes-biens")
	public String biensClient(Model model,@ModelAttribute("name")String clienName) {
		double som1=0,som2=0;
		model.addAttribute("mesBiens",clientRepo.findByName(clienName).getMesBiens());
		//valeur des biens
		for(Bien bien:clientRepo.findByName(clienName).getMesBiens()) {
			som1+=bien.getMontant();
		}
		
		//montant dépensé pour et l'achat et la location
		for(AchatLocation var:clientRepo.findByName(clienName).getMesAchatsLocations()) {
			som2+=var.getBienAcheteLocation().getMontant();
		}
	
		model.addAttribute("som1",som1);
		model.addAttribute("som2",som2);
		model.addAttribute("mesAchatsLocations",clientRepo.findByName(clienName).getMesAchatsLocations());
		return "mes-biens";
	}
	
	
	//Ajout bien et recherche selon un critère
	
	@GetMapping("/bien")
	public String bien(Model model) {
		model.addAttribute("bien",new Bien());
		return "bien";
	}
	
	@PostMapping("/addBien")
	public String addBien(Bien b,@ModelAttribute("name") String name,BindingResult result, Model model, HttpServletRequest req ) {
		if(result.hasErrors())
			return "bien";
		
		//save bien
		b.setDate_ajout(new Date());
		bienService.save(b);
		
		//update client
		Client c = clientRepo.findByName(name);
		c.getMesBiens().add(b);
		clientRepo.save(c);
		
		return "redirect:accueil";
	}
	
		
	@GetMapping("/critere")
	public String search(Model model) {
		model.addAttribute("critereRecherche",new CritereRecherche());
		return "critere";
	}

	@PostMapping("/addSearch")
	public String rechercher(CritereRecherche cr,@ModelAttribute("name") String name,BindingResult result, Model model, HttpServletRequest req ) {
		if(result.hasErrors())
			return "bien";
		
		//save Criteria
		cr.setDate_rech(new Date());
		criteria.save(cr);
		
		//update client
		Client c = clientRepo.findByName(name);
		c.getCriteres().add(cr);
		clientRepo.save(c);
		 
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("client",  clientRepo.findByName(name).getEmail())
				.addString("ville", cr.getParVille())
				.addLong("montant", (long) cr.getParPrix())
				.toJobParameters();
		
		try {
			jobLauncher.run(job4, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:accueil";
		
	}
	
}
