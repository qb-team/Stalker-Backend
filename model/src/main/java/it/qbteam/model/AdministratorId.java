package it.qbteam.model;

import java.io.Serializable;
import java.util.Objects;

public class AdministratorId implements Serializable {
    private String administratorId;

    private String orgAuthServerId;

    public AdministratorId() {
    }

    public AdministratorId(String administratorId, String orgAuthServerId) {
        this.administratorId = administratorId;
        this.orgAuthServerId = orgAuthServerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AdministratorId))
            return false;
        AdministratorId that = (AdministratorId) o;
        return Objects.equals(administratorId, that.administratorId)
                && Objects.equals(orgAuthServerId, that.orgAuthServerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(administratorId, orgAuthServerId);
    }
}