package util

import java.sql.Timestamp
import java.time.Instant

fun now() = Timestamp.from(Instant.now())
