import org.gradle.api.artifacts.dsl.DependencyHandler

object Config {

    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16 // 21 is strongly suggested
    }

    object Dependency {
        const val kotlin = "1.3.41"
        const val kotlinCoroutines = "1.2.2"
        const val appcompat = "1.1.0-rc01"
        const val ktx = "1.2.0-alpha02"
        const val material = "1.1.0-alpha07"
        const val room = "2.1.0"
        const val lifecycle = "2.2.0-alpha02"
        const val navigation = "2.1.0-alpha06"
        const val bca = "3.0.0-beta1" // please do not update; nested binding fails to resolve on newer versions
        const val glide = "4.9.0"
        const val state = "1.4.1"
        const val rxkotlin = "2.3.0"
        const val rxandroid = "2.1.1"
        const val dexter = "5.0.0"
        const val work = "2.1.0-rc01"
        const val animation = "1.1.0-alpha02"
        const val animationKtx = "1.0.0-alpha02"
        const val constraintLayout = "2.0.0-beta2"
        const val gradlePlugin = "3.4.0"
        const val koin = "2.0.1"
        const val retrofit = "2.6.0"
        const val retrofitCoroutines = "0.9.2"
        const val okhttp = "4.0.0"
        const val moshi = "1.8.0"
        const val crashlytics = "2.10.1"
        const val fabric = "1.+"
        const val kotpref = "2.8.0"
        const val timber = "4.7.1"
        const val sanitizer = "0.5"
        const val teanity = "0.+"
    }

    object Build {

        object Dimen {
            const val DEFAULT = "default"
        }

        object Type {
            const val RELEASE = "release"
            const val DEBUG = "debug"
            const val ALPHA = "alpha"
            const val BETA = "beta"
        }

        //const val VANILLA = "vanilla"
        //const val CHOCOLATE = "chocolate"
    }

    object Sign {
        const val DEFAULT = "default-config"
        const val DEBUG = "debug-config"
        const val ALPHA = "alpha-config"
        const val BETA = "beta-config"
        //const val VANILLA_RELEASE = "release-${Build.VANILLA}-config"
    }

}

fun DependencyHandler.teanity(version: String = Config.Dependency.teanity): Any =
    "com.github.skoumalcz:teanity:$version"