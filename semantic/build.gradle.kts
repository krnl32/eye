dependencies {
	testImplementation(platform("org.junit:junit-bom:5.13.4"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.4")

	testImplementation(project(":parser"));

	implementation(project(":common"))
	implementation(project(":ast"))
}

tasks.test {
	useJUnitPlatform()
}
