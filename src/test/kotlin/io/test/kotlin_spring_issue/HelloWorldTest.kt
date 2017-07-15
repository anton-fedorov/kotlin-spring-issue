package io.test.kotlin_spring_issue


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@ComponentScan(basePackages = arrayOf("io.test.kotlin_spring_issue"))
@SpringBootTest(classes = arrayOf(HelloNotWorkingApp::class))
open class HelloWorldTest : AbstractTestNGSpringContextTests() {

    @Autowired
    private lateinit var controller: HelloController

    @Test
    fun test() {
        assert("hello" == (this.controller.hello()))
    }

}
