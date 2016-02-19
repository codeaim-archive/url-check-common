package com.codeaim.urlcheck.common.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public class Alert
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Check check;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Contact contact;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Result result;
    @NotNull
    private Stage stage;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime updated;
    @Version
    private int version;

    public Alert(
            final Long id,
            final Check check,
            final Contact contact,
            final Result result,
            final Stage stage,
            final LocalDateTime created,
            final LocalDateTime updated,
            final int version
    )
    {
        this.id = id;
        this.check = check;
        this.contact = contact;
        this.result = result;
        this.stage = stage;
        this.created = created;
        this.updated = updated;
        this.version = version;
    }

    public Long getId()
    {
        return id;
    }

    public Check getCheck()
    {
        return check;
    }

    public Contact getContact()
    {
        return contact;
    }

    public Result getResult()
    {
        return result;
    }

    public Stage getStage()
    {
        return stage;
    }

    public LocalDateTime getCreated()
    {
        return created;
    }

    public LocalDateTime getUpdated()
    {
        return updated;
    }

    public int getVersion()
    {
        return version;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(Alert alert)
    {
        return builder()
                .id(alert.getId())
                .check(alert.getCheck())
                .contact(alert.getContact())
                .result(alert.getResult())
                .stage(alert.getStage())
                .created(alert.getCreated())
                .version(alert.getVersion() + 1);
    }

    @Override
    public String toString()
    {
        return String.format("Alert{" +
                        "id='%s'," +
                        "check='%s'," +
                        "contact='%s'," +
                        "result='%s'," +
                        "stage='%s'," +
                        "created='%s'," +
                        "updated='%s'," +
                        "version='%s'}",
                this.getId(),
                this.getCheck() != null ? this.getCheck().getId() : "",
                this.getContact() != null ? this.getContact().getId() : "",
                this.getResult() != null ? this.getResult().getId() : "",
                this.getStage(),
                this.getCreated(),
                this.getUpdated(),
                this.getVersion());
    }

    public static class Builder
    {
        private Long id;
        private Check check;
        private Contact contact;
        private Result result;
        private Stage stage;
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

        public Builder contact(final Contact contact)
        {
            this.contact = contact;
            return this;
        }

        public Builder result(final Result result)
        {
            this.result = result;
            return this;
        }

        public Builder stage(final Stage stage)
        {
            this.stage = stage;
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

        public Alert build()
        {
            return new Alert(
                    this.id,
                    this.check,
                    this.contact,
                    this.result,
                    this.stage,
                    this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                    LocalDateTime.now(ZoneOffset.UTC),
                    this.version <= 0 ? 1 : this.version
            );
        }
    }
}
