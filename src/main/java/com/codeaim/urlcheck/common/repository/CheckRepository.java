package com.codeaim.urlcheck.common.repository;

import java.time.LocalDateTime;

import com.codeaim.urlcheck.common.model.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long>
{
    @Query(
            value =
            "SELECT c " +
            "FROM Check c " +
            "   LEFT JOIN FETCH c.user " +
            "   LEFT JOIN FETCH c.latestResult " +
            "WHERE ((c.state = com.codeaim.urlcheck.common.model.State.WAITING " +
            "           AND c.refresh <= :currentDate) " +
            "       OR (c.state = com.codeaim.urlcheck.common.model.State.ELECTED " +
            "           AND c.locked <= :currentDate)) " +
            "   AND ((:isClustered = false) " +
            "       OR (c.confirming = false)" +
            "       OR  (:isClustered = true " +
            "           AND c.probe <> :probe ))",
            countQuery =
            "SELECT COUNT(c) " +
            "FROM Check c " +
            "WHERE ((c.state = com.codeaim.urlcheck.common.model.State.WAITING " +
            "           AND c.refresh <= :currentDate) " +
            "       OR (c.state = com.codeaim.urlcheck.common.model.State.ELECTED " +
            "           AND c.locked <= :currentDate)) " +
            "   AND ((:isClustered = false) " +
            "       OR (c.confirming = false)" +
            "       OR  (:isClustered = true " +
            "           AND c.probe <> :probe ))"
    )
    Page<Check> findElectable(
            @Param("probe") String probe,
            @Param("isClustered") boolean isClustered,
            @Param("currentDate") LocalDateTime currentDate,
            Pageable pageRequest
    );

    Page<Check> findByUserId(Long userId, Pageable pageRequest);
}
