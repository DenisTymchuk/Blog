package com.company.entity;

/**
 * Created by Oksa on 24.11.2017.
 */
public class User {
    private long id;
    private String name;
    private String password;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder setId(long id) {
            user.setId(id);
            return this;
        }

        public Builder setName(String name) {
            user.setName(name);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
