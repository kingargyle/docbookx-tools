package com.agilejava.docbkx.maven;

/*
 * Copyright 2006 Wilfred Springer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import javax.xml.transform.Transformer;

/**
 * A dedicated base class for plugins generating HTML output, in order to allow
 * the specific stylesheet chosen to be dependent on the {@link #chunkedOutput}
 * property.
 * 
 * @author Wilfred Springer
 * 
 */
public abstract class AbstractHtmlMojo extends AbstractMojoBase {

    /**
     * Indicates if the output should either be a single page, or if it should
     * be spread across multiple pages.
     * 
     * @parameter default="false"
     */
    private boolean chunkedOutput;

    protected String getNonDefaultStylesheetLocation() {
        if (chunkedOutput) {
            return "docbook/"+getType()+"/chunk.xsl";
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc} This implementation will set the root.filename property,
     * based on the targetFile's name.
     */
    public void adjustTransformer(Transformer transformer,
            String sourceFilename, File targetFile) {
        super.adjustTransformer(transformer, sourceFilename, targetFile);
        if (chunkedOutput) {
            getLog().debug("Chunking output.");
            String rootFilename = targetFile.getName();
            rootFilename = rootFilename.substring(0, rootFilename
                    .lastIndexOf('.'));
            transformer.setParameter("root.filename", rootFilename);
            transformer.setParameter("base.dir", targetFile.getParent()
                    + File.separator);
        }
    }

}
