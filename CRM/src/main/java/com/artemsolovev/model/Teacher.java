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
@Table(name = "teachers")
public class Teacher extends User {

    private int experience;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<WorkDaySchedule> workDaySchedules;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JsonIgnore
    private List<Subject> subjects;

    public Teacher(long id, @NonNull String login, @NonNull String password, @NonNull String fio,
                   @NonNull LocalDateTime regDate, @NonNull String phone, int experience, List<Student> students,
                   List<Lesson> lessons, List<WorkDaySchedule> workDaySchedules, List<Subject> subjects) {
        super(id, login, password, fio, regDate, phone);
        this.experience = experience;
        this.students = students;
        this.lessons = lessons;
        this.workDaySchedules = workDaySchedules;
        this.subjects = subjects;
    }
}
