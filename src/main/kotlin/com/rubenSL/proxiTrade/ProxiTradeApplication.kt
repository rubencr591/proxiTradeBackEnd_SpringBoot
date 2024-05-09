package com.rubenSL.proxiTrade

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin





@SpringBootApplication(exclude = [SecurityAutoConfiguration::class, ManagementWebSecurityAutoConfiguration::class])
class ProxiTradeApplication

fun main(args: Array<String>) {
	runApplication<ProxiTradeApplication>(*args)
}

