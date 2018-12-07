package com.github.michaelederaut.selenium3.platform;

import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.StrBuilder;
import org.apache.xpath.axes.DescendantIterator;  // xalan/xalan.2.7.2.jar
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.UnionPathIterator;   // xalan/xalan.2.7.2.jar
import org.apache.xpath.axes.WalkingIteratorSorted;
import org.jaxen.Navigator;
import org.jaxen.dom.DocumentNavigator;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.XPathExpr;
import org.jaxen.saxpath.SAXPathException;

import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.google.common.base.Strings;

import regexodus.Pattern;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
// import org.apache.xpath.axes.LocPathIterator;
// import com.sun.org.apache.xpath.internal.axes.LocPathIterator;

import java.util.List;
import java.util.Objects;

// import org.apache.xpath.XPath;
// import org.apache.xpath.XPathFactory;

// import org.apache.xpath.axes.ChildTestIterator;
// import org.apache.xpath.jaxp.XPathFactoryImpl;


public class XpathConcatenator {

	public static final String S_xpath           = "xpath";
	public static final String S_main_expression = "m_mainExp";
	public static final String S_is_top_level    = "m_isTopLevel";
	public static final String S_root_expr       = "rootExpr";
	public static final String S_re_indexed      = "^\\s*(.*?)\\[\\s*([^\\]]*)\\s*\\]\\s*$";
	public static final Pattern P_indexed        =  Pattern.compile(S_re_indexed);
	public static final String S_re_parenthesed  = "^\\s*\\((.*?)\\)\\s*$";
	public static final Pattern P_parenthesed    =  Pattern.compile(S_re_parenthesed);

	
	public static boolean FB_is_top_level(final String PI_S_xpath) {
		
		RuntimeException       E_rt;
		NullPointerException   E_np;
		ClassCastException     E_class_cast;
		Boolean                B_is_top_level;
		Object                 O_obj;
		javax.xml.xpath.XPath  O_xpath;
		org.apache.xpath.XPath O_xpath_apache;  // must contain field "path"
	//	javax.xml.xpath.XPathExpression O_xpath_apache;
		javax.xml.xpath.XPathFactory  O_xpath_factory;  // 
		XPathExpression   O_xpath_expr;
		DescendantIterator      O_descendant_iterator;
		LocPathIterator         O_loc_path_iterator;
		
		String S_msg_1, S_msg_2;
		boolean B_retval_is_top_level = true;
		
	//	O_xpath = XPathFactory.newInstance().newXPath();
		O_xpath_factory = XPathFactory.newInstance();
		O_xpath = O_xpath_factory.newXPath();
		
		O_xpath_expr = null;
		try {
			O_xpath_expr = O_xpath.compile(PI_S_xpath);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + PI_S_xpath + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
		try {
			O_obj = FieldUtils.readField(O_xpath_expr, S_xpath, true);
		} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field named: \'" + S_xpath + "\' from " + Objects.toString(O_xpath_expr);
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		   }
		
		try {
			O_xpath_apache = (org.apache.xpath.XPath)O_obj;
		} catch (ClassCastException | NullPointerException PI_E_class_cast) {
			S_msg_1 = "Unable to obtain value of field \'" + S_xpath + "\' from " + Objects.toString(O_xpath_expr);
			E_rt = new RuntimeException(S_msg_1, PI_E_class_cast);
			throw E_rt;
		    }
		
		try {
			O_obj = FieldUtils.readField(O_xpath_apache, S_main_expression, true);
		} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field named: \'" + S_main_expression + "\' from " + Objects.toString(O_xpath_apache);
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		   }
		
		if (O_obj instanceof UnionPathIterator) {
			return false;
		    }
		
       if (O_obj instanceof DescendantIterator) {
			O_descendant_iterator = (DescendantIterator)O_obj;
			B_retval_is_top_level = O_descendant_iterator.getIsTopLevel();
		    }
		else if (O_obj instanceof LocPathIterator) {
			O_loc_path_iterator = (LocPathIterator)O_obj;
			B_retval_is_top_level = O_loc_path_iterator.getIsTopLevel();
		   }
		else {
		    S_msg_1 = "Invalid object type \'" + O_obj.getClass().getName() + "\'";
		    E_class_cast = new ClassCastException(S_msg_1);
			S_msg_2 = "Unable to obtain value of field \'" + S_main_expression + "\' from " + Objects.toString(O_xpath_apache);
			E_rt = new RuntimeException(S_msg_2, E_class_cast);
		    }
		
		return B_retval_is_top_level;
	}
	
	
public static String FS_unparenthese(final String PI_S_xpath) {
		
	    GroupMatchResult O_grp_match_result;
		String S_retval_xpath = null;
		
		if (PI_S_xpath == null) {
			return PI_S_xpath;
		    }
		
		O_grp_match_result = RegexpUtils.FO_match(PI_S_xpath, P_parenthesed);
		if (O_grp_match_result.I_array_size_f1 >= 2) {
			S_retval_xpath = O_grp_match_result.AS_numbered_groups[1];
		    }
		else {
			S_retval_xpath = PI_S_xpath;
		    }
		
		return S_retval_xpath;
	}

/**
 * 
 * @param PI_SB_xpath {@link IndexedStrBuilder} contains the <a href="https://www.w3schools.com/xml/xpath_intro.asp"><tt>xpath-expression</tt></a>, whose trailing index has to be removed.
 * @return A {@link String} with trailing index [...] and redundant enclosing parentheses removed.
 * 
 * @author Mr. Michael Eder
 */
public static String FS_unindex(final IndexedStrBuilder PI_SB_xpath) {
	
	RuntimeException E_rt;
	AssertionError E_assert;
	
	int I_starting_pos_of_idx_f0, I_len_xpath_total_f1, I_max_starting_pos_f0;
	String S_msg_1, S_msg_2, S_xpath_raw;
	String S_retval_xpath = null;
	
	if (PI_SB_xpath == null) {
	   return S_retval_xpath;
	   }
	
	I_starting_pos_of_idx_f0 = PI_SB_xpath.I_starting_pos_of_idx;
	S_retval_xpath =  PI_SB_xpath.FS_get_buffer();
	
	if (I_starting_pos_of_idx_f0 < 0) {
	   return S_retval_xpath;
	   }
	
	S_xpath_raw = S_retval_xpath;
	I_len_xpath_total_f1 = StringUtils.length(S_xpath_raw);
	I_max_starting_pos_f0 = I_len_xpath_total_f1 - 3;
	if (I_starting_pos_of_idx_f0 > I_max_starting_pos_f0) {
		S_msg_1 = "Starting position: " + I_starting_pos_of_idx_f0 + " higher than maximum possible value of: " + I_max_starting_pos_f0 + ".";
		E_assert = new AssertionError(S_msg_1);
		S_msg_2 = "Unable to remove trailing index expression from: \'" + S_xpath_raw + "\'";
		E_rt = new RuntimeException(S_msg_2, E_assert);
		throw E_rt;
	    }
	
	S_xpath_raw = S_xpath_raw.substring(0, I_starting_pos_of_idx_f0);
	S_retval_xpath = FS_unparenthese(S_xpath_raw);
	return S_retval_xpath;	
    }

	public static boolean FB_is_parenthesed (final String PI_S_xpath) {
		
		String S_xpath;
		boolean B_retval = false;
		
		S_xpath = StringUtils.trimToEmpty(PI_S_xpath);
		if (S_xpath.startsWith("(")) {
			if (S_xpath.endsWith(")")) {
				B_retval = true;
			    }	
		    }
		return B_retval;
	}

	
	public static boolean FB_is_indexed(
			final String PI_S_xpath, 
			final StrBuilder PO_SB_prefix,
			final StrBuilder PO_SB_index) {
		
		GroupMatchResult O_grp_match_result;
		String S_prefix, S_index, S_xpath_raw;
		
		boolean B_retval = false;
		
		O_grp_match_result = RegexpUtils.FO_match(PI_S_xpath, P_indexed);
		if (O_grp_match_result.I_array_size_f1 >= 3) {
			B_retval = true;
			if (PO_SB_prefix != null) {
				PO_SB_prefix.clear();
				S_prefix = O_grp_match_result.AS_numbered_groups[1];
				PO_SB_prefix.append(S_prefix);
				if (PO_SB_index != null) {
					PO_SB_index.clear();
					S_index =  O_grp_match_result.AS_numbered_groups[2];
					PO_SB_index.append(S_index);
				    }
				 }
		      }
		return B_retval;
	}
	
	public static boolean FB_is_indexed(
			final String PI_S_xpath, 
			final StrBuilder PO_SB_prefix) {
		boolean B_retval;
		
		B_retval = FB_is_indexed(PI_S_xpath, PO_SB_prefix, null);
		return B_retval;
	}
	
	public static boolean FB_is_indexed(
			final String PI_S_xpath) {
		boolean B_retval;
		
		B_retval = FB_is_indexed(PI_S_xpath, null);
		return B_retval;
	}
	
	
	public static boolean FB_is_nested (final String PI_S_xpath) {
		
		RuntimeException E_rt;
		
		XPathExpr           O_xpath_expr;
		org.jaxen.XPath     O_xpath;
		LocationPath        O_location_path;
		Navigator O_navigator;
		List<?> AO_steps;
		Object O_res_xpath_expr, O_res_root_expr;
		
		String S_msg_1;
		int I_nbr_steps_f1;
		
		boolean B_retval_is_nested = false;
		
		if (StringUtils.isBlank(PI_S_xpath)) {
		   return B_retval_is_nested;
		   }
		
		 O_navigator = new DocumentNavigator();
		 O_xpath = null;
		 try {
		    O_xpath = O_navigator.parseXPath(PI_S_xpath);
		} catch (SAXPathException PI_E_parse) {
			S_msg_1 = "Unable to determeine nesting depth of XPath-Expression: \"" + PI_S_xpath + "\"";
			E_rt = new RuntimeException(S_msg_1, PI_E_parse);
			throw E_rt;
		    }
		
		O_res_xpath_expr = null;
		try {
			O_res_xpath_expr = FieldUtils.readField(O_xpath, XpathConcatenator.S_xpath, true);
		} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field named: \'" +  XpathConcatenator.S_xpath + "\' from " + Objects.toString(O_xpath);
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
		 
		 AO_steps = O_location_path.getSteps();
		 I_nbr_steps_f1 = AO_steps.size();
		 if (I_nbr_steps_f1 >= 3) {
			 B_retval_is_nested = true;
		     }
		
		return B_retval_is_nested;
	}
	
	public static boolean FB_needs_parenthesing (
			final String PI_S_xpath,
			final boolean PI_B_for_indexing) {
		boolean B_is_parenthesed, B_is_top_level, B_is_nested;
		
		boolean B_retval = false;
		
		if (StringUtils.isBlank(PI_S_xpath)) {
			return B_retval;
			}
		
		B_is_parenthesed = FB_is_parenthesed(PI_S_xpath);
		if (B_is_parenthesed) {
			return false;	
		   }
		
		if (PI_B_for_indexing) {
			B_is_nested = FB_is_nested(PI_S_xpath);
			if (B_is_nested) {
			   return true;
		    }
		}
		B_is_top_level = FB_is_top_level(PI_S_xpath);
		if (!B_is_top_level) {
		   return true;
		   }
		
		return B_retval;
	}
	
	public static boolean FB_needs_parenthesing (
			final String PI_S_xpath) {
		
		boolean B_retval;
		B_retval = FB_needs_parenthesing(
				PI_S_xpath,
				false);   // not for indexing
		return B_retval;
	}
	
	
	public static String FS_append (
			final String PI_S_xp1, 
			final String PI_S_xp2) {
		
		StringBuffer SB_res_append;
		boolean B_is_blank_1, B_is_blank_2, B_needs_parenthesing_1, B_needs_parenthesing_2, B_needs_slash;
		
		
	    String S_retval = null;
		
	    SB_res_append = new StringBuffer("");
	    B_is_blank_1 = StringUtils.isBlank(PI_S_xp1);
	    B_is_blank_2 = StringUtils.isBlank(PI_S_xp2);
	    
	    if (B_is_blank_1) {
	    	B_needs_parenthesing_1 = false;
	        }
	    else {
	       B_needs_parenthesing_1 = FB_needs_parenthesing(PI_S_xp1);
	       }
	    
	    if (B_is_blank_2) {
	    	B_needs_parenthesing_2 = false;
	        }
	    else {
	    	B_needs_parenthesing_2 = FB_needs_parenthesing(PI_S_xp2);
	        }
	        
	    if (B_needs_parenthesing_1) {
	    	SB_res_append.append("(");
	       }
	    SB_res_append.append(PI_S_xp1);
	    if (B_needs_parenthesing_1) {
	    	SB_res_append.append(")");
	       }
	    
	    if (B_is_blank_1 || B_is_blank_2 || PI_S_xp2.startsWith("/"))   {
	    	B_needs_slash = false;
	        }
	    else {
	    	 B_needs_slash = true;
	         }
	    if (B_needs_slash) {
	    	SB_res_append.append("/");
	        }
	    
	    if (B_needs_parenthesing_2) {
	    	SB_res_append.append("(");
	       }
	    SB_res_append.append(PI_S_xp2);
	    if (B_needs_parenthesing_2) {
	    	SB_res_append.append(")");
	       }
	    
		S_retval = SB_res_append.toString();
		return S_retval;
	}
}
