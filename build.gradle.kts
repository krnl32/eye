plugins {
    id("java")
}

group = "com.krnl32"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
	apply(plugin = "java-library")

	group = rootProject.group
	version = rootProject.version

	the<JavaPluginExtension>().toolchain {
		languageVersion.set(JavaLanguageVersion.of(25))
	}

	repositories {
		mavenCentral()
	}

	tasks.withType<JavaCompile>().configureEach {
		sourceCompatibility = "25"
		targetCompatibility = "25"
	}
}
