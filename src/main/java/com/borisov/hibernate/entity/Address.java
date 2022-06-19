package com.borisov.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Address {

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;
}
