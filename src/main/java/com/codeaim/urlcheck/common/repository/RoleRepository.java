package com.codeaim.urlcheck.common.repository;

import com.codeaim.urlcheck.common.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
}

