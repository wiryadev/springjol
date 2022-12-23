package com.wiryadev.springjol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(
	exclude = [
		UserDetailsServiceAutoConfiguration::class,
	]
)
class SpringjolApplication

fun main(args: Array<String>) {
	runApplication<SpringjolApplication>(*args)
}
