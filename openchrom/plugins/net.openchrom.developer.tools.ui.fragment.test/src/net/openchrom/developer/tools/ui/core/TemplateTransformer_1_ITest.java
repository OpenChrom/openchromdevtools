/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.developer.tools.ui.core;

import junit.framework.TestCase;

public class TemplateTransformer_1_ITest extends TestCase {

	private TemplateTransformer templateTransformer;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		templateTransformer = new TemplateTransformer();
	}

	@Override
	protected void tearDown() throws Exception {

		templateTransformer = null;
		super.tearDown();
	}

	public void test1() {

		String domainName = "net.openchrom.chromatogram";
		String detectorType = "msd";
		String pluginType = "processor.supplier";
		String pluginName = "massshiftdetector";
		//
		String templateDirectory = "TODO-SET";
		String targetDirectory = "TODO-SET";
		//
		BundleComposition bundleComposition = new BundleComposition(domainName, detectorType, pluginType, pluginName);
		templateTransformer.copy(templateDirectory, targetDirectory, bundleComposition);
	}
}
