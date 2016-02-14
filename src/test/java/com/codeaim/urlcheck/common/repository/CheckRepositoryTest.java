package com.codeaim.urlcheck.common.repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.codeaim.urlcheck.common.model.Check;
import com.codeaim.urlcheck.common.model.State;
import com.codeaim.urlcheck.common.model.Status;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codeaim.urlcheck.common.Application;
import com.codeaim.urlcheck.common.model.User;

@RunWith(SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(Application.class)
@Configuration
public class CheckRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CheckRepository checkRepository;

    @Test
    public void findAll() {
        List<Check> checks = checkRepository.findAll();
        Assert.assertNotNull(checks);
    }

    @Test
    public void findElectable() {
        Page<Check> saved = checkRepository.findElectable("", false, LocalDateTime.now(ZoneOffset.UTC), null);
        Assert.assertNotNull(saved);
    }

    @Test
    public void findByUserId() {
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check saved = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        Page<Check> checks = checkRepository.findByUserId(parent.getId(), null);
        Assert.assertEquals(parent.getId(), checks.getContent().get(0).getUser().getId());
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

        Check saved = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());
        Assert.assertNotNull(saved);
        userRepository.delete(parent);
    }

    @Test
    public void update() {
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check saved = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        Check updated = checkRepository.save(Check.buildFrom(saved).build());
        Assert.assertEquals(2, updated.getVersion());
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

        Check saved = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        checkRepository.delete(saved);
        userRepository.delete(parent);
    }

    @Test
    public void deleteParentUser() {
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        userRepository.delete(parent);
        List<Check> checks = checkRepository.findByUserId(parent.getId(), null).getContent();
        Assert.assertTrue(checks.isEmpty());
    }

    @Test
    public void getCheckUserName()
    {
        String userName = "testUser";
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name(userName)
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check saved = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        Check check = checkRepository.findOne(saved.getId());
        Assert.assertEquals(userName, check.getUser().getName());
        userRepository.delete(parent);
    }

    @Test
    public void getNewCheckUserName()
    {
        String userName = "testUser";
        User parent = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name(userName)
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet("admin", "verified", "registered"))
                .build());

        Check saved = checkRepository.save(Check.builder()
                .probe("testProbe")
                .interval(1)
                .name("testCheck")
                .state(State.ELECTED)
                .status(Status.UP)
                .user(parent)
                .url("http://www.test.com")
                .build());

        Check check = checkRepository.findOne(saved.getId());
        Check newCheck = Check.buildFrom(check).build();
        Assert.assertEquals(userName, newCheck.getUser().getName());
        userRepository.delete(parent);
    }
}
