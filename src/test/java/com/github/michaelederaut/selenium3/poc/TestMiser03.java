package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.StringReader;

import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.text.StrBuilder;
import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.dom4j.Node;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Wait;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.sun.jna.platform.win32.WinReg.HKEY;

import com.github.michaelederaut.basics.HtmlUtils;

import junit.framework.Assert;

public class TestMiser03 {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	@Test
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		IOException E_io;
		
		WebElement         O_web_element_miser_loc, O_res_web_element_9;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
	
		HKEY HKEY_CLASSES_ROOT;
		HtmlUtils.Components O_html_components;
	
//		JavascriptExecutor O_js_exexutor;
    	Object O_res_click, 
    	O_res_exec_01, O_res_exec_02, O_res_exec_03, O_res_exec_04, O_res_exec_05, O_res_exec_06,
    	O_res_exec_07, O_res_exec_08, O_res_exec_08_nbr_elems, O_res_exec_08_node, 
    	O_res_exec_09_node, O_res_exec_09_vector, O_res_exec_09, O_res_exec_10;
	    StrBuilder SB_prefix_08, SB_idx_08;

	    AbstractMap<String, ? extends Object> HO_res_exec_08, HO_res_exec_09, HO_res_exec_10;
	    ArrayList<ArrayList<Object>> AA_offset_vector;
	    
		Class O_clazz;
		Logger O_logger;
		
		
		String S_msg_1, S_msg_2, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle, S_parent_wdw_title,
		S_clazz_name_short, S_clazz_name_full, S_res_xpath, 
		S_xpath_08, S_xpath_09, S_xpath_07_prefix,  S_xpath_07_index, S_xpath_10, S_xpath_class_names,
		S_cmd_js_01, S_cmd_js_02, S_cmd_js_03, S_cmd_js_04, S_cmd_js_05, 
		S_cmd_js_06, S_cmd_js_07, S_cmd_js_08, S_cmd_js_09, S_cmd_js_10,
		S_page_source_raw, S_page_source_body, S_page_source_cleaned, S_dom_path, S_tag_name;
	
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		boolean B_is_indexed;
		int I_nbr_web_elements_f1;
		
		// I N I T 
		
		O_clazz = TestMiser01.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();

//	    E_browser_type = BrowserTypes.FireFox;
    	E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
		
         S_parent_wdw_handle = NavigationUtils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = NavigationUtils.O_rem_drv.getTitle();
         S_page_source_raw = /* "<!DOCTYPE HTML>" "\n"  + */  NavigationUtils.O_rem_drv.getPageSource();   
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

	List <WebElement> AO_web_elements;

	NavigationUtils.O_rem_drv.getPageSource();
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(By.xpath(".//span"));
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	
	AO_web_elements = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(".//span"));
	Assert.assertNotNull(AO_web_elements);
	I_nbr_web_elements_f1 = AO_web_elements.size();
	Assert.assertTrue(I_nbr_web_elements_f1 >= 1);
	O_web_element_miser_loc = AO_web_elements.get(0);
	
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 1:       " + S_res_xpath);
	
	// https://www.w3.org/TR/webdriver/
	// stackoverflow.com/questions/28051172/how-to-convert-xpath-to-java-code
	
	S_cmd_js_01 = "javascript:document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; return XPathResult.length" ;    
	O_res_exec_01 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_01);  // null for xpath == ".//span"

	
	S_cmd_js_02 = "return document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " ;    
	O_res_exec_02 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_02); 
	// rem-web-element (found by == null) for xpath == ".//span"
	O_web_element_miser_loc = (RemoteWebElement)O_res_exec_02;
	S_tag_name = O_web_element_miser_loc.getTagName();
	
	S_cmd_js_03 = "var AO_results = {}; " +
			"AO_results = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " + 
            "return AO_results";
	O_res_exec_03 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_03);
	// rem-web-element (found by == null) for xpath == ".//span"
	
	S_cmd_js_04 = "var AO_results = []; " +
			"AO_results = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "return AO_results.length";    
	O_res_exec_04 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_04); // null for xpath == ".//span"
	
	S_cmd_js_05 = "var AO_results = []; var O_iterator, O_this_node, i1; " +
			"O_iterator = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "i1 = 0; " +
             "O_this_node = O_iterator.iterateNext(); " +
             "while (O_this_node) { " +
      //         "console.log('idx: ' + i1); " +
               "AO_results.push(O_this_node); " +
               "O_this_node = O_iterator.iterateNext(); " +
               "i1++; " +
             "} " +
            "return [AO_results.length, i1]";    
	O_res_exec_05 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_05);
	// Array[0..1] of 1671L(Long) for xpath == ".//span"
	
	S_cmd_js_06 = "var AO_results = []; var O_this_node; var i1; " +
			"AO_results = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); " + 
            "O_this_node = AO_results.snapshotItem(0); " +
            "i1 = AO_results.snapshotLength; " +
            "return [O_this_node, i1]";    
	O_res_exec_06 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_06);
	// [RemoteWebElement, 1672L(ong)] for xpath == ".//span"
	
	S_cmd_js_07 = "var O_this_node, i1, O_iterator; " +
			"O_iterator = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "i1 = 0; " +
            "O_this_node = O_iterator.iterateNext(); " +
            "return O_this_node";    
	O_res_exec_07 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_07);
	// Remote WebElement for xpath == ".//span"
	
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(By.xpath(".//span"));
	
	SB_prefix_08 = new StrBuilder();
	SB_idx_08    = new StrBuilder();
	S_xpath_08 = "(.//span)[2]";
	B_is_indexed = XpathConcatenator.FB_is_indexed(S_xpath_08, SB_prefix_08, SB_idx_08);
	
	S_cmd_js_08 = 
			"var H_retval = {};  " + 
	        "var O_iterator, O_node_current, O_node_retval, i1; " +
			"O_iterator = document.evaluate(\"" + 
			S_xpath_08 + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "i1 = 0; " +
             "O_node_retval = O_iterator.iterateNext(); " +
             "O_node_current = O_node_retval; " +
             "while (O_node_current) { " +
  //             "console.log('idx: ' + i1); " +
               "O_node_current = O_iterator.iterateNext(); " +
               "i1++; " +
             "} " +
             "H_retval = {I_nbr_elems: i1, O_node: O_node_retval}; " + 
             "return H_retval;";
	O_res_exec_08 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_08);
	HO_res_exec_08 = (AbstractMap<String, ? extends Object>)O_res_exec_08;
	O_res_exec_08_nbr_elems =  HO_res_exec_08.get("I_nbr_elems"); // 701
	O_res_exec_08_node      =  HO_res_exec_08.get("O_node");      //  rem-web-element (found by == null) for xpath == ".//span"
	
	S_xpath_09 = "(.//span)[1600]";  // 1668: 1   1669: null   "(.//span)[2600]"
	S_cmd_js_09 = 
		"var H_retval = {}; " + 
	    "var AA_vector = []; " +
	    "var O_iterator, " + 
		"O_node_current, O_node_parent, O_node_prev, O_element_current, B_cont_loop_prv, O_elemt_parent, " + 
	    "O_node_retval, S_node_name, i1, I_nbr_prev_f1, I_node_type_current; " +
		"O_iterator = document.evaluate(\"" + 
		S_xpath_09 + 
        "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
        "i1 = 0; " +
        "if (O_iterator) {" +
            "O_node_retval = O_iterator.iterateNext(); " + 
            "console.log('Start loop:'); " +
            "O_node_current = O_node_retval; " +
            "LOOP_PARENTS: while (O_node_current) {" +
               "I_node_type = O_node_current.nodeType; "  +
               "if (I_node_type != 1) {"  +
                  "break LOOP_PARENTS; }" +
               "S_node_name = O_node_current.nodeName; " +
               "if (S_node_name == 'BODY') {" +
                   "break LOOP_PARENTS; } " +
               "if (S_node_name == 'HTML') {" +
                   "break LOOP_PARENTS; } " +
               "if (S_node_name == '#document') {" +
                   "break LOOP_PARENTS; } " +
               "B_cont_loop_prv = true; " +
               "I_nbr_prev_f1 = 0; " +
               "O_node_prev = O_node_current; " +
               "LOOP_PRV: while (B_cont_loop_prv) {" +
                  "O_node_prev = O_node_prev.previousElementSibling; " +
                  "if (!O_node_prev) {" +
                     "break LOOP_PRV; } " +
                  "I_nbr_prev_f1++; " +
               "}" +
             "AA_vector.push([I_nbr_prev_f1, S_node_name]); " +
//             "console.log('Node-Type: ' + I_node_type); " +
//             "console.log('Node-Name: ' + S_node_name); " +
             "O_node_current = O_node_current.parentNode; " +
           "}} " +
         "H_retval = { elem : O_node_retval, vector : AA_vector }; " +
         "return H_retval; ";
	O_res_exec_09 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_09);
	HO_res_exec_09 = (AbstractMap<String, ? extends Object>)O_res_exec_09;
	O_res_exec_09_node      =  HO_res_exec_09.get("elem");
	O_res_web_element_9    =  (WebElement)O_res_exec_09_node;
	O_res_exec_09_vector    =  HO_res_exec_09.get("vector");
	AA_offset_vector       =  (ArrayList<ArrayList<Object>>)O_res_exec_09_vector;
	//                      [[1, SPAN], [0, A], [3, DIV], [26, DIV], [7, DIV], [3, FORM], [5, DIV], [0, DIV], [0, DIV], [1, BODY], [0, HTML], [0, #document]]
	// [[0, SPAN], [0, SPAN], [0, DIV], [0, A], [3, DIV], [26, DIV], [7, DIV], [3, FORM], [5, DIV], [0, DIV], [0, DIV], [1, BODY], [0, HTML], [0, #document]]
	// [[0, SPAN], [0, SPAN], [0, DIV], [0, A], [3, DIV], [26, DIV], [7, DIV], [3, FORM], [5, DIV], [0, DIV], [0, DIV]]
	
	ArrayList<ArrayList<String>> AAS_attrs;
	S_xpath_10 = "(.//form)[1]";
	S_cmd_js_10 = 
			"var H_retval = {}; " + 
	        "var AH_attrs = {}; " +
	        "var AO_attrs = {}; " +
	        "var O_attr = {}; " +
			"var A_attr = []; " +
			"var AA_attrs = []; " +
		    "var O_iterator, O_node_retval, i2, I_nbr_attrs_f1, S_attr_name, S_attr_val, S_inner_html, S_elem_txt, S_inner_html" + 
			"O_iterator = document.evaluate(\"" + 
			S_xpath_10 + 
	        "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
	        "if (O_iterator) {" +
                "O_node_retval = O_iterator.iterateNext(); " +
                "S_elem_txt   = O_node_retval.txt; " +
                "S_inner_html = O_node_retval.innerHTML; " +
	            "AO_attrs = O_node_retval.attributes; " +
	            "I_nbr_attrs_f1 = AO_attrs.length; " +
	            "console.log('nbr attrs: ' + I_nbr_attrs_f1);" +
	            "for (i2 = 0; i2 < I_nbr_attrs_f1; i2++) { " +
	                "O_attr = AO_attrs[i2]; " +
	                "S_attr_name  = O_attr.name; " +
	                "S_attr_value = O_attr.value; " +
	                "console.log('name: ' + S_attr_name + ' - value: ' + S_attr_value); " +
	                "A_attr = [S_attr_name, S_attr_value]; " +
	                "AA_attrs.push(A_attr); " +
	             "} " +
	        "} " +
	        "H_retval = {elem : O_node_retval, attrs: AA_attrs}; " +  
            "return H_retval; ";
	O_res_exec_10 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_10);
	HO_res_exec_10 = (AbstractMap<String, ? extends Object>)O_res_exec_10;
	AAS_attrs = (ArrayList<ArrayList<String>>)HO_res_exec_10.get("attrs");
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt", "div"));
    O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt", "div"));
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 1 (div): " + S_res_xpath);
    
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
	// O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className(new String[]{"gh_loc_bt", "mid"}, "div"));
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.loc(
			Locator.className,
			new String[]{"gh_loc_bt", "mid"},
			"div"));
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
//	O_web_element_miser_loc = O_nav_utils.FO_find_fluent(
//	ByXp.className("gh_loc_bt"), 
//	WaiterFactory.I_timeout_std,
//	WaiterFactory.I_poll_std);
	
	O_logger.traceExit();
	return;

	}

}
