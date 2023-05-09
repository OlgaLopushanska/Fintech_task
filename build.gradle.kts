plugins {
	java
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.fintech.task"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
	implementation("org.hibernate:hibernate-validator:8.0.0.Final")
//	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.awaitility:awaitility:4.2.0")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
