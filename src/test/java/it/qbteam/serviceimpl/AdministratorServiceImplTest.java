package it.qbteam.serviceimpl;


import it.qbteam.model.Permission;
import it.qbteam.repository.OrganizationRepository;
import it.qbteam.repository.PermissionRepository;
import it.qbteam.service.AdministratorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class AdministratorServiceImplTest {

    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    @TestConfiguration
    static class AdministratorServiceImplConfiguration{
        @Bean
        public AdministratorServiceImpl organizationService(OrganizationRepository organizationRepository, PermissionRepository permissionRepository) {
            return new AdministratorServiceImpl(permissionRepository, organizationRepository);
        }
    }

    @Autowired
    private AdministratorServiceImpl administratorService;

    private Permission testPermission = new Permission();

    private Optional empty = Optional.empty();

    private List<Permission> testList = new LinkedList<>();

    private Iterable<Permission> testIterableList= testList;

    @Before
    public void setUp(){

    }

    @Test
    public void testBindAdministratorToOrganizationReturnOptionaEmptyWithNullPermission() {

        Assert.assertEquals(empty, administratorService.bindAdministratorToOrganization(null));

        Mockito.when(organizationRepository.existsById(anyLong())).thenReturn(false);
        Assert.assertEquals(empty, administratorService.bindAdministratorToOrganization(testPermission));
        
    }

    @Test
    public void testBindAdministratorToOrganizationReturnValidOptionalWithValidPermissionInput(){
        testPermission.setOrganizationId(1L);
        Optional<Permission> validPermission = Optional.of(testPermission);

        Mockito.when(organizationRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(permissionRepository.save(any(Permission.class))).thenReturn(testPermission);
        Assert.assertEquals(Optional.of(testPermission), administratorService.bindAdministratorToOrganization(testPermission));
    }

    @Test
    public void testCreateNewAdministratorInOrganizationReturnOptionalEmptyWithNullPermission(){
        Assert.assertEquals(empty, administratorService.createNewAdministratorInOrganization(null));
    }

    @Test
    public void testCreateNewAdministratorInOrganizationReturnValidOptionalWithValidPermissionInput(){
        testPermission.setOrganizationId(1L);
        Mockito.when(organizationRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(permissionRepository.save(any(Permission.class))).thenReturn(testPermission);
        Assert.assertEquals(Optional.of(testPermission), administratorService.createNewAdministratorInOrganization(testPermission));
    }

    @Test
    public void testGetAdministratorListOfOrganizationReturnValidListGivenValidOrganizationId(){
        testPermission.setOrganizationId(1l);
        testList.add(testPermission);
        Mockito.when(permissionRepository.findByOrganizationId(anyLong())).thenReturn(testIterableList);
        Assert.assertEquals(testList, administratorService.getAdministratorListOfOrganization(anyLong()));
    }

    @Test
    public void testGetPermissionListReturnValidListGivenValidAdministratorId(){
        testPermission.setOrganizationId(1L);
        testList.add(testPermission);
        Mockito.when(permissionRepository.findByAdministratorId(anyString())).thenReturn(testIterableList);
        Assert.assertEquals(testList, administratorService.getPermissionList(anyString()));
    }

    @Test
    public void testUpdateAdministratorPermissionReturnOptionalEmptyGivenNullPermission(){
        Assert.assertEquals(empty, administratorService.updateAdministratorPermission(null));
    }

    @Test
    public void testUpdateAdministratorPermissionReturnValidOptionalPermissionGivenValidPermission(){
        testPermission.setOrganizationId(1L);
        Mockito.when(permissionRepository.save(any(Permission.class))).thenReturn(testPermission);
        Assert.assertEquals(Optional.of(testPermission), administratorService.updateAdministratorPermission(testPermission));
    }

    @Test
    public void testUnbindAdministratorFromOrganizationPerformDeleteGivenValidPermission(){

    }
}
