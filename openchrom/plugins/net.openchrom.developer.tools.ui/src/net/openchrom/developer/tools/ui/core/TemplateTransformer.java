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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TemplateTransformer {

	public static final String PLACEHOLDER_DOMAIN_NAME = "__domainname__";
	public static final String PLACEHOLDER_DETECTOR_TYPE = "__detectortype__";
	public static final String PLACEHOLDER_PLUGIN_TYPE = "__plugintype__";
	public static final String PLACEHOLDER_PLUGIN_NAME = "__pluginname__";

	public void copy(String templateDirectoryPath, String targetDirectoryPath, BundleComposition bundleComposition) {

		File templateDirectory = new File(templateDirectoryPath);
		File targetDirectory = new File(targetDirectoryPath);
		//
		if(templateDirectory.isDirectory() && targetDirectory.isDirectory()) {
			try {
				copyFiles(bundleComposition, templateDirectory, targetDirectory);
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	private void copyFiles(BundleComposition bundleComposition, File template, File rootDirectory) throws IOException {

		if(template.isDirectory()) {
			for(File fileTemplate : template.listFiles()) {
				if(fileTemplate.isDirectory()) {
					String modifiedDirectoryName = getModifiedFileName(fileTemplate.getName(), bundleComposition);
					File targetDirectory = new File(rootDirectory.getAbsolutePath() + File.separator + modifiedDirectoryName);
					for(File fileTarget : fileTemplate.listFiles()) {
						copyFiles(bundleComposition, fileTarget, targetDirectory);
					}
				} else {
					copyTemplate(bundleComposition, fileTemplate, rootDirectory);
				}
			}
		} else {
			copyTemplate(bundleComposition, template, rootDirectory);
		}
	}

	private String getModifiedFileName(String fileName, BundleComposition bundleComposition) {

		return getModifiedLine(fileName, bundleComposition);
	}

	private void copyTemplate(BundleComposition bundleComposition, File fileTemplate, File rootDirectory) throws IOException {

		String modifiedFileName = getModifiedFileName(fileTemplate.getName(), bundleComposition);
		File fileTarget = new File(rootDirectory.getAbsolutePath() + File.separator + modifiedFileName);
		File parentDirectory = fileTarget.getParentFile();
		/*
		 * If parent directory doesn't exits, create it.
		 */
		if(!parentDirectory.exists()) {
			parentDirectory.mkdirs();
		}
		/*
		 * Create the file and copy the content.
		 */
		if(!fileTarget.exists()) {
			try {
				if(fileTarget.createNewFile()) {
					copyFileContent(bundleComposition, fileTemplate, fileTarget);
				}
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	private void copyFileContent(BundleComposition bundleComposition, File fileTemplate, File fileTarget) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileTemplate));
		PrintWriter printWriter = new PrintWriter(fileTarget);
		//
		String line;
		while((line = bufferedReader.readLine()) != null) {
			printWriter.println(getModifiedLine(line, bundleComposition));
		}
		//
		bufferedReader.close();
		printWriter.flush();
		printWriter.close();
	}

	private String getModifiedLine(String line, BundleComposition bundleComposition) {

		line = line.replace(PLACEHOLDER_DOMAIN_NAME, bundleComposition.getDomainName());
		line = line.replace(PLACEHOLDER_DETECTOR_TYPE, bundleComposition.getDetectorType());
		line = line.replace(PLACEHOLDER_PLUGIN_TYPE, bundleComposition.getPluginType());
		return line.replace(PLACEHOLDER_PLUGIN_NAME, bundleComposition.getPluginName());
	}
}
