package com.codeaim.urlcheck.common.repository;

import com.codeaim.urlcheck.common.model.Contact;
import com.codeaim.urlcheck.common.model.ContactEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactEmailRepository extends JpaRepository<ContactEmail, Long>
{
    Page<ContactEmail> findByCheckId(Long checkId, Pageable pageRequest);
}

