package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "apprentices")
public class Apprentice extends User {

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Unacceptable phone format")
    private String phoneNumber;

    @OneToMany(mappedBy = "apprentice",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Training> trainings;

    public Apprentice(long id, @NonNull String login, @NonNull String password, @NonNull String surname,
                      @NonNull String name, @NonNull String patronymic, @NonNull LocalDateTime regDate,
                      String phoneNumber, List<Training> trainings) {
        super(id, login, password, surname, name, patronymic, regDate);
        this.phoneNumber = phoneNumber;
        this.trainings = trainings;
    }
}
