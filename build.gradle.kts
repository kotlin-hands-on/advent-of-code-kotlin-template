plugins {
    kotlin("jvm") version "1.6.0"
}

group = project.property("group").toString()
version = project.property("version").toString()

dependencies {
    testImplementation(kotlin("test"))
}

repositories {
    mavenCentral()
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = project.findProperty("gradleVersion").toString()
    }
}
