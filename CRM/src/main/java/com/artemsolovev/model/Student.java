package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "students")
public class Student extends User {

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Lesson> lessons;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    public Student(long id, @NonNull String login, @NonNull String password, @NonNull String fio,
                   @NonNull LocalDateTime regDate, @NonNull String phone, List<Lesson> lessons, @NonNull Teacher teacher) {
        super(id, login, password, fio, regDate, phone);
        this.lessons = lessons;
        this.teacher = teacher;
    }
}
