package it.qbteam.model;

import java.io.Serializable;
import java.util.Objects;

public class UserId implements Serializable {
    private String userId;

    private Integer ldapId;

    public UserId() {}

    public UserId(String userId, Integer ldapId) {
        this.userId = userId;
        this.ldapId = ldapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId) &&
                Objects.equals(ldapId, userId1.ldapId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, ldapId);
    }
}