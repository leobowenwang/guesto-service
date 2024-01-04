package guesto.event.repository;

import guesto.event.model.Event;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByEventName(String eventName);

}
