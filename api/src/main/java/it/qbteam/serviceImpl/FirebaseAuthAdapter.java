package it.qbteam.serviceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import org.springframework.beans.factory.annotation.Autowired;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.service.AuthenticationService;

public class FirebaseAuthAdapter implements AuthenticationService {

    private static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "Token is not valid or expired.";

    /**
     * Access point to Firebase Admin SDK: Authentication
     */
    FirebaseAuth firebaseAdaptee;

    @Autowired
    public FirebaseAuthAdapter(FirebaseApp firebaseApp) {
        this.firebaseAdaptee = FirebaseAuth.getInstance(firebaseApp);
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

        final UserRecord.CreateRequest request = new UserRecord.CreateRequest().setEmail(email).setPassword(password);

        try {
            firebaseAdaptee.createUser(request);
            return true;
        } catch (FirebaseAuthException exception) {
            return false;
        }
    }

    /**
     * Returns the claims of the user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return Map<String, Boolean> claims of the user requested by the client
     */
    @Override
    public Map<String, Boolean> getClaims(String accessToken) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        final FirebaseToken userData = getFirebaseUser(accessToken).get();

        final Map<String, Boolean> m = new HashMap<>();

        userData.getClaims().forEach((key, value) -> m.put(key, Boolean.parseBoolean(value.toString())));

        return m;
    }

    /**
     * Returns the userId related to the email address.
     * 
     * @param email e-mail address of the userID to be returned
     * @return userId if the user account was found, and null if it was not.
     */
    @Override
    public Optional<String> getUserIdByEmail(String accessToken, String email) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        try {
            final UserRecord userRecord = firebaseAdaptee.getUserByEmail(email);
            return Optional.of(userRecord.getUid());
        } catch (FirebaseAuthException exception) {
            return Optional.empty();
        }
    }

    /**
     * Given a map of ({@code String},{@code Boolean}) pairs in which {@code String}
     * keys are considered to be only {@code USER} or {@code ADMIN} sets the claims
     * of the user/admin owner of the {@code accessToken}.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param claims      claims to set to the user requested by the client
     * @return Boolean {@code true} if {@code Permission} data got stored in the
     *         authentication service, otherwise {@code false}. It returns
     *         {@code false} even if {@code accessToken} was not valid
     */
    @Override
    public Boolean setClaims(String accessToken, Map<String, Boolean> claims) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE);

        final FirebaseToken userData = getFirebaseUser(accessToken).get();

        try {
            if (checkClaimsValidity(claims)) {
                final Map<String, Object> m = userData.getClaims();

                firebaseAdaptee.setCustomUserClaims(userData.getUid(), m);
                return true;
            } else {
                return false;
            }
        } catch (FirebaseAuthException exc) {
            return false;
        }
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

    /**
     * Checks whether the claims map given is correct.
     * 
     * @param claims is a map of pairs (String, Object)
     * @return true if the map is valid, false if it is not
     */
    private Boolean checkClaimsValidity(Map<String, Boolean> claims) {
        Iterator<Entry<String, Boolean>> entryIterator = claims.entrySet().iterator();
        Boolean invalidEntryExists = false;

        while (!invalidEntryExists || entryIterator.hasNext()) {
            final Entry<String, Boolean> e = entryIterator.next();
            final String key = e.getKey();
            if (!key.equals(ADMIN) && !key.equals(USER)) {
                invalidEntryExists = true;
            }
        }

        return !invalidEntryExists;
    }

}