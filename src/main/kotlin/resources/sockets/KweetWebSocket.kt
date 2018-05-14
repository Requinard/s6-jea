package resources.sockets

import annotations.Open
import com.google.gson.Gson
import models.KweetModel
import services.ProfileService
import services.UserService
import utils.JwtUtils
import utils.extensions.logger
import javax.inject.Inject
import javax.websocket.OnClose
import javax.websocket.OnError
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(
    value = "/api/sockets/kweets",
    encoders = []
)
@Open
open class KweetWebSocket @Inject constructor(
    val userService: UserService,
    val profileService: ProfileService,
    val jwtUtils: JwtUtils
) {
    val authRegex = Regex("(?:token=)([A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*)")

    companion object {
        var sessions = mutableMapOf<String, Session>()
    }

    @OnMessage
    fun echo(client: Session, message: String) {
        logger.info("Echoing . . .")
        client.basicRemote.sendText("Echo")
    }

    @OnOpen
    fun openSession(peer: Session) {
        logger.info("Opening session")

        val tokenString = authRegex.findAll(peer.queryString).first().groupValues.elementAt(1)
        val token = jwtUtils.verifyToken(tokenString)

        if (token !== null) {
            val user = userService.getByUsername(token.getClaim("username").asString())!!
            sessions[user.profileModel!!.screenname] = peer
        } else {
            logger.warn("User failed auth! closing")
            peer.close()
        }
        logger.info("Now at ${sessions.size} peers")
    }

    @OnClose
    fun closeSession(peer: Session) {
        logger.info("Closing session")
        removePeer(peer)
        logger.info("Now at ${sessions.size} peers")
    }

    private fun removePeer(peer: Session) {
        val map = sessions.filter { it.value == peer }
        sessions.remove(map.keys.first())
    }

    @OnError
    fun error(t: Throwable) =
        {
            logger.warn("error occured!", t)
        }

    fun send(message: KweetModel, peer: Session) {
        if (peer.isOpen) {
            logger.info("Sending message to ${peer.id}")
            val msg = mapOf(
                "message" to message.message,
                "profile" to message.profileModel.screenname,
                "created" to message.created.toString()
            )
            peer.basicRemote.sendText(Gson().toJson(msg))
        } else {
            logger.warn("Remote was closed! removing.")
            removePeer(peer)
        }
    }

    fun sendAll(message: KweetModel) {
        logger.info("Sending message to ${sessions.count()} peers")
        sessions.filter { it.key in message.profileModel.followers.map { it.screenname } }
            .map { send(message, it.value) }
    }
}
