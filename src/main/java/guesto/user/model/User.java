package guesto.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id,
        String username,
        String password

) {

}
