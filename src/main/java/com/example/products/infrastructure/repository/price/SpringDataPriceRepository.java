package com.example.products.infrastructure.repository.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p "
            + "FROM prices AS p "
            + "WHERE (:applicationDate IS NULL OR :applicationDate BETWEEN p.startDate AND p.endDate) "
            + "AND (:productId IS NULL OR p.productId = :productId) "
            + "AND (:brandId IS NULL OR p.brandId = :brandId)"
    )
    List<PriceEntity> findPriceByParameters(LocalDateTime applicationDate, Long productId, Long brandId);
}
