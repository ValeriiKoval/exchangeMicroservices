package com.microservice.walletservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
public class Wallet extends IdBasedEntity implements Serializable {

    @Column(unique = true, nullable = false)
    private Long userId;

    @Min(0)
    private Long balance;
}
