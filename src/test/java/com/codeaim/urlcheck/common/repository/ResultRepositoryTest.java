package com.codeaim.urlcheck.common.repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.codeaim.urlcheck.common.model.*;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codeaim.urlcheck.common.Application;

@RunWith(SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(Application.class)
@Configuration
public class ResultRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Test
    public void findAll() {
        List<Result> results = resultRepository.findAll();
        Assert.assertNotNull(results);
    }

    @Test
    public void findExpired() {
        List<Result> expired = resultRepository.findExpired(LocalDateTime.now(ZoneOffset.UTC));
        Assert.assertNotNull(expired);
    }

    @Test
    public void findByCheckId() {

        User parent = userRepository.save(User.builder()
            .accessToken("testAccessToken")
            .email("test@test.com")
            .name("testUser")
            .password("testPassword")
            .resetToken("testResetToken")
            .roles(Sets.newHashSet("admin", "verified", "registered"))
            .build());

        Check parentCheck = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        resultRepository.save(Result.builder()
                .probe("testProbe")
                .changed(true)
                .confirmation(true)
                .check(parentCheck)
                .responseTime(15)
                .status(Status.UP)
                .statusCode(200)
                .build());

        Page<Result> results = resultRepository.findByCheckId(parent.getId(), null);
        Assert.assertEquals(parent.getId(), results.getContent().get(0).getCheck().getId());
        userRepository.delete(parent);
    }

    @Test
    public void create() {
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check parentCheck = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        Result saved = resultRepository.save(Result.builder()
                .probe("testProbe")
                .changed(true)
                .confirmation(true)
                .check(parentCheck)
                .responseTime(15)
                .status(Status.UP)
                .statusCode(200)
                .build());

        Assert.assertNotNull(saved);
        userRepository.delete(parent);
    }

    @Test
    public void delete() {
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check parentCheck = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        Result saved = resultRepository.save(Result.builder()
                .probe("testProbe")
                .changed(true)
                .confirmation(true)
                .check(parentCheck)
                .responseTime(15)
                .status(Status.UP)
                .statusCode(200)
                .build());

        resultRepository.delete(saved);
        userRepository.delete(parent);
    }

    @Test
    public void deleteParentCheck() {
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check parentCheck = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        resultRepository.save(Result.builder()
                .probe("testProbe")
                .changed(true)
                .confirmation(true)
                .check(parentCheck)
                .responseTime(15)
                .status(Status.UP)
                .statusCode(200)
                .build());

        checkRepository.delete(parentCheck);
        List<Result> results = resultRepository.findByCheckId(parentCheck.getId(), null).getContent();
        Assert.assertTrue(results.isEmpty());
        userRepository.delete(parent);
    }
}
