package it.qbteam.controller;

import it.qbteam.api.AuthenticationApi;
import it.qbteam.model.AuthResponseAdmin;
import it.qbteam.model.AuthResponseUser;
import it.qbteam.model.AuthenticationDataAdmin;
import it.qbteam.model.AuthenticationDataUser;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public class AuthenticationApiController implements AuthenticationApi {
    /**
     * POST /authentication/adminLogin : Lets the admin login via the authentication service.
     * Lets the admin login via the authentication service.
     *
     * @param authenticationDataAdmin (required)
     * @return Logged in successfully. (status code 200)
     * or Incorrect credentials. (status code 400)
     */
    @Override
    public ResponseEntity<AuthResponseAdmin> adminLogin(@Valid AuthenticationDataAdmin authenticationDataAdmin) {
        return null;
    }

    /**
     * POST /authentication/adminLogout : Lets the admin logout from the system.
     * Lets the admin logout from the system.
     *
     * @return Logged out successfully. (status code 200)
     */
    @Override
    public ResponseEntity<Void> adminLogout() {
        return null;
    }

    /**
     * POST /authentication/userLogin : Lets the user login via the authentication service.
     * Lets the user login via the authentication service.
     *
     * @param authenticationDataUser (required)
     * @return Logged in successfully. (status code 200)
     * or Incorrect credentials. (status code 400)
     */
    @Override
    public ResponseEntity<AuthResponseUser> userLogin(@Valid AuthenticationDataUser authenticationDataUser) {
        return null;
    }

    /**
     * POST /authentication/userLogout : Lets the user logout from the system.
     * Lets the user logout from the system.
     *
     * @return Logged out successfully. (status code 200)
     */
    @Override
    public ResponseEntity<Void> userLogout() {
        return null;
    }

    /**
     * POST /authentication/userRegistration : Lets the user registrate into the system.
     * Lets the user registrate into the system.
     *
     * @param authenticationDataUser (required)
     * @return Registered successfully. (status code 200)
     * or Too weak password. (status code 400)
     * or Account already present with this e-mail address. (status code 406)
     */
    @Override
    public ResponseEntity<AuthResponseUser> userRegistration(@Valid AuthenticationDataUser authenticationDataUser) {
        return null;
    }
}
