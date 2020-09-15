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

import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.jeyzer.annotations.ExcludeThreadName;
import org.jeyzer.annotations.error.JeyzerElementInstanciationException;

public class JeyzerExcludeThreadName extends JeyzerNameBasedElement{
	
	public static final int DEFAULT_SIZE = -1;
	
	private int size;
	
	public JeyzerExcludeThreadName(Element annotatedElement) throws JeyzerElementInstanciationException{
		super(annotatedElement,
				annotatedElement.getAnnotation(ExcludeThreadName.class).name(),
				annotatedElement.getAnnotation(ExcludeThreadName.class).pattern(),
				annotatedElement.getAnnotation(ExcludeThreadName.class).patternRegex(),
				annotatedElement.getAnnotation(ExcludeThreadName.class).priority()
				);
		this.size = annotatedElement.getAnnotation(ExcludeThreadName.class).size(); 
	}
	
	@Override
	protected List<ElementKind> getSupportedElementKinds() {
		return Arrays.asList(
				ElementKind.CLASS);
	}

	public int getSize() {
		return size;
	}
}
