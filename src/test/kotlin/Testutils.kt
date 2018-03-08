import domain.Kweet
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

val helloWorldKweet = Kweet(
    created = now(),
    message = "hello world"
)

val salveMundiKweet = Kweet(
    created = now(),
    message = "salve mundi"
)
