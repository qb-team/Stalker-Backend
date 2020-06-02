package it.qbteam.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

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


}
