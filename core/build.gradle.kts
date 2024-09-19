plugins {
    id("java")

    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "8.0.0"
}

group = "io.mblueberry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.esotericsoftware:kryonet:2.22.0-RC1")

    implementation("com.raylabz:opensimplex:1.0.3")
    implementation("org.json:json:20240303")
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
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveClassifier.set("all")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
tasks.test {
    useJUnitPlatform()
}