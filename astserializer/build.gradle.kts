dependencies {
	testImplementation(platform("org.junit:junit-bom:5.13.4"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")

	implementation(project(":ast"))

	api("com.fasterxml.jackson.core:jackson-databind:2.19.2")
}

tasks.test {
	useJUnitPlatform()
}
