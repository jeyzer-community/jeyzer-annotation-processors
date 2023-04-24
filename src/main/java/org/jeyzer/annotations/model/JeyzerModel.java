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

import java.util.ArrayList;
import java.util.List;

import org.jeyzer.annotations.model.code.JeyzerExclude;
import org.jeyzer.annotations.model.code.JeyzerExecutor;
import org.jeyzer.annotations.model.code.JeyzerFunction;
import org.jeyzer.annotations.model.code.JeyzerLocker;
import org.jeyzer.annotations.model.code.JeyzerOperation;
import org.jeyzer.annotations.model.name.JeyzerExcludeThreadName;
import org.jeyzer.annotations.model.name.JeyzerExecutorThreadName;

public class JeyzerModel {
	
	private List<JeyzerFunction> functions = new ArrayList<>();
	private List<JeyzerOperation> operations = new ArrayList<>();
	private List<JeyzerOperation> lowOperations = new ArrayList<>();
	private List<JeyzerLocker> lockers = new ArrayList<>();
	private List<JeyzerExecutor> executors = new ArrayList<>();
	private List<JeyzerExecutorThreadName> executorThreadNames = new ArrayList<>();
	private List<JeyzerExclude> excludes = new ArrayList<>();
	private List<JeyzerExcludeThreadName> excludeThreadNames = new ArrayList<>();
	
	public List<JeyzerFunction> getFunctions() {
		return functions;
	}
	
	public List<JeyzerOperation> getOperations() {
		return operations;
	}
	
	public List<JeyzerOperation> getLowOperations() {
		return lowOperations;
	}
	
	public List<JeyzerLocker> getLockers() {
		return lockers;
	}
	
	public List<JeyzerExecutor> getExecutors() {
		return executors;
	}
	
	public List<JeyzerExecutorThreadName> getExecutorThreadNames() {
		return executorThreadNames;
	}
	
	public List<JeyzerExclude> getExcludes() {
		return excludes;
	}

	public List<JeyzerExcludeThreadName> getExcludeThreadNames() {
		return excludeThreadNames;
	}	
	
}
