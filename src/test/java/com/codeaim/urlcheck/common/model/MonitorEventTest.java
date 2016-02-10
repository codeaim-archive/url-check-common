package com.codeaim.urlcheck.common.model;

import org.junit.Assert;
import org.junit.Test;

public class MonitorEventTest
{
    @Test
    public void createMonitorEvent()
    {
        MonitorEvent newMonitorEvent = MonitorEvent.builder().build();

        Assert.assertNotNull(newMonitorEvent);
    }

    @Test
    public void toStringMonitorEvent()
    {
        System.out.println(MonitorEvent.builder()
                .auditor("testAuditor")
                .changed(true)
                .confirmation(true)
                .monitor(Monitor.builder().build())
                .previous(MonitorEvent.builder().build())
                .responseTime(15)
                .status(Status.UP)
                .statusCode(200)
                .build().toString());
    }
}
