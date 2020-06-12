package it.qbteam.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

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

        Assert.assertTrue(abr.equals(abr));
        Assert.assertFalse(abr.equals(null));
        Assert.assertFalse(abr.equals(new AdministratorBindingRequest()));

        Assert.assertEquals("class AdministratorBindingRequest {\n    organizationId: 1\n    orgAuthServerId: uidTest\n    mail: prova@prova.it\n    password: testpwd\n    permission: 3\n}", abr.toString());
    }

    @Test
    public void testFavorite() {
        Favorite f = new Favorite();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals("uid", f.userId("uid").getUserId());
        Assert.assertEquals(1L, f.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals("uid", f.orgAuthServerId("uid").getOrgAuthServerId());
        Assert.assertEquals(date, f.creationDate(date).getCreationDate());

        Assert.assertTrue(f.equals(f));
        Assert.assertFalse(f.equals(null));
        Assert.assertFalse(f.equals(new Favorite()));

        Assert.assertEquals("class Favorite {\n    userId: uid\n    organizationId: 1\n    orgAuthServerId: uid\n    creationDate: " + date.toString() + "\n}", f.toString());
    }

    @Test
    public void testOrganization() {
        Organization o = new Organization();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals(1L, o.id(1L).getId(), 0);
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

        Assert.assertTrue(o.equals(o));
        Assert.assertFalse(o.equals(null));
        Assert.assertFalse(o.equals(new Organization()));

        String toString = "class Organization {\n" +
                "    id: 1\n" + "    name: testorg\n" + "    description: descr\n" +
                "    image: image\n" + "    street: street\n" + "    number: 3a\n" +
                "    postCode: 11111\n" + "    city: city\n" + "    country: country\n" +
                "    authenticationServerURL: authurl.it\n" + "    creationDate: " + date.toString() + "\n    lastChangeDate: " + date.toString() + "\n" +
                "    trackingArea: {\"json\": true}\n" + "    trackingMode: " + Organization.TrackingModeEnum.anonymous.toString() + "\n}";
        Assert.assertEquals(toString, o.toString());
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

        Assert.assertTrue(access.equals(access));
        Assert.assertFalse(access.equals(null));
        Assert.assertFalse(access.equals(new OrganizationAccess()));

        Assert.assertEquals("class OrganizationAccess {\n    id: 1\n    entranceTimestamp: " + date.toString() + "\n    exitTimestamp: " + date.toString() + "\n    exitToken: exitToken\n    organizationId: 1\n    orgAuthServerId: serverId\n}", access.toString());
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

    @Test
    public void testOrganizationConstraint() {
        OrganizationConstraint oc = new OrganizationConstraint();

        Assert.assertEquals(1L, oc.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals(2000.50D, oc.maxArea(2000.50D).getMaxArea(), 0);

        Assert.assertTrue(oc.equals(oc));
        Assert.assertFalse(oc.equals(null));
        Assert.assertFalse(oc.equals(new OrganizationConstraint()));

        Assert.assertEquals("class OrganizationConstraint {\n    organizationId: 1\n    maxArea: 2000.5\n}", oc.toString());
    }

    @Test
    public void testOrganizationDeletionRequest() {
        OrganizationDeletionRequest odr = new OrganizationDeletionRequest();

        Assert.assertEquals(15L, odr.organizationId(15L).getOrganizationId(), 0);
        Assert.assertEquals("adminId", odr.administratorId("adminId").getAdministratorId());
        Assert.assertEquals("super important reason", odr.requestReason("super important reason").getRequestReason());

        Assert.assertTrue(odr.equals(odr));
        Assert.assertFalse(odr.equals(null));
        Assert.assertFalse(odr.equals(new OrganizationDeletionRequest()));

        Assert.assertEquals("class OrganizationDeletionRequest {\n    organizationId: 15\n    requestReason: super important reason\n    administratorId: adminId\n}", odr.toString());
    }

    @Test
    public void testOrganizationMovement() {
        OrganizationMovement om = new OrganizationMovement();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals("exitToken", om.exitToken("exitToken").getExitToken());
        Assert.assertEquals(date, om.timestamp(date).getTimestamp());
        Assert.assertEquals(1, om.movementType(1).getMovementType(), 0);
        Assert.assertEquals(10L, om.organizationId(10L).getOrganizationId(), 0);
        Assert.assertEquals("id", om.orgAuthServerId("id").getOrgAuthServerId());

        Assert.assertTrue(om.equals(om));
        Assert.assertFalse(om.equals(null));
        Assert.assertFalse(om.equals(new OrganizationMovement()));

        Assert.assertEquals("class OrganizationMovement {\n    exitToken: exitToken\n    timestamp: " + date.toString() + "\n    movementType: 1\n    organizationId: 10\n    orgAuthServerId: id\n}", om.toString());
    }

    @Test
    public void testOrganizationPresenceCounter() {
        OrganizationPresenceCounter opc = new OrganizationPresenceCounter();

        Assert.assertEquals(1L, opc.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals(100, opc.counter(100).getCounter(), 0);

        Assert.assertTrue(opc.equals(opc));
        Assert.assertFalse(opc.equals(null));
        Assert.assertFalse(opc.equals(new OrganizationPresenceCounter()));

        Assert.assertEquals("class OrganizationPresenceCounter {\n    organizationId: 1\n    counter: 100\n}", opc.toString());
    }

    @Test
    public void testPermission() {
        Permission p = new Permission();

        Assert.assertEquals("adminId", p.administratorId("adminId").getAdministratorId());
        Assert.assertEquals("id", p.orgAuthServerId("id").getOrgAuthServerId());
        Assert.assertEquals("mail", p.mail("mail").getMail());
        Assert.assertEquals(1L, p.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals(2, p.permission(2).getPermission(), 0);
        Assert.assertEquals("adminId2", p.nominatedBy("adminId2").getNominatedBy());

        Assert.assertTrue(p.equals(p));
        Assert.assertFalse(p.equals(null));
        Assert.assertFalse(p.equals(new Permission()));

        Assert.assertEquals("class Permission {\n    administratorId: adminId\n    organizationId: 1\n    orgAuthServerId: id\n    mail: mail\n    permission: 2\n    nominatedBy: adminId2\n}", p.toString());
    }

    @Test
    public void testPlace() {
        Place p = new Place();

        Assert.assertEquals(20L, p.id(20L).getId(), 0);
        Assert.assertEquals("placename", p.name("placename").getName());
        Assert.assertEquals(1L, p.organizationId(1L).getOrganizationId(), 0);
        Assert.assertEquals("{\"json\": true}", p.trackingArea("{\"json\": true}").getTrackingArea());

        Assert.assertTrue(p.equals(p));
        Assert.assertFalse(p.equals(null));
        Assert.assertFalse(p.equals(new Place()));

        Assert.assertEquals("class Place {\n    id: 20\n    name: placename\n    organizationId: 1\n    trackingArea: {\"json\": true}\n}", p.toString());
    }

    @Test
    public void testPlaceAccess() {
        PlaceAccess access = new PlaceAccess();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals(1L, access.id(1L).getId(), 0);
        Assert.assertEquals(date, access.entranceTimestamp(date).getEntranceTimestamp());
        Assert.assertEquals(date, access.exitTimestamp(date).getExitTimestamp());
        Assert.assertEquals("exitToken", access.exitToken("exitToken").getExitToken());
        Assert.assertEquals(1L, access.placeId(1L).getPlaceId(), 0);
        Assert.assertEquals("serverId", access.orgAuthServerId("serverId").getOrgAuthServerId());

        Assert.assertTrue(access.equals(access));
        Assert.assertFalse(access.equals(null));
        Assert.assertFalse(access.equals(new PlaceAccess()));

        Assert.assertEquals("class PlaceAccess {\n    id: 1\n    entranceTimestamp: " + date.toString() + "\n    exitTimestamp: " + date.toString() + "\n    exitToken: exitToken\n    placeId: 1\n    orgAuthServerId: serverId\n}", access.toString());
    }

    @Test
    public void testPlaceMovement() {
        PlaceMovement om = new PlaceMovement();

        OffsetDateTime date = OffsetDateTime.now();

        Assert.assertEquals("exitToken", om.exitToken("exitToken").getExitToken());
        Assert.assertEquals(date, om.timestamp(date).getTimestamp());
        Assert.assertEquals(1, om.movementType(1).getMovementType(), 0);
        Assert.assertEquals(10L, om.placeId(10L).getPlaceId(), 0);
        Assert.assertEquals("id", om.orgAuthServerId("id").getOrgAuthServerId());

        Assert.assertTrue(om.equals(om));
        Assert.assertFalse(om.equals(null));
        Assert.assertFalse(om.equals(new PlaceMovement()));

        Assert.assertEquals("class PlaceMovement {\n    exitToken: exitToken\n    timestamp: " + date.toString() + "\n    movementType: 1\n    placeId: 10\n    orgAuthServerId: id\n}", om.toString());
    }

    @Test
    public void testPlacePresenceCounter() {
        PlacePresenceCounter opc = new PlacePresenceCounter();

        Assert.assertEquals(1L, opc.placeId(1L).getPlaceId(), 0);
        Assert.assertEquals(100, opc.counter(100).getCounter(), 0);

        Assert.assertTrue(opc.equals(opc));
        Assert.assertFalse(opc.equals(null));
        Assert.assertFalse(opc.equals(new PlacePresenceCounter()));

        Assert.assertEquals("class PlacePresenceCounter {\n    placeId: 1\n    counter: 100\n}", opc.toString());
    }

    @Test
    public void testTimePerUserReport() {
        TimePerUserReport t = new TimePerUserReport();

        Assert.assertEquals(10L, t.placeId(10L).getPlaceId(), 0);
        Assert.assertEquals("id", t.orgAuthServerId("id").getOrgAuthServerId());
        Assert.assertEquals(3600L, t.totalTime(3600L).getTotalTime(), 0);

        Assert.assertTrue(t.equals(t));
        Assert.assertFalse(t.equals(null));
        Assert.assertFalse(t.equals(new TimePerUserReport()));

        Assert.assertEquals("class TimePerUserReport {\n    placeId: 10\n    orgAuthServerId: id\n    totalTime: 3600\n}", t.toString());
    }

}
