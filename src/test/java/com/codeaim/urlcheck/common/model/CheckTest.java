package com.codeaim.urlcheck.common.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest
{
    @Test
    public void create()
    {
        Check newCheck = Check.builder().build();

        Assert.assertEquals(1, newCheck.getVersion());
    }

    @Test
    public void update()
    {
        Check newCheck = Check.builder().build();
        Check updatedCheck = Check.buildFrom(newCheck).build();

        Assert.assertEquals(2, updatedCheck.getVersion());
    }

    @Test
    public void toStringCheck()
    {
        System.out.println(Check.builder()
                .refresh(LocalDateTime.now(ZoneOffset.UTC))
                .probe("testProbe")
                .confirming(true)
                .created(LocalDateTime.now(ZoneOffset.UTC))
                .interval(5)
                .locked(LocalDateTime.now(ZoneOffset.UTC))
                .name("testCheck")
                .latestResult(Result.builder().build())
                .state(State.ELECTED)
                .status(Status.UP)
                .user(User.builder().build())
                .url("http://www.test.com")
                .build().toString());
    }
}
