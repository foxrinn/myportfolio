package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "compliment_user")
public class ComplimentUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String compliment;

    @Column(nullable = false)
    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime complimentTime = LocalDateTime.now();

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
