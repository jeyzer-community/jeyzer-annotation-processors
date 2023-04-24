package org.jeyzer.annotations.error;

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

public class JeyzerFileGenerationException extends Exception  {

	private static final long serialVersionUID = 4703074197762430910L;

	public JeyzerFileGenerationException(String message){
		super(message);
	}
	
}
