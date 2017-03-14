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
package net.openchrom.developer.tools.ui.wizards.processor;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.developer.tools.ui.core.BundleInfo;

public class PageBundleInfo extends WizardPage {

	private Text versionText;
	private Text vendorText;
	private Text descriptionText;
	private Text websiteText;
	private Text fileExtensionText;
	private Text licenseText;
	private Text labelText;

	public PageBundleInfo() {
		super("wizardPage");
		setTitle("OpenChrom Processor (Info)");
		setDescription("This wizard helps you to create a new processor for OpenChrom.");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		//
		createVersionSection(composite);
		createVendorSection(composite);
		createDescriptionSection(composite);
		createWebsiteSection(composite);
		createLabelSection(composite);
		createFileExtensionSection(composite);
		createLicenseSection(composite);
		//
		initialize();
		dialogChanged();
		setControl(composite);
	}

	public BundleInfo getBundleInfo() {

		BundleInfo bundleInfo = new BundleInfo();
		bundleInfo.setDescription(descriptionText.getText().trim());
		bundleInfo.setLicense(licenseText.getText().trim());
		bundleInfo.setVendor(vendorText.getText().trim());
		bundleInfo.setVersion(versionText.getText().trim());
		bundleInfo.setFileExtension(fileExtensionText.getText().trim());
		bundleInfo.setWebsite(websiteText.getText().trim());
		return bundleInfo;
	}

	private void createVersionSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Version:");
		//
		versionText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		versionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		versionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createVendorSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Vendor:");
		//
		vendorText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		vendorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		vendorText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createDescriptionSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Description:");
		//
		descriptionText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		descriptionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createWebsiteSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Website:");
		//
		websiteText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		websiteText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		websiteText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createLabelSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Label:");
		//
		labelText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		labelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		labelText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createFileExtensionSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("File Extension:");
		//
		fileExtensionText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		fileExtensionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileExtensionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createLicenseSection(Composite composite) {

		licenseText = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		licenseText.setLayoutData(gridData);
		licenseText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void initialize() {

		String userName = System.getProperty("user.name");
		//
		StringBuilder license = new StringBuilder();
		license.append("/*******************************************************************************\n");
		license.append(" * Copyright (c) 2017 Lablicate GmbH.\n");
		license.append(" *\n");
		license.append(" * All rights reserved. This program and the accompanying materials\n");
		license.append(" * are made available under the terms of the Eclipse Public License v1.0\n");
		license.append(" * which accompanies this distribution, and is available at\n");
		license.append(" * http://www.eclipse.org/legal/epl-v10.html\n");
		license.append(" * Contributors:\n");
		license.append(" * " + userName + " - initial API and implementation\n");
		license.append(" *******************************************************************************/");
		//
		versionText.setText("1.2.0");
		vendorText.setText("OpenChrom");
		descriptionText.setText("This processor modifies the chromatogram.");
		websiteText.setText("http://www.openchrom.net");
		labelText.setText("My Plugin");
		fileExtensionText.setText(".mpe");
		licenseText.setText(license.toString());
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {

		if(!versionText.getText().trim().matches("([0-9]*)(\\.)([0-9]*)(\\.)([0-9]*)")) {
			updateStatus("The version must match the pattern, e.h.: 1.2.0.");
			return;
		}
		//
		if(vendorText.getText().trim().equals("")) {
			updateStatus("Please set a vendor, e.g. OpenChrom.");
			return;
		}
		//
		if(descriptionText.getText().trim().equals("")) {
			updateStatus("Please set description.");
			return;
		}
		//
		if(websiteText.getText().trim().equals("")) {
			updateStatus("Please set website.");
			return;
		}
		//
		if(licenseText.getText().trim().equals("")) {
			updateStatus("Please set license.");
			return;
		}
		//
		if(labelText.getText().trim().equals("")) {
			updateStatus("Please set plugin label, e.g.: My Plugin.");
			return;
		}
		//
		if(!fileExtensionText.getText().trim().matches("(\\.)([a-zA-Z])([a-zA-Z])([a-zA-Z])")) {
			updateStatus("The file extension must match the patter, e.g: '.mpe'.");
			return;
		}
		//
		updateStatus(null);
	}

	private void updateStatus(String message) {

		setErrorMessage(message);
		setPageComplete(message == null);
	}
}