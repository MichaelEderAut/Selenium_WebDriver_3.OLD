package com.github.michaelederaut.selenium3.framework;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelectorXp;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.ExtendedCssSelector;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.LinkText;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

public class RemoteWebElementCssS extends RemoteWebElement {
	
	public static class LocatorSelectorCss extends LocatorSelector {
		public String                      S_tag_expected;
		public int                         I_idx_f0;
		public LinkText                    O_lnk_txt;
		public String                      S_prefix;
	
		public Constructor<? extends ByCssS> M_ctor;
		
	    protected static void FV_ctor (
				 final LocatorSelectorCss          PO_O_loc_sel_xp,
				 final String                      PI_S_tag_expected,
				 final LinkText                    PI_O_lnk_txt,
				 final int                         PI_I_idx_f0,
				 final String                      PI_S_prefix,
				 final Constructor<? extends ByCssS> PI_M_ctor) {

			 PO_O_loc_sel_xp.S_tag_expected     =  PI_S_tag_expected;
			 PO_O_loc_sel_xp.O_lnk_txt          =  PI_O_lnk_txt;
			 PO_O_loc_sel_xp.I_idx_f0           =  PI_I_idx_f0;
			 PO_O_loc_sel_xp.S_prefix           =  PI_S_prefix;
			
			 PO_O_loc_sel_xp.M_ctor             = PI_M_ctor;
		 }
		 

		 public LocatorSelectorCss (
			final String         PI_S_locator,
			final LocatorVariant PI_E_locator_variant,
			final DomVectorExtendedSelector   PI_O_using,
			final String         PI_S_tag_expected,
			final LinkText       PI_O_lnk_txt,
			final int            PI_I_idx_f0,
			final String         PI_S_prefix,
			final Constructor<? extends ByCssS> PI_M_ctor) {
			 
		
			super (PI_S_locator, PI_E_locator_variant, PI_O_using);
		    FV_ctor(this, PI_S_tag_expected, PI_O_lnk_txt, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
			return;
		 }
		 
		  public  LocatorSelectorCss (
	     			final Locator        PI_E_locator,
	     			final LocatorVariant PI_E_locator_variant,
	     			final DomVectorExtendedSelector  PI_O_using,
	     			final String         PI_S_tag_expected,
	     			final LinkText       PI_O_lnk_txt,
	     			final int            PI_I_idx_f0,
					final String         PI_S_prefix,
					final Constructor<? extends ByCssS> PI_M_ctor) { 
	    	  
	   		 super (PI_E_locator, PI_E_locator_variant, PI_O_using);
	   		 FV_ctor(this, PI_S_tag_expected, PI_O_lnk_txt, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
	   		 return;
	      }
     }
	
		public static List<WebElement> FAO_find_elements_by_css(
			final ByCssS PI_O_locator) {
		
	    LocatorSelectorCss O_by_locator_css;
	    LinkText O_lnk_txt;
	    XpathGenerators.LocatorVariant E_locator_variant;
		DomOffset  AO_DOM_offset_vector_requested[];
		/* DomVectorExtendedSelector */  ExtendedCssSelector SB_css_equivalent;
		AbstractMap<String, ? extends Object> HO_res_exec;
		
			
		StringBuilder     SB_document_root;	
	    String S_cmd_js_multiple, S_css_unindexed;
		Stack<WebElement> AO_retval_web_element = new Stack<WebElement>();
		
		RemoteWebDriver O_web_driver_parent;
		RemoteWebElement O_res_web_element;
		StringBuilder SB_cmd_js_multiple;
		
		String S_web_driver_parent, S_found_by, S_lnk_txt_comp_operation;
		Object O_res_exec;
		Integer IO_requested_idx_f0;
		long L_nbr_elems_f1;
		int i1, I_requested_idx_f0;
		
		ArrayList<Object> AO_res_exec_elements, AO_res_vectors;
		O_by_locator_css = PI_O_locator.O_loc_sel_css;
		O_lnk_txt = O_by_locator_css.O_lnk_txt;
		AO_DOM_offset_vector_requested = O_by_locator_css.SBO_using.AO_dom_offsets;
		
		SB_cmd_js_multiple = new StringBuilder();
		SB_document_root = RemoteWebElementXp.FS_generate_root_element(AO_DOM_offset_vector_requested);
		
		SB_css_equivalent = (ExtendedCssSelector)O_by_locator_css.SBO_using;
		S_css_unindexed = SB_css_equivalent.FS_get_buffer();
		I_requested_idx_f0 = O_by_locator_css.I_idx_f0;
		if (I_requested_idx_f0 == XpathGenerators.IGNORED_IDX) {
			I_requested_idx_f0 = 0;
		    }
		IO_requested_idx_f0 = Integer.valueOf(I_requested_idx_f0);
		
		// https://www.w3schools.com/jsref/prop_anchor_text.asp
		// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
		// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/match
        // 'The quick brown fox jumps over the lazy dog. It barked.'.match('^.*$')
		// --> object array
		
		E_locator_variant = null;
		S_lnk_txt_comp_operation = null;
		if (O_lnk_txt != null) {
			E_locator_variant = O_lnk_txt.E_variant; 
			if (E_locator_variant == LocatorVariant.regular) {
				S_lnk_txt_comp_operation = "S_lnk_txt === S_comp_patt";
			    }
			else if (E_locator_variant == LocatorVariant.prefix) {
				S_lnk_txt_comp_operation = "S_lnk_txt.startsWith('S_comp_patt')";
			    }
			else if (E_locator_variant == LocatorVariant.regexp) {
				S_lnk_txt_comp_operation = "S_lnk_txt.match('S_comp_patt')";
			    }
			else if (E_locator_variant == LocatorVariant.partial) {
				S_lnk_txt_comp_operation = "S_lnk_txt.includes('S_comp_patt')";
			    }
			else if (E_locator_variant == LocatorVariant.suffix) {
				S_lnk_txt_comp_operation = "S_lnk_txt.endsWith('S_comp_patt')";
			   }
		    }
		if (NavigationUtils.O_rem_drv instanceof InternetExplorerDriver) {
			// inject polyfill here if necessary
			if (E_locator_variant == LocatorVariant.prefix) {  // startsWith()
				// stackoverflow.com/questions/30867172/code-not-running-in-ie-11-works-fine-in-chrome
		        SB_cmd_js_multiple.append(
	    		"if (!String.prototype.startsWith) { " +
                    "String.prototype.startsWith = function(searchString, position) { " +
                       "position = position || 0; " +
                       "return this.indexOf(searchString, position) === position;" +
                       "};}");
			     }
			else if (E_locator_variant == LocatorVariant.partial) {  // includes()
				// stackoverflow.com/questions/31119300/ie11-object-doesnt-support-property-or-method-includes-javascript-window
			   SB_cmd_js_multiple.append(
			   "if (!String.prototype.includes) {" +
                  "String.prototype.includes = function(search, start) { " +
                  "if (typeof start !== 'number') {" +
                      "start = 0; " +
                      "}" +
                  "if (start + search.length > this.length) { " +
                       "return false; " +
                  "} else { " +
                     "return this.indexOf(search, start) !== -1; " +
                  "}};}");
			      }
			 else if (E_locator_variant == LocatorVariant.suffix) { // endsWith()
				 // developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
				  SB_cmd_js_multiple.append(
				  "if (!String.prototype.endsWith) { " +
	                  "String.prototype.endsWith = function(search, this_len) { " + 
		              "if (this_len === undefined || this_len > this.length) { " +
			              "this_len = this.length; " + 
		                  "} " +
		               "return this.substring(this_len - search.length, this_len) === search; " +
	                   "};}");
			      }
		       }
		SB_cmd_js_multiple.append( 
			"var HS_retval = {'elemcount' : 0 , 'AA_vectors' : []}; " +
		    "var I_req_idx_f0 = arguments[0]; " +
			"var AAA_vectors = []; " +
		    "var AA_vectors_interim = []; " +
		    "var i1, I_nbr_elems_f1, I_nbr_elems_interim_f1, I_nbr_elems_interim_f0, AO_vector_iterim, " +
		        "B_add_this_node, " +
		        "S_lnk_txt;" + 
		        "O_elem, O_node_retval; " +
			"var AO_elems = []; " +
		    "I_nbr_elemens_f1 = 0; " + 
		    "AO_elems = document.querySelectorAll(\"" + S_css_unindexed  + "\"); " +
			"if (AO_elems) {" +
	            "I_nbr_elemens_interim_f1 = AO_elems.length;} " +
			    "else { " +
	               "I_nbr_elemens_f1 = 0; " +
			        "} " +
	        //     "HS_retval['elemcount'] = I_nbr_elemens_f1; " +
	             "for (i1 = 0; i1 < I_nbr_elemens_interim_f1; i1++) { " +
				     "O_elem = AO_elems[i1]; " +
	                 "S_lnk_txt = O_elem.text; " +
				     "B_add_this_node = true; "); 
		if (O_lnk_txt != null) {
			SB_cmd_js_multiple.append(
					"if (!(" + S_lnk_txt_comp_operation + ")) {" +
		                "B_add_this_node = false; } ");
		     }   
		            SB_cmd_js_multiple.append(
			        "if (B_add_this_node) {" +
		                "AA_vectors_interim.push([O_elem, S_lnk_txt]); " +
			        "}}" + 
		         "I_nbr_elems_interim_f1 = AA_vectors_interim.length; " + 
			     "I_nbr_elems_interim_f0 = I_nbr_elems_interim_f1 - 1; " +
			     "for (i1 = 0; i1 < I_nbr_elemens_interim_f1; i1++) { " +
		             "B_add_this_node = false;" +
			         "if (I_req_idx_f0 >= 0) { " +
		             "if (i1 == I_req_idx_f0) {" +
		                 "B_add_this_node = true; }" +
		                 "}" +
		             "else if (I_req_idx_f0 == " + XpathGenerators.ALL_IDX + ") { " + 
		                     "B_add_this_node = true; }" +
		             "else if (I_req_idx_f0 == " + XpathGenerators.LAST_IDX + ") { " + 
		                 "if (i1 == I_nbr_elemens_interim_f1) { " +
		                 "B_add_this_node = true; }}" +
		             "if (B_add_this_node) {" +         
		                 "AO_vector_iterim = AA_vectors_interim[i1]; " +
			             "O_node_retval = AO_vector_iterim[0]; " +
		                 "S_lnk_txt = AO_vector_iterim[1]; " +
		                 "AAA_vectors.push([O_node_retval, null, null, null, null, S_lnk_txt, null, null, null, null]); " +
		                 "}} " +
			 "HS_retval = {'elemcount' : I_nbr_elemens_interim_f1, 'vector' : AAA_vectors}; " +
			 "return HS_retval;") ;
		
		S_cmd_js_multiple = SB_cmd_js_multiple.toString();
        O_res_exec = NavigationUtils.O_rem_drv.executeScript(S_cmd_js_multiple, IO_requested_idx_f0);
        HO_res_exec = (AbstractMap<String, ? extends Object>)O_res_exec;
        L_nbr_elems_f1 = (Long)(HO_res_exec.get("elemcount"));
        AO_res_exec_elements = (ArrayList<Object>)(HO_res_exec.get("vector"));
        for (i1 = 0; i1 < L_nbr_elems_f1; i1++) {
        	AO_res_vectors = (ArrayList<Object>)AO_res_exec_elements.get(i1);
        	O_res_web_element   = (RemoteWebElement)AO_res_vectors.get(0);
        	O_web_driver_parent = RemoteWebElementXp.FO_get_parent_driver(O_res_web_element);
			S_web_driver_parent = O_web_driver_parent.toString();
			S_found_by = String.format("[%s] -> %s: %s", S_web_driver_parent, "css selector", S_css_unindexed);
    		RemoteWebElementXp.FV_set_found_by(O_res_web_element, S_found_by);
        	AO_retval_web_element.push(O_res_web_element);
        }
		return AO_retval_web_element; 
		}
	
		
	public static WebElement FO_find_element_by_css(
			final ByCssS PI_O_locator) {
	
	List<WebElement> AO_web_elements;
	int I_nbr_web_elements;
	WebElement O_retval_web_element = null;
	
	AO_web_elements = FAO_find_elements_by_css(PI_O_locator);
	if (AO_web_elements != null) {
		I_nbr_web_elements = AO_web_elements.size();
		if (I_nbr_web_elements >= 1) {
			O_retval_web_element = AO_web_elements.get(0);
		    }
	    }
	
	return O_retval_web_element;
	}
}
