package com.github.michaelederaut.selenium3.poc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.script.*;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.michaelederaut.basics.joox.selector.CSS2XPath;
import com.github.michaelederaut.basics.xpath2cssselector.DomRootElements.DomOffset;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.CssSGenerators;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.LinkText;

import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
// import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;

import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;
// import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

// import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import junit.framework.Assert;

@Deprecated
public class TestQuerySelectorPoc {

		public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";

	
		public static void main(String[] args) {
		IOException E_io;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		FirefoxBinary O_ff_bin;
		
		ProcessBuilder      O_proc_bldr;
		Process             O_proc;
		InputStream O_proc_stream_out, O_proc_stream_err;
		
		URL                 O_url_script;
		ScriptEngineManager O_scr_eng_mgr;
		ScriptEngine        O_scr_eng_1, O_scr_eng_2;
		ScriptEngineFactory O_scr_eng_fact_2;
		ScriptObjectMirror  O_scr_obj_mirror;
		List<ScriptEngineFactory> AO_scr_eng_factories;
		List<String>        AO_engine_names_inner;
		List<String>        AO_engine_versions_outer;
		
		WebElement         O_web_element_miser_loc_1, O_web_element_miser_loc_2,
		                   O_web_element;
		AbstractMap<String, ? extends Object> HO_res_exec, HS_elem;
//		JavascriptExecutor O_js_exexutor;
		byte AC_input_stream[];
		Object O_res_execute_1, O_res_execute_1_bindings, O_res_exec_js;
		DomVectorExtendedSelector O_res_sel_1, O_res_sel_err, O_res_sel_2, O_res_sel_3;
        ArrayList<Object> AO_res_exec_elements_extended, AO_res_vectors;
        String S_txt, S_tag, S_script_js;

        
		Class O_clazz;
		Logger O_logger;
		
		String S_msg_1, S_msg_2, S_line_out, 
		             S_script_engine_name_outer, 
		             S_script_engine_version_outer, 
		             S_script_engine_name_inner,
		             S_parent_wdw_handle, S_sub_wdw_handle,
		       S_parent_wdw_title, S_cmd_1,
		       S_pna_python_path,
		       S_clazz_name_short, S_clazz_name_full, S_res_xpath, S_xpath_class_names, S_clickable_typeof,
		       S_xpath_1, S_xpath_2, S_xpath_3, S_pn_script;
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
		S_xpath_1 = CSS2XPath.css2xpath(".gh_loc_bt");
		S_xpath_2 = CSS2XPath.css2xpath(".gh_loc_bt", false);
		System.out.println("S_xpath_1, true : "  + S_xpath_1);
		System.out.println("S_xpath_2, false: " + S_xpath_2);
		O_res_sel_1 = XpathGenerators.FSBO_get_xpath(new LocatorEnums(Locator.cssSelector), ".class1");
		S_xpath_1 = O_res_sel_1.FS_get_buffer();
		System.out.println("S_xpath_1:" + S_xpath_1);
		O_res_sel_2 = XpathGenerators.FSBO_get_xpath(new LocatorEnums(Locator.cssSelector), ".class2");
		S_xpath_2 = O_res_sel_2.FS_get_buffer();
		System.out.println("S_xpath_2:" + S_xpath_2);
		
		O_proc_bldr = new ProcessBuilder(
				"python.exe", 
				"G:\\ws\\Exercises\\_eclipse\\Selenium_WebDriver_3\\src\\main\\resources\\cssify.py", 
				"//div");
//		O_proc_bldr = O_proc_bldr.redirectOutput(Redirect.INHERIT);
//		O_proc_bldr = O_proc_bldr.redirectError(Redirect.INHERIT);
		try {
			O_proc = O_proc_bldr.start();
		} catch (IOException PI_E_IO) {
			O_proc = null;
			PI_E_IO.printStackTrace(System.err);
		}
		AC_input_stream = null;
		O_proc_stream_out = O_proc.getInputStream();
		try {
			AC_input_stream = O_proc_stream_out.readAllBytes();
		} catch (IOException PI_E_io) {
			PI_E_io.printStackTrace(System.err);
		    }
        S_pna_python_path = new String(AC_input_stream);
		O_res_sel_3 = XpathGenerators.FSBO_get_xpath(new LocatorEnums(Locator.cssSelector), ".class3");
		S_xpath_3 = O_res_sel_3.FS_get_buffer();
	    System.out.println("S_xpath_3:" + S_xpath_3);
	    
	    O_res_sel_1 = CssSGenerators.FSBO_get_csss(
	    		new LocatorEnums(Locator.xpath), 
	    		new String[] {"//div"}, // using
	    		(String) null,  // tag
	    		(LinkText)null,
	    		XpathGenerators.IGNORED_IDX,
	    		(DomOffset[]) null,
	    		(String)null); // prefix );
	    
//	    O_res_sel_err = CssSGenerators.FSBO_get_csss(
//	    		new LocatorEnums(Locator.xpath), 
//	    		new String[] {"xxx yyy zzz ???"}, // using
//	    		(String) null,  // tag
//	    		(LinkText)null,
//	    		XpathGenerators.IGNORED_IDX,
//	    		(String)null, // prefix 
//	    		(DomOffset[]) null);
	    
	    O_res_sel_2 = CssSGenerators.FSBO_get_csss(
	    		new LocatorEnums(Locator.xpath), 
	    		new String[] {"//div[@class='myClass1']"}, // using
	    		(String) null,  // tag
	    		(LinkText)null,
	    		XpathGenerators.IGNORED_IDX,
	    		(DomOffset[]) null,
	    		(String)null // prefix 
	    		);
	     
	    O_res_sel_3 = CssSGenerators.FSBO_get_csss(
	    		new LocatorEnums(Locator.xpath), 
	    		new String[] {"//div[@id='myId1']"}, // using
	    		(String) null,  // tag
	    		(LinkText)null,
	    		XpathGenerators.IGNORED_IDX,
	    		(DomOffset[]) null,
	    		(String)null // prefix 
	    		); 
	    
	    S_xpath_1 = O_res_sel_1.FS_get_buffer();
		System.out.println("S_xpath_1:" + S_xpath_1);

		O_scr_eng_mgr = new ScriptEngineManager();
		AO_scr_eng_factories = O_scr_eng_mgr.getEngineFactories();
		for (ScriptEngineFactory O_scr_engine_fact: AO_scr_eng_factories) {
			S_script_engine_name_outer    = O_scr_engine_fact.getEngineName();
			S_script_engine_version_outer = O_scr_engine_fact.getEngineVersion();
			System.out.println("name: " + S_script_engine_name_outer + " - version: " + S_script_engine_version_outer);
			AO_engine_names_inner = O_scr_engine_fact.getNames();
			for (String S_scr_engine_name_inner : AO_engine_names_inner) {
				System.out.println("\t" + S_scr_engine_name_inner);	
			}
		}
		
		O_scr_eng_1 = O_scr_eng_mgr.getEngineByName("js");
		
		// stackoverflow.com/questions/48911937/can-i-run-ecmascript-6-from-java-9-nashorn-engine
		O_scr_eng_fact_2 = new NashornScriptEngineFactory();
		System.setProperty("nashorn.args", "--language=es6");
		O_scr_eng_2 = O_scr_eng_mgr.getEngineByName("Nashorn");
		
	//	FileInputStream F_inp_str;
		InputStream O_inp_str;
		String S_bn_js = "/xpath_to_css.js";	
//		try {
//			 F_inp_str = new FileInputStream(S_pn_js);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
		O_url_script  = TestQuerySelectorPoc.class.getResource(S_bn_js);
		S_pn_script = O_url_script.getPath();
		
		O_inp_str = TestQuerySelectorPoc.class.getResourceAsStream(S_bn_js);
		 TestQuerySelectorPoc.class.getResource(S_bn_js).getPath();
		Assert.assertNotNull(O_inp_str);
//		https://github.com/jonathanp/xpath-to-css/blob/master/index.js
		O_res_execute_1_bindings = null;
		try {
			S_script_js = IOUtils.toString(O_inp_str, StandardCharsets.UTF_8.name());
		} catch (IOException PI_E_io) {
			PI_E_io.printStackTrace(System.err);
			S_script_js = null;
		    }
		if (StringUtils.isNotBlank(S_script_js)) {
			try {
			  O_res_execute_1_bindings = O_scr_eng_2.eval(S_script_js);
		   } catch (ScriptException PI_E_scr) {
			   PI_E_scr.printStackTrace(System.err);
		   }
//		   if (O_res_execute_1_bindings != null) {
//			   O_scr_obj_mirror = (ScriptObjectMirror)O_res_execute_1_bindings;
//		   }
		}
		
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
	

	O_web_element_miser_loc_2 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_2);
	 
	S_cmd_1 = "var HS_retval = {'elemcount' : 0 , 'AA_vectors' : []}; " +
			  "var i1, I_nbr_elems_f1, O_elem, S_id, S_clickable_typeof; " + 
			  "var AO_elems = []; " +
	          "var AA_vectors = []; " +
			  "var HS_elem_1 = {}; " +
			  "var HS_elem_2 = {}; " +
			  
	          "I_nbr_elemens_f1 = document.childElementCount; " +
			  "console.log('document.childElementCount: ' + I_nbr_elemens_f1); "  +
	          
			  "S_id = document.children[0].tagName; " +
			  "I_nbr_elemens_f1 = document.children[0].childElementCount; " +
	          "console.log('document.children[0].childElementCount: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
	 		  "S_id = document.children[0].children[0].tagName; " +
	 		  "I_nbr_elemens_f1 = document.children[0].children[0].childElementCount; " +
	 		  "console.log('document.children[0].children[0].childElementCount: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
	 		  "S_id = document.children[0].children[1].tagName; " +
	 		  "I_nbr_elemens_f1 = document.children[0].children[1].childElementCount; " +
	 		  "console.log('document.children[0].children[1].childElementCount: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
	 		  "S_id = document.children[0].children[1].children[0].tagName; " +
	 		  "I_nbr_elemens_f1 = document.children[0].children[1].children[0].childElementCount; " +
	 		  "console.log('document.children[0].children[1].children[0].childElementCount: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
	 		  "S_id = document.children[0].children[1].children[0].tagName; " +
	 		  "I_nbr_elemens_f1 = document.children[0].children[1].children[0].querySelectorAll('div').length; " +
	 		  "console.log('document.children[0].children[1].children[0].querySelectorAll(div).length: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
	 		  "S_id = document.children[0].children[1].children[0].tagName; " +
	 		  "I_nbr_elemens_f1 = document.children[0].children[1].children[0].querySelectorAll(':root').length; " +
	 		  "console.log('document.children[0].children[1].children[0].querySelectorAll(:root).length: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
	 		  "S_id = document.body.tagName; " +
	 		  "I_nbr_elemens_f1 = document.body.childElementCount; " +
	 		  "console.log('document.body.childElementCount: ' + I_nbr_elemens_f1 + ' - id: ' + S_id); " +
	 		  
//			  "AO_elems = document.children[0].children[1].querySelectorAll('" + ".xf_check" + "'); " +
//			  "AO_elems = document.children[0].children[1].children[0].querySelectorAll('" + ":root" + "'); " +
//            "AO_elems = document.querySelectorAll('" + ":root" + "'); " +
              "AO_elems = document.children[0].children[1].children[0].querySelectorAll('" + "" + "'); " +
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
	Assert.assertNotNull(O_web_element_miser_loc_1);
//	O_res_execute_1_bindings = (Bindings)O_res_execute_1; // --> ClassCastException
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
