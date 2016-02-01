/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.okbuck.composer

import com.github.piasy.okbuck.dependency.Dependency
import com.github.piasy.okbuck.helper.ProjectHelper
import com.github.piasy.okbuck.rules.AndroidManifestRule
import org.gradle.api.Project

public final class AndroidManifestRuleComposer {

    private AndroidManifestRuleComposer() {
        // no instance
    }

    public static AndroidManifestRule compose(Project project, Set<Dependency> finalDependencies) {
        List<String> manifestDeps = new ArrayList<>()
        for (Dependency dependency : finalDependencies) {
            Project internalDep = ProjectHelper.getModuleDependencyProject(project.rootProject,
                    dependency.depFile)
            if (internalDep != null) {
                switch (ProjectHelper.getSubProjectType(internalDep)) {
                    case ProjectHelper.ProjectType.AndroidAppProject:
                    case ProjectHelper.ProjectType.AndroidLibProject:
                        manifestDeps.add(dependency.srcCanonicalName)
                        break
                    default:
                        break
                }
            } else {
                switch (dependency.type) {
                    case Dependency.DependencyType.LocalAarDependency:
                    case Dependency.DependencyType.MavenAarDependency:
                    case Dependency.DependencyType.ModuleAarDependency:
                        manifestDeps.add(dependency.srcCanonicalName)
                        break
                    default:
                        break
                }
            }
        }

        return new AndroidManifestRule(Arrays.asList("PUBLIC"), manifestDeps,
                ":generate_manifest_main")
    }
}