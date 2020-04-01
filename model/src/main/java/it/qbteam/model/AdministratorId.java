package it.qbteam.model;

import java.io.Serializable;
import java.util.Objects;

public class AdministratorId implements Serializable {
    private String administratorId;

    private Integer ldapId;

    public AdministratorId() {}

    public AdministratorId(String administratorId, Integer ldapId) {
        this.administratorId = administratorId;
        this.ldapId = ldapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdministratorId)) return false;
        AdministratorId that = (AdministratorId) o;
        return Objects.equals(administratorId, that.administratorId) &&
                Objects.equals(ldapId, that.ldapId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(administratorId, ldapId);
    }
}