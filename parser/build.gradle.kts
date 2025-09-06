dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

	implementation(project(":common"))
	implementation(project(":ast"))
	implementation(project(":astserializer"))

	implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
}

tasks.test {
    useJUnitPlatform()
}
