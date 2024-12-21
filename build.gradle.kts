import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	kotlin("plugin.allopen") version "1.7.22"
	kotlin("plugin.noarg") version "1.7.22"
	kotlin("kapt") version "1.7.22"
}

group = "io.kotlin"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	runtimeOnly("mysql:mysql-connector-java:8.0.31")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("com.h2database:h2")
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}
// bundling tasks
tasks.getByName("bootJar") {
	enabled = true
}
tasks.getByName("jar") {
	enabled = false
}