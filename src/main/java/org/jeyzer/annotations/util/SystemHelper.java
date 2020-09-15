package org.jeyzer.annotations.util;

/*-
 * ---------------------------LICENSE_START---------------------------
 * Jeyzer Annotation Processors
 * --
 * Copyright (C) 2020 Jeyzer SAS
 * --
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * ----------------------------LICENSE_END----------------------------
 */

import java.io.File;

import org.jeyzer.annotations.error.JeyzerFileGenerationException;

public class SystemHelper {
	public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";  
	public static final String WINDOWS_OS = "Win";
	public static final String CR = System.getProperty("line.separator");
	
	private SystemHelper() {
	}

	public static boolean isWindows(){
		return System.getProperty(SYSTEM_PROPERTY_OS_NAME).startsWith(WINDOWS_OS);
	}

	public static String sanitizePathSeparators(String path){
		if (isWindows()){
			return path.replace('/', '\\');
		}
		else{
			return path.replace('\\', '/');
		}
	}
	
	public static void createDirectory(String path) throws JeyzerFileGenerationException{
		 File dir = new File(path);
		 if (!dir.exists()){
			 if (!dir.mkdirs())
				 throw new JeyzerFileGenerationException("Failed to create directory : " + sanitizePathSeparators(path));
		 }
	}

	public static boolean deleteFile(String path) {
		File targetFile = new File(path);
		// remove if already exists
		if (targetFile.exists())
			return targetFile.delete();
		else
			return false;
	}
}
