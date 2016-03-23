package com.codeaim.urlcheck.common.model;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UserTest
{
    @Test
    public void createUser()
    {
        User newUser = User.builder().build();

        Assert.assertEquals(1, newUser.getVersion());
    }

    @Test
    public void updateUser()
    {
        User newUser = User.builder().build();
        User updatedUser = User.buildFrom(newUser).build();

        Assert.assertEquals(2, updatedUser.getVersion());
    }

    @Test
    public void toStringUser()
    {
        System.out.println(User.builder()
                .accessToken("testAccessToken")
                .created(LocalDateTime.now(ZoneOffset.UTC))
                .email("test@test.com")
                .emailVerified(true)
                .name("testUser")
                .password("testPassword")
                .resetToken("testResetToken")
                .roles(Sets.newHashSet(Sets.newHashSet(
                        Role.builder().name("admin").build(),
                        Role.builder().name("verified").build(),
                        Role.builder().name("registered").build())))
                .build()
                .toString());
    }
}
