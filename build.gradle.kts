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
        maven("https://git.karmakrafts.dev/api/v4/projects/307/packages/maven") // Mulitplatform JNI
    }
}