dependencies {
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")

	implementation("ch.qos.logback:logback-classic:1.5.18")
}

tasks.test {
	useJUnitPlatform()
}
