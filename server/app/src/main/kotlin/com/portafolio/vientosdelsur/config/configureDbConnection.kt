package com.portafolio.vientosdelsur.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDbConnection() {
    val env = environment.config

    val config = HikariConfig().apply {
        jdbcUrl = checkNotNull(env.propertyOrNull("database.jdbcUrl")?.getString()) { "DB URL is not provided" }
        driverClassName = checkNotNull(
            env.propertyOrNull("database.driverClassName")?.getString()
        ) { "Driver classname is not provided" }
        username = checkNotNull(env.propertyOrNull("database.username")?.getString()) { "Username is not provided" }
        password = checkNotNull(env.propertyOrNull("database.password")?.getString()) { "Password is not provided" }
        isReadOnly = false
        maximumPoolSize = checkNotNull(
            env.propertyOrNull("database.maximumPoolSize")?.getString()?.toIntOrNull()
        ) { "Maximum pool size is not provided" }
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }

    val dataSource = HikariDataSource(config)

    Database.connect(datasource = dataSource)
}