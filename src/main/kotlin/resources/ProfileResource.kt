package resources

import annotations.JwtTokenNeeded
import annotations.Open
import com.google.gson.Gson
import models.ProfileModel
import serializers.ProfileSerializer
import serializers.inputs.ChangeProfileSerializer
import services.ProfileService
import services.UserService
import utils.JwtUtils
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("profiles")
@Open
class ProfileResource @Inject constructor(
    val profileService: ProfileService,
    val userService: UserService,
    val jwtUtils: JwtUtils
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    fun get(
        @Context headers: HttpHeaders
    ): Response {
        val user = jwtUtils.loggedInUser(headers)
        return Response.ok(ProfileSerializer(user.profileModel!!)).build()
    }

    @PUT
    @JwtTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    fun put(
        body: String,
        @Context headers: HttpHeaders
    ): Response {
        val newProfile = Gson().fromJson(body, ChangeProfileSerializer::class.java)
        val profile = jwtUtils.loggedInUser(headers).profileModel!!

        profile.location = newProfile.location ?: profile.location
        profile.bio = newProfile.bio ?: profile.bio
        profile.website = newProfile.website ?: profile.website

        profileService.update(profile)

        return Response.ok(ProfileSerializer(profile)).build()
    }

    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    fun getByScreenName(
        @PathParam("screenname") screenname: String
    ): Response {
        val profile = profileService.getByScreenname(screenname)

        if (profile != null)
            return Response.ok(ProfileSerializer(profile)).build()
        else return Response.noContent().build()
    }

    @GET
    @Path("{screenname}/kweets")
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    fun getKweetsByScreenName(
        @PathParam("screenname") screenname: String
    ) = profileService.getByScreenname(screenname)?.kweets

    @POST
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    fun postFollowScreenname(
        @PathParam("screenname") screenname: String,
        @Context headers: HttpHeaders
    ): Response {
        val follower = jwtUtils.loggedInUser(headers).profileModel ?: return Response.status(404, "Did not find follower").build()
        val leader: ProfileModel = profileService.getByScreenname(screenname)
            ?: return Response.status(404, "Did not find leader").build()

        val wasAdded = profileService.follow(follower, leader)

        if (wasAdded)
            return Response.ok(ProfileSerializer(follower))
                .build()
        return Response.notModified("Follower is already following leader!").build()
    }
}
