package org.ow2.proactive.microservice_template.model;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class User {
    private long id;
    private String name;
    private int age;
    private double salary;
}
