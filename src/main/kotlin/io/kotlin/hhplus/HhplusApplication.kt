package io.kotlin.hhplus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HhplusApplication

fun main(args: Array<String>) {
	runApplication<HhplusApplication>(*args)
}
