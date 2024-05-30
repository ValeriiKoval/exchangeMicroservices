package com.microservice.orderservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order extends IdBasedEntity implements Serializable {

    private Long userId;
    private String companySymbol;
    @Column(columnDefinition = "DECIMAL")
    @Digits(integer = 10, fraction = 2)
    private Double price;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
