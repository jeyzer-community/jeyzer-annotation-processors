package org.jeyzer.annotations.model.code;

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

import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.Elements;

import org.jeyzer.annotations.Function;
import org.jeyzer.annotations.error.JeyzerElementInstanciationException;

public class JeyzerFunction extends JeyzerCodeBasedElement{
	
	public JeyzerFunction(Element annotatedElement, Elements elementUtils) throws JeyzerElementInstanciationException{
		super(annotatedElement, 
				annotatedElement.getAnnotation(Function.class).name(), 
				annotatedElement.getAnnotation(Function.class).priority(),
				elementUtils
				);
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
