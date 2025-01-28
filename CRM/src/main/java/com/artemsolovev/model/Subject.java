package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Lesson> lessons;
}
