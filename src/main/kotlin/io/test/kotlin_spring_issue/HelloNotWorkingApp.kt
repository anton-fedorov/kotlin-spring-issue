package io.test.kotlin_spring_issue

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HelloNotWorkingApp {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(HelloNotWorkingApp::class.java, *args)
        }
    }

}