/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import net.openchrom.developer.tools.ui.TestPathHelper;

import junit.framework.TestCase;

public class TemplateTransformer_Processor_2_ITest extends TestCase {

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

		/*
		 * 
		 */
		String domainName = "net.openchrom";
		String detectorType = "xxd";
		String pluginType = "processor.supplier";
		String pluginName = "projectmanager";
		BundleComposition bundleComposition = new BundleComposition(domainName, detectorType, pluginType, pluginName);
		/*
		 * 
		 */
		StringBuilder license = new StringBuilder();
		license.append("/*******************************************************************************\n");
		license.append(" * Copyright (c) 2017, 2018 Lablicate GmbH.\n");
		license.append(" *\n");
		license.append(" * All rights reserved. This program and the accompanying materials\n");
		license.append(" * are made available under the terms of the Eclipse Public License v1.0\n");
		license.append(" * which accompanies this distribution, and is available at\n");
		license.append(" * http://www.eclipse.org/legal/epl-v10.html\n");
		license.append(" * Contributors:\n");
		license.append(" *\n");
		license.append(" * Dr. Philip Wenig - initial API and implementation\n");
		license.append(" *******************************************************************************/");
		//
		BundleInfo bundleInfo = new BundleInfo();
		bundleInfo.setVersion("1.3.0");
		bundleInfo.setVendor("OpenChrom");
		bundleInfo.setDescription("This is a project manager to run analytical tasks.");
		bundleInfo.setWebsite("http://www.openchrom.net");
		bundleInfo.setLabel("Project Manager");
		bundleInfo.setFileExtension("opm");
		bundleInfo.setLicense(license.toString());
		//
		String pathTemplateZIP = TestPathHelper.getAbsolutePath(TestPathHelper.TEMPLATE_PROCESSOR);
		String pathTargetDirectory = TestPathHelper.getAbsolutePath(TestPathHelper.EXPORT_DIRECTORY);
		//
		BundleSpecification bundleSpecification = new BundleSpecification(bundleComposition, bundleInfo);
		templateTransformer.copy(pathTemplateZIP, pathTargetDirectory, bundleSpecification);
	}
}
