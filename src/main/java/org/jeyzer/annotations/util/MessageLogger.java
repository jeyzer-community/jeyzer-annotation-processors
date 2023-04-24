package org.jeyzer.annotations.util;

/*-
 * ---------------------------LICENSE_START---------------------------
 * Jeyzer Annotation Processors
 * --
 * Copyright (C) 2020 - 2023 Jeyzer
 * --
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * ----------------------------LICENSE_END----------------------------
 */

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import org.jeyzer.annotations.error.JeyzerElementInstanciationException;

public class MessageLogger {

	private Messager messager;
	
	public MessageLogger(Messager messager){
		this.messager = messager;
	}
	
	public void error(JeyzerElementInstanciationException ex) {
	    messager.printMessage(
	    	Diagnostic.Kind.ERROR,
	    	ex.getMessage(),
	    	ex.getAnnotatedElement());
	  }
	
	public void error(Exception ex) {
	    messager.printMessage(
	    	Diagnostic.Kind.ERROR,
	    	ex.getMessage());
	  }
	
	public void error(String message) {
	    messager.printMessage(
	    	Diagnostic.Kind.ERROR,
	    	message);
	  }
	
	public void info(String message, Element element) {
	    messager.printMessage(
	    	Diagnostic.Kind.NOTE,
	    	message,
	    	element);
	  }

	public void info(String message) {
	    messager.printMessage(
	    	Diagnostic.Kind.NOTE,
	    	message);
	  }
}
