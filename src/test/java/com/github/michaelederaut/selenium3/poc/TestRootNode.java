package com.github.michaelederaut.selenium3.poc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

// see also
// https://stackoverflow.com/questions/8325872/alternative-to-com-sun-org-apache-xerces-internal-dom-elementimpl

//      com.sun.org.apache.xerces.internal.impl.xs.opti;
// import  com.github.michaelederaut.basics.xml.DefaultNode;

// com.sun.org.apache.xerces.internal.dom.CharacterDataImpl
// import  com.github.michaelederaut.basics.xml.CharacterDataImpl;

// import com.sun.xml.internal.messaging.saaj.soap.impl.TextImpl;

// import com.sun.webkit.dom.HTMLBaseElementImpl;
// import com.sun.webkit.dom.TextImpl; 

// com.sun.org.apache.xerces.internal.impl.xs.opti.NodeImpl
import com.github.michaelederaut.basics.xml.NodeImpl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jaxen.BaseXPath;
import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.DefaultNavigator;
import org.jaxen.FunctionCallException;
import org.jaxen.FunctionContext;
import org.jaxen.JaxenException;
import org.jaxen.XPathFunctionContext;
import org.jaxen.dom.DocumentNavigator;
import org.jaxen.expr.DefaultAbsoluteLocationPath;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.DefaultAllNodeStep;
import org.jaxen.expr.DefaultXPathExpr;
import org.jaxen.expr.XPathExpr;
import org.jaxen.NamespaceContext;
import org.jaxen.Navigator;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.VariableContext;
import org.jaxen.XPath;
import org.jaxen.function.ConcatFunction;
import org.jaxen.saxpath.SAXPathException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.w3c.dom.CharacterData;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;


public class TestRootNode {
	
	@Test
	@SuppressWarnings({"restriction"})
	public static void main(final String argv[]) {
	
	RuntimeException E_rt;	
	XPathExpr            O_xpath_expr;
	DefaultAbsoluteLocationPath O_root_expr;
	LocationPath                O_location_path;
	ContextSupport O_context_support;
	FunctionContext O_xpath_function_context;
	NamespaceContext O_name_space_context;
	VariableContext O_variable_context;
	NodeImpl O_default_node;
	Navigator O_navigator, O_navigator_1, O_navigator_2;
	Iterator O_iterator_1;
	
	ConcatFunction O_concat_function;
	
	BaseXPath O_base_xpath_1, O_base_xpath_2;
	XPath     O_xpath_1, O_xpath_2;
	Context O_context;	
	Object O_res_concat, O_node_visited, O_res_xpath_expr, O_res_root_expr, O_step;
	List<String> AS_arglist;
	List<?>      AO_Steps;
	
	String S_msg_1, S_line_out,
	S_xpath, S_xpath_4, S_base_xpath_1, S_base_xpath_2, S_node_visited;
	int i1, I_nbr_xpaths_f1, I_nbr_steps_f1;
	boolean B_is_top_level_1, B_is_top_level_2, B_is_top_level;
	
	O_concat_function = new ConcatFunction();
		
	String AS_xpath[] = {
		"//a", 	 "//a/b",   ".//b[@id='xyz']",    "b",
		"(//a)", "(//a/b)", "(.//b[@id='xyz'])", "(b)",
		"a | b", "(a | b)", "a/d | b | c", "(a/d | .//b[@id='xyz'] | c)"}; 
	
	 I_nbr_xpaths_f1 = AS_xpath.length;
	 S_line_out = String.format("%-22s %s", "xpath", "is top level");
	 
	 System.out.println(S_line_out);
	 System.out.println("------------------------------------");
	 for (i1 = 0; i1 < I_nbr_xpaths_f1; i1++) {
		 S_xpath = AS_xpath[i1];
		 B_is_top_level =  XpathConcatenator.FB_is_top_level(S_xpath);
		 S_line_out = String.format("%-30s %s",  S_xpath, B_is_top_level);
		 System.out.println(S_line_out);
	 }
	
	 O_navigator_1 = new DocumentNavigator();
	 O_xpath_1 = null;
	 try {
	    O_xpath_1 =	O_navigator_1.parseXPath("//a/b/c");
	} catch (SAXPathException PI_E_parse) {
		PI_E_parse.printStackTrace();
	    }
	
	O_res_xpath_expr = null;
	try {
		O_res_xpath_expr = FieldUtils.readField(O_xpath_1, XpathConcatenator.S_xpath, true);
	} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
		S_msg_1 = "Unable to read field named: \'" +  XpathConcatenator.S_xpath + "\' from " + Objects.toString(O_xpath_1);
		E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
		throw E_rt;
	   }
	
	O_xpath_expr = null;
	try {
		O_xpath_expr = (org.jaxen.expr.XPathExpr)O_res_xpath_expr;
	} catch (ClassCastException | NullPointerException PI_E_class_cast) {
		S_msg_1 = "Unable to obtain value of field \'" + XpathConcatenator.S_xpath + "\' from " + Objects.toString(O_xpath_expr);
		E_rt = new RuntimeException(S_msg_1, PI_E_class_cast);
		throw E_rt;
	    }
	
	O_res_root_expr = null;
	try {
		O_res_root_expr = FieldUtils.readField(O_xpath_expr, XpathConcatenator.S_root_expr, true);
	} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
		S_msg_1 = "Unable to read field named: \'" +  XpathConcatenator.S_root_expr + "\' from " + Objects.toString(O_res_root_expr);
		E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
		throw E_rt;
	   }
	
	 O_location_path = null;
	 try {
		 O_location_path = (LocationPath)O_res_root_expr;
		} catch (ClassCastException | NullPointerException PI_E_class_cast) {
			S_msg_1 = "Unable to obtain value of field \'" + XpathConcatenator.S_root_expr + "\' from " + Objects.toString(O_res_root_expr);
			E_rt = new RuntimeException(S_msg_1, PI_E_class_cast);
			throw E_rt;
		    }
	 
	 AO_Steps = O_location_path.getSteps();
	 I_nbr_steps_f1 = AO_Steps.size();
	 O_step = AO_Steps.get(0);
	
	O_xpath_function_context = XPathFunctionContext.getInstance();
	O_name_space_context = new SimpleNamespaceContext();
	O_variable_context = new SimpleVariableContext();
	O_navigator = new DocumentNavigator();
	O_context_support = new ContextSupport(
			O_name_space_context, 
			O_xpath_function_context, 
			O_variable_context, 
			O_navigator);
	O_context = new Context(O_context_support);

	 O_navigator_1 = O_xpath_1.getNavigator();
	 O_iterator_1 = null;
	// Object contextNode;	 
//	 O_default_node = new HTMLBaseElementImpl(0);
	 O_default_node = new NodeImpl();
//	 O_default_node.setNodeValue(".");
	 try {
		O_iterator_1 = O_navigator_1.getAncestorAxisIterator(O_default_node);
	} catch (UnsupportedAxisException PI_E_unsup_axis) {
		PI_E_unsup_axis.printStackTrace();
	}

	while (O_iterator_1.hasNext()) {
		O_node_visited = O_iterator_1.next();
		S_node_visited = O_node_visited.toString();
		System.out.println(S_node_visited);
	}
	 
//	B_is_top_level_1 = XpathConcatenator.FB_is_top_level(AS_xpath[0]);
//	B_is_top_level_2 = XpathConcatenator.FB_is_top_level(AS_xpath[1]);
	
	AS_arglist = Arrays.asList(new String[] {AS_xpath[0], AS_xpath[2]});
	O_res_concat = null;
	try {
		O_res_concat = O_concat_function.call(O_context, AS_arglist);
	} catch (FunctionCallException PI_E_fc) {
		PI_E_fc.printStackTrace(System.err);
	    }
	S_xpath_4 = Objects.toString(O_res_concat);
	
	O_base_xpath_1 = null;
	O_base_xpath_2 = null;
	try {
		O_base_xpath_1 = new BaseXPath(AS_xpath[0], O_navigator);
	} catch (JaxenException PI_S_jaxen) {
		PI_S_jaxen.printStackTrace();
	    }
	
	try {
		O_base_xpath_2 = new BaseXPath(AS_xpath[1], O_navigator);
	} catch (JaxenException PI_S_jaxen) {
		PI_S_jaxen.printStackTrace();
	    }
	S_base_xpath_1 = O_base_xpath_1.toString();
	S_base_xpath_2 = O_base_xpath_2.toString();
	return;
	 }
}
