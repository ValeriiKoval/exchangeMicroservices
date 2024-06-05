package com.microservice.orderservice.repository;

import com.microservice.orderservice.model.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long> {

    Optional<Share> findByUserIdAndCompanySymbol(Long userId, String companySymbol);

    List<Share> findAllByUserIdAndAmountGreaterThan(Long userId, Long amountGreaterThan);

}
