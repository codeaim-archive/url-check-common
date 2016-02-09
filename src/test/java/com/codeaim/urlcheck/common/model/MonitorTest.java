package com.codeaim.urlcheck.common.model;

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
}
