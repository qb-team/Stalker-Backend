package it.qbteam.repository.nosql;

import it.qbteam.model.PlacePresenceCounter;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacePresenceCounterRepository extends KeyValueRepository<PlacePresenceCounter, Long> {

}
