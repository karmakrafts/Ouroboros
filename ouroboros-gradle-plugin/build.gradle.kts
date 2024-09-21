/*
 * Copyright 2024 Karma Krafts & associates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.tools.ant.filters.ReplaceTokens
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.div

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.kotlin.gradle.plugin)
}

val generatedSourceDir: Path = projectDir.toPath() / "build" / "generated" / "sources"
val buildInfoFile: Path = projectDir.toPath() / "src" / "main" /
        rootProject.group.toString().replace('.', '/') / "gradle" / "BuildInfo.kt"

sourceSets {
    val main by getting {
        kotlin {
            exclude(buildInfoFile.absolutePathString())
            srcDir(generatedSourceDir)
        }
    }
}

tasks {
    create<Copy>("processBuildInfo") {
        from(buildInfoFile) {
            filter<ReplaceTokens>(
                "tokens" to mapOf(
                    "GROUP" to group,
                    "VERSION" to version,
                    "PLUGIN_NAME" to "${rootProject.name}-compiler-plugin"
                )
            )
        }
        into(generatedSourceDir)
    }
}

@Suppress("UnstableApiUsage")
gradlePlugin {
    website.set("https://git.karmakrafts.dev/kk/ouroboros")
    vcsUrl.set("https://git.karmakrafts.dev/kk/ouroboros")
    plugins {
        create("Ouroboros Gradle Plugin") {
            id = "$group.gradle-plugin"
            implementationClass = "io.karma.ouroboros.gradle.OuroborosGradlePlugin"
            displayName = "Ouroboros Gradle Plugin"
            description = "Gradle plugin for applying the Ouroboros Kotlin compiler plugin"
            tags.addAll("kotlin", "native", "jvm", "jni", "interop", "codegen")
        }
    }
}
