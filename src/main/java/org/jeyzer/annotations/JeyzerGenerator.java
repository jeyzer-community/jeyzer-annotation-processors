package org.jeyzer.annotations;

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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.jeyzer.annotations.error.JeyzerFileGenerationException;
import org.jeyzer.annotations.model.JeyzerElement;
import org.jeyzer.annotations.model.JeyzerModel;
import org.jeyzer.annotations.model.code.JeyzerExclude;
import org.jeyzer.annotations.model.code.JeyzerExecutor;
import org.jeyzer.annotations.model.code.JeyzerFunction;
import org.jeyzer.annotations.model.code.JeyzerLocker;
import org.jeyzer.annotations.model.code.JeyzerOperation;
import org.jeyzer.annotations.model.name.JeyzerExcludeThreadName;
import org.jeyzer.annotations.model.name.JeyzerExecutorThreadName;
import org.jeyzer.annotations.model.name.JeyzerNameBasedElement;
import org.jeyzer.annotations.util.MessageLogger;
import org.jeyzer.annotations.util.SystemHelper;

public class JeyzerGenerator {
	
	private static final String PATTERNS_FILE = "new_pattern_entries.xml";
	
	private Filer filer;
	private MessageLogger logger;
	
	public JeyzerGenerator(Filer filer, MessageLogger logger){
		this.filer = filer;
		this.logger = logger;
	}
	
	public void generatePatterns(JeyzerModel model) throws JeyzerFileGenerationException{
    	try{
        	generateOutsidePatterns(model);
		}catch(JeyzerFileGenerationException ex){
			logger.error(ex);
			generateDefaultPatterns(model);
		}
	}
	
	private void generateDefaultPatterns(JeyzerModel model) throws JeyzerFileGenerationException {
        FileObject resource;

        try {
			resource = filer.createResource( StandardLocation.SOURCE_OUTPUT, "", PATTERNS_FILE);
		} catch (IOException ex) {
            throw new JeyzerFileGenerationException("Failed to create the " + PATTERNS_FILE + " file. Error is : " + ex.getMessage());
		}

        try (
        		Writer writer = resource.openWriter();
        	)
        {
            writePatterns(writer, model);
        }
        catch (IOException e)
        {
            throw new JeyzerFileGenerationException("Failed to write the " + PATTERNS_FILE + " file. Error is : " + e.getMessage());
        }	
    }

	private void generateOutsidePatterns(JeyzerModel model) throws JeyzerFileGenerationException {
		FileObject resource = null;
		
		try {
            resource = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", PATTERNS_FILE);
		} catch (IOException ex)
        {
        	throw new JeyzerFileGenerationException("Failed to create the " + PATTERNS_FILE + " file. Error is : " + ex.getMessage());
        }

		File file = new File(resource.toUri());
		String parentPath = file.getParent(); // parent is classes (if sources go there)
		file = new File(parentPath);
		parentPath = file.getParent(); // parent is target under maven
		String jeyzerPath = parentPath + "/jeyzer";
		SystemHelper.createDirectory(jeyzerPath);

		File jeyzerFile = new File(jeyzerPath, PATTERNS_FILE);
		if (jeyzerFile.exists() && !jeyzerFile.delete())
            logger.error("Failed to delete the file " + jeyzerFile.getAbsolutePath());
		
        try (
                FileWriter fileWriter = new FileWriter(jeyzerFile.getAbsoluteFile());
        		BufferedWriter writer = new BufferedWriter(fileWriter);
           )
        {
            writePatterns(writer, model);
        }
        catch (IOException e)
        {
            throw new JeyzerFileGenerationException("Failed to write the " + PATTERNS_FILE + " file. Error is : " + e.getMessage());
        }
	}

	private void writePatterns(Writer writer, JeyzerModel model) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<patterns>\n");
        writeFunctions(writer, model);
        writeOperations(writer, model);
        writeLockers(writer, model);
        writeExecutors(writer, model);
        writeExecutorThreadNames(writer, model);
        writeExcludes(writer, model);
        writeExcludeThreadNames(writer, model);
        writer.write("</patterns>\n");
	}

	private void writeFunctions(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<functions>\n");
		for (JeyzerFunction function : model.getFunctions()){
			writeCodeBasedElement("function", function, writer);
		}
		writer.write("\t</functions>\n");
	}
	
	private void writeOperations(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<operations>\n");
		for (JeyzerOperation operation : model.getOperations()){
			writeOperationCodeBasedElement("operation", operation, writer);
		}
		for (JeyzerOperation operation : model.getLowOperations()){
			writeOperationCodeBasedElement("operation", operation, writer);
		}
		writer.write("\t</operations>\n");
	}
	
	private void writeLockers(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<lockers>\n");
		for (JeyzerLocker locker : model.getLockers()){
			writeCodeBasedElement("locker", locker, writer);
		}
		writer.write("\t</lockers>\n");
	}
	
	private void writeExecutors(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<executors>\n");
		for (JeyzerExecutor executor : model.getExecutors()){
			writeCodeBasedElement("executor", executor, writer);
		}
		writer.write("\t</executors>\n");
	}
	
	private void writeExecutorThreadNames(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<executor_thread_names>\n");
		for (JeyzerExecutorThreadName executorThreadName : model.getExecutorThreadNames()){
			writeNameBasedElement("executor_thread_name", executorThreadName, writer);
		}
		writer.write("\t</executor_thread_names>\n");
	}

	private void writeExcludes(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<excludes>\n");
		for (JeyzerExclude exclude : model.getExcludes()){
			writeCodeBasedElement("exclude", exclude, writer);
		}
		writer.write("\t</excludes>\n");
	}
	
	private void writeExcludeThreadNames(Writer writer, JeyzerModel model) throws IOException {
		writer.write("\t<exclude_thread_names>\n");
		for (JeyzerExcludeThreadName excludeThreadName : model.getExcludeThreadNames()){
			writeNameBasedElement("exclude_thread_name", excludeThreadName, writer);
		}
		writer.write("\t</exclude_thread_names>\n");
	}
	
	private void writeCodeBasedElement(String type, JeyzerElement element, Writer writer) throws IOException {
		// pattern
		writer.write("\t\t<" + type + " pattern=\"");
		writer.write(element.getPattern());
		
		// name
		writer.write("\" name=\"");
		writer.write(element.getName());
		
		// priority
		if (element.getPriority() != JeyzerElement.DEFAULT_PRIORITY){
			writer.write("\" priority=\"");
			writer.write(Integer.toString(element.getPriority()));
		}

		writer.write("\"/>\n");
	}
	
	private void writeOperationCodeBasedElement(String type, JeyzerOperation element, Writer writer) throws IOException {
		// pattern
		writer.write("\t\t<" + type + " pattern=\"");
		writer.write(element.getPattern());
		
		// name
		writer.write("\" name=\"");
		writer.write(element.getName());
		
		// priority
		if (element.getPriority() != JeyzerElement.DEFAULT_PRIORITY){
			writer.write("\" priority=\"");
			writer.write(Integer.toString(element.getPriority()));
		}
		
		String contentionType = element.getContentionType();
		if (contentionType != null && !contentionType.isEmpty()){
			writer.write("\" type=\"");
			writer.write(element.getContentionType());
		}

		writer.write("\"/>\n");
	}
	
	private void writeNameBasedElement(String type, JeyzerNameBasedElement nameBasedElement, Writer writer) throws IOException {
		
		if (nameBasedElement.getPattern() != null && !nameBasedElement.getPattern().isEmpty()){
			// pattern
			writer.write("\t\t<" + type + " pattern=\"");
			writer.write(nameBasedElement.getPattern());
		}else{
			// regex pattern
			writer.write("\t\t<" + type + " pattern_regex=\"");
			writer.write(nameBasedElement.getRegexPattern());
		}
		
		// name
		writer.write("\" name=\"");
		writer.write(nameBasedElement.getName());
		
		// priority
		if (nameBasedElement.getPriority() != JeyzerElement.DEFAULT_PRIORITY){
			writer.write("\" priority=\"");
			writer.write(Integer.toString(nameBasedElement.getPriority()));
		}
		
		// size
		if (nameBasedElement instanceof JeyzerExcludeThreadName){
			JeyzerExcludeThreadName excludeThreadName = (JeyzerExcludeThreadName)nameBasedElement;
			if (excludeThreadName.getSize() != JeyzerExcludeThreadName.DEFAULT_SIZE){
				writer.write("\" size=\"");
				writer.write(Integer.toString(excludeThreadName.getSize()));
			}
		}

		writer.write("\"/>\n");
	}


}
