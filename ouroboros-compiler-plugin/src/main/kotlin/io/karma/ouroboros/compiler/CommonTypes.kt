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

package io.karma.ouroboros.compiler

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.name.ClassId

internal open class CommonTypes(protected val pluginContext: IrPluginContext) {
    val bridged: IrType =
        requireNotNull(pluginContext.referenceClass(ClassId.fromString("io.karma.ouroboros.runtime.Bridged"))?.defaultType)
    val bridgedType: IrType =
        requireNotNull(pluginContext.referenceClass(ClassId.fromString("io.karma.ouroboros.runtime.BridgedType"))?.defaultType)
}