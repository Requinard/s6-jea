package resources

import models.ProfileModel
import serializers.ProfileSerializer
import services.ProfileService
import services.UserService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("profiles")
class ProfileResource @Inject constructor(
    val profileService: ProfileService,
    userService: UserService
) : BaseResource(userService) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get() = ProfileSerializer(profileService.getByScreenname("john")!!)

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun putByScreenname(): Response {
        val profile = user.profileModel!!
        profile.bio = getParam("bio") ?: profile.bio
        profile.website = getParam("website") ?: profile.website
        profile.location = getParam("location") ?: profile.location

        profileService.update(profile)

        return Response.ok(ProfileSerializer(profile)).build()
    }

    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
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
    fun getKweetsByScreenName(
        @PathParam("screenname") screenname: String
    ) = profileService.getByScreenname(screenname)?.kweets

    @POST
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    fun postFollowScreenname(
        @PathParam("screenname") screenname: String
    ): Response {
        val follower = user.profileModel ?: return Response.status(404, "Did not find follower").build()
        val leader: ProfileModel = profileService.getByScreenname(screenname) ?: return Response.status(404, "Did not find leader").build()

        val wasAdded = profileService.follow(follower, leader)

        if (wasAdded)
            return Response.ok(ProfileSerializer(follower))
                .build()
        return Response.notModified("Follower is already following leader!").build()
    }
}
