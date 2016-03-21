package com.codeaim.urlcheck.common.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public final class Result
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Check check;
    @OneToOne(fetch = FetchType.LAZY)
    private Result previous;
    @NotNull
    private Status status;
    @NotNull
    private String probe;
    private int statusCode;
    private long responseTime;
    private boolean changed;
    private boolean confirmation;
    @NotNull
    private LocalDateTime created;

    public Result(
        final Long id,
        final Check check,
        final Result previous,
        final Status status,
        final String probe,
        final int statusCode,
        final long responseTime,
        final boolean changed,
        final boolean confirmation,
        final LocalDateTime created
    )
    {
        this.id = id;
        this.check = check;
        this.previous = previous;
        this.status = status;
        this.probe = probe;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.changed = changed;
        this.confirmation = confirmation;
        this.created = created;
    }

    protected Result() {}

    public Long getId()
    {
        return this.id;
    }

    public Check getCheck()
    {
        return this.check;
    }

    public Result getPrevious()
    {
        return this.previous;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public String getProbe()
    {
        return this.probe;
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
        return String.format("Result{" +
                        "id='%s'," +
                        "check='%s'," +
                        "previous='%s'," +
                        "status='%s'," +
                        "probe='%s'," +
                        "statusCode='%s'," +
                        "responseTime='%s'," +
                        "changed='%s'," +
                        "confirmation='%s'," +
                        "created='%s'}",
                this.getId(),
                this.getCheck() != null ? this.getCheck().getId() : "",
                this.getPrevious() != null ? this.getPrevious().getId() : "",
                this.getStatus(),
                this.getProbe(),
                this.getStatusCode(),
                this.getResponseTime(),
                this.isChanged(),
                this.isConfirmation(),
                this.getCreated());
    }

    public static class Builder
    {
        private Long id;
        private Check check;
        private Result previous;
        private Status status;
        private String probe;
        private int statusCode;
        private long responseTime;
        private boolean changed;
        private boolean confirmation;

        public Builder check(final Check check)
        {
            this.check = check;
            return this;
        }

        public Builder previous(final Result previous)
        {
            this.previous = previous;
            return this;
        }

        public Builder status(final Status status)
        {
            this.status = status;
            return this;
        }

        public Builder probe(final String probe)
        {
            this.probe = probe;
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

        public Result build()
        {
            return new Result(
                this.id,
                this.check,
                this.previous,
                this.status,
                this.probe,
                this.statusCode,
                this.responseTime,
                this.changed,
                this.confirmation,
                LocalDateTime.now(ZoneOffset.UTC)
            );
        }
    }
}
