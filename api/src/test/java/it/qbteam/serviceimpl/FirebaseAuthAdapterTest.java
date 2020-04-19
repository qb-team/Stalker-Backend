package it.qbteam.serviceimpl;

import java.util.HashMap;
import java.util.Random;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

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

    @Before
    public void mockFirebaseAuthMethods() throws FirebaseAuthException {
        randomToken = new Double(new Random().nextDouble()).toString();
        // Mocking FirebaseAuth
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

}
