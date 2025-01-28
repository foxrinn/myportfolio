package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "trainers")
public class Trainer extends User {

    @Positive(message = "Experience must be positive")
    private int experience;

    @Email(message = "Unacceptable email format")
    private String email;

    @OneToMany(mappedBy = "trainer",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Training> trainings;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "trainer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TrainerSchedule trainerSchedule;

    public Trainer(long id, @NonNull String login, @NonNull String password, @NonNull String surname, @NonNull String name,
                   @NonNull String patronymic, @NonNull LocalDateTime regDate, int experience, String email,
                   List<Training> trainings, TrainerSchedule trainerSchedule) {
        super(id, login, password, surname, name, patronymic, regDate);
        this.experience = experience;
        this.email = email;
        this.trainings = trainings;
        this.trainerSchedule = trainerSchedule;
    }
}
