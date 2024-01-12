package guesto.event.repository;

import guesto.event.model.GuestList;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface GuestListRepository extends JpaRepository<GuestList, Long> {
    @Query("SELECT gl FROM GuestList gl WHERE gl.event.id = :eventId")
    Optional<GuestList> findByEventId(Long eventId);
}
