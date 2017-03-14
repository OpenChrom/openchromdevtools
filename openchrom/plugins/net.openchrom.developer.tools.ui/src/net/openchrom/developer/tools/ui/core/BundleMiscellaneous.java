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

public class BundleMiscellaneous {

	private String license;
	private String version;
	private String vendor;
	private String company;
	private String contributor;
	private String description;

	public String getLicense() {

		return license;
	}

	public void setLicense(String license) {

		this.license = license;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	public String getVendor() {

		return vendor;
	}

	public void setVendor(String vendor) {

		this.vendor = vendor;
	}

	public String getCompany() {

		return company;
	}

	public void setCompany(String company) {

		this.company = company;
	}

	public String getContributor() {

		return contributor;
	}

	public void setContributor(String contributor) {

		this.contributor = contributor;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}
}
