package com.codeaim.urlcheck.common.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Version;

@Entity
public final class Check
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    private Result latestResult;
    @NotNull
    private String name;
    @NotNull
    @URL
    private String url;
    @NotNull
    private State state;
    @NotNull
    private Status status;
    private String probe;
    @NotNull
    private LocalDateTime updated;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime refresh;
    private LocalDateTime locked;
    @Min(1)
    @Max(60)
    private int interval;
    private boolean confirming;
    @Version
    private int version;

    public Check(
            final Long id,
            final User user,
            final Result latestResult,
            final String name,
            final String url,
            final State state,
            final Status status,
            final String probe,
            final LocalDateTime updated,
            final LocalDateTime created,
            final LocalDateTime refresh,
            final LocalDateTime locked,
            final int interval,
            final int version,
            final boolean confirming
    )
    {
        this.id = id;
        this.user = user;
        this.latestResult = latestResult;
        this.name = name;
        this.url = url;
        this.state = state;
        this.status = status;
        this.probe = probe;
        this.updated = updated;
        this.created = created;
        this.refresh = refresh;
        this.locked = locked;
        this.interval = interval;
        this.version = version;
        this.confirming = confirming;
    }

    protected Check()
    {
    }

    public Long getId()
    {
        return this.id;
    }

    public User getUser()
    {
        return this.user;
    }

    public Result getLatestResult()
    {
        return this.latestResult;
    }

    public String getName()
    {
        return this.name;
    }

    public String getUrl()
    {
        return this.url;
    }

    public State getState()
    {
        return this.state;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public String getProbe()
    {
        return this.probe;
    }

    public LocalDateTime getUpdated()
    {
        return this.updated;
    }

    public LocalDateTime getCreated()
    {
        return this.created;
    }

    public LocalDateTime getRefresh()
    {
        return this.refresh;
    }

    public LocalDateTime getLocked()
    {
        return this.locked;
    }

    public int getInterval()
    {
        return this.interval;
    }

    public int getVersion()
    {
        return this.version;
    }

    public boolean isConfirming()
    {
        return this.confirming;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(Check check)
    {
        return builder()
                .id(check.getId())
                .user(check.getUser())
                .latestResult(check.getLatestResult())
                .name(check.getName())
                .url(check.getUrl())
                .state(check.getState())
                .status(check.getStatus())
                .probe(check.getProbe())
                .created(check.getCreated())
                .refresh(check.getRefresh())
                .locked(check.getLocked())
                .interval(check.getInterval())
                .version(check.getVersion() + 1)
                .confirming(check.isConfirming());
    }

    @Override
    public String toString()
    {
        return String.format("Check{" +
                        "id='%s'," +
                        "user='%s'," +
                        "latestResult='%s'," +
                        "name='%s'," +
                        "url='%s'," +
                        "state='%s'," +
                        "status='%s'," +
                        "probe='%s'," +
                        "updated='%s'," +
                        "created='%s'," +
                        "refresh='%s'," +
                        "locked='%s'," +
                        "interval='%s'," +
                        "confirming='%s'," +
                        "version='%s'}",
                this.getId(),
                this.getUser() != null ? this.getUser().getId() : "",
                this.getLatestResult() != null ? this.getLatestResult().getId() : "",
                this.getName(),
                this.getUrl(),
                this.getState(),
                this.getStatus(),
                this.getProbe(),
                this.getUpdated(),
                this.getCreated(),
                this.getRefresh(),
                this.getLocked(),
                this.getInterval(),
                this.isConfirming(),
                this.getVersion());
    }

    public static class Builder
    {
        private Long id;
        private Result latestResult;
        private User user;
        private String name;
        private String url;
        private State state;
        private Status status;
        private String probe;
        private LocalDateTime created;
        private LocalDateTime refresh;
        private LocalDateTime locked;
        private int interval;
        private int version;
        private boolean confirming;

        private Builder id(final Long id)
        {
            this.id = id;
            return this;
        }

        public Builder user(final User user)
        {
            this.user = user;
            return this;
        }

        public Builder latestResult(final Result latestResult)
        {
            this.latestResult = latestResult;
            return this;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder url(final String url)
        {
            this.url = url;
            return this;
        }

        public Builder state(final State state)
        {
            this.state = state;
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

        public Builder created(final LocalDateTime created)
        {
            this.created = created;
            return this;
        }

        public Builder refresh(final LocalDateTime refresh)
        {
            this.refresh = refresh;
            return this;
        }

        public Builder locked(final LocalDateTime locked)
        {
            this.locked = locked;
            return this;
        }

        public Builder interval(final int interval)
        {
            this.interval = interval;
            return this;
        }

        public Builder version(final int version)
        {
            this.version = version;
            return this;
        }

        public Builder confirming(final boolean confirming)
        {
            this.confirming = confirming;
            return this;
        }

        public Check build()
        {
            return new Check(
                    this.id,
                    this.user,
                    this.latestResult,
                    this.name,
                    this.url,
                    this.state == null ? State.WAITING : this.state,
                    this.status == null ? Status.UNKNOWN : this.status,
                    this.probe,
                    LocalDateTime.now(ZoneOffset.UTC),
                    this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                    this.refresh == null ? LocalDateTime.now(ZoneOffset.UTC) : this.refresh,
                    this.locked == null ? LocalDateTime.now(ZoneOffset.UTC) : this.locked,
                    this.interval,
                    this.version <= 0 ? 1 : this.version,
                    this.confirming
            );
        }
    }
}
