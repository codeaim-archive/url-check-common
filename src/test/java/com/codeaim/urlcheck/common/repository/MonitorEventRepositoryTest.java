package com.codeaim.urlcheck.common.repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codeaim.urlcheck.common.Application;
import com.codeaim.urlcheck.common.model.Monitor;
import com.codeaim.urlcheck.common.model.MonitorEvent;

@RunWith(SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(Application.class)
@Configuration
public class MonitorEventRepositoryTest
{

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitorEventRepository monitorEventRepository;

    @Test
    public void findAllMonitorEvents() {
        List<MonitorEvent> monitorEvents = monitorEventRepository.findAll();
        Assert.assertNotNull(monitorEvents);
    }

    @Test
    public void findExpiredMonitorEvents() {
        List<MonitorEvent> expired = monitorEventRepository.findExpiredMonitorEvents(LocalDateTime.now(ZoneOffset.UTC));
        Assert.assertNotNull(expired);
    }

    @Test
    public void findByMonitorIdMonitorEvents() {
        Monitor monitor = monitorRepository.save(Monitor.builder().build());
        monitorEventRepository.save(MonitorEvent.builder().monitor(monitor).build());
        Page<MonitorEvent> monitorEvents = monitorEventRepository.findByMonitorId(monitor.getId(), null);
        Assert.assertEquals(monitor.getId(), monitorEvents.getContent().get(0).getMonitor().getId());
    }

    @Test
    public void createMonitorEvent() {
        MonitorEvent saved = monitorEventRepository.save(MonitorEvent.builder().build());
        Assert.assertNotNull(saved);
    }

    @Test
    public void deleteMonitorEvent() {
        MonitorEvent saved = monitorEventRepository.save(MonitorEvent.builder().build());
        monitorEventRepository.delete(saved);
    }

    @Test
    public void deleteParentMonitorMonitorEvent() {
        Monitor parent = monitorRepository.save(Monitor.builder().build());
        monitorEventRepository.save(MonitorEvent.builder().monitor(parent).build());
        monitorRepository.delete(parent);
        List<MonitorEvent> monitorEvents = monitorEventRepository.findByMonitorId(parent.getId(), null).getContent();
        Assert.assertTrue(monitorEvents.isEmpty());
    }
}
