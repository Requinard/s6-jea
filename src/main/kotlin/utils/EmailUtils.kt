package utils

import annotations.Open
import models.KweetModel
import models.ProfileModel
import utils.extensions.logger
import javax.annotation.Resource
import javax.ejb.Stateless
import javax.mail.Message.RecipientType
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Open
@Stateless
class EmailUtils {
    @Resource(name = " java:jboss/mail/Default")
    lateinit var session: Session

    fun send(to: String, subject: String, body: String) {
        logger.info("Sending mail to $to")
        logger.debug("Subject: $subject")
        logger.debug("Body; $body")

        val message = MimeMessage(session).apply {
            setRecipients(RecipientType.TO, InternetAddress.parse(to))
            setSubject(subject)
            setText(body)
        }
        try {
            Transport.send(message)
        } catch (ex: MessagingException) {
            logger.error("Error sending mail", ex)
        }
    }

    fun likeKweet(kweetModel: KweetModel, profileModel: ProfileModel) {
        send(
            kweetModel.profileModel.userModel!!.email,
            "A user has liked your kweet!",
            "${profileModel.screenname} has liked your kweet saying: \"${kweetModel.message}\""
        )
    }
}
