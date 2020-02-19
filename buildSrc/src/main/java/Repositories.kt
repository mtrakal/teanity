import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Repositories {

    fun with(handler: RepositoryHandler) = with(handler) {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android\\.arch.*")
            }
        }
        jcenter()
        maven(url = "https://maven.fabric.io/public") {
            content {
                includeGroupByRegex("com\\.crashlytics.*")
                includeGroupByRegex("io\\.fabric.*")
            }
        }
        maven(url = "https://jitpack.io") {
            content {
                includeGroupByRegex("com\\.github.*")
                includeGroupByRegex("wiki\\.depasquale.*")
            }
        }
    }

}