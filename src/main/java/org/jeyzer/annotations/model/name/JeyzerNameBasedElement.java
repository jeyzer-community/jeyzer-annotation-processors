package org.jeyzer.annotations.model.name;

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

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.lang.model.element.Element;

import org.jeyzer.annotations.error.JeyzerElementInstanciationException;
import org.jeyzer.annotations.model.JeyzerElement;

public abstract class JeyzerNameBasedElement extends JeyzerElement{
	
	private String regexPattern;

	public JeyzerNameBasedElement(Element annotatedElement, String annotationName, String annotationPattern, String annotationRegexPattern, int annotationPriority) throws JeyzerElementInstanciationException {
		super(annotatedElement, annotationPriority);
		validateName(annotatedElement, annotationName);
		validatePattern(annotatedElement, annotationPattern, annotationRegexPattern);
		
		// name
		this.name = annotationName.substring(0,1).toUpperCase() + annotationName.substring(1);
		
		// pattern
		this.pattern = annotationPattern;
		
		// regex pattern
		this.regexPattern = annotationRegexPattern; 
	}

	private void validatePattern(Element annotatedElement, String annotationPattern, String annotationRegexPattern) throws JeyzerElementInstanciationException {
		if ((annotationPattern == null || annotationPattern.isEmpty()) 
				&& (annotationRegexPattern == null || annotationRegexPattern.isEmpty()))
	    	throw new JeyzerElementInstanciationException(
	    			annotatedElement, 
	    			"Pattern or Regular expression pattern is missing on the annotated element. It is mandatory on the Jeyzer name based elements");
		
		if ((annotationPattern != null && !annotationPattern.isEmpty()) 
				&& (annotationRegexPattern != null && !annotationRegexPattern.isEmpty()))
	    	throw new JeyzerElementInstanciationException(
	    			annotatedElement, 
	    			"Pattern or Regular expression pattern are both set on the annotated element. Only one is accepted on the Jeyzer name based elements");
		
		// check the regular expression
		if (annotationRegexPattern != null && !annotationRegexPattern.isEmpty()){
			try{
				Pattern.compile(annotationRegexPattern);	
			}catch(PatternSyntaxException ex){
		    	throw new JeyzerElementInstanciationException(
		    			annotatedElement, 
		    			"Regular expression pattern is invalid." + ex.getMessage());				
			}
		}
	}

	private void validateName(Element annotatedElement, String annotationName) throws JeyzerElementInstanciationException {
		if (annotationName == null || annotationName.isEmpty())
	    	throw new JeyzerElementInstanciationException(
	    			annotatedElement, 
	    			"Name is missing on the annotated element. It is mandatory on the Jeyzer name based elements");
	}

	public String getRegexPattern() {
		return regexPattern;
	}

}
