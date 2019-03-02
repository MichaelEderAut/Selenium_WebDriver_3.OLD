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
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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

// import junit.framework.Assert;
import org.junit.Assert;

public class TestMiser04ByCssSel {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	// @Test
	public static void main(String[] args) {
		IOException E_io;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		FirefoxBinary O_ff_bin;
		
		List<WebElement> AO_web_element_miser_loc_1_01, AO_web_element_miser_loc_1_02,
		                 AO_web_element_miser_loc_1_03, AO_web_element_miser_loc_1_04;
		WebElement  O_web_element_miser_loc_01, O_web_element_miser_loc_02,
					O_web_element_miser_loc_03, O_web_element_miser_loc_04,
					O_web_element_miser_loc_05, O_web_element_miser_loc_06, O_web_element_miser_loc_07,
					O_web_element_miser_loc_08,
					O_web_element_miser_loc_09, O_web_element_miser_loc_10,
					O_web_element_miser_loc_11_1, O_web_element_miser_loc_11_2, 
					O_web_element_miser_loc_11_3, O_web_element_miser_loc_11_4,
					O_web_element_miser_loc_11_5, O_web_element_miser_loc_11_6,
					O_web_element_miser_loc_11_7, O_web_element_miser_loc_11_8,
					O_web_element_miser_loc_12_1, O_web_element_miser_loc_12_2,
					O_web_element_miser_loc_13, O_web_element_miser_loc_14, O_web_element_miser_loc_15,
					
					O_web_element_miser_loc_16_1, O_web_element_miser_loc_16_2, O_web_element_miser_loc_16_3,
					O_web_element_miser_loc_16_4, 
					O_web_element_miser_loc_17_1,
					O_web_element_miser_loc_18_1,
					O_web_element_miser_loc_19_1, O_web_element_miser_loc_20_1, O_web_element_miser_loc_21_1,
					O_web_element_miser_loc_22_1, O_web_element_miser_loc_23_1,
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
		       S_xpath,
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
	    O_nav_utils = new NavigationUtils(E_browser_type); 
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
		
         S_parent_wdw_handle = O_nav_utils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = O_nav_utils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

	List <WebElement> AO_web_elements;

	O_web_element_miser_loc_01 = NavigationUtils.O_rem_drv.findElement(By.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_01);

	O_web_element_miser_loc_01 = NavigationUtils.O_rem_drv.findElement(By.cssSelector(".gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_01);
	
	AO_web_element_miser_loc_1_01 = NavigationUtils.O_rem_drv.findElements(By.className("m"));
	Assert.assertNotNull(AO_web_element_miser_loc_1_01);
	
	AO_web_element_miser_loc_1_02 = NavigationUtils.O_rem_drv.findElements(By.cssSelector(".m"));
	Assert.assertNotNull(AO_web_element_miser_loc_1_02);
	
	AO_web_element_miser_loc_1_03 = NavigationUtils.O_rem_drv.findElements(By.className("n"));
	Assert.assertNotNull(AO_web_element_miser_loc_1_03);
	
	AO_web_element_miser_loc_1_04 = NavigationUtils.O_rem_drv.findElements(By.cssSelector(".n"));
	Assert.assertNotNull(AO_web_element_miser_loc_1_04);
	
	O_web_element_miser_loc_02 = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_02);
	
	O_web_element_miser_loc_03 = NavigationUtils.O_rem_drv.findElement(ByCssS.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_03);
	
	O_web_element_miser_loc_04 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(
			Locator.className, 
			LocatorVariant.regular, 
			"gh_loc_bt", 
			(String)null, // tag
			(LinkText)null, 
			XpathGenerators.IGNORED_IDX, 
			(DomOffset[])null,
			(String)null));  // prefix
	
	O_web_element_miser_loc_05 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(Locator.className, "gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_05);
	
	O_lnk_txt_6 = new LinkText("D", LocatorVariant.prefix);
	O_web_element_miser_loc_06 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc((Locator)null, (String)null, (String)null, O_lnk_txt_6));
	Assert.assertNotNull(O_web_element_miser_loc_06);
	
    O_web_element_miser_loc_07 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.linkText, "Deutschland"));
    // S_txt_content == "Deutschland"
	
	O_lnk_txt_8 = new LinkText("Deutschland");
//	O_lnk_txt_8 = new LinkText("Proshop.at");
	O_web_element_miser_loc_08 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc((Locator)null, (String)null, (String)null, O_lnk_txt_8));
	Assert.assertNotNull(O_web_element_miser_loc_08);
	 
	try {
		O_web_element_miser_loc_10 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(
				Locator.xpath, LocatorVariant.regular, "//a[@title=Geizhals]"));  // to-csss conversion ERROR Geizhals has no surrounding quotes
	} catch (RuntimeException PI_E_rt) {
		System.out.println("error trying xpath: " + "//a[@title=Geizhals]");
		PI_E_rt.printStackTrace(System.err);
	    }
		
	try {
		O_web_element_miser_loc_11_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[title=Geizhals]"));     // @ missing before attr-name
	} catch (NoSuchElementException | IllegalArgumentException PI_E_nse) {
		System.out.println("error trying xpath: " + "//a[title=Geizhals]");
		PI_E_nse.printStackTrace(System.err);
	}
	try {
		O_web_element_miser_loc_11_2 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[@title=Geizhals]"));    // missing quotes 
	} catch (NoSuchElementException PI_E_nse) {
		System.out.println("error trying xpath: " + "//a[@title=Geizhals]");
		PI_E_nse.printStackTrace(System.err);
	    }
	
	try {
		O_web_element_miser_loc_11_3 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[title='Geizhals']"));     // @ missing before attr-name
	} catch (NoSuchElementException PI_E_nse) {
		System.out.println("error trying xpath: " + "//a[title=Geizhals]");
		PI_E_nse.printStackTrace(System.err);
	    }
	O_web_element_miser_loc_11_4 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[@title='Geizhals']"));    // OK 

	try {
		O_web_element_miser_loc_11_5 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[title=\"Geizhals\"]"));   // @ missing before attr-name
	} catch (NoSuchElementException PI_E_nse) {
			System.out.println("error trying xpath: " + "//a[title=\"Geizhals\"]");
		    PI_E_nse.printStackTrace(System.err);
	    }
	O_web_element_miser_loc_11_6 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[@title=\"Geizhals\"]"));  // OK 
	
	try {
		O_web_element_miser_loc_11_7 = NavigationUtils.O_rem_drv.findElement(By.xpath("//a[@title=\'Geizhals\"]"));  // different left and right quotes 
	} catch (InvalidSelectorException PI_E_inv_sel) {
		System.out.println("error trying xpath: " + "//a[@title=\'Geizhals\"]");
		PI_E_inv_sel.printStackTrace(System.err);
	    }
	
	O_web_element_miser_loc_12_1 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(
			Locator.xpath, LocatorVariant.regular, "//a[@title='Geizhals']"));   // OK
	
	try {
		O_web_element_miser_loc_12_2 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc( // should crash here, bcs xpath is invalid, missing @ before attr-name
				Locator.xpath, LocatorVariant.regular, "//a[title='Geizhals']"));
	} catch (RuntimeException PI_E_rt) {
		System.out.println("error trying xpath: " + "//a[title='Geizhals']");
		PI_E_rt.printStackTrace();
	    } 
	
	O_web_element_miser_loc_13 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(
			Locator.xpath, LocatorVariant.regular, "//a[@title=\"Geizhals\"]")); 
	
	try {
		O_web_element_miser_loc_14 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc( // should crash here, bcs xpath is invalid, different left and right quotes
				Locator.xpath, LocatorVariant.regular, "//a[@title='Geizhals\"]"));
	} catch (RuntimeException PI_E_rt) {
		System.out.println("error trying xpath: " + "//a[@title='Geizhals\"]");
		PI_E_rt.printStackTrace();
	    } 
	
	try {
		O_web_element_miser_loc_15 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc( // should crash here, bcs xpath is invalid, different left and right quotes
				Locator.xpath, LocatorVariant.regular, "//a[@title=\"Geizhals']"));
	} catch (RuntimeException PI_E_rt) {
		System.out.println("error trying xpath: " + "//a[@title=\"Geizhals']");
		PI_E_rt.printStackTrace();
	} 
	
	try {
		O_web_element_miser_loc_16_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("id(fs)"));  // crashes fs is not surrounded by quotes
	} catch (NoSuchElementException PI_E_nse) {
		System.out.println("error trying xpath: " + "id(fs)");
		PI_E_nse.printStackTrace();
	    }
	
	O_web_element_miser_loc_16_2 = NavigationUtils.O_rem_drv.findElement(By.xpath("id('fs')"));  // OK
	
	try {
		O_web_element_miser_loc_16_3 = NavigationUtils.O_rem_drv.findElement(By.xpath("id(gh_searchform)"));  // crashes here
	} catch (NoSuchElementException PI_E_nse) {
		System.out.println("error trying xpath: " + "id(gh_searchform)");
		PI_E_nse.printStackTrace();
	    }

	O_web_element_miser_loc_16_4 = NavigationUtils.O_rem_drv.findElement(By.xpath("id('gh_searchform')"));  // OK
	
	
	// should crash here, bcs xpath is invalid, name('...')
	try {
		O_web_element_miser_loc_17_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("name('sform')"));
	} catch (InvalidSelectorException PI_E_inv_sel) {
		System.out.println("error trying xpath: " + "name('sform')");
		PI_E_inv_sel.printStackTrace();
	}
	
	// should crash here, bcs xpath is invalid, class('...')
	try {
		O_web_element_miser_loc_18_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("class('gh_loc_bt')"));
	} catch (InvalidSelectorException PI_E_inv_sel) {
		System.out.println("error trying xpath: " + "class('gh_loc_bt')");
		PI_E_inv_sel.printStackTrace();
	}
	
	try {
		O_web_element_miser_loc_19_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("[@id='gh_searchform']"));
	} catch (InvalidSelectorException PI_E_inv_sel) {
		System.out.println("error trying xpath: " + "[@id='gh_searchform']");
		PI_E_inv_sel.printStackTrace(System.err);
	    }
	
	S_xpath = "//[@id='gh_searchform']";
	try {
		O_web_element_miser_loc_19_1 = NavigationUtils.O_rem_drv.findElement(By.xpath(S_xpath));
	} catch (InvalidSelectorException PI_E_inv_sel) {
		System.out.println("Error trying xpath: \'" + S_xpath + "\'");
		PI_E_inv_sel.printStackTrace(System.err);
	    }
	
	S_xpath = "*[@id='gh_searchform']";
	try {
		O_web_element_miser_loc_20_1 = NavigationUtils.O_rem_drv.findElement(By.xpath(S_xpath));
	} catch (NoSuchElementException PI_E_nse) {
        System.out.println("Error trying xpath: \'" + S_xpath + "\'");
		PI_E_nse.printStackTrace(System.err);
	    }
	
	O_web_element_miser_loc_21_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("//*[@id='gh_searchform']"));  // OK
	
    O_web_element_miser_loc_22_1 = NavigationUtils.O_rem_drv.findElement(By.xpath("//form[@id='gh_searchform']"));  // OK
    
    S_xpath = "//xxxyz[@id='gh_searchform']";
    try {
		O_web_element_miser_loc_23_1 = NavigationUtils.O_rem_drv.findElement(By.xpath(S_xpath));
	} catch (NoSuchElementException PI_E_nse) {
		System.out.println("Error trying xpath: \'" + S_xpath + "\'");
		PI_E_nse.printStackTrace(System.err);
	}  
	
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
	
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_01);
	System.out.println("Xpath 1:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt", "div"));
	O_web_element_miser_loc_01 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt", "div"));
	Assert.assertNotNull(O_web_element_miser_loc_01);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_01);
	System.out.println("Xpath 1 (div): " + S_res_xpath);
    

	// Xpath: .//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ')]
	S_xpath_class_names = ".//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ') and " + 
	                          "contains(concat(' ',normalize-space(@class),' '),' mid ')]";
	
	O_web_element_miser_loc_01 = NavigationUtils.O_rem_drv.findElement(ByXp.xpath(S_xpath_class_names));
	Assert.assertNotNull(O_web_element_miser_loc_01);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_01);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className(new String[]{"gh_loc_bt", "mid"}, "div"));
    O_web_element_miser_loc_01 = NavigationUtils.O_rem_drv.findElement(
    		ByXp.loc(
    		  Locator.className, 
    		  new String[]{"gh_loc_bt", "mid"}, 
    		  "div"));
	Assert.assertNotNull(O_web_element_miser_loc_01);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_01);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
	O_logger.traceExit();
	return;

	}

}
