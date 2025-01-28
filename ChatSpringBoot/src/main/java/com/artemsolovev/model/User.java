package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
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
    private String login;

    @Column(nullable = false)
    @NonNull
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Message> messages;

}
