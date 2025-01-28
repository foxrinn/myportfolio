package com.artemsolovev.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @NonNull
    @Column(nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quiz_user_id", nullable = false)
    private QuizUser quizUser;

    private boolean isCorrect;
}
