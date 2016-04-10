package net.loshodges.vertx.kotlin

import io.vertx.kotlin.lang.DefaultVertx
import io.vertx.kotlin.lang.body
import io.vertx.kotlin.lang.bodyJson
import io.vertx.kotlin.lang.contentType

fun main(args: Array<String>) {

    DefaultVertx {
        routingServer(port = 8084, host = "localhost") {
            GET("/path/:one/:two") {
                val params = request().params().toList().groupBy { it.key }.mapValues { it.value.map { it.value } }
                respond {
                    bodyJson { params }
                }
            }

            POST("/path/:other/:brother") {
                respond {
                    val params = request().params().toList().groupBy { it.key }.mapValues { it.value.map { it.value } }
                    contentType("text/plain")
                    body {
                        write(
                                "Hello World from Vert.x. parms were $params!"
                        )
                    }
                }
            }
        }
    }
}

