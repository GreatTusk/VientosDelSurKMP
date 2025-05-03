package com.portafolio.vientosdelsur.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

internal class DatabaseFactory(private val applicationEnvironment: ApplicationEnvironment) {
    private fun getHikariConfig(): HikariConfig {
        val env = applicationEnvironment.config

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

    fun connectToDb() = Database.connect(datasource = dataSource)
}