package it.qbteam.controller;

import it.qbteam.api.FavoriteApi;
import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;
import it.qbteam.service.AuthenticationService;

import it.qbteam.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@Controller
public class FavoriteApiController implements FavoriteApi {

    private FavoriteService favoriteService;

    private AuthenticationFacade authFacade;

    @Autowired
    public FavoriteApiController(NativeWebRequest request, AuthenticationService authenticationService, FavoriteService favoriteService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService);
        this.favoriteService = favoriteService;
    }

    /**
     * POST /favorite/addfavorite : Adds a new organization to the user&#39;s favorite organization list.
     * Adds a new organization to the user&#39;s favorite organization list. If the organization has trackingMode: authenticated, then the user account of the organization must be linked to Stalker&#39;s account. Only app users can access this end-point.
     *
     * @param favorite (required)
     * @return Organization successfully added to the list of favorite.  The favorite record just added (including the organization) gets returned. (status code 201)
     * or The user already added the organization to the list of favorite organizations. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Users who did not bind their account with their organization&#39;s account and administrators cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Favorite> addFavoriteOrganization(@Valid Favorite favorite) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
        }

        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }

        if(favoriteService.getFavorite(favorite)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }

        Optional<Favorite> favouriteInsertion = favoriteService.addFavoriteOrganization(favorite);

        if(favouriteInsertion.isPresent())
        {
            return new ResponseEntity<>(favouriteInsertion.get(), HttpStatus.CREATED); // 201
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
        }
    }

    /**
     * GET /favorite/{userId} : Gets the list of favorite organizations of a user.
     * Gets the list of favorite organizations of a user.  Only app users can access this end-point.
     *
     * @param userId ID of the user. It must be the same of the userId of the authenticated user. (required)
     * @return List of favorite organizations returned successfully. (status code 200)
     * or List of favorite organizations is empty. Nothing gets returned. (status code 204)
     * or The supplied userId is incorrect. Nothing gets returned. (status code 400)
     * or The user or the administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<Organization>> getFavoriteOrganizationList(String userId) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
        }
        
        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }

        if(!authFacade.authenticationProviderUserId(authFacade.getAccessToken().get()).get().equals(userId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }

        List<Organization> returnList = favoriteService.getFavoriteOrganizationList(userId);

        if(returnList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } else {
            return new ResponseEntity<>(returnList, HttpStatus.OK); // 200
        }

    }


    /**
     * POST /favorite/removefavorite : Removes the organization from the user&#39;s favorite organization list.
     * Removes the organization from the user&#39;s favorite organization list. Only app users can access this end-point.
     *
     * @param favorite (required)
     * @return Organization successfully removed from the list of favorites. (status code 205)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or The favorite was not found, hence it was not removed. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> removeFavoriteOrganization(@Valid Favorite favorite) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
        }

        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }

        if (!favoriteService.getFavorite(favorite)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
        
        favoriteService.removeFavoriteOrganization(favorite);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT); //205

    }
}
