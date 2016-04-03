package com.codeaim.urlcheck.common.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.codeaim.urlcheck.common.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>
{
    @Query(" SELECT r " +
            "FROM Result r " +
            "WHERE r.created <= :expiryDate " +
            "   AND (r.changed = false " +
            "       OR r.confirmation = false)"
    )
    List<Result> findExpired(
            @Param("expiryDate") LocalDateTime expiryDate
    );

    @Query(
            value =
            "SELECT r " +
            "FROM Result r " +
            "   LEFT JOIN FETCH r.check " +
            "   LEFT JOIN FETCH r.previous " +
            "WHERE r.previous.id = :resultId",
            countQuery =
            "SELECT r " +
            "FROM Result r " +
            "WHERE r.previous.id = :resultId"
    )
    List<Result> findByPrevious(
            @Param("resultId") Long resultId
    );

    Page<Result> findByCheckId(Long checkId, Pageable pageRequest);
}

