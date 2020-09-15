package org.jeyzer.annotations.model.code;

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

import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

import org.jeyzer.annotations.Operation;
import org.jeyzer.annotations.error.JeyzerElementInstanciationException;

public class JeyzerOperation extends JeyzerCodeBasedElement{

	private final boolean lowStack;
	private final String contentionType;
	
	public JeyzerOperation(Element annotatedElement, Elements elementUtils) throws JeyzerElementInstanciationException{
		super(annotatedElement, 
				annotatedElement.getAnnotation(Operation.class).name(), 
				annotatedElement.getAnnotation(Operation.class).priority(),
				elementUtils 
				);
		
		this.lowStack = annotatedElement.getAnnotation(Operation.class).lowStack();
		if (this.lowStack && validateLowStack(annotatedElement))
			this.pattern += "."; // ex: java.lang.String.
		this.contentionType = annotatedElement.getAnnotation(Operation.class).contentionType();
	}
	
	private boolean validateLowStack(Element annotatedElement) throws JeyzerElementInstanciationException {
		// valid only for packages and classes
		if ((annotatedElement.getKind()  == ElementKind.CLASS 
				|| annotatedElement.getKind()  == ElementKind.PACKAGE)){
			return true;
		}
		else{
        	throw new JeyzerElementInstanciationException(
        			annotatedElement, 
        			"Low stack usage is possible only on classes and packages. Found on annotation of kind : " + annotatedElement.getKind().name());
		}
	}

	public boolean isLowStack(){
		return this.lowStack;
	}
	
	public String getContentionType(){
		return this.contentionType;
	}
	
	@Override
	protected List<ElementKind> getSupportedElementKinds() {
		return Arrays.asList(
				ElementKind.METHOD, 
				ElementKind.CONSTRUCTOR, 
				ElementKind.PACKAGE, 
				ElementKind.CLASS);
	}
	
}
