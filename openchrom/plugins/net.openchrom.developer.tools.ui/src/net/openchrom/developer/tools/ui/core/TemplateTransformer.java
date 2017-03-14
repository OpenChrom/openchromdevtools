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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TemplateTransformer {

	public static final String PLACEHOLDER_DOMAIN_NAME = "__domainname__";
	public static final String PLACEHOLDER_DETECTOR_TYPE = "__detectortype__";
	public static final String PLACEHOLDER_PLUGIN_TYPE = "__plugintype__";
	public static final String PLACEHOLDER_PLUGIN_NAME = "__pluginname__";
	//
	private static final String SRC_FOLDER = "/src/";

	public void copy(String pathTemplateZIP, String pathExportDirectory, BundleSpecification bundleSpecification) {

		File templateZIP = new File(pathTemplateZIP);
		File exportDirectory = new File(pathExportDirectory);
		//
		if(templateZIP.isFile() && exportDirectory.isDirectory()) {
			try {
				copyFiles(bundleSpecification, templateZIP, exportDirectory);
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	private void copyFiles(BundleSpecification bundleSpecification, File templateZIP, File exportDirectory) throws IOException {

		ZipFile zipFile = new ZipFile(templateZIP);
		try {
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
			while(zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = zipEntries.nextElement();
				if(zipEntry.isDirectory()) {
					if(!zipEntry.getName().contains(SRC_FOLDER)) {
						createExportDirectory(bundleSpecification, zipEntry.getName(), exportDirectory);
					}
				} else {
					copyTemplate(bundleSpecification, zipFile, zipEntry, exportDirectory);
				}
			}
		} finally {
			zipFile.close();
		}
	}

	private String getModifiedFileName(BundleSpecification bundleSpecification, String fileName) {

		return getModifiedLine(bundleSpecification.getBundleComposition(), fileName);
	}

	private void createExportDirectory(BundleSpecification bundleComposition, String templateName, File exportDirectory) {

		String modifiedFileName = getModifiedFileName(bundleComposition, templateName);
		File targetDirectory = new File(exportDirectory.getAbsolutePath() + File.separator + modifiedFileName);
		//
		if(!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}
	}

	private void copyTemplate(BundleSpecification bundleSpecification, ZipFile zipFile, ZipEntry zipEntry, File exportDirectory) throws IOException {

		if(!zipEntry.isDirectory()) {
			/*
			 * Note: src files need to be handled in a different way.
			 */
			if(zipEntry.getName().contains(SRC_FOLDER)) {
				String modifiedFileName = getModifiedFileName(bundleSpecification, zipEntry.getName());
				int indexSrc = modifiedFileName.indexOf(SRC_FOLDER);
				String part1 = modifiedFileName.substring(0, indexSrc);
				String tmp = modifiedFileName.substring(indexSrc, modifiedFileName.length());
				int indexSlash = tmp.lastIndexOf("/");
				String part2 = tmp.substring(0, indexSlash).replaceAll("\\.", File.separator);
				String part3 = tmp.substring(indexSlash, tmp.length());
				//
				File fileTarget = new File(exportDirectory.getAbsolutePath() + File.separator + part1 + part2 + part3);
				copyTarget(bundleSpecification, zipFile, zipEntry, fileTarget);
			} else {
				String modifiedFileName = getModifiedFileName(bundleSpecification, zipEntry.getName());
				File fileTarget = new File(exportDirectory.getAbsolutePath() + File.separator + modifiedFileName);
				copyTarget(bundleSpecification, zipFile, zipEntry, fileTarget);
			}
		}
	}

	private void copyTarget(BundleSpecification bundleSpecification, ZipFile zipFile, ZipEntry zipEntry, File fileTarget) {

		/*
		 * If parent directory doesn't exits, create it.
		 */
		File parentDirectory = fileTarget.getParentFile();
		if(!parentDirectory.exists()) {
			parentDirectory.mkdirs();
		}
		/*
		 * Create the file and copy the content.
		 */
		if(!fileTarget.exists()) {
			try {
				if(fileTarget.createNewFile()) {
					transferFileContent(bundleSpecification, zipFile, zipEntry, fileTarget);
				}
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}

	private void transferFileContent(BundleSpecification bundleSpecification, ZipFile zipFile, ZipEntry zipEntry, File fileTarget) throws IOException {

		if(!zipEntry.isDirectory()) {
			DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(zipFile.getInputStream(zipEntry)));
			InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			PrintWriter printWriter = new PrintWriter(fileTarget);
			//
			try {
				String line;
				while((line = bufferedReader.readLine()) != null) {
					printWriter.println(getModifiedLine(bundleSpecification.getBundleComposition(), line));
				}
			} catch(Exception e) {
				System.out.println(e);
			} finally {
				bufferedReader.close();
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	private String getModifiedLine(BundleComposition bundleComposition, String line) {

		line = line.replace(PLACEHOLDER_DOMAIN_NAME, bundleComposition.getDomainName());
		line = line.replace(PLACEHOLDER_DETECTOR_TYPE, bundleComposition.getDetectorType());
		line = line.replace(PLACEHOLDER_PLUGIN_TYPE, bundleComposition.getPluginType());
		return line.replace(PLACEHOLDER_PLUGIN_NAME, bundleComposition.getPluginName());
	}
}
