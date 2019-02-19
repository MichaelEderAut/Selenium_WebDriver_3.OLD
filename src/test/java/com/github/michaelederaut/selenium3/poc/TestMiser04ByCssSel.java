package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;

import com.github.michaelederaut.selenium3.framework.ByCssS;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.LinkText;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import junit.framework.Assert;

public class TestMiser04ByCssSel {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	// @Test
	public static void main(String[] args) {
		IOException E_io;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		FirefoxBinary O_ff_bin;
		
		WebElement  O_web_element_miser_loc_1, O_web_element_miser_loc_2,
					O_web_element_miser_loc_3, O_web_element_miser_loc_4,
					O_web_element_miser_loc_5, O_web_element_miser_loc_6, O_web_element_miser_loc_7, O_web_element_miser_loc_8,
		            O_web_element;
		AbstractMap<String, ? extends Object> HO_res_exec, HS_elem;
//		JavascriptExecutor O_js_exexutor;
		Object O_res_execute_1, O_res_web_element, O_res_vectors;
        ArrayList<Object> AO_res_exec_elements_extended, AO_res_vectors;
     

		Class O_clazz;
		Logger O_logger;
		
		LinkText O_lnk_txt_6, O_lnk_txt_8;
		String S_txt, S_tag,
		S_msg_1, S_msg_2, S_line_out, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle,
		       S_parent_wdw_title, S_cmd_1,
		       S_clazz_name_short, S_clazz_name_full, S_res_xpath, S_xpath_class_names, S_clickable_typeof;
		long L_nbr_elems_f1;
		int i1;
		
		Set<String> AS_sub_wdw_handles;
		
		// HashSet<Class<? extends Throwable>> HO_ignored_exceptions_generic;
	
//		Wait<RemoteWebDriver>  O_waiter_elem_std, O_waiter_elem_long;
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = TestMiser04ByCssSel.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
			
	    E_browser_type = BrowserTypes.FireFox;
//		E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
		
         S_parent_wdw_handle = O_nav_utils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = O_nav_utils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

	List <WebElement> AO_web_elements;

	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(By.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_1);

	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(By.cssSelector(".gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	
	O_web_element_miser_loc_2 = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_2);
	
	O_web_element_miser_loc_3 = NavigationUtils.O_rem_drv.findElement(ByCssS.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_3);
	
	O_web_element_miser_loc_4 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(
			Locator.className, 
			LocatorVariant.regular, 
			"gh_loc_bt", 
			(String)null, // tag
			(LinkText)null, 
			XpathGenerators.IGNORED_IDX, 
			(DomOffset[])null,
			(String)null));  // prefix
	
	O_web_element_miser_loc_5 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(Locator.className, "gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_5);
	
	O_lnk_txt_6 = new LinkText("D", LocatorVariant.prefix);
	O_web_element_miser_loc_6 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc((Locator)null, (String)null, (String)null, O_lnk_txt_6));
	Assert.assertNotNull(O_web_element_miser_loc_6);
	
    O_web_element_miser_loc_7 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.linkText, "Deutschland"));
    // S_txt_content == "Deutschland"
	
	O_lnk_txt_8 = new LinkText("Deutschland");
//	O_lnk_txt_8 = new LinkText("Proshop.at");
	O_web_element_miser_loc_8 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc((Locator)null, (String)null, (String)null, O_lnk_txt_8));
	
	Assert.assertNotNull(O_web_element_miser_loc_8);
	 
	
	S_cmd_1 = "var HS_retval = {'elemcount' : 0 , 'AA_vectors' : []}; " +
			  "var i1, I_nbr_elems_f1, O_elem, S_clickable_typeof; " + 
			  "var AO_elems = []; " +
	          "var AA_vectors = []; " +
			 
			  "var HS_elem_1 = {}; " +
			  "var HS_elem_2 = {}; " +
	          "I_nbr_elemens_f1 = 0; " +
			  "AO_elems = document.querySelectorAll('" + ".xf_check" + "'); " +
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
	O_res_execute_1 = NavigationUtils.O_rem_drv.executeScript(S_cmd_1);
	Assert.assertNotNull(O_res_execute_1);
	HO_res_exec = (AbstractMap<String, ? extends Object>)O_res_execute_1;
	
	L_nbr_elems_f1 = (Long)(HO_res_exec.get("elemcount"));
	AO_res_exec_elements_extended = (ArrayList<Object>)(HO_res_exec.get("vector"));
	for (i1 = 0; i1 < L_nbr_elems_f1; i1++) {
		
		// O_res_web_element = AO_res_exec_elements_extended.get(i1);
	    AO_res_vectors = (ArrayList<Object>)AO_res_exec_elements_extended.get(i1);
	    O_web_element      = (WebElement)AO_res_vectors.get(0);
	    S_clickable_typeof = (String) AO_res_vectors.get(1);
	//	AO_res_vectors = (AbstractMap<String, ? extends Object>)O_res_vectors;
	//	O_web_element = (WebElement)O_res_web_element;
		S_tag = O_web_element.getTagName();
		S_txt = O_web_element.getText();
		S_line_out = String.format("%3d %-5s %-30s click: %-10s", i1, S_tag, S_txt, S_clickable_typeof);
		System.out.println(S_line_out);
	    }
	
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 1:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt", "div"));
	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt", "div"));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 1 (div): " + S_res_xpath);
    

	// Xpath: .//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ')]
	S_xpath_class_names = ".//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ') and " + 
	                          "contains(concat(' ',normalize-space(@class),' '),' mid ')]";
	
	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(ByXp.xpath(S_xpath_class_names));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className(new String[]{"gh_loc_bt", "mid"}, "div"));
    O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(
    		ByXp.loc(
    		  Locator.className, 
    		  new String[]{"gh_loc_bt", "mid"}, 
    		  "div"));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
	O_logger.traceExit();
	return;

	}

}
