package com.jinni01

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LucyXssFilterKotlinApplication

fun main(args: Array<String>) {
    runApplication<LucyXssFilterKotlinApplication>(*args)
}