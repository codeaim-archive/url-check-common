package com.codeaim.urlcheck.common.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codeaim.urlcheck.common.Application;
import com.codeaim.urlcheck.common.model.User;

@RunWith(SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(Application.class)
@Configuration
public class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllUsers() {
        List<User> users = userRepository.findAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void findByEmailUser() {
        String email = "test@test.com";
        userRepository.save(User.builder().email(email).build());
        User user = userRepository.findByEmail(email);
        Assert.assertEquals(email, user.getEmail());
    }

    @Test
    public void createUser() {
        User saved = userRepository.save(User.builder().build());
        Assert.assertNotNull(saved);
    }

    @Test
    public void updateUser() {
        User saved = userRepository.save(User.builder().build());
        User updated = userRepository.save(User.buildFrom(saved).build());
        Assert.assertEquals(2, updated.getVersion());
    }

    @Test
    public void deleteUser() {
        User saved = userRepository.save(User.builder().build());
        userRepository.delete(saved);
    }

    @Test
    public void getRolesUser()
    {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("verified");
        roles.add("registered");

        User saved = userRepository.save(User.builder().roles(roles).build());
        User user = userRepository.findOne(saved.getId());

        Assert.assertEquals(roles, user.getRoles());
    }

    @Test
    public void getNewUserRolesUser()
    {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("verified");
        roles.add("registered");

        User saved = userRepository.save(User.builder().roles(roles).build());
        User user = userRepository.findOne(saved.getId());
        User newUser = User.buildFrom(user).build();

        Assert.assertEquals(roles, newUser.getRoles());
    }
}
