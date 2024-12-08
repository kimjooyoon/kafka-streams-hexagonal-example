package com.example.kafkastreamsexample.infrastructure.`in`.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig(private val handler: BlogHandler) {

    @Bean
    fun blogRoutes(): RouterFunction<ServerResponse> = router {
        "/api".nest {
            "/blogs".nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    GET("", handler::getBlogs)
                    POST("", handler::createBlog)
                }
            }
        }
    }
}
