plugins {
	kotlin("jvm") version "2.1.10"
	kotlin("plugin.spring") version "2.1.10"
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.devtools.ksp") version "2.1.0-1.0.29"
}



java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("io.arrow-kt:arrow-core:2.0.0")
	implementation("io.arrow-kt:arrow-annotations:2.0.0")
	implementation("io.arrow-kt:arrow-fx-coroutines:2.0.0")
	implementation("io.arrow-kt:arrow-optics:2.0.0")
	ksp("io.arrow-kt:arrow-optics-ksp-plugin:2.0.0")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	ksp("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.testcontainers:kafka")
	testImplementation("org.testcontainers:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("com.ocadotechnology.gembus:test-arranger:1.6.3")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder = "paketobuildpacks/builder-jammy-base:latest"
}
