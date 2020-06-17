package it.qbteam.model;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {
    private static final long serialVersionUID = -2082543530941777784L;

    private String userId;

    private Long organizationId;

    public FavoriteId() {}

    public FavoriteId(String userId, Long organizationId) {
        this.userId = userId;
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId)) return false;
        FavoriteId that = (FavoriteId) o;
        return userId.equals(that.userId) &&
                organizationId.equals(that.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, organizationId);
    }
}