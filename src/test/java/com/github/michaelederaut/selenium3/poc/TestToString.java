package com.github.michaelederaut.selenium3.poc;

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

// import static com.github.michaelederaut.basics.PropertyContainerUtils.AT_s1;

import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.ByXp.Loc;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelectorXp;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;

public class TestToString {
	
	public static void main(String PI_AS_args[]) {
	
	LocatorSelectorXp	O_locator_xp;
	LocatorEnums   O_loc_enums;
	 LocatorSelector O_loc_sel;
	LocatorEnums E_locator_enum_1;
	Loc O_by_id_2, O_by_id_3, O_by_id_4;
	ByXp.ById O_by_id_1;
	ByXp.ByClassName O_by_cls_and_1, O_by_cls_and_2, O_by_cls_and_3, O_by_cls_and_4;
	Constructor <? extends ByXp> O_ctor_1;
	
	DomVectorExtendedSelector SB_xpath_1;
	String S_xpath_1, S_dump_locator_1, S_dump_locator_xp_1,
	       S_by_id_1, S_by_id_2, S_by_id_3, S_by_id_4,
	       S_by_cls_and_1, S_by_cls_and_2, S_by_cls_and_3, S_by_cls_and_4,
	       S_dom_vect_ext_selector_1, S_dom_vect_ext_selector_2;
    DomVectorExtendedSelector O_using_1, O_using_2,  O_dom_vect_ext_selector_1,  O_dom_vect_ext_selector_2;
    
	O_by_cls_and_1 = new ByXp.ByClassName("gh_loc_bt");
	S_by_cls_and_1 = O_by_cls_and_1.toString();
	System.out.println("S_by_cls_and_1: " + S_by_cls_and_1);
	
	O_dom_vect_ext_selector_1 = new DomVectorExtendedSelector(new int[]{0, 2, 1});
	S_dom_vect_ext_selector_1 = O_dom_vect_ext_selector_1.toString();
	System.out.println("dom-vect-extended_sel_1:" + S_dom_vect_ext_selector_1);
	
	O_dom_vect_ext_selector_2 = new DomVectorExtendedSelector(new DomOffset[]{
			new DomOffset(0, "div"),
			new DomOffset(2, "a"),
			new DomOffset(1, "span"),
			new DomOffset(3, "table"),
			new DomOffset(5),
			new DomOffset(0, "tr"),
			new DomOffset(2, "td")
			});
	S_dom_vect_ext_selector_2 = O_dom_vect_ext_selector_1.toString();
	System.out.println("dom-vect-extended_sel_2:" + S_dom_vect_ext_selector_2);
	
//	O_ctor_1 = ConstructorUtils.getAccessibleConstructor(ByXp.ById.class, String.class);
	O_ctor_1 = ConstructorUtils.getAccessibleConstructor(Loc.class, LocatorEnums.class, String.class);
	
	E_locator_enum_1 = new LocatorEnums("id"); 
	SB_xpath_1  = XpathGenerators.FSBO_get_xpath(E_locator_enum_1, "myId"); 
	S_xpath_1 = SB_xpath_1.toString();
	
	O_using_1 = new DomVectorExtendedSelector("myid");
	
	O_loc_enums = new LocatorEnums("id");
	O_loc_sel = new LocatorSelector(O_loc_enums);
	S_dump_locator_1 = O_loc_enums.toString();
	System.out.println("S_dump_locator: " + S_dump_locator_1);
	
//	SB_xpath_1 = XpathGenerators.FS_get_xpath(ByXp.ById.class, "myid", "a", 0,  ".//");
	SB_xpath_1 = XpathGenerators.FSBO_get_xpath(new LocatorEnums(Locator.id), "myid", "a", 0, ".//");
	System.out.println("S_dump_xp_1: " + SB_xpath_1.toString() + System.lineSeparator());
	O_locator_xp = new LocatorSelectorXp("id", LocatorVariant.regular, O_using_1 , "a", 0, ".//", O_ctor_1);
	S_dump_locator_xp_1 =  O_locator_xp.toString();
	System.out.println("S_dump_locator_xp_1: " + S_dump_locator_xp_1 + System.lineSeparator());
	
	O_by_id_1 = new ByXp.ById("my_id");
	S_by_id_1 = O_by_id_1.toString();
	System.out.println("S_by_id_1: " + S_by_id_1);
	
//	O_by_id_2 = new ByXp.ById("your_id", "div");
	O_by_id_2 = new ByXp.Loc(Locator.id, "your_id", "div");

	
	S_by_id_2 = O_by_id_2.toString();
	System.out.println("S_by_id_2: " + S_by_id_2);
	
//	O_by_id_3 = new ByXp.ById("his_id", "div", 5);
	O_by_id_3 = new ByXp.Loc(Locator.id, "his_id", "div", 5);
	
	S_by_id_3 = O_by_id_3.toString();
	System.out.println("S_by_id_3: " + S_by_id_3);
	
//	O_by_id_4 = new ByXp.ById("her_id", "a", XpathGenerators.LAST_IDX, "../");
	O_by_id_4 = new ByXp.Loc(Locator.id, "her_id", "a", XpathGenerators.LAST_IDX, "../");
	S_by_id_4 = O_by_id_4.toString();
	System.out.println("S_by_id_4: " + S_by_id_4);
	}
}
