

plugins {
    id("java")
    application
}

group = "org.umbrella"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("com.beust:jcommander:1.82")
    implementation("com.h2database:h2:1.4.200")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.google.inject:guice:7.0.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("com.umbrella.carsharing.Main")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
                "Main-Class" to application.mainClass.get(),
                "Class-Path" to configurations.runtimeClasspath.get().joinToString(" ") {
                    "libs/${it.name}"
                }
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}



