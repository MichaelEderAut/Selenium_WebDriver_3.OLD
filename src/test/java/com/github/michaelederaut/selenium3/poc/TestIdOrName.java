package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

public class TestIdOrName {
	
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	public static void main(String[] args) {
		IOException E_io;
	
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
	//    DomOffset       AO_dom_offset_vector[];
		
		List <WebElement> AO_web_elements_01, AO_web_elements_02, AO_web_elements_03, AO_web_elements_04,
		                  AO_web_elements_10, AO_web_elements_11;
		WebElement        O_web_element_id_gh_body, O_web_element_id_sform, O_web_element_name_sform, 
		                  O_web_element_id_or_name_sform;
		
		ByXp.ByDomOffsets O_by_dom_offsets;
		RemoteWebElementXp O_web_element_xp;
		
		String S_by_dom_offsets;
	
//	E_browser_type = BrowserTypes.InternetExplorer;
	E_browser_type = BrowserTypes.FireFox;
    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
    
    O_nav_utils.FO_get_fluent(
    		S_url_miser,
    		WaiterFactory.I_timeout_long, 
    		WaiterFactory.I_poll_std);
	
    O_web_element_id_gh_body = NavigationUtils.O_rem_drv.findElement(ByXp.Loc.loc(Locator.idOrName, "gh_main"));
    
    O_web_element_id_sform = NavigationUtils.O_rem_drv.findElement(ByXp.Loc.loc(Locator.id, "sform"));
    O_web_element_name_sform = NavigationUtils.O_rem_drv.findElement(ByXp.Loc.loc(Locator.name, "sform"));
    
	O_web_element_id_or_name_sform = NavigationUtils.O_rem_drv.findElement(ByXp.Loc.loc(Locator.idOrName, "sform"));
    
//	 AO_web_elements_01 = NavigationUtils.O_rem_drv.findElements(ByXp.idOrName("gh_main"));
	AO_web_elements_01 = NavigationUtils.O_rem_drv.findElements(ByXp.Loc.loc(Locator.idOrName, "gh_main"));
	
//	AO_web_elements_02 = NavigationUtils.O_rem_drv.findElements(ByXp.idOrName("sform"));
	AO_web_elements_02 = NavigationUtils.O_rem_drv.findElements(ByXp.Loc.loc(Locator.idOrName, "sform"));
	 
//	AO_web_elements_03 = NavigationUtils.O_rem_drv.findElements(ByXp.idOrName("fcols"));
	AO_web_elements_03 = NavigationUtils.O_rem_drv.findElements(ByXp.Loc.loc(Locator.idOrName, "sform"));
	 
//	 AO_web_elements_04 = NavigationUtils.O_rem_drv.findElements(ByXp.partialIdOrName("ch"));
	 AO_web_elements_04 = NavigationUtils.O_rem_drv.findElements(ByXp.Loc.loc(Locator.idOrName, LocatorVariant.partial, "ch"));
	
	// AO_web_elements_10 = NavigationUtils.O_rem_drv.findElements(ByXp.idOrNameSuffix("ch"));
	 AO_web_elements_10 = NavigationUtils.O_rem_drv.findElements(ByXp.Loc.loc(Locator.idOrName, LocatorVariant.suffix, "ch"));
	
//	O_web_element = AO_web_elements.get(0);
	O_web_element_xp = (RemoteWebElementXp)O_web_element_id_gh_body;
//	AO_dom_offset_vector = O_web_element_xp.AO_dom_offset_vector;
//	O_by_dom_offsets     = new ByXp.ByDomOffsets(AO_dom_offset_vector);
//	S_by_dom_offsets     = O_by_dom_offsets.toString();
//	System.out.println("ByDomOffsets: " + S_by_dom_offsets);
	
	System.exit(0);
	}
}
