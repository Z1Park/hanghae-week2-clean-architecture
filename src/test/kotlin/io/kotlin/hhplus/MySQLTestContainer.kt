package io.kotlin.hhplus

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
abstract class MySQLTestContainer {

    companion object {
        @Container
        @JvmStatic
        val container = MySQLContainer<Nothing>("mysql:8.0.19")
            .apply {
                withDatabaseName("hhplus-local")
                withUsername("root")
                withPassword("1234")
                withUrlParam("characterEncoding", "UTF-8")
                withUrlParam("serverTimezone", "UTC")
            }
            .apply {
                start()
            }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)
        }
    }
}