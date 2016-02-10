package com.codeaim.urlcheck.common.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.Test;

public class MonitorTest
{
    @Test
    public void createMonitor()
    {
        Monitor newMonitor = Monitor.builder().build();

        Assert.assertEquals(1, newMonitor.getVersion());
    }

    @Test
    public void updateMonitor()
    {
        Monitor newMonitor = Monitor.builder().build();
        Monitor updatedMonitor = Monitor.buildFrom(newMonitor).build();

        Assert.assertEquals(2, updatedMonitor.getVersion());
    }

    @Test
    public void toStringMonitor()
    {
        System.out.println(Monitor.builder()
                .audit(LocalDateTime.now(ZoneOffset.UTC))
                .auditor("testAuditor")
                .confirming(true)
                .created(LocalDateTime.now(ZoneOffset.UTC))
                .interval(5)
                .locked(LocalDateTime.now(ZoneOffset.UTC))
                .name("testMonitor")
                .monitorEvent(MonitorEvent.builder().build())
                .state(State.ELECTED)
                .status(Status.UP)
                .user(User.builder().build())
                .url("http://www.test.com")
                .build().toString());
    }
}
