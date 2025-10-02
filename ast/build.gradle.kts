dependencies {
	testImplementation(platform("org.junit:junit-bom:5.13.4"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")

	implementation(project(":common"))
}

tasks.test {
	useJUnitPlatform()
}
