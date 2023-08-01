package ma.ensa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensa.model.AchatLocation;
import ma.ensa.repository.AchatRepository;

@Service
public class AchatService {

	@Autowired
	private AchatRepository achatRepo;
	
	public void save(AchatLocation achat) {
		achatRepo.save(achat);
	}
}
