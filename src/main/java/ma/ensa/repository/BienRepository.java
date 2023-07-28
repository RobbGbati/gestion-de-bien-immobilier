package ma.ensa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ensa.model.Bien;

public interface BienRepository extends JpaRepository<Bien, Long> {

}
