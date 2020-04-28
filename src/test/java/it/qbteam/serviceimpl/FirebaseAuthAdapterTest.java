package it.qbteam.serviceimpl;

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

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.repository.sql.PermissionRepository;

@RunWith(SpringRunner.class)
// @ExtendWith(SpringExtension.class)
// @ContextConfiguration(classes = FirebaseAuthAdapterTest.AuthenticationServiceConfiguration.class)
// @SpringBootTest
public class FirebaseAuthAdapterTest {
    @MockBean
    private FirebaseAuth firebaseAuth;

    @MockBean
    private PermissionRepository permissionRepo;

    @MockBean
    private FirebaseToken firebaseToken;
    
    @TestConfiguration
    static public class AuthenticationServiceConfiguration {
        @Bean
        FirebaseAuthAdapter authenticationService(FirebaseAuth firebaseAuth, PermissionRepository permissionRepository) {
            return new FirebaseAuthAdapter(firebaseAuth, permissionRepository);
        }
    }
    
    @Autowired
    private FirebaseAuthAdapter authenticationService;

    private String randomToken;

    private CreateRequest validRequest;

    private CreateRequest invalidRequest;

    private UserRecord userRecord;

    public FirebaseAuthAdapterTest() {}

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
    // @Test
    public void testCreateUserThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        // Assertions.assertThrows(AuthenticationException.class, () -> {
            final String token = ""; // token can also be null
            // expected can either be true or false
            Assertions.assertEquals(false, authenticationService.createUser(token, "email", "password"));
        // } );
    }

    @Test(expected = AuthenticationException.class)
    // @Test
    public void testGetUserIdByEmailThrowsExceptionIfAccessTokenIsInvalid() throws AuthenticationException {
        // Assertions.assertThrows(AuthenticationException.class, () -> {
            final String token = ""; // token can also be null
            // expected can be whatever string
            Assertions.assertEquals("test@test.it", authenticationService.getUserIdByEmail(token, "email"));
        // });
    }

    @Test
    public void testCreateUserCreatesUserRecord() throws AuthenticationException, FirebaseAuthException {
        // Mocking FirebaseAuth#createUser
        Mockito.when(firebaseAuth.createUser(validRequest)).thenReturn(userRecord);
        
        Assertions.assertEquals(true, authenticationService.createUser(randomToken, "email@email.it", "password"));
    }

    @Test
    public void testCreateUserDoesNotCreateUserRecordBecauseOfInvalidMail() throws AuthenticationException, FirebaseAuthException {
        // Mocking FirebaseAuth#createUser
        Mockito.when(firebaseAuth.createUser(invalidRequest)).thenReturn(userRecord);

        Assertions.assertEquals(false, authenticationService.createUser(randomToken, "email", "password"));
    }

    @Test
    public void testCreateUserDoesNotCreateUserRecordBecauseOfInvalidPassword() throws AuthenticationException, FirebaseAuthException {
        // Mocking FirebaseAuth#createUser
        Mockito.when(firebaseAuth.createUser(validRequest)).thenReturn(userRecord);

        Assertions.assertEquals(false, authenticationService.createUser(randomToken, "email@email.it", "psw"));
    }

    @Test
    public void testCreateUserDoesNotCreateUserRecord() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.createUser(null)).thenThrow(FirebaseAuthException.class);

        // Mockito.doThrow(FirebaseAuthException.class).when(firebaseAuth).createUser(null);

        // Mockito.doThrow(FirebaseAuthException.class).when(firebaseAuth.createUser(invalidRequest));>

        Assertions.assertEquals(false, authenticationService.createUser(randomToken, "email@email.it", "password"));
    }

    @Test
    public void testGetUserIdByEmailReturnsMail() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenReturn(userRecord);
        Mockito.when(userRecord.getUid()).thenReturn("uid");

        Assertions.assertEquals(true, authenticationService.getUserIdByEmail(randomToken, "email@email.it").isPresent());
        Assertions.assertEquals("uid", authenticationService.getUserIdByEmail(randomToken, "email@email.it").get());
    }

    @Test
    public void testGetUserIdByEmailNotValidRequest() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenThrow(FirebaseAuthException.class);

        Assertions.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, "email@email.it").isPresent());
    }

    @Test
    public void testGetUserIdByEmailNullOrEmptyMail() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenReturn(userRecord);

        Assertions.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, "").isPresent());
        Assertions.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, null).isPresent());
    }

    @Test
    public void testGetUserIdByEmailInvalidMail() throws AuthenticationException, FirebaseAuthException {
        Mockito.when(firebaseAuth.getUserByEmail("email@email.it")).thenReturn(userRecord);
        Mockito.when(userRecord.getUid()).thenReturn("uid");

        Assertions.assertEquals(false, authenticationService.getUserIdByEmail(randomToken, "email").isPresent());
    }
}
