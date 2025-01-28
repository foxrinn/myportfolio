package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private double investPrice;

    @Column(nullable = false)
    @NonNull
    private double expectedPrice;

    @Column(nullable = false)
    @NonNull
    private int years;

    @Column(nullable = false)
    @NonNull
    private String comment;

    @Column(nullable = false)
    private Solution solution = Solution.UNWATCHED;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private InvestProperty investProperty;
}
