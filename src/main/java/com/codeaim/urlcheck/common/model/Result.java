package com.codeaim.urlcheck.common.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public final class Result
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @NotNull
    private LocalDateTime updated;
    @Version
    private int version;

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
            final LocalDateTime created,
            final LocalDateTime updated,
            final int version
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
        this.updated = updated;
        this.version = version;
    }

    protected Result()
    {
    }

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

    public LocalDateTime getUpdated()
    {
        return this.updated;
    }

    public int getVersion()
    {
        return version;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(Result result)
    {
        return builder()
                .id(result.getId())
                .check(result.getCheck())
                .previous(result.getPrevious())
                .status(result.getStatus())
                .probe(result.getProbe())
                .statusCode(result.getStatusCode())
                .responseTime(result.getResponseTime())
                .changed(result.isChanged())
                .confirmation(result.isConfirmation())
                .created(result.getCreated())
                .version(result.getVersion() + 1);
    }

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
                        "created='%s'," +
                        "updated='%s'," +
                        "version='%s'}",
                this.getId(),
                this.getCheck() != null ? this.getCheck().getId() : "",
                this.getPrevious() != null ? this.getPrevious().getId() : "",
                this.getStatus(),
                this.getProbe(),
                this.getStatusCode(),
                this.getResponseTime(),
                this.isChanged(),
                this.isConfirmation(),
                this.getCreated(),
                this.getUpdated(),
                this.getVersion());
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
        private LocalDateTime created;
        private int version;

        private Builder id(final Long id)
        {
            this.id = id;
            return this;
        }

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

        public Builder created(final LocalDateTime created)
        {
            this.created = created;
            return this;
        }

        public Builder version(final int version)
        {
            this.version = version;
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
                    this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                    LocalDateTime.now(ZoneOffset.UTC),
                    this.version <= 0 ? 1 : this.version
            );
        }
    }
}
