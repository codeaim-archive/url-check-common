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
import com.codeaim.urlcheck.common.model.User;

@RunWith(SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(Application.class)
@Configuration
public class MonitorRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MonitorRepository monitorRepository;

    @Test
    public void findAllMonitors() {
        List<Monitor> monitors = monitorRepository.findAll();
        Assert.assertNotNull(monitors);
    }

    @Test
    public void findElectableMonitor() {
        Page<Monitor> saved = monitorRepository.findElectable("", false, LocalDateTime.now(ZoneOffset.UTC), null);
        Assert.assertNotNull(saved);
    }

    @Test
    public void findByUserIdMonitors() {
        User user = userRepository.save(User.builder().build());
        monitorRepository.save(Monitor.builder().user(user).build());
        Page<Monitor> monitors = monitorRepository.findByUserId(user.getId(), null);
        Assert.assertEquals(user.getId(), monitors.getContent().get(0).getUser().getId());
    }

    @Test
    public void createMonitor() {
        Monitor saved = monitorRepository.save(Monitor.builder().build());
        Assert.assertNotNull(saved);
    }

    @Test
    public void updateMonitor() {
        Monitor saved = monitorRepository.save(Monitor.builder().build());
        Monitor updated = monitorRepository.save(Monitor.buildFrom(saved).build());
        Assert.assertEquals(2, updated.getVersion());
    }

    @Test
    public void deleteMonitor() {
        Monitor saved = monitorRepository.save(Monitor.builder().build());
        monitorRepository.delete(saved);
    }

    @Test
    public void deleteParentUserMonitor() {
        User parent = userRepository.save(User.builder().build());
        monitorRepository.save(Monitor.builder().user(parent).build());
        userRepository.delete(parent);
        List<Monitor> monitors = monitorRepository.findByUserId(parent.getId(), null).getContent();
        Assert.assertTrue(monitors.isEmpty());
    }

    @Test
    public void getUserNameMonitor()
    {
        String userName = "testUser";
        User user = userRepository.save(User.builder().name("testUser").build());
        Monitor saved = monitorRepository.save(Monitor.builder().user(user).build());
        Monitor monitor = monitorRepository.findOne(saved.getId());
        Assert.assertEquals(userName, monitor.getUser().getName());
    }

    @Test
    public void getNewMonitorUserNameMonitor()
    {
        String userName = "testUser";
        User user = userRepository.save(User.builder().name("testUser").build());
        Monitor saved = monitorRepository.save(Monitor.builder().user(user).build());
        Monitor monitor = monitorRepository.findOne(saved.getId());
        Monitor newMonitor = Monitor.buildFrom(monitor).build();
        Assert.assertEquals(userName, newMonitor.getUser().getName());
    }
}
