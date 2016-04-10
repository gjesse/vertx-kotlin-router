package net.loshodges.vertx.kotlin

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.net.NetServerOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.impl.RouterImpl
import io.vertx.kotlin.lang.HttpServerOptions
import kotlinx.util.with


public inline fun Vertx.router(crossinline block: Router.() -> Unit): Router {
    val route = RouterImpl(this)
    route.with(block)
    return route;
}

public inline fun Vertx.routingServer(port: Int, host: String = NetServerOptions.DEFAULT_HOST, crossinline block: Router.() -> Unit): Unit {
    HttpServerOptions(port, host)
    val server = this.createHttpServer(HttpServerOptions(port, host))
    val route = RouterImpl(this)
    route.with(block)
    server.requestHandler { req -> route.accept(req) }
    server.listen()
}

public fun Router.GET(path: String, handler: RoutingContext.() -> Unit) =
        this.get(path).handler(handler)

public fun Router.PUT(path: String, handler: RoutingContext.() -> Unit) =
        this.put(path).handler(handler)

public fun Router.POST(path: String, handler: RoutingContext.() -> Unit) =
        this.post(path).handler(handler)

public fun Router.PATCH(path: String, handler: RoutingContext.() -> Unit) =
        this.patch(path).handler(handler)

public fun Router.HEAD(path: String, handler: RoutingContext.() -> Unit) =
        this.head(path).handler(handler)

public fun Router.OPTIONS(path: String, handler: RoutingContext.() -> Unit) =
        this.options(path).handler(handler)

public fun RoutingContext.respond(block: HttpServerResponse.() -> Unit) {
    block.invoke(response())
}