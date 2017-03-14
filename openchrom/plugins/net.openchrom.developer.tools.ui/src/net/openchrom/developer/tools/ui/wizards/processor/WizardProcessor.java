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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import net.openchrom.developer.tools.ui.PathResolver;
import net.openchrom.developer.tools.ui.core.BundleComposition;
import net.openchrom.developer.tools.ui.core.BundleInfo;
import net.openchrom.developer.tools.ui.core.BundleSpecification;
import net.openchrom.developer.tools.ui.core.TemplateTransformer;

public class WizardProcessor extends Wizard implements INewWizard {

	private PageBundleComposition pageBundleComposition;
	private PageBundleInfo pageBundleInfo;

	public WizardProcessor() {
		super();
		setNeedsProgressMonitor(true);
	}

	public void addPages() {

		pageBundleComposition = new PageBundleComposition();
		pageBundleInfo = new PageBundleInfo();
		//
		addPage(pageBundleComposition);
		addPage(pageBundleInfo);
	}

	public boolean performFinish() {

		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor) throws InvocationTargetException {

				try {
					doFinish(monitor);
				} catch(CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		//
		try {
			getContainer().run(true, false, runnable);
		} catch(InterruptedException e) {
			return false;
		} catch(InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	private void doFinish(IProgressMonitor monitor) throws CoreException {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		BundleComposition bundleComposition = pageBundleComposition.getBundleComposition();
		BundleInfo bundleInfo = pageBundleInfo.getBundleInfo();
		BundleSpecification bundleSpecification = new BundleSpecification(bundleComposition, bundleInfo);
		/*
		 * Validate the workspace.
		 */
		try {
			monitor.beginTask("Creating the processor", 2);
			monitor.worked(1);
			//
			String pathTemplateZIP = PathResolver.getAbsolutePath(PathResolver.TEMPLATE_PROCESSOR);
			String pathTargetDirectory = root.getLocation().toFile().toString();
			//
			TemplateTransformer templateTransformer = new TemplateTransformer();
			templateTransformer.copy(pathTemplateZIP, pathTargetDirectory, bundleSpecification);
			//
			monitor.setTaskName("Opening file for editing...");
			monitor.worked(1);
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}
}