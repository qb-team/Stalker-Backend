package it.qbteam.serviceimpl;

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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.repository.PermissionRepository;

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

    private CreateRequest validRequest;

    private CreateRequest invalidRequest;

    private UserRecord userRecord;

    public AuthenticationServiceImplTest() {}

    // @BeforeEach
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
}
