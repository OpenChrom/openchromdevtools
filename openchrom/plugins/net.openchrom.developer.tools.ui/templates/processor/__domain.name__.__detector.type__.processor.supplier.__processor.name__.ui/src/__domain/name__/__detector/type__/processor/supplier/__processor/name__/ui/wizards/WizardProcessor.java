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
package __domain.name__.__detector.type__.processor.supplier.__processor.name__.ui.wizards;

import java.util.Date;

import org.eclipse.chemclipse.support.ui.wizards.AbstractFileWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import __domain.name__.__detector.type__.processor.supplier.__processor.name__.core.Processor;

public class WizardProcessor extends AbstractFileWizard {

	private IProcessorWizardElements wizardElements = new ProcessorWizardElements();
	private PageDescription pageDescription;

	public WizardProcessor() {
		super("MyProcessor_" + new Date().getTime(), Processor.PROCESSOR_FILE_EXTENSION);
	}

	@Override
	public void addPages() {

		super.addPages();
		/*
		 * Pages must implement IExtendedWizardPage / extend AbstractExtendedWizardPage
		 */
		pageDescription = new PageDescription(wizardElements);
		//
		addPage(pageDescription);
	}

	@Override
	public boolean canFinish() {

		boolean canFinish = pageDescription.canFinish();
		return canFinish;
	}

	@Override
	public void doFinish(IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("My Processor File Create", IProgressMonitor.UNKNOWN);
		final IFile file = super.prepareProject(monitor);
		// TODO Write file
		/*
		 * Refresh
		 */
		super.refreshWorkspace(monitor);
		super.runOpenEditor(file, monitor);
	}
}
