package org.delcom.app.repositories;

import org.delcom.app.entities.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CashFlowRepository extends JpaRepository<CashFlow, UUID> {

    @Query("SELECT c FROM CashFlow c WHERE " +
           "LOWER(c.type) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.source) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.label) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CashFlow> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT c.label FROM CashFlow c ORDER BY c.label ASC")
    List<String> findDistinctLabels();
}