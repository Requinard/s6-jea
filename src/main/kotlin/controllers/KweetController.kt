package controllers

import models.KweetModel
import services.KweetService
import utils.extensions.logger
import java.io.Serializable
import java.util.UUID
import javax.enterprise.context.SessionScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
class KweetController @Inject constructor(
    val kweetService: KweetService
) : Serializable {
    fun getUid() = UUID.randomUUID()

    fun getTimeline() = kweetService.getAllKweets()

    fun delete(kweet: KweetModel) {
        this.logger.info("Deleting tweet with id ${kweet.Id}")
        kweetService.delete(kweet)
    }
}
