plugins {
    id("idea")
    kotlin("jvm") version "1.9.20"
}

group = "io.github.tsgrissom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.essentialsx.net/snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(group="com.uchuhimo", name="kotlinx-bimap", version="1.2")
    compileOnly(group="org.spigotmc", name="spigot-api", version="1.20.1-R0.1-SNAPSHOT")
    implementation(group="com.github.stefvanschie.inventoryframework", name="IF", version="0.10.11")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.9.0")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
    testLogging {
        events("passed", "skipped", "failed")
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }
}

kotlin {
    jvmToolchain(17)
}