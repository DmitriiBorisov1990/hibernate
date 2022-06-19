package com.borisov.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_lang",schema = "employee_storage")
public class EmployeeLang implements BaseEntity<EmployeeLang.EmployeeLangId>{

    @EmbeddedId
    private EmployeeLangId id;

    private String description;

    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeLangId implements Serializable{

        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "lang")
        private String lang;
    }
}
