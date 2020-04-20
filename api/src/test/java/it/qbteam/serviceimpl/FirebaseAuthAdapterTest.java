package it.qbteam.serviceimpl;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.HashMap;
import java.util.Map;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import it.qbteam.exception.AuthenticationException;

@RunWith(SpringRunner.class)
public class FirebaseAuthAdapterTest {
    @MockBean
    private FirebaseAuth firebaseAuth;

    @MockBean
    private FirebaseToken firebaseToken;
    
    @TestConfiguration
    static public class AuthenticationServiceConfiguration {
        @Bean
        FirebaseAuthAdapter authenticationService(FirebaseAuth firebaseAuth) {
            return new FirebaseAuthAdapter(firebaseAuth);
        }
    }
    
    @Autowired
    private FirebaseAuthAdapter authenticationService;

    private String randomToken;

    private CreateRequest validRequest;

    private CreateRequest invalidRequest;

    private UserRecord userRecord;

    @Before
    public void setUp() throws FirebaseAuthException {
        randomToken = Double.valueOf(new Random().nextDouble()).toString();
        // Mocking FirebaseAuth#verifyIdToken
        // CASE 1: accessToken is null
        // CASE 2: accessToken is empty
        // CASE 3: accessToken is neither null nor empty, then we assume it is valid
        // CASE 4: accessToken is neither null nor empty but invalid falls into CASE 1 and CASE 2
        
        Mockito.when(firebaseAuth.verifyIdToken(null)).thenThrow(FirebaseAuthException.class); // 1, 4
        Mockito.when(firebaseAuth.verifyIdToken("")).thenThrow(FirebaseAuthException.class); // 2, 4
        Mockito.when(firebaseAuth.verifyIdToken(randomToken)).thenReturn(firebaseToken); // 3

        // Mocking FirebaseAuth#createUser
        validRequest = Mockito.mock(UserRecord.CreateRequest.class);
        invalidRequest = Mockito.mock(UserRecord.CreateRequest.class);
        userRecord = Mockito.mock(UserRecord.class);
    }
    
    @Test(expected = AuthenticationException.class)
    public void testCreateUserThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can either be true or false
        Assert.assertEquals(false, authenticationService.createUser(token, "email", "password"));
    }

    @Test(expected = AuthenticationException.class)
    public void testSetClaimsThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = null; // token can also be empty
        // expected can either be true or false
        Assert.assertEquals(true, authenticationService.setClaims(token, new HashMap<>()));
    }

    @Test(expected = AuthenticationException.class)
    public void testGetClaimsThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever map or object
        Assert.assertEquals(new HashMap<>(), authenticationService.getClaims(token));
    }

    @Test(expected = AuthenticationException.class)
    public void testGetUserIdByEmailThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        final String token = ""; // token can also be null
        // expected can be whatever string
        Assert.assertEquals("test@test.it", authenticationService.getUserIdByEmail(token, "email"));
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
    public void testCreateUserDoesNotCreateUserRecord() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.createUser(null)).thenThrow(FirebaseAuthException.class);

        // Mockito.doThrow(FirebaseAuthException.class).when(firebaseAuth).createUser(null);

        // Mockito.doThrow(FirebaseAuthException.class).when(firebaseAuth.createUser(invalidRequest));>
        
        Assert.assertEquals(false, authenticationService.createUser(randomToken, "email@email.it", "password"));
    }

    @Test
    public void testGetClaimsMapReturned() throws AuthenticationException {
        FirebaseToken userData = Mockito.mock(FirebaseToken.class);

        doReturn(new HashMap<>()).when(userData).getClaims();

        Assert.assertEquals(new HashMap<>(), authenticationService.getClaims(randomToken));
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
    public void testGetUserIdByEmailInvalidMail() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenReturn(userRecord);
        Mockito.when(userRecord.getUid()).thenReturn("uid");

        Assert.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, "email").isPresent());
    }

    @Test
    public void testSetClaimsEmptyMapSet() throws AuthenticationException, FirebaseAuthException {
        FirebaseToken userData = Mockito.mock(FirebaseToken.class);
        Mockito.when(userData.getUid()).thenReturn("uid");
        doNothing().when(firebaseAuth).setCustomUserClaims("uid", new HashMap<>());
        
        Assert.assertEquals(true, authenticationService.setClaims(randomToken, new HashMap<>()));
    }

    @Test
    public void testSetClaimsMapSet() throws AuthenticationException, FirebaseAuthException {
        Map<String, Boolean> m1 = new HashMap<>();
        Map<String, Object> m2 = new HashMap<>();

        m1.put(FirebaseAuthAdapter.ADMIN, true);
        m1.put(FirebaseAuthAdapter.USER, false);

        m2.put(FirebaseAuthAdapter.ADMIN, true);
        m2.put(FirebaseAuthAdapter.USER, false);

        FirebaseToken userData = Mockito.mock(FirebaseToken.class);
        Mockito.when(userData.getUid()).thenReturn("uid");
        doNothing().when(firebaseAuth).setCustomUserClaims("uid", m2);

        Assert.assertEquals(true, authenticationService.setClaims(randomToken, m1));
    }

    @Test
    public void testSetClaimsMapNotSetInvalidMap() throws AuthenticationException, FirebaseAuthException {
        Map<String, Boolean> m1 = new HashMap<>();
        Map<String, Object> m2 = new HashMap<>();

        m1.put("wrong_key", true);

        m2.put("wrong_key", true);

        FirebaseToken userData = Mockito.mock(FirebaseToken.class);
        Mockito.when(userData.getUid()).thenReturn("uid");
        doNothing().when(firebaseAuth).setCustomUserClaims("uid", m2);

        Assert.assertEquals(false, authenticationService.setClaims(randomToken, m1));
    }

    @Test
    public void testSetClaimsMapNotSetExceptionThrown() throws AuthenticationException, FirebaseAuthException {

        // FirebaseToken userData = Mockito.mock(FirebaseToken.class);
        // Mockito.when(userData.getUid()).thenReturn("uid");
        doThrow(FirebaseAuthException.class).when(firebaseAuth).setCustomUserClaims(anyString(), anyMap());
        
        Assert.assertEquals(false, authenticationService.setClaims(randomToken, new HashMap<>()));
    }
}
