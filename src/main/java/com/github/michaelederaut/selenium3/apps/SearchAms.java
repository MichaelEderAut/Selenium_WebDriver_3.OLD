package com.github.michaelederaut.selenium3.apps;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Wait;

import com.github.michaelederaut.basics.ToolsBasics;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.NavigationUtils.ClickMode;
import com.github.michaelederaut.selenium3.framework.NavigationUtils.ClickResult;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import org.junit.Assert;

public class SearchAms {
   
	public static final String S_url_ams = "https://www.ams.at/";
	
  // public static final String S_pn_ff_prf = "G:\\var\\Browsers\\FireFox\\Profiles\\Selenium";
	
	public static final String S_xp_service_arbeitssuchende = "//a[@href='/service-arbeitsuchende']";

	public static final String S_lnk_txt_e_ams_konto = "eAMS-Konto";
//	 public static final String S_xp_e_ams_konto = "id('content')/x:ul/x:li[2]/x:a/x:strong";
//	public static final String S_xp_e_ams_konto  = "id('content')/ul/li[2]/a/strong";
	 public static final String S_xp_e_ams_konto = "//a[@title='Link öffnet sich in neuem Fenster']" +
	                                                  "[@href='https://www.e-ams.at/eamslogin.html']" +
			                                          "[@target='_blank']";
	 
//	 public static final String S_xp_e_ams_konto = "(//a[@title='Link öffnet sich in neuem Fenster']" +
//                                                      "[@href='https://www.e-ams.at/eamslogin.html']" +
//                                                      "[@target='_blank'])[1]";
	public static final String S_xp_username_input = "id('j_username')";
	public static final String S_xp_passwd_input   = "id('j_password')";
	public static final String S_xm_id_submit = "id('submit')";
	
	public static WebElement O_web_element_xchange;
	public static String S_sub_wdw_handle_xchange;
//	public static String S_parent_wdw_handle_xchange;
	
	// @Test
	public static void main(String[] args) {
		IOException E_io;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		FirefoxBinary O_ff_bin;
		
		WebElement O_web_element_arbeits, O_web_element_e_ams_k;
		RemoteWebElement O_rem_ele_1;
		JavascriptExecutor O_js_exexutor;
		Object O_res_click, O_res_execute_1, O_res_execute_2, O_res_execute_3, O_res_execute_4;
		ClickResult O_click_result;
		RemoteWebElementXp O_rem_ele_1_xp;

		Class O_clazz;
		Logger O_logger;
			
		String S_msg_1, S_msg_2, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle, S_parent_wdw_title,
		S_clazz_name_short, S_clazz_name_full, S_css_sel, S_js_cmd_1, S_js_cmd_2, S_js_cmd_3, S_js_cmd_4;
		Set<String> AS_sub_wdw_handles;
		HashSet<Class<? extends Throwable>> HO_ignored_exceptions_generic;
	
		Wait<RemoteWebDriver>  O_waiter_elem_std, O_waiter_elem_long;
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = SearchAms.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
			
//	    E_browser_type = BrowserTypes.FireFox;
//   	E_browser_type = BrowserTypes.InternetExplorer;
		E_browser_type = BrowserTypes.Edge;
	    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_ams,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std,
	    		NavigationUtils.FB_accecpt_equ_hostnames);
		
//		if (Search.O_web_element_xchange == null) {
//			   S_url_actual = O_rem_web_drv.getCurrentUrl();
//			   S_msg_1 = "Unable to locate website " + S_url_ams + " with Element: \"" + S_xp_service_arbeitssuchende ;
//			    Assert.fail(S_msg_1);
//			}
		// O_web_element = Search.O_web_element_xchange;
		
         S_parent_wdw_handle = O_nav_utils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = O_nav_utils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

//   // "ams-e-button ams-m-cookie-warning__button ams-e-button--primary"
     O_res_execute_1 = NavigationUtils.O_rem_drv.findElementByClassName("ams-e-button");
//    ToolsBasics.FV_sleep(2000);
//   O_res_execute_1 = NavigationUtils.O_rem_drv.findElementByLinkText("Einverstanden");
     O_res_execute_1 = null;
     try {
         O_res_execute_1 = NavigationUtils.O_rem_drv.findElement(By.linkText("Einverstanden"));
     } catch (NoSuchElementException PI_E_no_such_element) {
    	O_rem_ele_1_xp = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.linkText, "Einverstanden"));
        O_rem_ele_1 = O_rem_ele_1_xp.FO_get_reduced_web_element();
        O_res_execute_1 = O_rem_ele_1;
        }
     
if (O_res_execute_1 != null) {
	 O_res_click = NavigationUtils.FO_click_js((RemoteWebElement)O_res_execute_1, ClickMode.click);
	 if (O_res_click != null) {
		 System.out.println("Type of click result: \'" + O_res_click.getClass().getTypeName());
		 if (O_res_click instanceof ClickResult) {
		     O_click_result = (ClickResult)O_res_click;
		     System.out.println("Url after: " + O_click_result.S_url_after);
		     System.out.println("Elapsed time: " + O_click_result.L_elapsed_time + " ms.");
		     }
	       }
		 }
     O_res_execute_2 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, new String[] {"ams-e-button"})); 
     O_res_execute_3 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, new String[] {"ams-m-cookie-warning__button"})); 
         
//	 O_res_execute_1 = NavigationUtils.O_rem_drv.findElementByLinkText("Arbeitsuchende");
	 O_res_execute_4 = NavigationUtils.O_rem_drv.findElementByXPath("//*[text()='Arbeitsuchende']"); 
//	 O_res_execute_2 = NavigationUtils.O_rem_drv.executeScript(S_js_cmd_1 /* , new Integer(0) */);

//  S_css_sel = "*[title='Arbeitsuchende']"; // OK
//	S_css_sel = "a[title='Arbeitsuchende']"; // OK
//	S_css_sel = "ul[id='mainMenu']";  // OK
//	S_css_sel = "*[id='mainMenu']";  // OK
//	S_css_sel = "a[text='Arbeitsuchende']";  // Not OK
	S_css_sel = "a[title='Arbeitsuchende']";
	O_web_element_arbeits = NavigationUtils.O_rem_drv.findElementByCssSelector(S_css_sel);
//	O_web_element_arbeits = O_web_element_arbeits.findElement(By.xpath(".."));
	O_js_exexutor = (JavascriptExecutor)NavigationUtils.O_rem_drv;
	O_res_execute_1 = O_js_exexutor.executeScript("return arguments[0];", O_web_element_arbeits);  // useless result
	
//	S_js_cmd_2 = "return document.querySelector(\"" +  S_css_sel + "\");" ;
//	O_res_execute_2 = O_js_exexutor.executeScript(S_js_cmd_2);
//	
//	S_js_cmd_3 = "var elem = document.querySelector(\"" +  S_css_sel + "\"), attrs, nbr_attrs; var attrs = elem.attributes; nbr_attrs = attrs.length; return nbr_attrs;" ;
//	O_res_execute_3 = O_js_exexutor.executeScript(S_js_cmd_3);
	
	String             S_res_xpath;
	IndexedStrBuilder SB_res_xpath;

	O_web_element_arbeits = O_nav_utils.FO_find_fluent(
		//	ByXp.linkText("Arbeitsuchende"), 
			ByXp.Loc.loc(Locator.linkText, "Arbeitsuchende"),
			WaiterFactory.I_timeout_std * 10000,
			WaiterFactory.I_poll_std);
	
//	O_web_element_arbeits = NavigationUtils.O_rem_drv.findElementByCssSelector("a[title='Arbeitsuchende']");
	
	O_web_element_arbeits = NavigationUtils.O_rem_drv.findElement(By.id("mainMenu"));
	
	S_css_sel = "a[title='Arbeitsuchende']";
	SB_res_xpath = XpathGenerators.FS_get_xpath_from_css_selector(S_css_sel);
	S_res_xpath = SB_res_xpath.toString();
	
	S_css_sel = "a";
	SB_res_xpath = XpathGenerators.FS_get_xpath_from_css_selector(S_css_sel);
	S_res_xpath = SB_res_xpath.toString();
	
    S_css_sel = "li[class='news3']";
    SB_res_xpath = XpathGenerators.FS_get_xpath_from_css_selector(S_css_sel);
	S_res_xpath = SB_res_xpath.toString();
    
    S_css_sel = "li";
    SB_res_xpath = XpathGenerators.FS_get_xpath_from_css_selector(S_css_sel);
	S_res_xpath = SB_res_xpath.toString();
    
    S_css_sel = "div";
    SB_res_xpath = XpathGenerators.FS_get_xpath_from_css_selector(S_css_sel);
	S_res_xpath = SB_res_xpath.toString();
    
	O_web_element_arbeits = NavigationUtils.O_rem_drv.findElementByLinkText("Arbeitsuchende");
	O_web_element_arbeits = NavigationUtils.O_rem_drv.findElement(By.linkText("Arbeitsuchende"));
	
	O_web_element_arbeits = O_nav_utils.FO_find_fluent(
		//	ByXp.linkText("Arbeitsuchende"), 
			ByXp.loc(Locator.linkText, "Arbeitsuchende"),
			WaiterFactory.I_timeout_std,
			WaiterFactory.I_poll_std);
	O_web_element_arbeits = O_nav_utils.FO_find_fluent(
			ByXp.loc(Locator.xpath, S_xp_service_arbeitssuchende), 
			WaiterFactory.I_timeout_std,
			WaiterFactory.I_poll_std);
	O_res_click = O_nav_utils.FO_click_js(O_web_element_arbeits); 		
	
	O_web_element_e_ams_k = O_nav_utils.FO_find_fluent(
			ByXp.loc(Locator.xpath, S_xp_e_ams_konto),
			WaiterFactory.I_timeout_std,
			WaiterFactory.I_poll_std);	
	O_res_click = O_nav_utils.FO_click_js(O_web_element_e_ams_k); 				
				
    if (O_web_element_e_ams_k == null) {
    	S_msg_1 = "Couldnt find Xpath \"" + S_xp_e_ams_konto + "\" for link text: \"" + S_lnk_txt_e_ams_konto + "\"";
    	Assert.fail(S_msg_1);
        }
	Assert.assertNotNull(O_web_element_e_ams_k);
	
	// O_rem_web_drv.navigate().to(url);
//	HvbToolsBasis.FV_sleep(5000);
//	AS_sub_wdw_handles = O_rem_web_drv.getWindowHandles();
//	
//	S_sub_wdw_handle = null;
//	
////	S_parent_wdw_handle_xchange = S_parent_wdw_handle;
//	O_waiter_elem_long.until(new Function<RemoteWebDriver, String>() {
//		public String apply (RemoteWebDriver PI_O_drvr) {
//			Set<String> AS_sub_wdw_handles;
//			String S_retval_sub_wdw_handle, S_parent_window_handle_tmp;
//			S_sub_wdw_handle_xchange = null;
//			S_parent_window_handle_tmp = PI_O_drvr.getWindowHandle();
//			AS_sub_wdw_handles = PI_O_drvr.getWindowHandles();
//			
//			LOOP_WINDOW_HANDLES: for (String S_wdw_handle_tmp : AS_sub_wdw_handles) {
//				if (!S_wdw_handle_tmp.equals(S_parent_window_handle_tmp)) {
//					S_sub_wdw_handle_xchange = S_wdw_handle_tmp;
//					break LOOP_WINDOW_HANDLES;
//				}
//			}
//			return S_sub_wdw_handle_xchange;	
//		}
//	});	
//	
//	S_sub_wdw_handle = S_sub_wdw_handle_xchange;
//	if (S_sub_wdw_handle == null) {
//		S_msg_1 = "No Sub-window handle found after clicking: " + S_lnk_txt_e_ams_konto;
//		Assert.fail(S_msg_1);
//	    }
//	
////	TargetLocator O_target_loc = O_ff_drv.switchTo();
//	O_rem_web_drv.switchTo().window(S_sub_wdw_handle);
//	O_web_element_arbeits =	O_rem_web_drv.findElement(By.xpath(S_xp_username_input));
//	O_web_element_arbeits.sendKeys("meder307");
//	O_web_element_arbeits =	O_rem_web_drv.findElement(By.xpath(S_xp_passwd_input));
//	O_web_element_arbeits.sendKeys("fusonfur1");
	
	 O_logger.traceExit();
	return;

	}

}
