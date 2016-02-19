package com.codeaim.urlcheck.common.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public class ContactEmail extends Contact
{
    @NotNull
    @Email
    private String email;

    public ContactEmail(
            final Long id,
            final Check check,
            final LocalDateTime created,
            final LocalDateTime updated,
            final int version,
            final String email
    )
    {
        super(id, check, created, updated, version);
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(ContactEmail contactEmail)
    {
        return builder()
                .id(contactEmail.getId())
                .check(contactEmail.getCheck())
                .created(contactEmail.getCreated())
                .version(contactEmail.getVersion() + 1)
                .email(contactEmail.getEmail());
    }

    @Override
    public String toString()
    {
        return String.format("ContactEmail{" +
                        "id='%s'," +
                        "check='%s'," +
                        "created='%s'," +
                        "updated='%s'," +
                        "version='%s'," +
                        "email='%s'}",
                this.getId(),
                this.getCheck() != null ? this.getCheck().getId() : "",
                this.getCreated(),
                this.getUpdated(),
                this.getVersion(),
                this.getEmail());
    }

    public static class Builder
    {
        private Long id;
        private Check check;
        private LocalDateTime created;
        private int version;
        private String email;

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

        public Builder email(final String email)
        {
            this.email = email;
            return this;
        }

        public ContactEmail build()
        {
            return new ContactEmail(
                    this.id,
                    this.check,
                    this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                    LocalDateTime.now(ZoneOffset.UTC),
                    this.version <= 0 ? 1 : this.version,
                    this.email
            );
        }
    }
}
