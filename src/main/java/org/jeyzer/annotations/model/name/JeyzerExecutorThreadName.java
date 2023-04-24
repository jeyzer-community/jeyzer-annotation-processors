package org.jeyzer.annotations.model.name;

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

import org.jeyzer.annotations.ExecutorThreadName;
import org.jeyzer.annotations.error.JeyzerElementInstanciationException;

public class JeyzerExecutorThreadName extends JeyzerNameBasedElement{
	
	public JeyzerExecutorThreadName(Element annotatedElement) throws JeyzerElementInstanciationException{
		super(annotatedElement,
				annotatedElement.getAnnotation(ExecutorThreadName.class).name(),
				annotatedElement.getAnnotation(ExecutorThreadName.class).pattern(),
				annotatedElement.getAnnotation(ExecutorThreadName.class).patternRegex(),
				annotatedElement.getAnnotation(ExecutorThreadName.class).priority()
				);
	}
	
	@Override
	protected List<ElementKind> getSupportedElementKinds() {
		return Arrays.asList(
				ElementKind.CLASS);
	}
}
