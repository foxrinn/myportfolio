package com.artemsolovev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invest_properties")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class InvestProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private double roa;
    @NonNull
    private double annualROI;
    @NonNull
    private double fullROI;
    @NonNull
    private double totalIncome;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDate;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDate;

    @NonNull
    @OneToOne
    @MapsId
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "request_id")
    private Request request;
}
