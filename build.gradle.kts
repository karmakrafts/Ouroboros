import java.util.Properties
import kotlin.io.path.div
import kotlin.io.path.inputStream

val buildConfig: Properties = Properties().apply {
    (rootDir.toPath() / "build.properties").inputStream().use {
        load(it)
    }
}

allprojects {
    group = buildConfig["group"] as String
    version = "${rootProject.libs.versions.ouroboros.get()}.${System.getenv("CI_PIPELINE_IID") ?: 0}"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}