package it.qbteam.model;

import java.io.Serializable;
import java.util.Objects;

public class UserId implements Serializable {
    private static final long serialVersionUID = 4732911864328968956L;

    private String userId;

    private Long organizationId;

    public UserId() {}

    public UserId(String userId, Long organizationId) {
        this.userId = userId;
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId) &&
                Objects.equals(organizationId, userId1.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, organizationId);
    }
}