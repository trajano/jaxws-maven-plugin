/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2006 Guillaume Nodet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jvnet.jax_ws_commons.jaxws;

import java.io.File;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Parses wsdl and binding files and generates Java code needed to access it
 * (for tests).
 *
 * <p>
 * <code>${maven.test.skip}</code> property is honored. If it is set, code generation is skipped.
 * </p>
 *
 * @goal wsimport-test
 * @phase generate-test-sources
 * @requiresDependencyResolution
 * @description JAXWS 2.x Plugin.
 *
 * @author Kohsuke Kawaguchi
 */
public class TestWsImportMojo extends WsImportMojo {

    /**
     * Specify where to place output generated classes. Use <code>xnocompile</code>
     * to turn this off.
     *
     * @parameter default-value="${project.build.testOutputDirectory}"
     */
    private File destDir;

    /**
     * Specify where to place generated source files, keep is turned on with this option.
     *
     * @parameter default-value="${project.build.directory}/generated-sources/test-wsimport"
     */
    private File sourceDestDir;

    /**
     * Specify where to generate JWS implementation file.
     *
     * @parameter default-value="${project.build.testSourceDirectory}"
     */
    private File implDestDir;

    /**
     * Set this to "true" to bypass code generation.
     *
     * @parameter expression="${maven.test.skip}"
     */
    private boolean skip;

    /**
     * Either ${build.outputDirectory} or ${build.testOutputDirectory}.
     */
    @Override
    protected File getDestDir() {
        return destDir;
    }

    /**
     * ${project.build.directory}/jaxws/wsimport/test.
     */
    @Override
    protected File getSourceDestDir() {
        return sourceDestDir;
    }

    @Override
    protected void addSourceRoot(String sourceDir) {
        project.addTestCompileSourceRoot(sourceDir);
    }

    @Override
    protected File getDefaultSrcOut() {
        return new File(project.getBuild().getDirectory(), "generated-sources/test-wsimport");
    }

    @Override
    protected File getImplDestDir() {
        return implDestDir;
    }

    @Override
    public void execute() throws MojoExecutionException {
        //if maven.test.skip is set test compilation is not called, so
        //no need to generate sources/classes
        if (skip) {
            getLog().info("Skipping tests, nothing to do.");
        } else {
            super.execute();
        }
    }
}
