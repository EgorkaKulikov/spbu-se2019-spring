plugins {
    kotlin("jvm") version "1.2.51"
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
    testCompile("com.nhaarman", "mockito-kotlin", "1.6.0")
}