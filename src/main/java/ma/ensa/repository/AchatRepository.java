package ma.ensa.repository;

import org.springframework.data.repository.CrudRepository;

import ma.ensa.model.AchatLocation;

public interface AchatRepository extends CrudRepository<AchatLocation, Long> {

}
