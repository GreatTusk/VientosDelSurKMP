package com.portafolio.vientosdelsur.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

internal object DatabaseFactory {
    private fun getHikariConfig(): HikariConfig {
        val env = applicationEnvironment().config

        env.keys().forEach(::println)

        return HikariConfig().apply {
            jdbcUrl = checkNotNull(env.propertyOrNull("database.jdbcUrl")?.getString()) { "DB URL is not provided" }
            driverClassName = checkNotNull(
                env.propertyOrNull("database.driverClassName")?.getString()
            ) { "Driver classname is not provided" }
            username = checkNotNull(env.propertyOrNull("database.username")?.getString()) { "Username is not provided" }
            password = checkNotNull(env.propertyOrNull("database.password")?.getString()) { "Password is not provided" }
            isReadOnly = false
            maximumPoolSize = 7
            transactionIsolation = "TRANSACTION_SERIALIZABLE"
        }
    }

    private val dataSource = HikariDataSource(getHikariConfig())

    val connectToDb = { Database.connect(datasource = dataSource) }
}