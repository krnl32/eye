plugins {
	application
}

group = "com.krnl32"
version = "1.0-SNAPSHOT"

application {
	mainClass.set("com.krnl32.eye.sandbox.Main")
}

dependencies {
	implementation(project(":common"))
	implementation(project(":parser"))
	implementation(project(":ast"))
	implementation(project(":astserializer"))
}

tasks.test {
    useJUnitPlatform()
}
