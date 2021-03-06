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
package net.openchrom.developer.tools.ui.wizards;

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
	private Text pluginNameText; // myprocessor, myconverter
	//
	private Text bundleModelText;
	private Text bundleUIText;
	private Text bundleTestFragmentText;
	private Text bundleFeatureText;
	private Text bundleCBIText;
	private Text bundleUpdateSiteText;
	//
	private BundleComposition bundleComposition = new BundleComposition("set", "set", "set", "set");
	//
	private String typeLabel = ""; // Processor | Converter
	private String[] detectorTypeItems; // new String[]{"msd", "csd", "wsd", "xxd"}
	private String pluginType; // "processor.supplier" | "converter.supplier"

	public PageBundleComposition(String typeLabel, String[] detectorTypeItems, String pluginType) {
		super("wizardPage");
		//
		this.typeLabel = typeLabel;
		this.detectorTypeItems = detectorTypeItems;
		this.pluginType = pluginType;
		//
		setTitle("OpenChrom " + typeLabel + " (Bundle Name)");
		setDescription("This wizard helps you to create a new " + typeLabel.toLowerCase() + " for OpenChrom.");
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
		createPluginNameSection(composite);
		createBundleCompositionSection(composite);
		//
		initialize();
		dialogChanged();
		setControl(composite);
	}

	@Override
	public boolean canFlipToNextPage() {

		return isPageComplete();
	}

	public BundleComposition getBundleComposition() {

		return bundleComposition;
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

	private void createPluginNameSection(Composite composite) {

		Label label = new Label(composite, SWT.NULL);
		label.setText(typeLabel + " Name:");
		//
		pluginNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		pluginNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		pluginNameText.addModifyListener(new ModifyListener() {

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
		detectorTypeCombo.setItems(detectorTypeItems);
		detectorTypeCombo.select(0);
		pluginNameText.setText("my" + typeLabel.toLowerCase());
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {

		/*
		 * Is a workspace available?
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if(root == null || !root.exists()) {
			updateStatus("A workspace must have been selected.");
			return;
		}
		//
		if(domainNameText.getText().trim().equals("")) {
			updateStatus("Please set domain name, e.g: 'net.openchrom.chromatogram'.");
			return;
		}
		//
		if(detectorTypeCombo.getText().trim().equals("")) {
			updateStatus("Please select a detector type.");
			return;
		}
		//
		if(pluginNameText.getText().trim().equals("")) {
			updateStatus("Please set a " + typeLabel.toLowerCase() + " name, e.g: 'my" + typeLabel.toLowerCase() + "'.");
			return;
		}
		/*
		 * Validate that no plug-in with the same name already exists.
		 * Set the text fields.
		 * [net.openchrom.chromatogram].xxd.[processor.supplier].myprocessor
		 */
		String domainName = domainNameText.getText().trim().toLowerCase();
		String detectorType = detectorTypeCombo.getText().trim().toLowerCase();
		String pluginName = pluginNameText.getText().trim().toLowerCase();
		bundleComposition = new BundleComposition(domainName, detectorType, pluginType, pluginName);
		//
		bundleModelText.setText(bundleComposition.getBundleModel());
		bundleUIText.setText(bundleComposition.getBundleUI());
		bundleTestFragmentText.setText(bundleComposition.getBundleTestFragment());
		bundleFeatureText.setText(bundleComposition.getBundleFeature());
		bundleCBIText.setText(bundleComposition.getBundleCBI());
		bundleUpdateSiteText.setText(bundleComposition.getBundleUpdateSite());
		//
		List<String> bundles = new ArrayList<String>();
		bundles.add(bundleComposition.getBundleModel());
		bundles.add(bundleComposition.getBundleUI());
		bundles.add(bundleComposition.getBundleTestFragment());
		bundles.add(bundleComposition.getBundleFeature());
		bundles.add(bundleComposition.getBundleCBI());
		bundles.add(bundleComposition.getBundleUpdateSite());
		//
		for(String bundle : bundles) {
			if(!isValidBundle(root, bundle)) {
				return;
			}
		}
		/*
		 * All tests passed.
		 */
		updateStatus(null);
	}

	private boolean isValidBundle(IWorkspaceRoot root, String bundle) {

		IResource resource = root.findMember(new Path(bundle));
		if(resource != null && resource.exists()) {
			updateStatus("The workspace bundle \"" + bundle + "\" already exists.");
			return false;
		} else if(bundle.contains("\\") || bundle.contains("/")) {
			updateStatus("The bundle \"" + bundle + "\" name must not contain '\\' or '/'.");
			return false;
		} else {
			return true;
		}
	}

	private void updateStatus(String message) {

		setErrorMessage(message);
		setPageComplete(message == null);
	}
}