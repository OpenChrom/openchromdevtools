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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.openchrom.developer.tools.ui.core.BundleComposition;

public class PageBundleComposition extends WizardPage {

	private Text domainNameText; // net.openchrom.chromatogram.msd.processor.supplier....
	private Combo detectorTypeCombo; // msd
	private Text processorNameText; // myprocessor
	//
	private Text bundleModelText;
	private Text bundleUIText;
	private Text bundleTestFragmentText;
	private Text bundleFeatureText;
	private Text bundleCBIText;
	private Text bundleUpdateSiteText;

	public PageBundleComposition() {
		super("wizardPage");
		setTitle("OpenChrom Processor (Bundle Name)");
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
		createDomainNameSection(composite);
		createDetectorTypeSection(composite);
		createProcessorNameSection(composite);
		createBundleCompositionSection(composite);
		//
		initialize();
		dialogChanged();
		setControl(composite);
	}

	public BundleComposition getBundleComposition() {

		/*
		 * [net.openchrom.chromatogram].xxd.[processor.supplier].myprocessor
		 */
		String domainName = domainNameText.getText().trim();
		String detectorType = detectorTypeCombo.getText().trim();
		String pluginType = "processor.supplier";
		String processorName = processorNameText.getText().trim();
		//
		return new BundleComposition(domainName, detectorType, pluginType, processorName);
	}

	private void createDomainNameSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Domain Name:");
		//
		domainNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		domainNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		domainNameText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createDetectorTypeSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Detector Type:");
		//
		detectorTypeCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		detectorTypeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detectorTypeCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				dialogChanged();
			}
		});
	}

	private void createProcessorNameSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Processor Name:");
		//
		processorNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		processorNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		processorNameText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {

				dialogChanged();
			}
		});
	}

	private void createBundleCompositionSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText("Final Bundle Composition:");
		GridData gridData = getGridData();
		gridData.verticalIndent = 10;
		label.setLayoutData(gridData);
		//
		bundleModelText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		bundleModelText.setLayoutData(getGridData());
		//
		bundleUIText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		bundleUIText.setLayoutData(getGridData());
		//
		bundleTestFragmentText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		bundleTestFragmentText.setLayoutData(getGridData());
		//
		bundleFeatureText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		bundleFeatureText.setLayoutData(getGridData());
		//
		bundleCBIText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		bundleCBIText.setLayoutData(getGridData());
		//
		bundleUpdateSiteText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		bundleUpdateSiteText.setLayoutData(getGridData());
	}

	private GridData getGridData() {

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		return gridData;
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {

		domainNameText.setText("net.openchrom.chromatogram");
		detectorTypeCombo.setItems(new String[]{"msd", "csd", "wsd", "xxd"});
		detectorTypeCombo.select(0);
		processorNameText.setText("myprocessor");
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {

		BundleComposition bundleComposition = getBundleComposition();
		/*
		 * Set the text fields.
		 */
		bundleModelText.setText(bundleComposition.getBundleModel());
		bundleUIText.setText(bundleComposition.getBundleUI());
		bundleTestFragmentText.setText(bundleComposition.getBundleTestFragment());
		bundleFeatureText.setText(bundleComposition.getBundleFeature());
		bundleCBIText.setText(bundleComposition.getBundleCBI());
		bundleUpdateSiteText.setText(bundleComposition.getBundleUpdateSite());
		/*
		 * Is a workspace available?
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if(root == null || !root.exists()) {
			updateStatus("A workspace must have been selected.");
			return;
		}
		/*
		 * Validate that no plug-in with the same name already exists.
		 */
		List<String> bundles = new ArrayList<String>();
		bundles.add(bundleComposition.getBundleModel());
		bundles.add(bundleComposition.getBundleUI());
		bundles.add(bundleComposition.getBundleTestFragment());
		bundles.add(bundleComposition.getBundleFeature());
		bundles.add(bundleComposition.getBundleCBI());
		bundles.add(bundleComposition.getBundleUpdateSite());
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
		} else if(bundle.contains("\\") || bundle.contains("/")) {
			updateStatus("The bundle \"" + bundle + "\" name must not contain '\\' or '/'.");
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