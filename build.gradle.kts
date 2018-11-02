plugins {
    kotlin("jvm") version "1.3.0"
    application
}

application {
    mainClassName = "io.fobo66.crypto.Lab3Kt"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("commons-cli:commons-cli:1.4")
}
