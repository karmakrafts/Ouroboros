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

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.defaultType
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms
import org.jetbrains.kotlin.platform.konan.NativePlatforms

internal class OuroborosIrGenerationExtension : IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext
    ) {
        when (moduleFragment.descriptor.platform) {
            in JvmPlatforms.allJvmPlatforms -> generateJvm(
                moduleFragment,
                pluginContext,
                JvmTypes(pluginContext)
            )

            in NativePlatforms.allNativePlatforms -> generateNative(
                moduleFragment,
                pluginContext,
                NativeTypes(pluginContext)
            )
        }
    }

    private fun IrModuleFragment.findInteropClasses(types: CommonTypes): List<IrType> {
        return files.flatMap { file ->
            ArrayList<IrType>().apply {
                file.transformChildrenVoid(object : IrElementTransformerVoid() {
                    override fun visitClass(declaration: IrClass): IrStatement {
                        if (!declaration.annotations.any { it.type == types.bridgedType }) {
                            return super.visitClass(declaration)
                        }
                        this@apply += declaration.defaultType
                        return super.visitClass(declaration)
                    }
                })
            }
        }
    }

    private fun generateNative(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext,
        types: NativeTypes
    ) {
        val classes = moduleFragment.findInteropClasses(types)
    }

    private fun generateJvm(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext,
        types: JvmTypes
    ) {
        val classes = moduleFragment.findInteropClasses(types)
    }
}