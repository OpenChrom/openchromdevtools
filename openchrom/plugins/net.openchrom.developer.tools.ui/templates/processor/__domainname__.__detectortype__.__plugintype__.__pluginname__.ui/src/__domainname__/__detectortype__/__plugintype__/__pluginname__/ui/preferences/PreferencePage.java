/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package __domainname__.__detectortype__.__plugintype__.__pluginname__.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import __domainname__.__detectortype__.__plugintype__.__pluginname__.preferences.PreferenceSupplier;
import __domainname__.__detectortype__.__plugintype__.__pluginname__.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("My Processor Settings");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new DoubleFieldEditor(PreferenceSupplier.P_MY_SETTING, "My Setting", PreferenceSupplier.MY_SETTING_MIN, PreferenceSupplier.MY_SETTING_MAX, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
