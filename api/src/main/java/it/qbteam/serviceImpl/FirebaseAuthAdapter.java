package it.qbteam.serviceImpl;

import java.util.Map;
import java.util.Optional;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.service.AuthenticationService;

public class FirebaseAuthAdapter implements AuthenticationService {
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
    @Override
    public Boolean checkToken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return false;
        }
        try {
            firebaseAdaptee.verifyIdToken(accessToken);
            return true;
        } catch (FirebaseAuthException exception) {
            return false;
        }
    }

    @Override
    public Boolean createUser(String email, String password) throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getClaims(String accessToken) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException("The token must be validated first.");

        final FirebaseToken userData = getFirebaseUser(accessToken).get();
        return userData.getClaims();
    }

    @Override
    public Optional<String> getUserByEmail(String email) throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean setClaims(String accessToken, Map<String, Object> claims) throws AuthenticationException {
        if (!checkToken(accessToken))
            throw new AuthenticationException("The token must be validated first.");

        FirebaseToken userData = getFirebaseUser(accessToken).get();

        try {
            firebaseAdaptee.setCustomUserClaims(userData.getUid(), claims);
            return true;
        } catch(FirebaseAuthException exc) {
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

}