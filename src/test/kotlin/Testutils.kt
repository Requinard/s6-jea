import domain.Kweet
import domain.KwetterGroup
import domain.KwetterUser
import domain.Profile
import util.now
import util.sha256

val johnProfile = Profile(
    created = now(),
    screenname = "john"
)

val johnUser = KwetterUser(
    username = "john",
    password = sha256("john")
)

val hankProfile = Profile(
    created = now(),
    screenname = "hank"
)

val regularGroup = KwetterGroup(
    groupname = "regular"
)

val adminGrou = KwetterGroup(
    groupname = "admin"
)

val helloWorldKweet = Kweet(
    created = now(),
    message = "hello world"
)

val salveMundiKweet = Kweet(
    created = now(),
    message = "salve mundi"
)
