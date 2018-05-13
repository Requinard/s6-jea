package resources.sockets

import annotations.Open
import serializers.SimpleKweetSerializer
import utils.extensions.logger
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
open class KweetWebSocket {
    companion object {
        var sessions = mutableSetOf<Session>()
    }

    @OnMessage
    fun echo(client: Session, message: String) {
        logger.info("Echoing . . .")
        client.basicRemote.sendText("Echo")
    }

    @OnOpen
    fun openSession(peer: Session) {
        logger.info("Opening session")
        sessions.add(peer)
        logger.info("Now at ${sessions.size} peers")
    }

    @OnClose
    fun closeSession(peer: Session) {
        logger.info("Closing session")
        sessions.remove(peer)
        logger.info("Now at ${sessions.size} peers")
    }

    @OnError
    fun error(t: Throwable) =
        {
            logger.warn("error occured!", t)
        }

    fun send(message: SimpleKweetSerializer, peer: Session) {
        if (peer.isOpen) {
            logger.info("Sending message to ${peer.id}")
            peer.basicRemote.sendText(message.message)
        } else {
            logger.warn("Remote was closed! removing.")
            sessions.remove(peer)
        }
    }

    fun sendAll(message: SimpleKweetSerializer) {
        logger.info("Sending message to ${sessions.count()} peers")
        sessions.forEach { send(message, it) }
    }
}
