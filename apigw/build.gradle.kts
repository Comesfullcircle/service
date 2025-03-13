import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "org.delivery"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Java 21 유지
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.2"

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-webflux") // ✅ WebFlux 추가
    implementation("io.projectreactor:reactor-core") // ✅ Reactor Core 추가

    // ✅ 필수 Spring Boot 라이브러리 추가
    implementation("org.springframework.boot:spring-boot-starter")
  //  implementation("org.springframework.boot:spring-boot-starter-web")

    // ✅ Spring Cloud Gateway 추가
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    // ✅ Jackson 관련 모듈 추가 (JSON 파싱 관련 오류 해결)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // ✅ Reactor (Mono, Flux 관련 오류 해결)
    implementation("io.projectreactor:reactor-core")

    // ✅ Spring Framework Utility (UriComponentsBuilder 등 사용)
    implementation("org.springframework:spring-web")

    // ✅ Kotlin Reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // ✅ Mac OS를 위한 네트워크 설정
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.94.Final:osx-aarch_64")

    // ✅ 테스트 의존성
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.2")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
