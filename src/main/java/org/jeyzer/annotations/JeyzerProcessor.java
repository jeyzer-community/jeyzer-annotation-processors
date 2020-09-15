package org.jeyzer.annotations;

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

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.SourceVersion;

import org.jeyzer.annotations.error.JeyzerElementInstanciationException;
import org.jeyzer.annotations.model.JeyzerModel;
import org.jeyzer.annotations.model.code.JeyzerExclude;
import org.jeyzer.annotations.model.code.JeyzerExecutor;
import org.jeyzer.annotations.model.code.JeyzerFunction;
import org.jeyzer.annotations.model.code.JeyzerLocker;
import org.jeyzer.annotations.model.code.JeyzerOperation;
import org.jeyzer.annotations.model.name.JeyzerExcludeThreadName;
import org.jeyzer.annotations.model.name.JeyzerExecutorThreadName;
import org.jeyzer.annotations.util.MessageLogger;

@SupportedAnnotationTypes({"org.jeyzer.annotations.*"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class JeyzerProcessor extends javax.annotation.processing.AbstractProcessor{

	private MessageLogger logger;
	private JeyzerGenerator generator;
	private Elements elementUtils;
	private JeyzerModel model = new JeyzerModel(); 
	
	@Override
	public synchronized void init(ProcessingEnvironment env){
		super.init(env);
	    logger = new MessageLogger(env.getMessager());
	    generator = new JeyzerGenerator(env.getFiler(), logger); 
	    elementUtils = processingEnv.getElementUtils();
	}	
	
	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment env) {
		logger.info("Generating the Jeyzer patterns");

        try{	    
        	processFunctions(env);
        	processOperations(env);
        	processLockers(env);
        	processExecutors(env);
        	processExecutorThreadNames(env);
        	processExcludes(env);
        	processExcludeThreadNames(env);
        	
            if (env.processingOver())
            	generator.generatePatterns(model);
        }catch(JeyzerElementInstanciationException ex){
        	logger.error(ex);
        }catch(Exception ex){
        	logger.error(ex);
        }
        return true;
	}
	
	private void processFunctions(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(Function.class)) {
	        JeyzerFunction function = new JeyzerFunction(annotatedElement, elementUtils);
	        model.getFunctions().add(function);
	        logger.info("Function created", annotatedElement);
 	     }
	}
	
	private void processOperations(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(Operation.class)) {
	    	JeyzerOperation operation = new JeyzerOperation(annotatedElement, elementUtils);
	    	if (operation.isLowStack())
	    		model.getLowOperations().add(operation);
	    	else
	    		model.getOperations().add(operation);
	    	logger.info("Operation created", annotatedElement);
 	     }
	}
	
	private void processLockers(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(Locker.class)) {
	    	JeyzerLocker locker = new JeyzerLocker(annotatedElement, elementUtils);
	        model.getLockers().add(locker);
	        logger.info("Locker created", annotatedElement);
 	     }
	}
	
	private void processExecutors(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(Executor.class)) {
	        JeyzerExecutor executor = new JeyzerExecutor(annotatedElement, elementUtils);
	        model.getExecutors().add(executor);
	        logger.info("Executor created", annotatedElement);
 	     }
	}
	
	private void processExecutorThreadNames(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(ExecutorThreadName.class)) {
	        JeyzerExecutorThreadName executorThreadName = new JeyzerExecutorThreadName(annotatedElement);
	        model.getExecutorThreadNames().add(executorThreadName);
	        logger.info("Executor thread name created", annotatedElement);
 	     }
	}
	
	private void processExcludes(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(Exclude.class)) {
	        JeyzerExclude exclude = new JeyzerExclude(annotatedElement, elementUtils);
	        model.getExcludes().add(exclude);
	        logger.info("Exclude created", annotatedElement);
 	     }
	}
	
	private void processExcludeThreadNames(RoundEnvironment env) throws JeyzerElementInstanciationException{
	    for (Element annotatedElement : env.getElementsAnnotatedWith(ExcludeThreadName.class)) {
	    	JeyzerExcludeThreadName excludeThreadName = new JeyzerExcludeThreadName(annotatedElement);
	        model.getExcludeThreadNames().add(excludeThreadName);
	        logger.info("Exclude thread name created", annotatedElement);
 	     }
	}

}
