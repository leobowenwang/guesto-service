package guesto.event.repository;

import guesto.event.model.GuestList;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface GuestListRepository extends JpaRepository<GuestList, Long> {

}
