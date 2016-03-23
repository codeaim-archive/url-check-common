package com.codeaim.urlcheck.common.repository;

import com.codeaim.urlcheck.common.Application;
import com.codeaim.urlcheck.common.model.Role;
import com.codeaim.urlcheck.common.model.User;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(Application.class)
@Configuration
public class UserRepositoryTest
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllUsers() {
        List<User> users = userRepository.findAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void findByEmailUser() {
        Set<Role> roles = Sets.newHashSet(roleRepository.save(Sets.newHashSet(
                Role.builder().name("admin").build(),
                Role.builder().name("verified").build(),
                Role.builder().name("registered").build())));

        String email = "test@test.com";
        userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email(email)
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(roles)
                .build());
        User user = userRepository.findByEmail(email);
        Assert.assertEquals(email, user.getEmail());
        userRepository.delete(user);
    }

    @Test
    public void createUser() {
        Set<Role> roles = Sets.newHashSet(roleRepository.save(Sets.newHashSet(
                Role.builder().name("admin").build(),
                Role.builder().name("verified").build(),
                Role.builder().name("registered").build())));

        User saved = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(roles)
                .build());

        Assert.assertNotNull(saved);
        userRepository.delete(saved);
    }

    @Test
    public void updateUser() {
        Set<Role> roles = Sets.newHashSet(roleRepository.save(Sets.newHashSet(
                Role.builder().name("admin").build(),
                Role.builder().name("verified").build(),
                Role.builder().name("registered").build())));

        User saved = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(roles)
                .build());

        User updated = userRepository.save(User.buildFrom(saved).build());
        Assert.assertEquals(2, updated.getVersion());
        userRepository.delete(saved);
    }

    @Test
    public void deleteUser() {
        Set<Role> roles = Sets.newHashSet(roleRepository.save(Sets.newHashSet(
                Role.builder().name("admin").build(),
                Role.builder().name("verified").build(),
                Role.builder().name("registered").build())));

        User saved = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(roles)
                .build());

        userRepository.delete(saved);
    }

    @Test
    @Transactional(readOnly = true)
    public void getRolesUser()
    {
        Set<Role> roles = Sets.newHashSet(roleRepository.save(Sets.newHashSet(
                Role.builder().name("admin").build(),
                Role.builder().name("verified").build(),
                Role.builder().name("registered").build())));

        User saved = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet(roles))
                .build());

        User user = userRepository.findOne(saved.getId());
        Assert.assertEquals(roles, user.getRoles());
        userRepository.delete(saved);
    }

    @Test
    @Transactional(readOnly = true)
    public void getNewUserRolesUser()
    {
        Set<Role> roles = Sets.newHashSet(roleRepository.save(Sets.newHashSet(
                Role.builder().name("admin").build(),
                Role.builder().name("verified").build(),
                Role.builder().name("registered").build())));

        User saved = userRepository.save(User.builder()
                .accessToken("testAccessToken")
                .email("test@test.com")
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(roles)
                .build());

        User user = userRepository.findOne(saved.getId());
        User newUser = User.buildFrom(user).build();
        Assert.assertEquals(roles, newUser.getRoles());
        userRepository.delete(saved);
    }
}
