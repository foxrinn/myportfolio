package com.artemsolovev.crmclient.model;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Subject {

    private long id;

    private Teacher teacher;

    @NonNull
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
