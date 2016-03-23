package com.codeaim.urlcheck.common.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public final class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(
            final Long id,
            final String name,
            final Set<User> users
    )
    {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    protected Role()
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

    public Set<User> getUsers()
    {
        return this.users;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(Role user)
    {
        return builder()
                .id(user.getId())
                .name(user.getName())
                .users(user.getUsers());
    }

    @Override
    public String toString()
    {
        return String.format("Role{" +
                        "id='%s'," +
                        "name='%s'," +
                        "users='%s'}",
                this.getId(),
                this.getName(),
                this.getUsers() != null ? this.getUsers()
                        .stream()
                        .map(User::getName)
                        .collect(Collectors.joining(", ")) : "");
    }

    public static class Builder
    {
        private Long id;
        private String name;
        private Set<User> users;

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

        public Builder users(final Set<User> users)
        {
            this.users = users;
            return this;
        }

        public Role build()
        {
            return new Role(
                    this.id,
                    this.name,
                    this.users
            );
        }
    }
}
