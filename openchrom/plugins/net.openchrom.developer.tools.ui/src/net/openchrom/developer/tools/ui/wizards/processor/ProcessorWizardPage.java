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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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

public class ProcessorWizardPage extends WizardPage {

	private Text idnText; // net.openchrom.chromatogram.msd.processor.supplier....

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ProcessorWizardPage() {
		super("wizardPage");
		setTitle("OpenChrom Processor");
		setDescription("This wizard helps you to create a new processor for OpenChrom.");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		//
		createIdnSection(container);
		//
		initialize();
		dialogChanged();
		setControl(container);
	}

	public String getIdnName() {

		return idnText.getText().trim();
	}

	private void createIdnSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("&IDN:");
		//
		idnText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		idnText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		idnText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {

		idnText.setText("net.openchrom.chromatogram.xxd.processor.supplier.myproc");
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {

		String idnName = getIdnName();
		/*
		 * Is a workspace available?
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if(root == null || !root.exists()) {
			updateStatus("A workspace must have been selected.");
			return;
		}
		/*
		 * IDN
		 */
		if(idnName.length() == 0) {
			updateStatus("IDN must be specified");
			return;
		}
		if(idnName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("IDN must be valid");
			return;
		}
		/*
		 * Validate that no plugin with the same name already exists.
		 */
		String bundleModel = idnName;
		String bundleUI = idnName + ".ui";
		String bundleTestFragment = idnName + ".fragment.test";
		String bundleFeature = idnName + ".feature";
		String bundleCBI = idnName + ".cbi";
		String bundleUpdateSite = idnName + ".updateSite";
		//
		List<String> bundles = new ArrayList<String>();
		bundles.add(bundleModel);
		bundles.add(bundleUI);
		bundles.add(bundleTestFragment);
		bundles.add(bundleFeature);
		bundles.add(bundleCBI);
		bundles.add(bundleUpdateSite);
		//
		for(String bundle : bundles) {
			if(!validateBundle(root, bundle)) {
				return;
			}
		}
		//
		updateStatus(null);
	}

	private boolean validateBundle(IWorkspaceRoot root, String bundle) {

		IResource resource = root.findMember(new Path(bundle));
		if(resource != null && resource.exists()) {
			updateStatus("The workspace bundle \"" + bundle + "\" already exists.");
			return true;
		} else {
			return false;
		}
	}

	private void updateStatus(String message) {

		setErrorMessage(message);
		setPageComplete(message == null);
	}
}