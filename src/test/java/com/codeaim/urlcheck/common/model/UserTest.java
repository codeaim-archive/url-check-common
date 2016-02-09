package com.codeaim.urlcheck.common.model;

import org.junit.Assert;
import org.junit.Test;

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
}
