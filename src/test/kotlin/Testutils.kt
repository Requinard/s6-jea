import domain.Kweet
import domain.KwetterGroup
import domain.Profile
import util.now

val johnProfile = Profile(
    created = now(),
    screenname = "john"
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
