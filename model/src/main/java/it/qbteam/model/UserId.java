package it.qbteam.model;

import java.io.Serializable;
import java.util.Objects;

public class UserId implements Serializable {
    private static final long serialVersionUID = 4732911864328968956L;

    private String userId;

    private String orgAuthServerId;

    public UserId() {}

    public UserId(String userId, String orgAuthServerId) {
        this.userId = userId;
        this.orgAuthServerId = orgAuthServerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId) &&
                Objects.equals(orgAuthServerId, userId1.orgAuthServerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orgAuthServerId);
    }
}