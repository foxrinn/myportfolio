package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NonNull
    private String telegramUserName;

    @Column(nullable = false)
    @NonNull
    private long idChat;

    private String login;

    private String name;

    private String age;

    private String step;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<ComplimentUser> compliments;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<HistoryUser> historyUserList;
}
