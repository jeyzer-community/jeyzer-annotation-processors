package org.jeyzer.annotations.model;

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

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.jeyzer.annotations.error.JeyzerElementInstanciationException;

public abstract class JeyzerElement {
	
	public static final int DEFAULT_PRIORITY = -1;
	private static final int MIN_PRIORITY = 101;
	private static final int MAX_PRIORITY = 1000;
	
	protected String name;
	protected String pattern;
	protected int priority;

	public JeyzerElement(Element annotatedElement, int annotationPriority) throws JeyzerElementInstanciationException{
		validateElement(annotatedElement);
		validatePriority(annotatedElement, annotationPriority);

		// priority
		this.priority = annotationPriority;
	}
	
	protected abstract List<ElementKind> getSupportedElementKinds();
	
	private void validatePriority(Element annotatedElement, int annotationPriority) throws JeyzerElementInstanciationException {
		if (annotationPriority == DEFAULT_PRIORITY)
			return;
		
		if (annotationPriority < MIN_PRIORITY || annotationPriority > MAX_PRIORITY)
	    	throw new JeyzerElementInstanciationException(
	    			annotatedElement, 
	    			"Priority value is invalid. It must be set between " + MIN_PRIORITY + " and " + MAX_PRIORITY);
	}

	private void validateElement(Element annotatedElement) throws JeyzerElementInstanciationException {
        if (getSupportedElementKinds().contains(annotatedElement.getKind())) {
        	return;
        }
    	throw new JeyzerElementInstanciationException(
    			annotatedElement, 
    			"Unsupported element kind : " + annotatedElement.getKind());
	}

	public String getName() {
		return name;
	}

	public String getPattern() {
		return pattern;
	}

	public int getPriority() {
		return priority;
	}
	
}
