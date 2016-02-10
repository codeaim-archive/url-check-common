package com.codeaim.urlcheck.common.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public final class MonitorEvent
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Monitor monitor;
    @OneToOne(fetch = FetchType.LAZY)
    private MonitorEvent previous;
    private Status status;
    private String auditor;
    private int statusCode;
    private long responseTime;
    private boolean changed;
    private boolean confirmation;
    private LocalDateTime created;

    public MonitorEvent(
        final Long id,
        final Monitor monitor,
        final MonitorEvent previous,
        final Status status,
        final String auditor,
        final int statusCode,
        final long responseTime,
        final boolean changed,
        final boolean confirmation,
        final LocalDateTime created
    )
    {
        this.id = id;
        this.monitor = monitor;
        this.previous = previous;
        this.status = status;
        this.auditor = auditor;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.changed = changed;
        this.confirmation = confirmation;
        this.created = created;
    }

    protected MonitorEvent() {}

    public Long getId()
    {
        return this.id;
    }

    public Monitor getMonitor()
    {
        return this.monitor;
    }

    public MonitorEvent getPrevious()
    {
        return this.previous;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public String getAuditor()
    {
        return this.auditor;
    }

    public int getStatusCode()
    {
        return this.statusCode;
    }

    public long getResponseTime()
    {
        return this.responseTime;
    }

    public boolean isChanged()
    {
        return this.changed;
    }

    public boolean isConfirmation()
    {
        return this.confirmation;
    }

    public LocalDateTime getCreated()
    {
        return this.created;
    }

    public static Builder builder() { return new Builder(); }

    @Override
    public String toString()
    {
        return "MonitorEvent{" +
                "id='" + id + '\'' +
                ", monitor='" + monitor.getId() + '\'' +
                ", previous='" + previous.getId() + '\'' +
                ", status=" + status +
                ", auditor='" + auditor + '\'' +
                ", statusCode=" + statusCode +
                ", responseTime=" + responseTime +
                ", changed=" + changed +
                ", confirmation=" + confirmation +
                ", created=" + created +
                '}';
    }

    public static class Builder
    {
        private Long id;
        private Monitor monitor;
        private MonitorEvent previous;
        private Status status;
        private String auditor;
        private int statusCode;
        private long responseTime;
        private boolean changed;
        private boolean confirmation;

        public Builder monitor(final Monitor monitor)
        {
            this.monitor = monitor;
            return this;
        }

        public Builder previous(final MonitorEvent previous)
        {
            this.previous = previous;
            return this;
        }

        public Builder status(final Status status)
        {
            this.status = status;
            return this;
        }

        public Builder auditor(final String auditor)
        {
            this.auditor = auditor;
            return this;
        }

        public Builder statusCode(final int statusCode)
        {
            this.statusCode = statusCode;
            return this;
        }

        public Builder responseTime(final long responseTime)
        {
            this.responseTime = responseTime;
            return this;
        }

        public Builder changed(final boolean changed)
        {
            this.changed = changed;
            return this;
        }

        public Builder confirmation(final boolean confirmation)
        {
            this.confirmation = confirmation;
            return this;
        }

        public MonitorEvent build()
        {
            return new MonitorEvent(
                this.id,
                this.monitor,
                this.previous,
                this.status,
                this.auditor,
                this.statusCode,
                this.responseTime,
                this.changed,
                this.confirmation,
                LocalDateTime.now(ZoneOffset.UTC)
            );
        }
    }
}
