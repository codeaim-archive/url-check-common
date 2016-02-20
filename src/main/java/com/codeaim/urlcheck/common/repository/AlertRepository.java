package com.codeaim.urlcheck.common.repository;

import com.codeaim.urlcheck.common.model.Alert;
import com.codeaim.urlcheck.common.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long>
{
    Page<Alert> findByUserId(Long userId, Pageable pageRequest);

    Page<Alert> findByCheckId(Long checkId, Pageable pageRequest);
}

