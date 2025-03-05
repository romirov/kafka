package com.orshlab.kafka.util

import java.time.format.DateTimeFormatter

object Constants {
	val dateTimeFormatter = DateTimeFormatter.ofPattern("""yyyy-MM-dd'T'HH:mm:ss.SSS'Z'""")
}