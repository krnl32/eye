dependencies {
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")

	testImplementation(project(":parser"));

	implementation(project(":common"))
	implementation(project(":ast"))
}

tasks.test {
	useJUnitPlatform()
}
