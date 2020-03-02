package it.qbteam.repository.nosql;

import it.qbteam.model.OrganizationPresenceCounter;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationPresenceCounterRepository extends KeyValueRepository<OrganizationPresenceCounter, Long> {

}
