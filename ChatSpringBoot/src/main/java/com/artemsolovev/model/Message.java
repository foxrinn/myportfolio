package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String message;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime sendDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
