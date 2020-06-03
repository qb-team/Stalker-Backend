package it.qbteam.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
public class ModelTest {
    @Test
    public void testAdministratorBindingRequest() {
        AdministratorBindingRequest abr = new AdministratorBindingRequest();

        Assert.assertEquals(1L, abr.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals("prova@prova.it", abr.mail("prova@prova.it").getMail());
        Assert.assertEquals("uidTest", abr.orgAuthServerId("uidTest").getOrgAuthServerId());
        Assert.assertEquals("testpwd", abr.password("testpwd").getPassword());
        Assert.assertEquals(3, abr.permission(3).getPermission(),0);
    }

    @Test
    public void testFavorite() {
        Favorite f = new Favorite();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals("uid", f.userId("uid").getUserId());
        Assert.assertEquals(1L, f.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals("uid", f.orgAuthServerId("uid").getOrgAuthServerId());
        Assert.assertEquals(date, f.creationDate(date).getCreationDate());
    }

    @Test
    public void testOrganization() {
        Organization o = new Organization();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals(1, o.id(1L).getId(), 0);
        Assert.assertEquals("testorg", o.name("testorg").getName());
        Assert.assertEquals("descr", o.description("descr").getDescription());
        Assert.assertEquals("image", o.image("image").getImage());
        Assert.assertEquals("street", o.street("street").getStreet());
        Assert.assertEquals("3a", o.number("3a").getNumber());
        Assert.assertEquals(Integer.valueOf(11111), o.postCode(11111).getPostCode());
        Assert.assertEquals("city", o.city("city").getCity());
        Assert.assertEquals("country", o.country("country").getCountry());
        Assert.assertEquals("authurl.it", o.authenticationServerURL("authurl.it").getAuthenticationServerURL());
        Assert.assertEquals(date, o.creationDate(date).getCreationDate());
        Assert.assertEquals(date, o.lastChangeDate(date).getLastChangeDate());
        Assert.assertEquals("{\"json\": true}", o.trackingArea("{\"json\": true}").getTrackingArea());
        Assert.assertEquals(Organization.TrackingModeEnum.anonymous, o.trackingMode(Organization.TrackingModeEnum.anonymous).getTrackingMode());
    }

    @Test
    public void testOrganizationAccess() {
        OrganizationAccess access = new OrganizationAccess();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals(1L, access.id(1L).getId(), 0);
        Assert.assertEquals(date, access.entranceTimestamp(date).getEntranceTimestamp());
        Assert.assertEquals(date, access.exitTimestamp(date).getExitTimestamp());
        Assert.assertEquals("exitToken", access.exitToken("exitToken").getExitToken());
        Assert.assertEquals(1L, access.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals("serverId", access.orgAuthServerId("serverId").getOrgAuthServerId());
    }

    @Test
    public void testOrganizationAuthenticationServerCredentials(){
        OrganizationAuthenticationServerCredentials testOASC = new OrganizationAuthenticationServerCredentials();

        Assert.assertEquals("prova", testOASC.username("prova").getUsername());
        testOASC.setUsername("prova");
        Assert.assertEquals("prova", testOASC.getUsername());

        Assert.assertEquals("prova", testOASC.password("prova").getPassword());
        testOASC.setPassword("prova");
        Assert.assertEquals("prova", testOASC.getUsername());

        Assert.assertTrue(testOASC.equals(testOASC));
        Assert.assertFalse(testOASC.equals(null));
        Assert.assertFalse(testOASC.equals(new Favorite().organizationId(1L)));
        OrganizationAuthenticationServerCredentials testEquals = new OrganizationAuthenticationServerCredentials().username("prova").password("prova");
        Assert.assertTrue(testOASC.equals(testEquals));

        Assert.assertEquals(Objects.hash(testOASC.getUsername(), testOASC.getPassword()), testOASC.hashCode());
        Assert.assertEquals("class OrganizationAuthenticationServerCredentials {\n    username: prova\n    password: prova\n}", testOASC.toString());
        OrganizationAuthenticationServerCredentials testToString = new OrganizationAuthenticationServerCredentials().password("prova");
        Assert.assertEquals("class OrganizationAuthenticationServerCredentials {\n    username: null\n    password: prova\n}", testToString.toString());
    }
    @Test
    public void testOrganizationAuthenticationServerInformation(){
        OrganizationAuthenticationServerInformation testOASI = new OrganizationAuthenticationServerInformation();
        testOASI.setOrgAuthServerId("prova");
        testOASI.setName("prova");
        testOASI.setSurname("prova");
        Assert.assertEquals("prova", testOASI.getOrgAuthServerId());
        Assert.assertEquals("prova", testOASI.getName());
        Assert.assertEquals("prova", testOASI.getSurname());

        OrganizationAuthenticationServerInformation testEquals = new OrganizationAuthenticationServerInformation().name("prova").surname("prova").orgAuthServerId("prova");
        Assert.assertTrue(testOASI.equals(testOASI));
        Assert.assertFalse(testEquals.equals(null));
        Assert.assertFalse(testEquals.equals(new Favorite().organizationId(1L)));
        Assert.assertTrue(testOASI.equals(testEquals));

        Assert.assertEquals(Objects.hash(testOASI.getOrgAuthServerId(), testOASI.getName(), testOASI.getSurname()), testOASI.hashCode());

        Assert.assertEquals("class OrganizationAuthenticationServerInformation {\n    orgAuthServerId: prova\n    name: prova\n    surname: prova\n}", testOASI.toString());
        OrganizationAuthenticationServerInformation testToString = new OrganizationAuthenticationServerInformation();
        Assert.assertEquals("class OrganizationAuthenticationServerInformation {\n    orgAuthServerId: null\n    name: null\n    surname: null\n}", testToString.toString());

    }

    @Test
    public void testOrganizationAuthenticationServerRequest(){
        OrganizationAuthenticationServerRequest testOASR = new OrganizationAuthenticationServerRequest().organizationId(1L).addOrgAuthServerIdsItem("prova");
        OrganizationAuthenticationServerCredentials testCredential = new OrganizationAuthenticationServerCredentials().password("prova");
        OrganizationAuthenticationServerRequest testOASRWithCredential = new OrganizationAuthenticationServerRequest().organizationId(1L).addOrgAuthServerIdsItem("prova").organizationCredentials(testCredential);

        Assert.assertEquals(testOASRWithCredential, testOASR.organizationCredentials(testCredential));

        Assert.assertEquals(testCredential, testOASRWithCredential.getOrganizationCredentials());
        testOASR.setOrganizationCredentials(new OrganizationAuthenticationServerCredentials().username("prova"));
        Assert.assertEquals(new OrganizationAuthenticationServerCredentials().username("prova"), testOASR.getOrganizationCredentials());
        testOASR.setOrganizationId(1L);
        Assert.assertEquals(new Long(1), testOASR.getOrganizationId());

        List<String> orgAuthServerIds = new ArrayList<>();
        orgAuthServerIds.add("prova");
        testOASR.orgAuthServerIds(orgAuthServerIds);
        testOASRWithCredential.setOrgAuthServerIds(orgAuthServerIds);
        Assert.assertEquals(testOASR.getOrgAuthServerIds(), testOASRWithCredential.getOrgAuthServerIds());



        Assert.assertTrue(testOASR.equals(testOASR));
        Assert.assertFalse(testOASR.equals(null));
        Assert.assertFalse(testOASR.equals(new Favorite()));

        testOASR.setOrganizationCredentials(testCredential);
        Assert.assertTrue(testOASR.equals(testOASRWithCredential));

        Assert.assertEquals(Objects.hash(testOASR.getOrganizationCredentials(), testOASR.getOrganizationId(), testOASR.getOrgAuthServerIds()), testOASR.hashCode());

        Assert.assertEquals("class OrganizationAuthenticationServerRequest {\n    organizationCredentials: class OrganizationAuthenticationServerCredentials {\n        username: null\n        password: prova\n    }\n    organizationId: 1\n    orgAuthServerIds: [prova]\n}", testOASR.toString());
        OrganizationAuthenticationServerRequest testToString = new OrganizationAuthenticationServerRequest();

        Assert.assertEquals("class OrganizationAuthenticationServerRequest {\n    organizationCredentials: null\n    organizationId: null\n    orgAuthServerIds: []\n}", testToString.toString());
    }

}
