package it.qbteam.serviceimpl;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Permission;
import it.qbteam.persistence.repository.PermissionRepository;

@RunWith(SpringRunner.class)
public class AuthenticationServiceImplTest {
    @MockBean
    private FirebaseAuth firebaseAuth;

    @MockBean
    private PermissionRepository permissionRepo;

    @MockBean
    private FirebaseToken firebaseToken;
    
    @TestConfiguration
    static public class AuthenticationServiceConfiguration {
        @Bean
        AuthenticationServiceImpl authenticationService(FirebaseAuth firebaseAuth, PermissionRepository permissionRepository) {
            return new AuthenticationServiceImpl(firebaseAuth, permissionRepository);
        }
    }
    
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    private String randomToken;

    @Mock
    private CreateRequest validRequest;

    @Mock
    private CreateRequest invalidRequest;

    @Mock
    private UserRecord userRecord;

    @Before
    public void setUp() throws FirebaseAuthException {
        MockitoAnnotations.initMocks(this);
        randomToken = Double.valueOf(new Random().nextDouble()).toString();
        // Mocking FirebaseAuth#verifyIdToken
        // CASE 1: accessToken is null
        // CASE 2: accessToken is empty
        // CASE 3: accessToken is neither null nor empty, then we assume it is valid
        // CASE 4: accessToken is neither null nor empty but invalid falls into CASE 1 and CASE 2
        
        Mockito.when(firebaseAuth.verifyIdToken(null)).thenThrow(FirebaseAuthException.class); // 1, 4
        Mockito.when(firebaseAuth.verifyIdToken("")).thenThrow(FirebaseAuthException.class); // 2, 4
        Mockito.when(firebaseAuth.verifyIdToken(randomToken)).thenReturn(firebaseToken); // 3
    }
    
    @Test(expected = AuthenticationException.class)
    public void testCreateUserThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can either be true or false
        Assert.assertEquals(false, authenticationService.createUser(token, "email", "password"));
    }

    @Test(expected = AuthenticationException.class)
    public void testGetUserIdByEmailThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever string
        Assert.assertEquals("test@test.it", authenticationService.getUserIdByEmail(token, "email"));
    }

    @Test(expected = AuthenticationException.class)
    public void testIsWebAppAdministratorThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever string
        Assert.assertEquals("test@test.it", authenticationService.isWebAppAdministrator(token));
    }

    @Test
    public void testIsWebAppAdministratorReturnFalse() throws FirebaseAuthException, AuthenticationException {
        Mockito.when(permissionRepo.findByAdministratorId(anyString())).thenReturn(new LinkedList<>());
        Assert.assertFalse(authenticationService.isWebAppAdministrator(randomToken));
    }

    @Test(expected = AuthenticationException.class)
    public void testIsAppUserThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever string
        Assert.assertEquals("test@test.it", authenticationService.isAppUser(token));
    }
    @Test
    public void testIsAppUserReturnFalse() throws FirebaseAuthException, AuthenticationException {
        Mockito.when(permissionRepo.findByAdministratorId(anyString())).thenReturn(new LinkedList<>());
        Assert.assertTrue(authenticationService.isAppUser(randomToken));
    }

    @Test(expected = AuthenticationException.class)
    public void testGetUserIdThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever string
        Assert.assertEquals("test@test.it", authenticationService.getUserId(token));
    }

    @Test(expected = AuthenticationException.class)
    public void testGetEmailByUserIdThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever string
        // instead of "uid" there could be whatever string
        Assert.assertEquals("test@test.it", authenticationService.getEmailByUserId(token, "uid"));
    }

    @Test
    public void testCreateUserCreatesUserRecord() throws AuthenticationException, FirebaseAuthException {
        // Mocking FirebaseAuth#createUser
        Mockito.when(firebaseAuth.createUser(validRequest)).thenReturn(userRecord);
        
        Assert.assertEquals(true, authenticationService.createUser(randomToken, "email@email.it", "password"));
    }

    @Test
    public void testCreateUserDoesNotCreateUserRecordBecauseOfInvalidMail() throws AuthenticationException, FirebaseAuthException {
        // Mocking FirebaseAuth#createUser
        Mockito.when(firebaseAuth.createUser(invalidRequest)).thenReturn(userRecord);

        Assert.assertEquals(false, authenticationService.createUser(randomToken, "email", "password"));
    }

    @Test
    public void testCreateUserDoesNotCreateUserRecordBecauseOfInvalidPassword() throws AuthenticationException, FirebaseAuthException {
        // Mocking FirebaseAuth#createUser
        Mockito.when(firebaseAuth.createUser(validRequest)).thenReturn(userRecord);

        Assert.assertEquals(false, authenticationService.createUser(randomToken, "email@email.it", "psw"));
    }

    @Test
    public void testGetUserIdByEmailReturnsMail() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenReturn(userRecord);
        Mockito.when(userRecord.getUid()).thenReturn("uid");

        Assert.assertEquals(true, authenticationService.getUserIdByEmail(randomToken, "email@email.it").isPresent());
        Assert.assertEquals("uid", authenticationService.getUserIdByEmail(randomToken, "email@email.it").get());
    }

    @Test
    public void testGetUserIdByEmailNotValidRequest() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenThrow(FirebaseAuthException.class);

        Assert.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, "email@email.it").isPresent());
    }

    @Test
    public void testGetUserIdByEmailNullOrEmptyMail() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenReturn(userRecord);

        Assert.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, "").isPresent());
        Assert.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, null).isPresent());
    }

    @Test
    public void testIsWebAppAdministratorReturnsTrueIfThereArePermissions() throws AuthenticationException {
        List<Permission> permList = new LinkedList<>();
        permList.add(new Permission().permission(2).administratorId("uid"));
        Mockito.when(permissionRepo.findByAdministratorId(anyString())).thenReturn(permList);
        Mockito.when(firebaseToken.getUid()).thenReturn("uid");

        Assert.assertEquals(true, authenticationService.isWebAppAdministrator(randomToken));
    }
    @Test
    public void testGetUserId() throws AuthenticationException {
        Mockito.when(firebaseToken.getUid()).thenReturn("uid");
        Assert.assertEquals("uid", authenticationService.getUserId(randomToken));
    }
    @Test
    public void testGetEmailByUserId() throws AuthenticationException, FirebaseAuthException {
        Assert.assertEquals(Optional.empty(), authenticationService.getEmailByUserId(randomToken, ""));
        Mockito.when(firebaseAuth.getUser(anyString())).thenThrow(FirebaseAuthException.class);
        Assert.assertEquals(Optional.empty(), authenticationService.getEmailByUserId(randomToken, "prova"));
    }

}
