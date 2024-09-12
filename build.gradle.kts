plugins {
    id("java")

    id("com.github.johnrengelman.shadow") version "8.0.0"
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    implementation("org.json:json:20240303")
// https://mvnrepository.com/artifact/com.raylabz/opensimplex
    implementation("com.raylabz:opensimplex:1.0.3")

    implementation("com.google.code.gson:gson:2.11.0")
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


tasks {
    // Configure the shadowJar task to create an uber JAR
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveClassifier.set("all")

        // Optionally, you can configure additional aspects here
    }
}

tasks {

    // Ensure the shadowJar task runs during the build process
    build {

        dependsOn(shadowJar)
    }
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}