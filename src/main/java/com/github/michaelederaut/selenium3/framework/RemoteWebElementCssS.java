package com.github.michaelederaut.selenium3.framework;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelectorXp;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
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
		DomOffset  AO_DOM_offset_vector_requested[];
		DomVectorExtendedSelector SB_css_equivalent;
		AbstractMap<String, ? extends Object> HO_res_exec;
		
			
		StringBuilder     SB_document_root;	
	    String S_cmd_js_multiple, S_css_unindexed;
		Stack<WebElement> AO_retval_web_element = new Stack<WebElement>();
		
		RemoteWebDriver O_web_driver_parent;
		RemoteWebElement O_res_web_element;
		Object O_res_exec;
		
		String S_web_driver_parent, S_found_by;
		long L_nbr_elems_f1;
		int i1;
		
		ArrayList<Object> AO_res_exec_elements, AO_res_vectors;
		O_by_locator_css = PI_O_locator.O_loc_sel_css;
		AO_DOM_offset_vector_requested = O_by_locator_css.SBO_using.AO_dom_offsets;
		SB_document_root = RemoteWebElementXp.FS_generate_root_element(AO_DOM_offset_vector_requested);
		
		SB_css_equivalent = O_by_locator_css.SBO_using;
		S_css_unindexed = SB_css_equivalent.FS_get_buffer();
		
		S_cmd_js_multiple = 
				"var HS_retval = {'elemcount' : 0 , 'AA_vectors' : []}; " +
		        "var AA_vectors = []; " +
		        "var i1, I_nbr_elems_f1 ;" +
				"var AO_elems = []; " +
		        "AO_elems = document.querySelectorAll(\"" + S_css_unindexed  + "\"); " +
				"if (AO_elems) {" +
	               "I_nbr_elemens_f1 = AO_elems.length;} " +
			    "else { " +
	               "I_nbr_elemens_f1 = 0; " +
			        "} " +
	             "HS_retval['elemcount'] = I_nbr_elemens_f1; " +
	             "for (i1 = 0; i1 < I_nbr_elemens_f1; i1++) { " +
			     "O_elem = AO_elems[i1]; " +
	             "S_clickable_typeof = typeof(O_elem.click); " +
	             "AA_vectors.push([O_elem, S_clickable_typeof]); " +
			 "}" +  
			 "HS_retval = {'elemcount' : I_nbr_elemens_f1, 'vector' : AA_vectors}; " +
			 "return HS_retval;" ;
        O_res_exec = NavigationUtils.O_rem_drv.executeScript(S_cmd_js_multiple);
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
