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

import net.openchrom.developer.tools.ui.core.BundleMiscellaneous;

public class PageMiscellaneous extends WizardPage {

	private Text licenseText;
	private Text versionText;
	private Text vendorText;
	private Text companyText;
	private Text contributorText;
	private Text descriptionText;

	public PageMiscellaneous() {
		super("wizardPage");
		setTitle("OpenChrom Processor (Miscellaneous)");
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
		createCompanySection(composite);
		createContributorSection(composite);
		//
		initialize();
		dialogChanged();
		setControl(composite);
	}

	public BundleMiscellaneous getBundleMiscellaneous() {

		BundleMiscellaneous bundleMiscellaneous = new BundleMiscellaneous();
		//
		//
		return bundleMiscellaneous;
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

	private void createCompanySection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Company:");
		//
		companyText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		companyText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		companyText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createContributorSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Contributor:");
		//
		contributorText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		contributorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		contributorText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private GridData getGridData() {

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		return gridData;
	}

	private void initialize() {

	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {

		updateStatus(null);
	}

	private void updateStatus(String message) {

		setErrorMessage(message);
		setPageComplete(message == null);
	}
}