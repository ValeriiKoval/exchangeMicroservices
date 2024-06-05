package com.microservice.orderservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shares")
@Data
@NoArgsConstructor
public class Share extends IdBasedEntity implements Serializable {

    private Long userId;
    private String companySymbol;
    private Long amount;
}
