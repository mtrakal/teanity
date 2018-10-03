buildscript {
    extra.set(
        "versions", mapOf(
            /* === LIB === */
            "kotlin" to "1.2.71",
            "supportlib" to "1.0.0",
            "room" to "2.0.0-rc01",
            "lifecycle" to "2.0.0-rc01",
            "navigation" to "1.0.0-alpha06",
            "bca" to "3.0.0-beta3",
            "glide" to "4.8.0-SNAPSHOT",
            "state" to "1.3.1",
            "rxkotlin" to "2.2.0",
            "rxandroid" to "2.1.0",
            "dexter" to "5.0.0",
            "ktx" to "1.0.0-alpha1",

            /* === APP === */
            "playServices" to "15.0.1",
            "constraintLayout" to "2.0.0-alpha2",
            "gradlePlugin" to "3.3.0-alpha03",
            "koin" to "1.0.1",
            "retrofit" to "2.4.0",
            "okhttp" to "3.11.0",
            "moshi" to "1.7.0",
            "crashlytics" to "2.9.3",
            "fabric" to "1.25.4"
        )
    )

    repositories {
        google()
        jcenter()
        maven(url = "https://maven.fabric.io/public")
    }

    val versions: Map<String, String> by extra

    dependencies {
        classpath("com.android.tools.build:gradle:${versions["gradlePlugin"]}")
        classpath(kotlin("gradle-plugin", version = versions["kotlin"]))
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:${versions["navigation"]}")
        classpath("io.fabric.tools:gradle:${versions["fabric"]}")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.fabric.io/public")
        maven(url = "http://oss.sonatype.org/content/repositories/snapshots") // Glide snapshot
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
