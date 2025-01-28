package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "response_code",
        "reg_date",
        "results"
})

@Entity
@Data
@Table(name = "quizes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long id;

    @JsonProperty("response_code")
    private Integer responseCode;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonProperty("reg_date")
    private LocalDateTime regDate = LocalDateTime.now();

    @JsonProperty("results")
    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Result> results;

    @ManyToOne
    @JoinColumn(name = "quiz_user_id", nullable = false)
    private QuizUser quizUser;
}
