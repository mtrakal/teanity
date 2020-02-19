buildscript {
    repositories { Repositories.with(this) }

    dependencies {
        classpath(Lib.androidx.build)
        classpath(Lib.kotlin.gradle)
        classpath(Lib.maven)
    }
}

allprojects { repositories { Repositories.with(this) } }

apply(from = "helper.gradle")

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
tasks.wrapper {
    // https://services.gradle.org/distributions/
    gradleVersion = Gradle.wrapper
    distributionType = Wrapper.DistributionType.ALL
}
