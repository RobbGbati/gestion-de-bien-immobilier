package ma.ensa.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ma.ensa.model.Bien;
import ma.ensa.repository.BienRepository;

@Service
public class BienService {

	@Autowired
	private BienRepository bienRepo;
	
	public Bien getBienById(Long id)  {
		Bien bien = bienRepo.findById(id).orElse(null);
		if(bien==null) {
			throw new RuntimeException("Bien avec id= "+id+"non disponible");
		}
		else return bien;
	}
	
	public void save(Bien bien) {
		bienRepo.save(bien);
	}
	
}
