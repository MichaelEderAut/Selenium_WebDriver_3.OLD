package com.github.michaelederaut.selenium3.platform;

import java.util.ArrayList;

import org.apache.commons.text.TextStringBuilder;

import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

import static org.apache.commons.lang3.StringUtils.LF;

/**
* @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;CssSelector-Generators">Mr. Michael Eder</a>
* 
*/
public class CssSGenerators {
	
	public static final String DEFAULT_PREFIX = "";
	public static final String EMPTY_PREFIX  = DEFAULT_PREFIX;
	
	public static class LinkText {
		public String         S_selector;  // using
		public LocatorVariant E_variant;
		
		public LinkText() {
			this.E_variant      = LocatorVariant.regular;
			return;
		}
		
		public LinkText (final String using) {
			this.S_selector     = using;
			this.E_variant      = LocatorVariant.regular;
		    }
		
		public LinkText (
				final String using,
				final LocatorVariant PI_E_variant) {
			this.S_selector     = using;
			this.E_variant      = PI_E_variant;
		    }
		
	}
	
	public static class ExtendedCssSelector extends DomVectorExtendedSelector	{
	   LinkText O_lnk_txt;
		
	}
	
	public static ExtendedCssSelector FSBO_get_csss (
		final LocatorEnums PI_O_locator_enums,
		final String    PI_AS_using[], 
		final String    PI_S_tag, 
		final LinkText  PI_O_link_text,
		final int       PI_I_idx_f0,
		final String    PI_S_prefix,
		final DomOffset PI_AO_dom_offsets[]) {
		
		RuntimeException         E_rt;
	    IllegalArgumentException E_ill_arg;
	    NullPointerException     E_np;
	    AssertionError           E_assert;
	    GroupMatchResult         O_grp_match_result;
	
	    String  S_msg_1, S_msg_2, S_csss, S_prefix, S_attr, S_locator, S_attr_name_prefix, S_using;
	    Locator        E_locator;
	    LocatorVariant E_locator_variant;

	    if (PI_O_locator_enums == null) {
			S_msg_1 = "1st argument of type \'" + LocatorEnums.class.getName() + "\' must not be null.";
			E_np = new NullPointerException(S_msg_1);
			S_msg_2 = "Unable to create new instance of: \'" + DomVectorExtendedSelector.class.getName() + "\'";
			E_rt = new RuntimeException(S_msg_2 ,E_np) ;
		    throw E_rt;
	    }
	
	    E_locator_variant = PI_O_locator_enums.E_locator_variant;
	    if (E_locator_variant == LocatorVariant.or) {
			  S_msg_1 = "Implementation restriction. For boolean operand " + E_locator_variant.name() + " in selector " + LF +
					    "use class: " + ByXp.Loc.class.getName() + " instead.";
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
			  throw E_ill_arg;
			  } 
	    
	  if (PI_AS_using == null) {
		  S_msg_1 = "2nd argument for selector(s) of type \'" + String[].class.getName() + "\' must not be null.";
		   E_np = new NullPointerException(S_msg_1);
		   S_msg_2 = "Unable to create new instance of: \'" + DomVectorExtendedSelector.class.getName() + "\'";
		   E_rt = new RuntimeException(S_msg_2 ,E_np) ;
		   throw E_rt;
	       }
	
	O_grp_match_result = RegexpUtils.FO_match(PI_S_tag, XpathGenerators.P_tag_name);
	if (O_grp_match_result.I_array_size_f1 == 0) { 
    	S_msg_1 = "Invalid value for tag: \'" + PI_S_tag + "\'";
    	E_ill_arg = new IllegalArgumentException(S_msg_1);
    	throw E_ill_arg;
        }
	
	DomVectorExtendedSelector SBO_retval_xpath = new DomVectorExtendedSelector("");
	
	int I_nbr_selectors_f1, I_nbr_selectors_f0, i1;
	StringBuffer SB_csss_tail;
	
	I_nbr_selectors_f1 = PI_AS_using.length;
	
	if (I_nbr_selectors_f1 == 0) {
		S_msg_1 = "Size of array containing class-names must not be " + I_nbr_selectors_f1;
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }
	
	
	E_locator         = PI_O_locator_enums.E_locator;
	
	switch (E_locator) {
	   case className:
		  ArrayList<String>        AS_class_names;
		  TextStringBuilder        SB_csss;
		  int                      I_idx_old_f0;  
		
		  AS_class_names = new ArrayList<String>();		
		  if (I_nbr_selectors_f1 == 1) {
		     E_locator_variant = LocatorVariant.regular;   
	         }	
		  else if (E_locator_variant == LocatorVariant.regular) {
			  if (I_nbr_selectors_f1 > 1) {
				E_locator_variant = LocatorVariant.and;
			    }
		      }
		  else if (E_locator_variant != LocatorVariant.and) {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		  for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {   // check for duplicate class names
		      S_using = PI_AS_using[i1];
		    //--- https://benfrain.com/when-and-where-you-can-use-numbers-in-id-and-class-names/
		    // ^\p{Alpha}[\w\-]*$
		    O_grp_match_result = RegexpUtils.FO_match(S_using, XpathGenerators.P_clazz_name);
		    if (O_grp_match_result.I_array_size_f1 == 0) { 
		        S_msg_1 = "Class-name parameter " + i1 + " \'" + S_using + "\' must be alpha-numeric with - or _";
		        E_ill_arg = new IllegalArgumentException(S_msg_1);
		        throw E_ill_arg;
		        }
		    I_idx_old_f0 = AS_class_names.indexOf(S_using);
		    if (I_idx_old_f0 > 0) {
		       S_msg_1 = "Duplicate class-name parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
		       E_ill_arg = new IllegalArgumentException(S_msg_1);
		       throw E_ill_arg;
		       }
		    AS_class_names.add(S_using);
		    }
		  I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		    
		  SB_csss = new TextStringBuilder(PI_S_prefix + PI_S_tag);

	      for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {
		     S_using = PI_AS_using[i1];
		     SB_csss.append("." + S_using);
		   
	         }
	      S_csss = SB_csss.toString();        
		   
	      break; // classname
	  }
	return (ExtendedCssSelector)null;
	}

}
