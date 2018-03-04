package util

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun sha256(input:String) = Hashing.sha256()
    .hashString(input, StandardCharsets.UTF_8)
    .toString()
