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

public class BundleComposition {

	private String domainName;
	private String detectorType;
	private String pluginType;
	private String processorName;
	//
	private String bundleModel;
	private String bundleUI;
	private String bundleTestFragment;
	private String bundleFeature;
	private String bundleCBI;
	private String bundleUpdateSite;

	public BundleComposition(String domainName, String detectorType, String pluginType, String processorName) {
		/*
		 * Get the base name.
		 */
		this.domainName = domainName;
		this.detectorType = detectorType;
		this.pluginType = pluginType;
		this.processorName = processorName;
		//
		StringBuilder builder = new StringBuilder();
		builder.append(domainName);
		builder.append(".");
		builder.append(detectorType);
		builder.append(".");
		builder.append(pluginType);
		builder.append(".");
		builder.append(processorName);
		String bundleBaseName = builder.toString();
		/*
		 * Create the bundle names.
		 */
		bundleModel = bundleBaseName;
		bundleUI = bundleBaseName + ".ui";
		bundleTestFragment = bundleBaseName + ".fragment.test";
		bundleFeature = bundleBaseName + ".feature";
		bundleCBI = bundleBaseName + ".cbi";
		bundleUpdateSite = bundleBaseName + ".updateSite";
	}

	public String getDomainName() {

		return domainName;
	}

	public String getDetectorType() {

		return detectorType;
	}

	public String getPluginType() {

		return pluginType;
	}

	public String getProcessorName() {

		return processorName;
	}

	public String getBundleModel() {

		return bundleModel;
	}

	public String getBundleUI() {

		return bundleUI;
	}

	public String getBundleTestFragment() {

		return bundleTestFragment;
	}

	public String getBundleFeature() {

		return bundleFeature;
	}

	public String getBundleCBI() {

		return bundleCBI;
	}

	public String getBundleUpdateSite() {

		return bundleUpdateSite;
	}
}
