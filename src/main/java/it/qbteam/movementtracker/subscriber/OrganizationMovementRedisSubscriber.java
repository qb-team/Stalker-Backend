package it.qbteam.movementtracker.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.repository.OrganizationAccessRepository;
import it.qbteam.repository.OrganizationAccessRepository;

@Service
public class OrganizationMovementRedisSubscriber extends OrganizationMovementSubscriber {

    private OrganizationAccessRepository organizationAccessRepository;

    public OrganizationMovementRedisSubscriber(OrganizationAccessRepository org){
        this.organizationAccessRepository= org;
    }
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("REDIS MESSAGE: " + message.toString());
        OrganizationMovement organizationMovementFromRedis = new OrganizationMovement();
        if(organizationMovementFromRedis.getMovementType()==1){    //ingresso
            OrganizationAccess organizationAccessFromRedis = new OrganizationAccess();
            organizationAccessFromRedis.setEntranceTimestamp(organizationMovementFromRedis.getTimestamp());
            organizationAccessFromRedis.setOrgAuthServerId(organizationAccessFromRedis.getOrgAuthServerId());
            organizationAccessFromRedis.setOrganizationId(organizationAccessFromRedis.getOrganizationId());

            organizationAccessRepository.save(organizationAccessFromRedis); 
        }
        else if(organizationMovementFromRedis.getMovementType()==-1){
            Iterable<OrganizationAccess> organizationAccessFromDB = organizationAccessRepository.findByExitTokenAndOrganizationId(organizationMovementFromRedis.getExitToken(), organizationMovementFromRedis.getOrganizationId());

            if(organizationAccessFromDB.iterator().hasNext()){
                OrganizationAccess organizationAccess = organizationAccessFromDB.iterator().next();
                
                organizationAccess.setExitTimestamp(organizationMovementFromRedis.getTimestamp());

                organizationAccessRepository.save(organizationAccess);

            }
            else{
               System.out.println("ACCESS NOT FOUND"); 
            }
        }
    }
}