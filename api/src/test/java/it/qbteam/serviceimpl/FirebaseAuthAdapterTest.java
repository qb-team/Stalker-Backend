package it.qbteam.serviceimpl;

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

import it.qbteam.serviceImpl.FirebaseAuthAdapter;

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

    @Before
    public void mockFirebaseAuthMethods() throws FirebaseAuthException {
        // Mocking FirebaseAuth
        Mockito.when(firebaseAuth.verifyIdToken(null)).thenThrow(FirebaseAuthException.class);
        Mockito.when(firebaseAuth.verifyIdToken("")).thenThrow(FirebaseAuthException.class);
        Mockito.when(firebaseAuth.verifyIdToken("ciao")).thenReturn(firebaseToken);
    }

    @Test
    public void testCorrectAccessTokenReturnsTrue() {
        Assert.assertEquals(true, authenticationService.checkToken("ciao"));
    }

    @Test
    public void testNullAccessTokenReturnsFalse() {
        Assert.assertEquals(false, authenticationService.checkToken(null));
    }

    @Test
    public void testEmptyAccessTokenReturnsFalse() {
        Assert.assertEquals(false, authenticationService.checkToken(""));
    }

}