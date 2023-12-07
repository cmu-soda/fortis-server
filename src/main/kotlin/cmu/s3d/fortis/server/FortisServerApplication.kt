package cmu.s3d.fortis.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FortisServerApplication

fun main(args: Array<String>) {
	runApplication<FortisServerApplication>(*args)
}
