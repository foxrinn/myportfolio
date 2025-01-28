package com.artemsolovev.crmclient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private long id;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private String fio;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime regDate = LocalDateTime.now();

    @NonNull
    private String phone;

    private String role;
}
