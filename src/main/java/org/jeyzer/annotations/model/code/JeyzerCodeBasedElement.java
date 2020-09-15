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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

import org.jeyzer.annotations.error.JeyzerElementInstanciationException;
import org.jeyzer.annotations.model.JeyzerElement;

public abstract class JeyzerCodeBasedElement extends JeyzerElement{

	// Constructor or variable initialization is diplayed as ".<init>" in stack traces
	private static final String CONSTRUCTOR_ELEMENT_NAME = "<init>";
	private static final String CONSTRUCTOR_SUFFIX = ".&lt;init&gt;";
	private static final String CONSTRUCTOR_NAME = "instantiation";
	
	public JeyzerCodeBasedElement(Element annotatedElement, String annotationName, int annotationPriority, Elements elementUtils) throws JeyzerElementInstanciationException{
		super(annotatedElement, annotationPriority);
		
		// name
		if (annotationName != null && !annotationName.isEmpty()){
			this.name = annotationName.substring(0,1).toUpperCase() + annotationName.substring(1);
		}else{
			this.name = getNameFromElementName(annotatedElement);
		}
		
		// pattern
		this.pattern = computePattern(annotatedElement, elementUtils);
	}
	
	private String computePattern(Element annotatedElement, Elements elementUtils) throws JeyzerElementInstanciationException{
		String containerPackage =  elementUtils.getPackageOf(annotatedElement).getQualifiedName().toString();
		String name;
		
		if (annotatedElement.getKind()  == ElementKind.CLASS){
			name = getCompositeClassName(annotatedElement, 
					annotatedElement.getSimpleName().toString());
		}
		else if (annotatedElement.getKind()  == ElementKind.CONSTRUCTOR){
			name = getCompositeClassName(annotatedElement.getEnclosingElement(), 
					annotatedElement.getEnclosingElement().getSimpleName().toString())
					 + CONSTRUCTOR_SUFFIX;
		}
		else if (annotatedElement.getKind()  == ElementKind.METHOD){
			name = getCompositeClassName(annotatedElement.getEnclosingElement(), 
					annotatedElement.getEnclosingElement().getSimpleName().toString())
					  + "." + annotatedElement.getSimpleName().toString();
		}
		else if (annotatedElement.getKind()  == ElementKind.PACKAGE){
			name = annotatedElement.getSimpleName().toString(); 
		}
		else{
			throw new JeyzerElementInstanciationException(
					annotatedElement, 
					"Unsupported element type in annotation : " + annotatedElement.getKind().name()
					);
		}
		
		return containerPackage + "." + name;
	}
	
	private String getCompositeClassName(Element annotatedElement, String name){
		Element enclosingElement = annotatedElement.getEnclosingElement();
		
		// inner class case
		if (enclosingElement.getKind() == ElementKind.CLASS){
			name = enclosingElement.getSimpleName().toString() + '$' + name;
			return getCompositeClassName(enclosingElement, name);
		}
		
		return name;
	}

	private String getNameFromElementName(Element annotatedElement) {
		String value = annotatedElement.getSimpleName().toString();
		int index = 1;
		int prevIndex = 0;
		
		if (value.isEmpty())
			return value;
		
		if (CONSTRUCTOR_ELEMENT_NAME.equals(value))
			return annotatedElement.getEnclosingElement().getSimpleName().toString() + " " + CONSTRUCTOR_NAME;
		
		// detect words based on capitalized letter and add spaces in between words
		String result = "";
		boolean start = true;
		while(true){
			if (index == value.length())
				break;
			char letter = value.charAt(index);
			if (Character.isUpperCase(letter)){
				result = result + (start? "" : " ") + value.substring(prevIndex, index).toLowerCase();
				start = false;
				prevIndex = index;
				
			}
			index++;
		}
		
		// keep leftover
		result = result + (start? "" : " ") + value.substring(prevIndex).toLowerCase();
		
		// capitalize first letter
		result = result.substring(0,1).toUpperCase() + result.substring(1);
		
		return result;
	}

}
