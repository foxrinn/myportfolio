package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "quiz_users")
public class QuizUser extends User {

    @OneToMany(mappedBy = "quizUser",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Quiz> quizList = new ArrayList<>();

    @OneToMany(mappedBy = "quizUser",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Answer> answers;

    public QuizUser(long id, @NonNull String login, @NonNull String password, @NonNull String fio,
                 @NonNull LocalDateTime regDate, List<Quiz> quizList) {
        super(id, login, password, fio, regDate);
        this.quizList = quizList;
    }
}
