package com.borisov.hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "name")
@Table(name = "employee", schema = "employee_storage")
public class Employee implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    //@Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Transient
    private boolean adult;

    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street"))
    })
    private Address workAddress;

    public Employee(String name) {
        this.name = name;
    }
}
