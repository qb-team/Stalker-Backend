package it.qbteam.serviceimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Permission;
import it.qbteam.repository.PermissionRepository;
import it.qbteam.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "Token is not valid or expired.";

    /**
     * Access point to Firebase Admin SDK: Authentication
     */
    private FirebaseAuth firebaseAdaptee;

    private PermissionRepository permissionRepo;

    @Autowired
    public AuthenticationServiceImpl(FirebaseAuth firebaseAuth, PermissionRepository permissionRepository) {
        this.firebaseAdaptee = firebaseAuth;
        this.permissionRepo = permissionRepository;
    }

    /**
     * Checks whether the access token is (still) valid or not.
     * 
     * @param accessToken access token returned by the authentication service in the
     *                    client application
     * @return Boolean {@code true} if {@code accessToken} is valid, otherwise
     *         {@code false}
     */
    private Boolean checkToken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty())
            return false;

        try {
            firebaseAdaptee.verifyIdToken(accessToken);
            return true;
        } catch (FirebaseAuthException exception) {
            return false;
        }
    }

        /**
     * Returns true if the user is a web-app administrator.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return true if the user is a web-app administrator., false otherwise
     */
    public Boolean isWebAppAdministrator(String accessToken) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        final String administratorId = getFirebaseUser(accessToken).get().getUid();
        List<Permission> adminList = new LinkedList<>();
        permissionRepo.findByAdministratorId(administratorId).forEach(adminList::add);

        return !adminList.isEmpty();
    }

    /**
     * Returns true if the user is an app user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return true if the user is an app user, false otherwise
     */
    public Boolean isAppUser(String accessToken) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        final String administratorId = getFirebaseUser(accessToken).get().getUid();
        List<Permission> adminList = new LinkedList<>();
        
        permissionRepo.findByAdministratorId(administratorId).forEach(adminList::add);

        return adminList.isEmpty();
    }

    /**
     * Creates a new user on the system with the given email and password. Depending
     * on the implementation, it then sends a validation e-mail to the given
     * address.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email       e-mail address of the user to be created
     * @param password    password of the user to be created
     * @return Boolean {@code true} if the user account got created, {@code false}
     *         if it was not
     */
    @Override
    public Boolean createUser(String accessToken, String email, String password) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);
        
        if(email == null || email.isEmpty() || password.length() < 6)
            return false;

        try {
            final CreateRequest request = new UserRecord.CreateRequest().setEmail(email).setPassword(password);
            firebaseAdaptee.createUser(request); // corrisponde a firebaseAuth nel test
            return true;
        } catch (FirebaseAuthException | IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * Returns the userId related to the email address.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email e-mail address of the userID to be returned
     * @return userId if the user account was found, and null if it was not.
     */
    @Override
    public Optional<String> getUserIdByEmail(String accessToken, String email) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        if(email == null || email.isEmpty())
            return Optional.empty();
        
        try {
            final UserRecord userRecord = firebaseAdaptee.getUserByEmail(email);
            return Optional.of(userRecord.getUid());
        } catch (FirebaseAuthException | IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    /**
     * Returns the userId related to the current user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email e-mail address of the userID to be returned
     * @return userId if the user account was found, and null if it was not.
     */
    @Override
    public String getUserId(String accessToken) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);
        
        final FirebaseToken userData = getFirebaseUser(accessToken).get();

        return userData.getUid();      
    }

    /**
     * Returns the FirebaseToken (Firebase object containing all data Firebase has
     * of the user if it is valid). Use only after checkToken has been called.
     * 
     * @param accessToken given by the user
     * @return the FirebaseToken instance containing all data of the user
     */
    private Optional<FirebaseToken> getFirebaseUser(String accessToken) {
        try {
            return Optional.of(firebaseAdaptee.verifyIdToken(accessToken));
        } catch (FirebaseAuthException exc) {
            return Optional.empty();
        }
    }

	@Override
	public Optional<String> getEmailByUserId(String accessToken, String userId) throws AuthenticationException {
        if (!checkToken(accessToken))
        throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        if (userId == null || userId.isEmpty())
            return Optional.empty();

        try {
            final UserRecord userRecord = firebaseAdaptee.getUser(userId);
            return Optional.of(userRecord.getEmail());
        } catch (FirebaseAuthException | IllegalArgumentException exception) {
            return Optional.empty();
        }
	}

    

}
