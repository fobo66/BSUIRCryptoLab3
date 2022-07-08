plugins {
    kotlin("jvm") version "1.7.10"
    application
}

application {
    mainClass.set("io.fobo66.crypto.Lab3Kt")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation("commons-cli:commons-cli:1.5.0")
}
