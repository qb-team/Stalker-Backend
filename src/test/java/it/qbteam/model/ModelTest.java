package it.qbteam.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.time.OffsetDateTime;
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

}
