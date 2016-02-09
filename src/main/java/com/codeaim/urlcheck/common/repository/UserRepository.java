package com.codeaim.urlcheck.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeaim.urlcheck.common.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);
}
