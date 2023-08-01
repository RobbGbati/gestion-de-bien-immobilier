package ma.ensa.repository;

import org.springframework.data.repository.CrudRepository;

import ma.ensa.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

	public Client findByEmail (String email); 
	
	public Client findByName(String name);

}
