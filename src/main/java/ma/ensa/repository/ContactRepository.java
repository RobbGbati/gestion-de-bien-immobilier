package ma.ensa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ma.ensa.model.Bien;
import ma.ensa.model.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {
	public List<Contact> findByBien(Bien bien);
}
