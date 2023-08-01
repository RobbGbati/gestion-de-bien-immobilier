package ma.ensa.repository;

import org.springframework.data.repository.CrudRepository;

import ma.ensa.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

}
