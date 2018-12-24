package com.github.michaelederaut.selenium3.framework;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
// import java.util.List;
import java.util.Vector;
// import java.util.Stack;
import java.util.function.BiFunction;

import com.google.common.base.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Wait;

import org.apache.commons.lang3.mutable.MutableObject;

import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;
import com.github.michaelederaut.selenium3.sitelib.RemoteWebDrivers;


import regexodus.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

// TODO
// http://toolsqa.com/mobile-automation/appium/appium-tutorial

/**
 *  @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium">Mr. Michael Eder</a>
 */
public class NavigationUtils {
	
	public static final int I_timeout_click_dflt = 30000; // ms
	public static final int I_polling_interval_click = 200; // ms, min = 10 ms
	
	public static final BiFunction<String, String, Boolean> FB_accecpt_all_urls = 
			(final String PI_S_url_requested,
			 final String PI_S_url_received) -> {
		return Boolean.TRUE;
	};
	
	public static final BiFunction<String, String, Boolean> FB_accecpt_non_blank_urls = 
			(final String PI_S_url_requested,
			 final String PI_S_url_received) -> {
				 
		if (StringUtils.isBlank(PI_S_url_received)) {
			return Boolean.FALSE;
		    }
		else {
			return Boolean.TRUE;
			}
	};
	
	public static final BiFunction<String, String, Boolean> FB_accecpt_exact_equ_urls =
		(final String PI_S_url_requested,
		 final String PI_S_url_received) -> {
		
			Boolean B_retval_equ;
			
			B_retval_equ = StringUtils.equals(PI_S_url_requested, PI_S_url_received);
			
			return B_retval_equ;
	};
	
	public static final BiFunction<String, String, Boolean> FB_accecpt_equ_hostnames =
		(final String PI_S_url_requested,
		 final String PI_S_url_received) -> {
		
			AssertionError E_assert;
			RuntimeException E_rt;
			URL O_url_req, O_url_rec;
			String S_msg_1, S_host_name_req, S_host_name_rec;
			
			Boolean B_retval_equ = (Boolean)null;
			
			if (StringUtils.isBlank(PI_S_url_requested)) {
				return B_retval_equ;
			   } 
			
			if (StringUtils.isBlank(PI_S_url_received)) {
				return B_retval_equ;
			    }
			
			try {
				O_url_req = new URL(PI_S_url_requested);
			} catch (MalformedURLException PI_E_malf_url) {
				S_msg_1 = "Requested URL \"" + PI_S_url_requested +  "\" malformed";
				E_rt = new RuntimeException(S_msg_1, PI_E_malf_url);
				throw E_rt;
			    }
			
			try {
				O_url_rec = new URL(PI_S_url_received);
			} catch (MalformedURLException PI_E_malf_url) {
				PI_E_malf_url.printStackTrace(System.out);
				return B_retval_equ;
			    }
			
			S_host_name_req = O_url_req.getHost();
			if (StringUtils.isBlank(S_host_name_req)) {
				S_msg_1 = "Illegal requested hostname: \'"  + S_host_name_req + 
						  "\' extracted from URL \"" + PI_S_url_requested + "\".";
				E_assert = new AssertionError(S_msg_1);
				throw E_assert;
			    }
			
			S_host_name_rec = O_url_rec.getHost();
			if (StringUtils.isBlank(S_host_name_rec)) {
				S_msg_1 = "Illegal received hostname: \'"  + S_host_name_rec + 
						  "\' extracted from URL \"" + PI_S_url_received + "\".";
				E_assert = new AssertionError(S_msg_1);
				throw E_assert;
			    }
			
			B_retval_equ = S_host_name_req.equals(S_host_name_rec);
			return B_retval_equ;
	};
	
	/**
	 * @author Mr. Michael Eder
	 * @see <a href=https://developer.mozilla.org/en-US/docs/Web/API/Document/readyState>Document.ready.State</a> 
	 */
    public enum RequestedDocumentState { loading, interactive, complete };
        
    /**
	 * 
	 * @author Mr. Michael Eder
	 *
	 *@see <a href=https://coderwall.com/p/b1o2gw/getting-a-list-of-your-eventlisteners-so-you-can-trigger-it-programmatically>trigger event programmatically</a><p>
	 * <code>
	 * EventTarget.dispatchEvent method, like for example:<br>
     * btn=document.getElementById('buttonx');<br>
     * btn.dispatchEvent(new MouseEvent('click')); // this will make any click event listener on the button to be invoked<br>
     * you can also use (new Event('click') simply)<br>
	 * </code>
	 */
    public enum ClickMode {click, dispatchMouseEvent, dispatchEvent};
    public enum ReadyState {loading, interactive, complete};
    
    public static class ClickResult {
    	public Boolean B_click_performed;
    	public String  S_err_msg;
    	public String  S_click_func;
    	public ReadyState E_ready_state = null;
    	public long       L_elapsed_time = -1L;
    	public String S_url_after;
    	public ArrayList<String[]> AAS_attrs;
    }
    
	public static final int I_max_wait_entries = 10;
	public static final String S_keyword_click_done  = "click_done";
	public static final String S_keyword_ready_state = "ready_state";
	public static final String S_keyword_url_after = "url_after";
	public static final String S_keyword_elapsed_time = "elapsed_time";
	
	public static RemoteWebDriver O_rem_drv;
	public static Waiters HO_waiters = null;
	public static Actions O_driver_actions;
	private static final String S_marker_xpath = " -> xpath: ";
	
	private static WebElement O_web_element_exchange;
	
	public NavigationUtils(final BrowserTypes PI_E_browser_type) {
		Class<? extends RemoteWebDriver> OT_webdriver;
		OT_webdriver = PI_E_browser_type.OT_webdriver;
		
		O_rem_drv = RemoteWebDrivers.FO_webdriver_factory(OT_webdriver);
		NavigationUtils.O_driver_actions = new Actions(O_rem_drv);
	}
	
	public static class FluentWaitKey {
		
		public FluentWaitKey(
				final int PI_I_timeout,
				final int PI_I_polling_interval
				) {
			this.I_timeout_in_ms = PI_I_timeout;
			this.I_polling_in_ms = PI_I_polling_interval;
		}
		
		@Override
		public int hashCode() {
			
			int I_retval_hash;
			I_retval_hash = this.I_timeout_in_ms ^ this.I_polling_in_ms;
			return I_retval_hash;
		}
		
		public int I_timeout_in_ms;
		public int I_polling_in_ms;
	
	   @Override
	   public boolean equals(final Object PI_O_key) {
		 FluentWaitKey  O_key_other;
		 boolean O_retval_equals = false;
		 O_key_other = (FluentWaitKey)PI_O_key;
		 
		 if (this.I_timeout_in_ms ==  O_key_other.I_timeout_in_ms) {
		    if (this.I_polling_in_ms ==  O_key_other.I_polling_in_ms) {
		    	O_retval_equals = true;
		        }
		    }
		 return O_retval_equals;
	   }
	}
	   
   public static class Waiters extends LinkedHashMap<FluentWaitKey, Wait<RemoteWebDriver>> {
	   public int I_max_entires;
	   public  FluentWaitKey O_last_fluent_wait_key = null;
	   public   Wait<RemoteWebDriver> O_last_waiter;
	   
	   public Waiters(final int PI_I_max_entires) {
		   super();
		   this.I_max_entires = PI_I_max_entires;
	   }
	   	   
	   public Waiters (final int PI_I_timeout,
			           final int PI_I_polling_interval,
			           final int PI_I_max_entries) {
		  super(PI_I_timeout); 
		  
		  this.FB_contains(PI_I_timeout, PI_I_polling_interval, true);
	   }

	   public boolean FB_contains(final int PI_I_timeout,
			                      final int PI_I_polling_interval,
			                      final boolean PI_B_create_if_not_extant) {
			                     
		   boolean B_retval_contains;
		  
		   B_retval_contains = false;
		   this.O_last_fluent_wait_key = new FluentWaitKey(
				   PI_I_timeout, PI_I_polling_interval);
		   B_retval_contains = this.containsKey(this.O_last_fluent_wait_key);
		   if (!B_retval_contains) {
		      if (PI_B_create_if_not_extant) {
		    	  this.O_last_waiter = WaiterFactory.FO_create_waiter_elem(
		    			  NavigationUtils.O_rem_drv,
		    			  PI_I_timeout, 
		    			  PI_I_polling_interval, 
		    			  WaiterFactory.HO_ignored_exceptions_generic);
		    	  this.put(this.O_last_fluent_wait_key, this.O_last_waiter);
		       }
		   }
		   return B_retval_contains;
	   }
	   
   }  // END Waiters
	
   public static final String S_re_locator_alternatives = 
		   "class name|css selector|id|link text|name|" + XpathGenerators.S_partial_link_text + "|tag name|" + Locator.xpath.name();
   public static final String S_re_found_by_infix = "\\]) \\-> (" + S_re_locator_alternatives + ")\\: (.*";
	  
   public static final String S_re_found_by = "^\\[(((?:\\[\\[)*)(.*?))(?:\\)" + S_re_found_by_infix + "?)$";
   public static final Pattern PE_found_by = Pattern.compile(S_re_found_by);
   
   public static final String S_re_found_by_last_ele = "(.*)((\\]" + S_re_found_by_infix + "?))$";
   public static final Pattern PE_found_by_last_ele = Pattern.compile(S_re_found_by_last_ele);
   public static final int I_nbr_required_matching_groups_f1 = 6;
   
   protected static void FV_add_loc_sel (
		final Vector<LocatorSelector> PO_AO_loc_sel,
		final MutableObject<String> PB_SB_tail) {
	   
	   AssertionError E_assert;
	   
	   LocatorSelector O_loc_sel;
	   GroupMatchResult O_grp_match_result;
	   String S_msg_1, S_tail, S_locator, S_selector, S_prefix, AS_matching_groups[];
	   
	   int I_nbr_matching_groups_f1;
	   
	   S_tail = PB_SB_tail.toString();
	   O_grp_match_result = RegexpUtils.FO_match(S_tail, PE_found_by_last_ele);
	   AS_matching_groups = O_grp_match_result.AS_numbered_groups;
	   I_nbr_matching_groups_f1 = O_grp_match_result.I_array_size_f1;
       if (I_nbr_matching_groups_f1 < I_nbr_required_matching_groups_f1) { // 6
		  S_msg_1 = "The tauk of the found-by-string:\n" + 
		     S_tail + "\n" + 
             "Doesn't match the regular Expression: \'" + S_re_found_by + "\'\n" +
             "Number: required matching groups:" + I_nbr_required_matching_groups_f1 + " Number of qchieved matches: " +
             I_nbr_matching_groups_f1;
		 E_assert = new AssertionError(S_msg_1);
		 throw E_assert;
		 } 
	  
	   S_prefix =   AS_matching_groups[1];
	   S_locator =  AS_matching_groups[4];
	   S_selector = AS_matching_groups[5];
	   O_loc_sel = new LocatorSelector(S_locator, S_selector);
	   PO_AO_loc_sel.insertElementAt(O_loc_sel, 0);
	   PB_SB_tail.setValue(S_prefix);
	   return; 
   }
   
   public static String FS_get_xpath (
		   WebElement PI_O_web_element) {
	   
    	String S_retval_xpath;
    	
    	S_retval_xpath = FS_get_xpath(
    			PI_O_web_element,
    			(Vector<LocatorSelector>)null,
    			(Vector<String>)null);
    	
    	return S_retval_xpath;
    }
   
    public static String FS_get_xpath (
		   WebElement PI_O_web_element,
		   Vector<LocatorSelector> PO_AO_loc_sel) {
    	String S_retval_xpath;
    	
    	S_retval_xpath = FS_get_xpath(
    			PI_O_web_element,
    			PO_AO_loc_sel,
    			(Vector<String>)null
    			);
    	
    	return S_retval_xpath;
    }
   
   public static String FS_get_xpath (
		   WebElement PI_O_web_element,
		   Vector<LocatorSelector> PO_AO_loc_sel,
		   Vector<String> PO_AS_xpath
		   ) {
		  
	      AssertionError E_assert;
	      IllegalArgumentException E_ill_arg;
	      RuntimeException E_rt;
	      
	      Vector<LocatorSelector> AO_loc_sel;
	      Vector<String> AS_xpath;
	      LocatorSelector O_loc_sel;
		  RemoteWebElementXp O_remote_web_element_xp;
		  RemoteWebElement   O_remote_web_element;
		  GroupMatchResult O_grp_match_res;
		  MutableObject<String> SB_suffix;
		  DomVectorExtendedSelector SBO_xpath;
		  String S_msg_1, S_msg_2, S_found_by, S_suffix, S_locator_leftmost, S_left_square_brackets,
		  S_selector, S_selector_leftmost, S_xpath, S_xpath_old;
		  int i1, i2, I_nbr_matching_groups_f1, I_nbr_left_square_brackets_f1, I_nbr_iterations_required, I_offset_loc_sel_idx_f1;
		 
		  String S_retval_xpath = null;
		  
		  if (PI_O_web_element instanceof RemoteWebElementXp) {
			  O_remote_web_element_xp = (RemoteWebElementXp)PI_O_web_element;
			  S_retval_xpath = O_remote_web_element_xp.SB_xpath_cummulated.toString();
			  return S_retval_xpath;
		      }
		  if (PI_O_web_element instanceof RemoteWebElement) {
			  O_remote_web_element = (RemoteWebElement)PI_O_web_element;
			  S_found_by = RemoteWebElementXp.FS_get_found_by(O_remote_web_element);			  
			  O_grp_match_res = RegexpUtils.FO_match(S_found_by, PE_found_by);
			  try {
			      I_nbr_matching_groups_f1 = O_grp_match_res.I_array_size_f1;
				  if (I_nbr_matching_groups_f1 < I_nbr_required_matching_groups_f1) { // 6
					  S_msg_1 = "The found-by-string:\n" + 
				                S_found_by + "\n" + 
				                "Doesn't match the regular Expression: \'" + S_re_found_by + "\'\n" +
				                "Number: required matching groups:" + I_nbr_required_matching_groups_f1 + " Number of qchieved matches: " +
				                I_nbr_matching_groups_f1;
					  E_assert = new AssertionError(S_msg_1);
					  throw E_assert;
				     }
				  S_left_square_brackets = O_grp_match_res.AS_numbered_groups[2];
				  I_nbr_left_square_brackets_f1 = S_left_square_brackets.length();
			  
				  if ((I_nbr_left_square_brackets_f1 & 1) != 0) {
					  S_msg_1 = "Number of opening square brackets: " + I_nbr_left_square_brackets_f1 + " not even.";
					  E_assert = new AssertionError(S_msg_1);
					  throw E_assert;
				      }
				  I_nbr_iterations_required = I_nbr_left_square_brackets_f1 / 2;
				  S_locator_leftmost        = O_grp_match_res.AS_numbered_groups[4];
				  S_suffix                  = O_grp_match_res.AS_numbered_groups[5];
				  if (PO_AO_loc_sel == null) {
				      AO_loc_sel                = new Vector<LocatorSelector>();
				      I_offset_loc_sel_idx_f1   = 0;
				      }
				  else { 	
					  AO_loc_sel = PO_AO_loc_sel;
					  I_offset_loc_sel_idx_f1 = AO_loc_sel.size();
				      }
				  if (PO_AS_xpath == null) {
					  AS_xpath = new Vector<String>();
				      } 
				  else {
					  AS_xpath = PO_AS_xpath;
				      }
				  SB_suffix = new MutableObject<String>(S_suffix);
				  for (i1 = 0; i1 < I_nbr_iterations_required; i1++) {
					  FV_add_loc_sel(AO_loc_sel, SB_suffix);
				      }
				  
				  S_selector_leftmost = SB_suffix.getValue();
				  O_loc_sel = new LocatorSelector(S_locator_leftmost, S_selector_leftmost);
				  AO_loc_sel.insertElementAt(O_loc_sel, 0);
				  SBO_xpath = XpathGenerators.FSBO_get_xpath(O_loc_sel);
				  S_retval_xpath = SBO_xpath.FS_get_buffer();
				  AS_xpath.add(S_retval_xpath);
				  for (i1 = 1; i1 <= I_nbr_iterations_required; i1++) {
					  i2 = i1 + I_offset_loc_sel_idx_f1;
					  O_loc_sel = AO_loc_sel.get(i2);
					  SBO_xpath = XpathGenerators.FSBO_get_xpath(O_loc_sel);
					  S_xpath = SBO_xpath.FS_get_buffer();
					  AS_xpath.add(S_xpath);
					  S_xpath_old = S_retval_xpath; // for debugging purpose only
					  S_retval_xpath = XpathConcatenator.FS_append(S_retval_xpath, S_xpath);
				      }
			  } catch (final Throwable PI_E_cause) {
				  S_msg_2 = "Unable to obain a valid xpath from the found-by-string:\n" +    
				  S_found_by;
				  E_rt = new RuntimeException (S_msg_2, PI_E_cause);
				  throw E_rt;
			      }
			  }	
		  else {
			 S_msg_1 = "Unsupported datatype: \'" + PI_O_web_element.getClass().getName() + "\'"; 
			 E_ill_arg = new IllegalArgumentException(S_msg_1);
			 S_msg_2 = "Unable to get xpath out of element";
			 E_rt = new RuntimeException(S_msg_1, E_ill_arg);
			 throw E_rt;
		     }
		  return S_retval_xpath;
	  }   
  
  public static ClickResult FO_click_js(
		   final WebElement PI_O_web_element) {
	  
	   ClickResult O_retval_click_result;
	   
	   O_retval_click_result = FO_click_js(
			   PI_O_web_element,
			   ClickMode.click,
			   ReadyState.complete,
			   I_timeout_click_dflt);
	   return O_retval_click_result;
  }
   
   public static ClickResult FO_click_js(
		   final WebElement PI_O_web_element,
		   final ClickMode  PI_E_click_mode) {
	  
	   ClickResult O_retval_click_result;
	   
	   O_retval_click_result = FO_click_js(
			   PI_O_web_element,
			   PI_E_click_mode,
			   ReadyState.complete,
			   I_timeout_click_dflt);
	   return O_retval_click_result;
  }
   
    public static ClickResult FO_click_js(
		   final WebElement PI_O_web_element,
		   final ReadyState PI_E_ready_state_required) {
	  
	   ClickResult O_retval_click_result;
	   
	   O_retval_click_result = FO_click_js(
			   PI_O_web_element,
			   ClickMode.click,
			   PI_E_ready_state_required,
			   I_timeout_click_dflt);
	   return O_retval_click_result;
  }
  
    public static ClickResult FO_click_js(
		   final WebElement PI_O_web_element,
		   final ClickMode  PI_E_click_mode,
		   final ReadyState PI_E_ready_state_required ) {
	  
	   ClickResult O_retval_click_result;
	   
	   O_retval_click_result = FO_click_js(
			   PI_O_web_element,
			   PI_E_click_mode,
			   PI_E_ready_state_required,
			   I_timeout_click_dflt);
	   return O_retval_click_result;
    }
    
    public static ClickResult FO_click_js(
		   final WebElement PI_O_web_element,
		   final int  PI_I_timeout) {
	  
	   ClickResult O_retval_click_result;
	   
	   O_retval_click_result = FO_click_js(
			   PI_O_web_element,
			   ClickMode.click,
			   ReadyState.complete,
			   PI_I_timeout);
	   return O_retval_click_result;
  }

   public static ClickResult FO_click_js(
		   final WebElement PI_O_web_element,
		   final ReadyState PI_E_ready_state_required,
		   final int  PI_I_timeout) {
	  
	   ClickResult O_retval_click_result;
	   
	   O_retval_click_result = FO_click_js(
			   PI_O_web_element,
			   ClickMode.click,
			   PI_E_ready_state_required,
			   PI_I_timeout);
	   return O_retval_click_result;
  }  
    
   
  public static ClickResult FO_click_js(
		  final WebElement  PI_O_web_element,
		  final ClickMode   PI_E_click_mode,
		  final ReadyState  PI_E_ready_state_required,
		  final int         PI_I_timeout_max) {
	   
	   NullPointerException     E_np;
	   IllegalArgumentException E_ill_arg;
	   
	   DomOffset  AO_DOM_offset_vector[]; 
	   AbstractMap<String, ? extends Object> HO_res_exec;
	   	
	   ReadyState E_ready_state;
	   String S_xpath, S_cmd, S_web_element, S_msg_1;
	   int    I_time_elapsed;
	   StringBuilder SB_document_root; // context-node
	 
	   ClickResult O_retval_click_result = new ClickResult();
	   
	  if (PI_O_web_element == null) {
		 S_msg_1 = "Argument for WebElement must not be null here.";
		 E_np = new NullPointerException(S_msg_1);
		 throw E_np;
	     }
	  
	  if (PI_O_web_element instanceof RemoteWebElementXp) {
		 DomVectorExtendedSelector SBO_xpath = ((RemoteWebElementXp)PI_O_web_element).SBO_xpath;
		 S_xpath = SBO_xpath.FS_get_buffer();
		 AO_DOM_offset_vector = SBO_xpath.AO_dom_offsets;
	     }
	  else {  // WebElement only
		 AO_DOM_offset_vector = null;
		 S_xpath = FS_get_xpath(PI_O_web_element);
	     }
	  
	  SB_document_root = RemoteWebElementXp.FS_generate_root_element(AO_DOM_offset_vector); 
	
	  if (StringUtils.isEmpty(S_xpath)) {
		  S_xpath = ".";
	     }
	  else if (StringUtils.isBlank(S_xpath)) {
	     S_web_element = PI_O_web_element.toString();
	     S_msg_1 = "Unable to extract Xpath from " + PI_O_web_element.getClass().getName() +
			     " \"" + S_web_element + "\"";
	     E_ill_arg = new IllegalArgumentException(S_msg_1);
	     throw E_ill_arg;
	         }	  
	  
// document.readyState: loading, interactive, complete 
	   String S_ready_state_required = PI_E_ready_state_required.name() ;
	 
//	   	   S_cmd = "window.HS_retval = {}; " +
//               "window.S_ready_state_required = '" + PI_S_ready_state_required + "'; " +
//			   
//	   		   "function FHS_click() {" +
//	     //    "var HS_retval = {}; " + 
//		           "var i1, I_time_stamp_current, I_time_elapsed; " +
//		   		   "var O_clickable_node; " + 
//		           "var S_ready_state; " + 
//	               "var S_log_1, S_log_cumm = '', S_typeof_click = null, " + 
//		   		   "    AO_attrs  = [], O_attr, I_nbr_attrs_f1, " +
//	               "    AAS_attrs = [], AS_attr = [], S_attr_name, S_attr_val, " +
//	               "S_onclick = null, F_onclick; " +
//	               "var I_phase_f0 = arguments[0]; " + 
//	               "var PI_S_click_mode = '" + PI_E_click_mode.name() + "'; " +
//	               
//	               "window.HS_retval['" + S_keyword_click_done + "'] = '0';" +
//	               "I_time_stamp_current = new Date().getTime(); " +
//	               "I_time_elapsed = I_time_stamp_current - window.I_time_stamp_start; " + 
//	               "window.HS_retval['" + S_keyword_elapsed_time + "'] = I_time_elapsed; " +
//	               "if (I_time_elapsed > " + PI_I_timeout_max + ") { " +
//	                   "window.clearInterval(window.I_interv_id); " +	               
//	                   "return; } " +
//	               "S_ready_state = document.readyState; " +
//	               "console.log('ready-state: ' + S_ready_state); " +
//	               "window.HS_retval['ready_state'] = S_ready_state; " +
//	               "if (S_ready_state == 'loading') {" + 
//	                   "if (window.S_ready_state_required == 'complete') { " +
//	                      "return; }" +
//	                   "if (window.S_ready_state_required == 'interactive') { " +
//	                      "return; }}" +
//	                "else if (S_ready_state == 'interactive') {" +   
//	                    "if (window.S_ready_state_required == 'complete') { " +
//	                        "return; }}" +
//	                "else if (S_ready_state != 'complete') {" +
//	                   "return; }  " +
//		   		   "O_clickable_node = document.evaluate(\"" + 
//		            S_xpath + 
//		           "\", " + SB_document_root + ", null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " +
//		           "if (O_clickable_node) { " +
//		               "window.HS_retval['node_found'] = '1'; " +
//		               "S_log_1 = 'Clickable Node: ' + O_clickable_node.toString(); " +
//		//                "console.log(S_log_1); " +
//		               "S_log_cumm += S_log_1; " +
//		               "AO_attrs = O_clickable_node.attributes; " +
//		               "I_nbr_attrs_f1 = AO_attrs.length; " +
//	                   "AAS_attrs = []; " +
//	     	           "for (i2 = 0; i2 < I_nbr_attrs_f1; i2++) { " +
//		                     "O_attr = AO_attrs[i2]; " +
//		                     "S_attr_name  = O_attr.name; " +
//		                     "S_attr_value = O_attr.value; " +
//	    //       	            "console.log('name: ' + S_attr_name + ' - value: ' + S_attr_value); " +
//		                     "AS_attr = [S_attr_name, S_attr_value]; " +
//		                     "AAS_attrs.push(AS_attr); " +
//		                     "} " +
//		               "S_typeof_click = typeof(O_clickable_node.click); " +
//		               "if (PI_S_click_mode === '" + ClickMode.click.name() + "') {" +
//			                "try {" +
//			                   "O_clickable_node.click(); " +
//		//	                   " S_log_cumm += ' - .click() done';" +
//			                   "window.HS_retval['" + S_keyword_click_done + "'] = '1';" +
//		                       "window.HS_retval['" + S_keyword_url_after  + "'] = window.location.href; " +
//			                   "I_time_stamp_current = new Date().getTime(); " +
//	                           "I_time_elapsed = I_time_stamp_current - window.I_time_stamp_start; " + 
//	                           "window.HS_retval['" + S_keyword_elapsed_time + "'] = I_time_elapsed; " +
//			                "} catch(PI_E_err) {" +
//			                   "S_log_cumm += ' - Error: ' + PI_E_err; " +
//			                   "window.HS_retval['" + S_keyword_click_done + "'] = '0'; }" +
//			                  "}" +
//		                "else { " +
//			                "if (PI_S_click_mode === '" + ClickMode.dispatchEvent.name() + "') {" +  
//			                    "var O_evt = new Event('click'); " +
//			                    "window.HS_retval['" + S_keyword_click_done + "'] = '1'; " +
//			                    "window.HS_retval['" + S_keyword_url_after  + "'] = window.location.href; " + 
//			                     "} " +
//		                    "else {" +  
//			                   "var O_evt = new MouseEvent('click');} " +
//		                    "try {" + 
//		                        "O_clickable_node.dispatchEvent(O_evt); " +  
//		                        "I_time_stamp_current = new Date().getTime(); " +
//	                            "I_time_elapsed = I_time_stamp_current - window.I_time_stamp_start; " + 
//	                            "window.HS_retval['" + S_keyword_elapsed_time + "'] = I_time_elapsed; " +
//		                        "} catch(PI_E_err) {" +
//		                         "S_log_cumm += ' - Error: ' + PI_E_err; " +
//		                         "window.HS_retval['" + S_keyword_click_done + "'] = '0'; " +
//		                         "}" +		
//		                      "}" +
//			              "} " +
//		            "else {" +
//		                "S_log_cumm = 'Element not found'; " +
//		                "window.HS_retval['node_found'] = '0';} " +
//		                 
//		            "window.HS_retval['log'] = S_log_cumm; " +
//		            "window.HS_retval['typeof_click'] = S_typeof_click; " + 
//		            "window.HS_retval['attributes'] = AAS_attrs; " +
//		            "window.HS_retval['onclick'] = S_onclick; " +
//		            "if (I_phase_f0 > 0) {" +
//		                "window.clearInterval(window.I_interv_id); " +
//		                "console.log('cleared interval-id: ' + window.I_interv_id); " + 
//		                "} " +
//	                "return; } " +
//		            
//	              "window.I_time_stamp_start = new Date().getTime(); " +
//		          "FHS_click(0); " +
//		          "window.I_interv_id = window.setInterval(FHS_click, " + I_polling_interval_click +  ", 1);" +
//                  "console.log('interval-id: ' + window.I_interv_id); " + 
//		          "return window.HS_retval;" ;
	   
	   S_cmd = "window.HS_retval = {}; " +
               "window.S_ready_state_required = '" + S_ready_state_required + "'; " +
			   
	   		   "function FHS_click() {" +
		           "var i1, I_time_stamp_current, I_time_elapsed; " +
		   		   "var O_clickable_node; " + 
		           "var S_ready_state; " + 
	               "var S_log_1, S_log_cumm = '', S_typeof_click = null, " + 
		   		   "    AO_attrs  = [], O_attr, I_nbr_attrs_f1, " +
	               "    AAS_attrs = [], AS_attr = [], S_attr_name, S_attr_val, " +
	               "S_onclick = null, F_onclick; " +
	               "var I_phase_f0 = arguments[0]; " + 
	               "var PI_S_click_mode = '" + PI_E_click_mode.name() + "'; " +
	               
	               "window.HS_retval['" + S_keyword_click_done + "'] = '0';" +
	               "I_time_stamp_current = new Date().getTime(); " +
	               "I_time_elapsed = I_time_stamp_current - window.I_time_stamp_start; " + 
	               "window.HS_retval['" + S_keyword_elapsed_time + "'] = I_time_elapsed; " +
	               "if (I_time_elapsed > " + PI_I_timeout_max + ") { " +
	                   "window.clearInterval(window.I_interv_id); " +	               
	                   "return; } " +
	               "S_ready_state = document.readyState; " +
	               "console.log('ready-state: ' + S_ready_state); " +
	               "window.HS_retval['ready_state'] = S_ready_state; " +
	               "if (S_ready_state == 'loading') {" + 
	                   "if (window.S_ready_state_required == 'complete') { " +
	                      "return; }" +
	                   "if (window.S_ready_state_required == 'interactive') { " +
	                      "return; }}" +
	                "else if (S_ready_state == 'interactive') {" +   
	                    "if (window.S_ready_state_required == 'complete') { " +
	                        "return; }}" +
	                "else if (S_ready_state != 'complete') {" +
	                   "return; }  " +
		   		   "O_clickable_node = document.evaluate(\"" + 
		            S_xpath + 
		           "\", " + SB_document_root + ", null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " +
		           "if (O_clickable_node) { " +
		               "window.HS_retval['node_found'] = '1'; " +
		               "S_log_1 = 'Clickable Node: ' + O_clickable_node.toString(); " +
		//                "console.log(S_log_1); " +
		               "S_log_cumm += S_log_1; " +
		               "AO_attrs = O_clickable_node.attributes; " +
		               "I_nbr_attrs_f1 = AO_attrs.length; " +
	                   "AAS_attrs = []; " +
	     	           "for (i2 = 0; i2 < I_nbr_attrs_f1; i2++) { " +
		                     "O_attr = AO_attrs[i2]; " +
		                     "S_attr_name  = O_attr.name; " +
		                     "S_attr_value = O_attr.value; " +
	    //       	            "console.log('name: ' + S_attr_name + ' - value: ' + S_attr_value); " +
		                     "AS_attr = [S_attr_name, S_attr_value]; " +
		                     "AAS_attrs.push(AS_attr); " +
		                     "} " +
		               "S_typeof_click = typeof(O_clickable_node.click); " +
		               "if (PI_S_click_mode === '" + ClickMode.click.name() + "') {" +
			                "try {" +
			                   "O_clickable_node.click(); " +
		//	                   " S_log_cumm += ' - .click() done';" +
			                   "window.HS_retval['" + S_keyword_click_done + "'] = '1';" +
		                       "window.HS_retval['" + S_keyword_url_after  + "'] = window.location.href; " +
			                   "I_time_stamp_current = new Date().getTime(); " +
	                           "I_time_elapsed = I_time_stamp_current - window.I_time_stamp_start; " + 
	                           "window.HS_retval['" + S_keyword_elapsed_time + "'] = I_time_elapsed; " +
			                "} catch(PI_E_err) {" +
			                   "S_log_cumm += ' - Error: ' + PI_E_err; " +
			                   "window.HS_retval['" + S_keyword_click_done + "'] = '0'; }" +
			                  "}" +
		                "else { " +
			                "if (PI_S_click_mode === '" + ClickMode.dispatchEvent.name() + "') {" +  
			                    "var O_evt = new Event('click'); " +
			                    "window.HS_retval['" + S_keyword_click_done + "'] = '1'; " +
			                    "window.HS_retval['" + S_keyword_url_after  + "'] = window.location.href; " + 
			                     "} " +
		                    "else {" +  
			                   "var O_evt = new MouseEvent('click');} " +
		                    "try {" + 
		                        "O_clickable_node.dispatchEvent(O_evt); " +  
		                        "I_time_stamp_current = new Date().getTime(); " +
	                            "I_time_elapsed = I_time_stamp_current - window.I_time_stamp_start; " + 
	                            "window.HS_retval['" + S_keyword_elapsed_time + "'] = I_time_elapsed; " +
		                        "} catch(PI_E_err) {" +
		                         "S_log_cumm += ' - Error: ' + PI_E_err; " +
		                         "window.HS_retval['" + S_keyword_click_done + "'] = '0'; " +
		                         "}" +		
		                      "}" +
			              "} " +
		            "else {" +
		                "S_log_cumm = 'Element not found'; " +
		                "window.HS_retval['node_found'] = '0';} " +
		                 
		            "window.HS_retval['log'] = S_log_cumm; " +
		            "window.HS_retval['typeof_click'] = S_typeof_click; " + 
		            "window.HS_retval['attributes'] = AAS_attrs; " +
		            "window.HS_retval['onclick'] = S_onclick; " +
		            "if (I_phase_f0 > 0) {" +
		                "window.clearInterval(window.I_interv_id); " +
		                "console.log('cleared interval-id: ' + window.I_interv_id); " + 
		                "} " +
	                "return; } " +
		            
	              "window.I_time_stamp_start = new Date().getTime(); " +
	              "console.log('trace-1'); " +
		          "FHS_click(0); " +
	              "console.log('trace-2'); " +
		          "window.I_interv_id = window.setInterval(FHS_click, " + I_polling_interval_click +  ", 1);" +
                  "console.log('interval-id: ' + window.I_interv_id); " + 
		          "return window.HS_retval;" ;
	  
	   HO_res_exec = (AbstractMap<String, ? extends Object>)NavigationUtils.O_rem_drv.executeScript(S_cmd);
	   
	   String S_click_done, S_ready_state, S_url_after;
	   Long   L_time_elapsed;
	   Boolean B_click_done;
	   ArrayList<ArrayList<String>> AALS_attrs;
	   ArrayList<String[]> AAS_attrs = new ArrayList<String[]>();
	   
	   S_click_done =  (String)HO_res_exec.get(S_keyword_click_done);
	
	   switch (S_click_done) {
	   case "0": B_click_done  = false; break;
	   case "1": B_click_done  = true;  break;
	   default :  B_click_done = null;  break;
	   }
	   O_retval_click_result.B_click_performed = B_click_done;
	   
	   S_ready_state = (String)HO_res_exec.get(S_keyword_ready_state);
	   try {
		   E_ready_state = ReadyState.valueOf(S_ready_state);
	   } catch (IllegalArgumentException | NullPointerException PI_E_ill_arg) {
		   E_ready_state = null;
	   }
	   O_retval_click_result.E_ready_state = E_ready_state;
	   
	   S_url_after = (String)HO_res_exec.get(S_keyword_url_after);
	   O_retval_click_result.S_url_after = S_url_after;
	
	   // java.lang.ClassCastException
	   L_time_elapsed = (Long)HO_res_exec.get(S_keyword_elapsed_time);
	   if (L_time_elapsed != null) {
	      O_retval_click_result.L_elapsed_time = L_time_elapsed; }
	  
	   AALS_attrs = (ArrayList<ArrayList<String>>)HO_res_exec.get("attributes");
	   for (ArrayList<String> ALS_attrs: AALS_attrs) {
		   String AS_attrs[] = new String[]{ALS_attrs.get(0), ALS_attrs.get(1)};
		   AAS_attrs.add(AS_attrs);   
	       }
	   
	   O_retval_click_result.AAS_attrs = AAS_attrs;
	   return O_retval_click_result;
   }
  
 
  public Actions FO_click_act(final WebElement PI_O_web_element) {
	  Action  O_act;
	  Actions O_retval_actions = null;
	  
	  O_retval_actions = this.O_driver_actions.moveToElement(PI_O_web_element);
	  O_retval_actions = O_retval_actions.click(PI_O_web_element);
	  O_act = O_retval_actions.build();
	  O_act.perform();
	  PI_O_web_element.sendKeys(StringUtils.EMPTY);
	  O_retval_actions = O_retval_actions.sendKeys(PI_O_web_element, StringUtils.EMPTY);
	  O_act = O_retval_actions.build();
	  O_act.perform();
	 
	  return O_retval_actions;
      }
  
  
  public Actions FO_sendkeys_act(
		  final WebElement PI_O_web_element, 
		  final CharSequence PI_S_content) {
    Actions O_retval_actions;
    
    O_retval_actions = this.FO_sendkeys_act(
    		PI_O_web_element,
    		PI_S_content,
    		false);  // append = false
    
    return O_retval_actions;
  }
	  
  public Actions FO_sendkeys_act(
		  final WebElement PI_O_web_element, 
		  final CharSequence PI_S_content,
		  final boolean BI_P_append) {
	  Action  O_act;
	  Actions O_retval_actions;
	  
	  O_retval_actions = FO_click_act(PI_O_web_element);
	  if (! BI_P_append) {
	      PI_O_web_element.clear();
	      }
	  O_retval_actions = O_retval_actions.sendKeys(PI_O_web_element, StringUtils.EMPTY);
	  O_act = O_retval_actions.build();
	  O_act.perform();
	  O_retval_actions = O_retval_actions.sendKeys(PI_O_web_element, PI_S_content);
	  O_act = O_retval_actions.build();
	  O_act.perform();
	  
	  return O_retval_actions;
  }
  
//   public void FO_get_fluent(
//		   final String PI_S_url,
//		   final int PI_I_timeout,
//           final int PI_I_polling_interval,
//           final boolean PI_B_requires_equal_url) {
//	 
//	   Wait<RemoteWebDriver> O_waiter;
//	   FluentWaitKey O_fluent_wait_key;
//	   boolean B_found_key;
//		
//	   if (NavigationUtils.HO_waiters == null) {
//			NavigationUtils.HO_waiters = new Waiters(
//					PI_I_timeout,
//					PI_I_polling_interval,
//					NavigationUtils.I_max_wait_entries);
//			O_waiter = NavigationUtils.HO_waiters.O_last_waiter;
//		   }
//		else {
//			B_found_key = NavigationUtils.HO_waiters.FB_contains(
//					PI_I_timeout, 
//					PI_I_polling_interval, 
//					true); // create wait-object if not yet exists
//			if (B_found_key) {
//				O_fluent_wait_key = NavigationUtils.HO_waiters.O_last_fluent_wait_key;
//				O_waiter = NavigationUtils.HO_waiters.get(O_fluent_wait_key);
//			    }
//			else {
//				O_waiter = NavigationUtils.HO_waiters.O_last_waiter;
//			    }
//		    }
//	   
//	   
//	   O_waiter.until(new Function<RemoteWebDriver, Boolean>() {
//		   public Boolean apply (RemoteWebDriver PI_O_remote_web_driver)  {
//			   
//			   MalformedURLException E_mal_url;
//			   URL                   O_url_actual;               
//			   String                S_url_actual;
//			   boolean               B_url_is_equal;
//			   
//			   boolean               B_retval_url_ok = false;
//			   
//			   PI_O_remote_web_driver.get(PI_S_url);
//			   S_url_actual = PI_O_remote_web_driver.getCurrentUrl();
//			   if (StringUtils.isBlank(S_url_actual)) {
//				   return B_retval_url_ok;
//			       }
//			   
//			   if (PI_B_requires_equal_url) {
//				   B_retval_url_ok = S_url_actual.equalsIgnoreCase(PI_S_url);
//				   return B_retval_url_ok;
//				  };
//			   
//			   try {
//					O_url_actual = new URL(S_url_actual);
//					B_retval_url_ok = true; 
//				} catch (MalformedURLException PI_E_malformed_url) {
//				    return B_retval_url_ok;
//				}
//			     
//			    return B_retval_url_ok;
//		        }
//		      }
//	   ); 	   
//   }  
   
   public void FO_get_fluent(
		   final String PI_S_url_requested,
		   final int PI_I_timeout,
           final int PI_I_polling_interval,
           final BiFunction<String, String, Boolean> FB_equal_urls) {
	 
	   Wait<RemoteWebDriver> O_waiter;
	   FluentWaitKey O_fluent_wait_key;
	   boolean B_found_key;
		
	   if (NavigationUtils.HO_waiters == null) {
			NavigationUtils.HO_waiters = new Waiters(
					PI_I_timeout,
					PI_I_polling_interval,
					NavigationUtils.I_max_wait_entries);
			O_waiter = NavigationUtils.HO_waiters.O_last_waiter;
		   }
		else {
			B_found_key = NavigationUtils.HO_waiters.FB_contains(
					PI_I_timeout, 
					PI_I_polling_interval, 
					true); // create wait-object if not yet exists
			if (B_found_key) {
				O_fluent_wait_key = NavigationUtils.HO_waiters.O_last_fluent_wait_key;
				O_waiter = NavigationUtils.HO_waiters.get(O_fluent_wait_key);
			    }
			else {
				O_waiter = NavigationUtils.HO_waiters.O_last_waiter;
			    }
		    }
	   
	   
	   O_waiter.until(new Function<RemoteWebDriver, Boolean>() {
		   public Boolean apply (RemoteWebDriver PI_O_remote_web_driver)  {
			   
		   MalformedURLException E_mal_url;
		   URL                   O_url_actual;               
		   String                S_url_actual;
		   boolean               B_url_is_equal;
		   
		   Boolean               B_retval_url_ok;
		   
		   PI_O_remote_web_driver.get(PI_S_url_requested);
		   S_url_actual = PI_O_remote_web_driver.getCurrentUrl();
		   
		   B_retval_url_ok = FB_equal_urls.apply(PI_S_url_requested, S_url_actual);
		   return B_retval_url_ok;
		   }
		   }
	   ); 	   
   }    
  
   public void FO_get_fluent(
		   final String PI_S_url,
		   final int PI_I_timeout,
           final int PI_I_polling_interval) {
	   
	  this.FO_get_fluent(PI_S_url, 
			  PI_I_timeout, 
			  PI_I_polling_interval, 
			  FB_accecpt_exact_equ_urls);
	  return;    
   }
   
   public WebElement FO_find_fluent(
		 //  final SearchContext PI_O_locator,
		   final ByXp PI_O_locator,
		   final int PI_I_timeout,
           final int PI_I_polling_interval) {
		
		WebElement O_retval_web_element;
		FluentWaitKey O_fluent_wait_key;
		Wait<RemoteWebDriver> O_waiter;
		
		String S_retval_handle;
		boolean B_found_key;
		
		O_retval_web_element = null;
	
		if (NavigationUtils.HO_waiters == null) {
			NavigationUtils.HO_waiters = new Waiters(
					PI_I_timeout,
					PI_I_polling_interval,
					NavigationUtils.I_max_wait_entries);
			O_waiter = NavigationUtils.HO_waiters.O_last_waiter;
		   }
		else {
			B_found_key = NavigationUtils.HO_waiters.FB_contains(
					PI_I_timeout, 
					PI_I_polling_interval, 
					true); 
			if (B_found_key) {
				O_fluent_wait_key = NavigationUtils.HO_waiters.O_last_fluent_wait_key;
				O_waiter = NavigationUtils.HO_waiters.get(O_fluent_wait_key);
			    }
			else {
				O_waiter = NavigationUtils.HO_waiters.O_last_waiter;
			}
		    }
		// TODO popups
		
		// final String S_xpath_final = S_xpath;
	//	final XpathExtendedLocator O_locator = new XpathExtendedLocator(PI_O_locator, PI_S_using);
	//	final ByXp O_locator = new ByXp(PI_O_locator);
		 S_retval_handle =  NavigationUtils.O_rem_drv.getWindowHandle();
		
		  O_waiter.until(new Function<RemoteWebDriver, WebElement>() {
			  public WebElement apply (RemoteWebDriver PI_O_web_driver) {
				  NavigationUtils.O_web_element_exchange =
						  NavigationUtils.O_rem_drv.findElement(PI_O_locator);
				  return  O_web_element_exchange;
			      }
			  }
	     );
		
		 O_retval_web_element =  NavigationUtils.O_web_element_exchange;
		 return O_retval_web_element;
	
         }
}