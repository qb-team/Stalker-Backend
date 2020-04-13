package it.qbteam.controller;

import it.qbteam.api.UserApi;
import it.qbteam.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
public class UserApiController implements UserApi {
    /**
     * POST /user/binduser : Bind an already existent user to its organization account.
     * Bind an already existent user to the organization. Only app users can access this end-point.
     *
     * @param user (required)
     * @return User bound successfully. The user record gets returned. (status code 201)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or The organization or the administrator could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<User> bindUserToOrganizationAccount(@Valid User user) {
        return null;
    }
}
