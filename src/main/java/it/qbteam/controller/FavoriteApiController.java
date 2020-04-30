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
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class FavoriteApiController extends StalkerBaseController implements FavoriteApi {

    private FavoriteService favoriteService;

    @Autowired
    public FavoriteApiController(NativeWebRequest request, AuthenticationService authenticationService, FavoriteService favoriteService) {
        super(request, authenticationService);
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
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<Favorite>(HttpStatus.UNAUTHORIZED); //401
        }
        
        if(isWebAppAdministrator(getAccessToken().get())){
            return new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN); //403
        }

        if(favoriteService.isPresent(favorite)){
            return new ResponseEntity<Favorite>(HttpStatus.BAD_REQUEST); //400
        }

        Optional<Favorite> favouriteInsertion = favoriteService.addFavoriteOrganization(favorite);

        if(favouriteInsertion.isPresent())
        {
            return new ResponseEntity<Favorite>(favouriteInsertion.get(), HttpStatus.CREATED); // 201
        }
        else
        {
            return new ResponseEntity<Favorite>(HttpStatus.NOT_FOUND); // 404
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
     * or List of organizations could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<Organization>> getFavoriteOrganizationList(String userId) {
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
        }
        
        if(isWebAppAdministrator(getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }

        if(!authenticationProviderUserId(getAccessToken().get()).get().equals(userId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }

        List<Organization> returnList = favoriteService.getFavoriteOrganizationList(userId);

        if(returnList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } else {
            return new ResponseEntity<>(returnList, HttpStatus.OK); // 200
        }
        // 404 non fatto
    }


    /**
     * POST /favorite/removefavorite : Removes the organization from the user&#39;s favorite organization list.
     * Removes the organization from the user&#39;s favorite organization list. Only app users can access this end-point.
     *
     * @param favorite (required)
     * @return Organization successfully removed from the list of favorites. (status code 205)
     * or The organization is not part of the list of favorite organizations. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> removeFavoriteOrganization(@Valid Favorite favorite) {
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED); //401
        }
        if (!favoriteService.isPresent(favorite)){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); //400 //404g
        }
        if(isWebAppAdministrator(getAccessToken().get())){
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN); //403
        }
        favoriteService.removeFavoriteOrganization(favorite);
        return new ResponseEntity<Void>(HttpStatus.OK); //200

    }
}
