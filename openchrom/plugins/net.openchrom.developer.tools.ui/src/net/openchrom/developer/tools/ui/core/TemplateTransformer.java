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

import java.io.File;

public class TemplateTransformer {

	public static final String PLACEHOLDER_DOMAIN_NAME = "__domain.name__";
	public static final String PLACEHOLDER_DETECTOR_TYPE = "__detector.type__";
	public static final String PLACEHOLDER_PROCESSOR_NAME = "__processor.name__";

	public void copy(String templateDirectory, String targetDirectory, BundleComposition bundleComposition) {

		File template = new File(templateDirectory);
		File target = new File(targetDirectory);
		//
		if(template.isDirectory() && target.isDirectory()) {
			//
			System.out.println(template);
			System.out.println(target);
			System.out.println("Implement Copy");
			//
		}
	}
}
