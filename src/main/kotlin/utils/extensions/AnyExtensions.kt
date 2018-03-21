package utils.extensions

import org.slf4j.LoggerFactory

val Any.logger get() = LoggerFactory.getLogger(this.javaClass.canonicalName)
