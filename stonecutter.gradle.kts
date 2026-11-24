plugins {
    id("dev.kikugie.stonecutter")
    id("net.neoforged.moddev") version "2.0.148" apply false
    id("me.modmuss50.mod-publish-plugin") version "2.2.1" apply false
}

stonecutter active "1.21.11"


// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    replacements {
        // 1.21.11 renamed ResourceLocation to Identifier
        string(current.parsed < "1.21.11") {
            replace("Identifier", "ResourceLocation")
        }
        // 1.21.11 renamed the advancement criteria package
        string(current.parsed < "1.21.11") {
            replace("advancements.criterion", "advancements.critereon")
        }
    }
}

// Runs datagen for every version. Each run starts Minecraft, so the runs go one after another, not in parallel.
val dataTasks = stonecutter.versions.map { ":${it.project}:runData" }
tasks.register("runDataAll") {
    group = "neoforged"
    description = "Runs datagen for all Minecraft versions"
    dependsOn(dataTasks)
}
gradle.projectsEvaluated {
    dataTasks.zipWithNext().forEach { (previous, next) ->
        rootProject.project(next.substringBeforeLast(':')).tasks.named("runData") { mustRunAfter(previous) }
    }
}

// Builds every version and copies the jars to build/libs/<minecraft version>/<mod id>-<mod version>.jar.
// Run it in a separate Gradle invocation after runDataAll (see build_all.bat): runData needs processResources,
// so in one invocation the jars would contain the old generated resources.
tasks.register("buildAndCollectAll") {
    group = "build"
    description = "Builds all Minecraft versions and collects the jars in one folder"
    dependsOn(stonecutter.versions.map { ":${it.project}:buildAndCollect" })
}

// Publishes every version to Modrinth. Run it after runDataAll (see publish_all.bat).
// Test first with `gradlew publishAll -PdryRun`: it does all the steps, but uploads nothing.
// The token is read from the `modrinthToken` property in ~/.gradle/gradle.properties
// or from the MODRINTH_TOKEN environment variable.
tasks.register("publishAll") {
    group = "publishing"
    description = "Publishes all Minecraft versions to Modrinth"
    dependsOn(stonecutter.versions.map { ":${it.project}:publishMods" })
}
