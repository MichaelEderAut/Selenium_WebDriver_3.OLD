package com.github.michaelederaut.selenium3.poc;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

public class TestByHref {


	public By id(final String id) {
		    if (id == null)
		      throw new IllegalArgumentException(
		          "Cannot find elements with a null id attribute.");

		    return new ById(id);
	 }
	 
	 public static void main(final String argv[]) {
		 
			ByXp      O_by_xp_1, O_by_xp_3, O_by_xp_2;
			DomOffset AO_dom_offsets[];
			int       AI_dom_offsets[];
			
		    ByXp.ByName O_by_id = new ByXp.ByName("xf_table");
			System.out.println("ById.toString        : " + O_by_id.toString());
			
//			ByXp.ByPartialId O_by_id_partial = new ByXp.ByPartialId("f_tab");
			ByXp.Loc O_by_id_partial = new  ByXp.Loc(Locator.id, LocatorVariant.partial, "f_tab");
			System.out.println("ByPartialId.toString : " + O_by_id_partial.toString());
		 
			AO_dom_offsets = new DomOffset[] {new DomOffset(0, "DIV"), new DomOffset(1, "UL") };
			
		//	O_by_xp_1 = ByXp.loc(
			
			O_by_xp_1 = ByXp.loc(
					Locator.id, 
					LocatorVariant.regular, 
					"my_id", 
					XpathGenerators.DEFAULT_TAG, 
					0, 
					XpathGenerators.DEFAULT_PREFIX,
					AO_dom_offsets);
			
			AI_dom_offsets = new int[] {0, 2, 1};
			O_by_xp_2 = ByXp.loc(
					Locator.id, 
					LocatorVariant.regular, 
					"my_href", 
					XpathGenerators.DEFAULT_TAG, 
					0, 
					XpathGenerators.DEFAULT_PREFIX,
					AI_dom_offsets);
		  
			
			O_by_xp_3 = ByXp.loc(
					Locator.href, 
					LocatorVariant.regular, 
					"my_href", 
					XpathGenerators.DEFAULT_TAG, 
					0, 
					XpathGenerators.DEFAULT_PREFIX,
					AO_dom_offsets);
			
			  return;
	 }
}
