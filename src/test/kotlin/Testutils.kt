import models.KweetModel
import models.GroupModel
import models.UserModel
import models.ProfileModel
import utils.now
import utils.sha256

val johnProfile = ProfileModel(
    created = now(),
    screenname = "john"
)

val johnUser = UserModel(
    username = "john",
    password = sha256("john")
)

val hankProfile = ProfileModel(
    created = now(),
    screenname = "hank"
)

val regularGroup = GroupModel(
    groupname = "regular"
)

val adminGrou = GroupModel(
    groupname = "admin"
)

val helloWorldKweet = KweetModel(
    created = now(),
    message = "hello world"
)

val salveMundiKweet = KweetModel(
    created = now(),
    message = "salve mundi"
)
