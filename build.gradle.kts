plugins {
    id("net.neoforged.moddev")
    id("neoforge-mutex")
    id("me.modmuss50.mod-publish-plugin")
}

version = "${property("mod.version")}+${sc.current.version}"
group = property("mod.group") as String
base.archivesName = property("mod.id") as String

val requiredJava = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    else -> JavaVersion.VERSION_21
}

// JEI has no NeoForge build for some versions (see stonecutter.properties.toml). Those versions build without the JEI plugin.
val jeiMc = property("deps.jei_mc") as String
val jeiVersion = property("deps.jei") as String
val hasJei = jeiMc.isNotEmpty() && jeiVersion.isNotEmpty()

repositories {
    maven("https://maven.blamejared.com/") { name = "Jared's maven" } // JEI
    maven("https://modmaven.dev") { name = "ModMaven" } // JEI fallback
}

dependencies {
    if (hasJei) {
        // Compile against the stable JEI API, but use the full JEI mod in development.
        compileOnly("mezz.jei:jei-$jeiMc-neoforge-api:$jeiVersion")
        runtimeOnly("mezz.jei:jei-$jeiMc-neoforge:$jeiVersion")
    }
}

neoForge {
    version = property("deps.neo_loader") as String

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        register("client") {
            gameDirectory = file("../../run/")
            client()
        }

        register("server") {
            gameDirectory = file("../../run/")
            server()
            programArgument("--nogui")
        }

        // Each version runs its own datagen and writes to versions/<version>/generated/resources.
        // Run one version with `:<version>:runData`, or all versions with `runDataAll`.
        register("data") {
            // Own game directory, so data runs do not share logs and config with the dev client
            gameDirectory = file("run-data/")
            // From 1.21.4, NeoForge splits datagen into client and server runs. clientData makes both.
            if (sc.current.parsed >= "1.21.4") clientData() else data()
            programArguments.addAll(
                "--mod", property("mod.id") as String,
                "--all",
                "--output", file("generated/resources").absolutePath,
                "--existing", rootProject.file("src/main/resources").absolutePath,
            )
        }

        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", property("mod.id") as String)
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }
}

java {
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

sourceSets.main {
    if (!hasJei) {
        java.exclude("com/nucleusbeast/fancy_lanterns/jei/**")
    }
    // 1.21.4 replaced the NeoForge model providers with the vanilla ModelProvider
    if (sc.current.parsed >= "1.21.4") {
        java.exclude("com/nucleusbeast/fancy_lanterns/datagen/ModBlockStateProvider.java")
        java.exclude("com/nucleusbeast/fancy_lanterns/datagen/ModItemModelProvider.java")
    } else {
        java.exclude("com/nucleusbeast/fancy_lanterns/datagen/ModModelProvider.java")
    }
    // Output of this version's datagen
    resources.srcDir(file("generated/resources"))
}

tasks {
    processResources {
        // Datagen hash cache, not a game resource
        exclude(".cache/**")

        val props = buildMap {
            fun prop(key: String, property: String) = put(key, project.property(property) as String)
            prop("mod_id", "mod.id")
            prop("mod_name", "mod.name")
            prop("mod_version", "mod.version")
            prop("mod_license", "mod.license")
            prop("mod_authors", "mod.authors")
            prop("mod_description", "mod.description")
            prop("minecraft_version_range", "mod.mc_compat")
            put("neo_version_range", "[21,)")
            put("loader_version_range", "[4,)")
        }
        inputs.properties(props)

        from(rootProject.file("src/main/templates")) {
            filesMatching("META-INF/neoforge.mods.toml") { expand(props) }
        }
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    // Includes the license file in the built mod
    withType<Jar> {
        val name = project.property("mod.id")
        inputs.property("mod_id", name)
        from("../../LICENSE") { rename { "$it-$name" } }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jar and copies it to `build/libs/{minecraft version}/{mod id}-{mod version}.jar`"

        val outName = "${project.property("mod.id")}-${project.property("mod.version")}.jar"
        inputs.property("out_name", outName)
        from(jar.flatMap { it.archiveFile })
        rename { outName }
        into(rootProject.layout.buildDirectory.dir("libs/${sc.current.version}"))
    }
}

// Modrinth upload. Run `gradlew publishAll` (all versions) or `:<version>:publishMods` (one version).
// The token is read from the `modrinthToken` property (~/.gradle/gradle.properties) or the MODRINTH_TOKEN environment variable.
// Add -PdryRun to test without an upload.
publishMods {
    val modVersion = property("mod.version") as String
    val mcVersion = sc.current.version
    // Modrinth calls 1.21.0 "1.21"
    val modrinthMcVersion = if (mcVersion == "1.21.0") "1.21" else mcVersion

    // Upload the jar from buildAndCollect: build/libs/<minecraft version>/<mod id>-<mod version>.jar
    file = rootProject.layout.buildDirectory.file("libs/$mcVersion/${property("mod.id")}-$modVersion.jar")
    // Same version number and name for every Minecraft version, like the older uploads
    displayName = "${property("mod.name")} $modVersion"
    version = modVersion
    changelog = providers.fileContents(rootProject.layout.projectDirectory.file("CHANGELOG.md")).asText.orElse("")
    type = STABLE
    modLoaders.add("neoforge")
    dryRun = providers.gradleProperty("dryRun").isPresent

    modrinth {
        projectId = "RbOy1V7M" // fancy-lanterns
        accessToken = providers.gradleProperty("modrinthToken").orElse(providers.environmentVariable("MODRINTH_TOKEN"))
        minecraftVersions.add(modrinthMcVersion)
        if (hasJei) optional("jei")
    }
}

// The upload file comes from buildAndCollect, so it must run first
tasks.matching { it.name == "publishModrinth" }.configureEach { dependsOn("buildAndCollect") }
