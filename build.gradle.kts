plugins {
    kotlin("jvm") version libs.versions.kotlin
    application
    `jvm-test-suite`
}

application {
    mainClass.set("dev.fobo66.crypto.Lab3Kt")
}

kotlin {
    jvmToolchain(17)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)
        }
    }
}

dependencies {
    implementation(libs.cli)
}
