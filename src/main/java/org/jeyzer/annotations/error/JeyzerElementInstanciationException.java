package org.jeyzer.annotations.error;

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

public class JeyzerElementInstanciationException extends Exception {

	private static final long serialVersionUID = 6974994670829861760L;
	
	private Element annotatedElement;
	
	public JeyzerElementInstanciationException(Element annotatedElement, String message){
		super(message);
		this.annotatedElement = annotatedElement;
	}
	
	public Element getAnnotatedElement(){
		return this.annotatedElement;
	}
	
	@Override
	public String getMessage() {
		return "Failed to instanciate Jeyzer element : " + super.getMessage();
	}
}
