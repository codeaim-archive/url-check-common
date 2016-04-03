package com.codeaim.urlcheck.common.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="\"user\"")
public final class User
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    private String resetToken;
    @NotNull
    private String accessToken;
    @NotNull
    private String password;
    @NotNull
    private LocalDateTime updated;
    @NotNull
    private LocalDateTime created;
    @Version
    private int version;
    private boolean emailVerified;
    @NotNull
    @ManyToMany
    private Set<Role> roles;

    public User(
        final Long id,
        final String name,
        final String email,
        final String resetToken,
        final String accessToken,
        final String password,
        final LocalDateTime updated,
        final LocalDateTime created,
        final int version,
        final boolean emailVerified,
        final Set<Role> roles
    )
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.resetToken = resetToken;
        this.accessToken = accessToken;
        this.password = password;
        this.updated = updated;
        this.created = created;
        this.version = version;
        this.emailVerified = emailVerified;
        this.roles = roles;
    }

    protected User()
    {
    }

    public Long getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getResetToken()
    {
        return this.resetToken;
    }

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public String getPassword()
    {
        return this.password;
    }

    public LocalDateTime getUpdated()
    {
        return this.updated;
    }

    public LocalDateTime getCreated()
    {
        return this.created;
    }

    public int getVersion()
    {
        return this.version;
    }

    public boolean isEmailVerified()
    {
        return this.emailVerified;
    }

    public Set<Role> getRoles()
    {
        return this.roles;
    }

    public static Builder builder() { return new Builder(); }

    public static Builder buildFrom(User user)
    {
        return builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .resetToken(user.getResetToken())
            .accessToken(user.getAccessToken())
            .password(user.getPassword())
            .created(user.getCreated())
            .version(user.getVersion() + 1)
            .emailVerified(user.isEmailVerified())
            .roles(user.getRoles());
    }

    @Override
    public String toString()
    {
        return String.format("User{" +
                        "id='%s'," +
                        "name='%s'," +
                        "email='%s'," +
                        "resetToken='%s'," +
                        "accessToken='%s'," +
                        "password='%s'," +
                        "updated='%s'," +
                        "created='%s'," +
                        "version='%s'," +
                        "emailVerified='%s'," +
                        "roles='%s'}",
                this.getId(),
                this.getName(),
                this.getEmail(),
                this.getResetToken(),
                this.getAccessToken(),
                this.getPassword(),
                this.getUpdated(),
                this.getCreated(),
                this.getVersion(),
                this.isEmailVerified(),
                this.getRoles() != null ? this.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", ")) : "");
    }

    public static class Builder
    {
        private Long id;
        private String name;
        private String email;
        private String resetToken;
        private String accessToken;
        private String password;
        private LocalDateTime created;
        private int version;
        private boolean emailVerified;
        private Set<Role> roles;

        private Builder id(final Long id)
        {
            this.id = id;
            return this;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder email(final String email)
        {
            this.email = email;
            return this;
        }

        public Builder resetToken(final String resetToken)
        {
            this.resetToken = resetToken;
            return this;
        }

        public Builder accessToken(final String accessToken)
        {
            this.accessToken = accessToken;
            return this;
        }

        public Builder password(final String password)
        {
            this.password = password;
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

        public Builder emailVerified(final boolean emailVerified)
        {
            this.emailVerified = emailVerified;
            return this;
        }

        public Builder roles(final Set<Role> roles)
        {
            this.roles = roles;
            return this;
        }

        public User build()
        {
            return new User(
                this.id,
                this.name,
                this.email,
                this.resetToken,
                this.accessToken,
                this.password,
                LocalDateTime.now(ZoneOffset.UTC),
                this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                this.version <= 0 ? 1 : this.version,
                this.emailVerified,
                this.roles
            );
        }
    }
}
