package com.github.michaelederaut.selenium3.framework;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

// import com.github.michaelederaut.selenium3.framework.ByXp.Loc;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementCssS.LocatorSelectorCss;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelectorXp;
import com.github.michaelederaut.selenium3.platform.CssSGenerators;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.LinkText;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorRegularity;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

import static com.github.michaelederaut.selenium3.platform.CssSGenerators.DEFAULT_PREFIX;
import static com.github.michaelederaut.selenium3.platform.CssSGenerators.DEFAULT_TAG;
import static com.github.michaelederaut.selenium3.platform.XpathGenerators.IGNORED_IDX;
import static org.apache.commons.lang3.StringUtils.LF;

/**
 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;ByCssSelector">Mr. Michael Eder</a>
 */
public abstract class ByCssS extends By {
	
	//---
	
	public static final Class<?>[] AT_e1_s1_o1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   LinkText.class};
	
		   	   
	public static final Class<?>[] AT_e1_s1_o1_i1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   LinkText.class,
		   int.class};
	   
		   
	public static final Class<?>[] AT_e1_s1_o1_ai = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   LinkText.class,
		   int[].class};	   
	
	public static final Class<?>[] AT_e1_s1_o1_i1_ai = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   LinkText.class,
		   int.class,
		   int[].class};	   
		   
	public static final Class<?>[] AT_e1_s2_o1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class};
		   
	public static final Class<?>[] AT_e1_s2_o1_ai = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int[].class};	   
	
	public static final Class<?>[]  AT_e1_as_o1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   LinkText.class};
		   
	public static final Class<?>[]  AT_e1_as_o1_i1 =  new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   LinkText.class,
		   int.class};

	public static final Class<?>[]  AT_e1_as_o1_i1_ai =  new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   LinkText.class,
		   int.class,
		   int[].class};
		   
	public static final Class<?>[]  AT_e1_as_o1_ai = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   LinkText.class,
		   int[].class};
		   
	public static final Class<?>[] AT_e1_as_s1_o1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class};		   
	
	public static final Class<?>[] AT_e1_as_s1_o1_ai = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   String[].class};		   
		   
	public static final Class<?>[] AT_e2_s1_o1 = new Class<?>[] {
		   Locator.class,
		   LocatorVariant.class,
		   String.class,
		   LinkText.class};		
		   
	public static final Class<?>[] AT_e2_s1_o1_i1 = new Class<?>[] {
		   Locator.class,
		   LocatorVariant.class,
		   String.class,
		   LinkText.class,
		   int.class};		   
	
	public static final Class<?>[] AT_e2_s1_o1_ai = new Class<?>[] {
		   Locator.class,
		   LocatorVariant.class,
		   String.class,
		   LinkText.class,
		   int[].class};		   
		   
	public static final Class<?>[] AT_e2_s1_o1_a1 = new Class<?>[] {
		   Locator.class,
		   LocatorVariant.class,
		   String.class,
		   LinkText.class,
		   int[].class};		   
		   
	public static final Class<?>[] AT_e2_s2_o1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class};
	
	public static final Class<?>[] AT_e2_as_o1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   LinkText.class};	   
	
	public static final Class<?>[] AT_e2_as_o1_i1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   LinkText.class,
		   int.class};	   
		   
    public static final Class<?>[] AT_e2_as_o1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   LinkText.class,
		   int[].class};	
		   
	public static final Class<?>[] AT_e2_as_s1_o1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class};	
	
	//----
	
	public static final Class<?>[] AT_e1_s2_o1_i1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_i1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class};		   
	
	public static final Class<?>[] AT_e2_s2_o1_i1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class};	
	
	//----
	
	public static final Class<?>[] AT_e1_s2_o1_i1_ai = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_i1_ai = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class};		   
	
	public static final Class<?>[] AT_e2_s2_o1_i1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class};	
	
	// int[]
	
	public static final Class<?>[] AT_e1_s2_o1_i1_ao = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_i1_ao = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class};		   
	
	public static final Class<?>[] AT_e2_s2_o1_i1_ao = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1_ao = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class};	
	
	// without prefix END	   
	// with prefix    START	   
	
	public static final Class<?>[] AT_e1_s3 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   String.class};
	
	public static final Class<?>[] AT_e1_as_s2 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   String.class};		   
		   
	public static final Class<?>[] AT_e2_s3 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   String.class};
	
	public static final Class<?>[] AT_e2_as_s2 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   String.class};			   
		   
	//--	   
		   
	public static final Class<?>[] AT_e1_s2_o1_s1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   String.class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_s1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   String.class};		   
		   
	public static final Class<?>[] AT_e2_s2_o1_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   String.class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   String.class};		   
		   
	//---	   
		   
	public static final Class<?>[] AT_e1_s2_o1_i1_s1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   String.class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_i1_s1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   String.class};		   
		   
	public static final Class<?>[] AT_e2_s2_o1_i1_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   String.class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   String.class};		   
		   	   
	public static final Class<?>[] AT_e1_s2_o1_i1_ai_s1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class,
		   String.class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_i1_ai_s1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class,
		   String.class};		   
		   
	public static final Class<?>[] AT_e2_s2_o1_i1_ai_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class,
		   String.class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1_ai_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   int[].class,
		   String.class};		   
	// int[]	   
		   
	public static final Class<?>[] AT_e1_s2_o1_i1_ao_s1 = new Class<?>[] {
		   Locator.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class,
		   String.class};
	
	public static final Class<?>[] AT_e1_as_s1_o1_i1_ao_s1 = new Class<?>[] {
		   Locator.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class,
		   String.class};		   
		   
	public static final Class<?>[] AT_e2_s2_o1_i1_ao_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class,
		   String.class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1_ao_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   DomOffset[].class,
		   String.class};	
	// with prefix    END	
		   
	public LocatorSelectorCss O_loc_sel_css;
	
	public LocatorSelectorCss FO_get_loc_sel_css(
			final LocatorEnums O_locator_enums) {
		  
		  RuntimeException     E_rt;
		  NullPointerException E_np;
		  
		  Class      <? extends ByCssS> OT_clazz;
		  Constructor<? extends ByCssS> M_ctor;
		  LocatorVariant E_locator_variant;
		  Locator        E_locator;
		  
		  Object O_selector;
		  String S_msg_1, S_msg_2, S_clazz_name,  S_selector, 
		          S_field_name_of_selector 
		    //    S_locator, S_clazz_name_tail
		          ;
		  boolean B_or;
		  LocatorSelectorCss O_retval_loc_sel_css = null;
		  
		  OT_clazz = this.getClass();
		  S_clazz_name = OT_clazz.getSimpleName();
		  S_field_name_of_selector = ByXp.S_std_field_name_of_selector;
		  
		 E_locator_variant =  O_locator_enums.E_locator_variant;
		 E_locator         =  O_locator_enums.E_locator;
		
		              
		try {
			O_selector = FieldUtils.readDeclaredField(this, S_field_name_of_selector, true);
		} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field: \'" + S_field_name_of_selector + "\' from object of type: \'" + this.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		  
		if (O_selector == null) {
			 S_msg_1 = "Result field of type: \'" + String.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" + S_field_name_of_selector + "\' from object of type: \'" + this.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		
		 if (O_selector instanceof String[]) {    // class-names 
			 S_selector = Arrays.toString((String[])O_selector);
			 S_field_name_of_selector = "className";
		     }
		 else if (O_selector instanceof DomOffset[]) {
			 S_selector = Arrays.toString((DomOffset[])O_selector);
		     }
		 else {
		    try {
			  S_selector = (String)O_selector;
		    } catch (ClassCastException PI_E_clsc) {
				S_msg_1 = "Unable to obtain field: \'" + S_field_name_of_selector + "\' from object of type: \'" + O_selector.getClass().getName() + "\""; 
				E_rt = new RuntimeException(S_msg_1, PI_E_clsc);
				throw E_rt;
			    } 
		  }
		 DomVectorExtendedSelector O_using;
		 O_using = new DomVectorExtendedSelector(S_selector);
		 O_retval_loc_sel_css = new LocatorSelectorCss(
				  E_locator, 
				  E_locator_variant, 
				  O_using,
				  (String)null, // tag
				  (LinkText)null,
				  IGNORED_IDX,
				  (String)null, // prefix
				  (Constructor<? extends ByCssS>)null);
				//  (IndexedStrBuilder)null  // this.S_xpath_selector
				 
		  return O_retval_loc_sel_css;   
	}	
	
	//  l o c 
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_s1);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_as);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s2);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final String         expectedTagname) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_s1);
		   return O_retval_by_css;
	 } 
	 
	 
	 	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final LinkText       linkText) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s1_o1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final LinkText      linkText) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_o1);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final LinkText       linkText) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_s1_o1);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final LinkText       linkText) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				    linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_as_o1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final LinkText       linkText) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s2_o1);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final String         expectedTagname,
    		   final LinkText       linkText) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_s1_o1);
		   return O_retval_by_css;
	 }
	 
	 	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s1_i1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_i1);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_s1_i1);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_s1_i1);
		   return O_retval_by_css;
	 }
	
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s2_i1);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator   locator,
    		   final String[]  using,
    		   final String    expectedTagname,
    		   final int       index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_s1_i1);
		   return O_retval_by_css;
	 } 
	  
	 	 public static ByCssS loc(
    		   final Locator   locator,
    		   final String    using,
    		   final LinkText  linkText,
    		   final int       index
    		   ) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s1_o1_i1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator       locator,
    		   final String[]      using,
    		   final LinkText      linkText,
    		   final int           index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_o1_i1);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final LinkText       linkText,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_s1_o1);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final LinkText       linkText,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_as_o1_i1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final LinkText       linkText,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s2_o1_i1);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final String         expectedTagname,
    		   final LinkText       linkText,
    		   final int            index) {
		 
		   ByCssS O_retval_by_css;
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   index,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_s1_o1_i1);
		   return O_retval_by_css;
	 } 
	 
    //---
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s1_ai);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_ai);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final int[]         domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_s1_ai);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_as_ai);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s2_ai);
		   return O_retval_by_css;
	 } 
	 
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final String         expectedTagname,
    		   final int[]          domOffsets)  {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_s1_ai
				   );
		   return O_retval_by_css;
	 } 
	 
	 	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final LinkText       linkText,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s1_o1_ai);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator       locator,
    		   final String[]      using,
    		   final LinkText      linkText,
    		   final int[]         domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   (DomOffset[])null,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_o1_ai);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final LinkText       linkText,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_s1_o1_ai);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final LinkText       linkText,
    		   final int[]          domOffsets 
			  ) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				    linkText,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_as_o1_ai);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final LinkText       linkText,
    		   final int[]          domOffsets
    		   ) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s2_o1_ai);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final String         expectedTagname,
    		   final LinkText       linkText,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_s1_o1_ai);
		   return O_retval_by_css;
	 }
	 
	 	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   
		   DomOffset AO_dom_offsets[];
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s1_i1_ai);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_i1);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final int            index,
    		   final int[]          domOffsets 
			  ) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   XpathGenerators.IGNORED_IDX,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_s1_i1);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   (LinkText)null,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e2_s1_i1_ai);
		   return O_retval_by_css;
	 }
	
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);		   
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_s2_i1_ai);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator   locator,
    		   final String[]  using,
    		   final String    expectedTagname,
    		   final int       index,
    		   final int[]     domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   (LinkText)null,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByXp.AT_e1_as_s1_i1_ai);
		   return O_retval_by_css;
	 } 
	  
	 	 public static ByCssS loc(
    		   final Locator   locator,
    		   final String    using,
    		   final LinkText  linkText,
    		   final int       index,
    		   final int[]     domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s1_o1_i1_ai);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator  locator,
    		   final String[]  using,
    		   final LinkText  linkText,
    		   final int       index,
    		   final int[]     domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_o1_i1_ai);
		   return O_retval_by_css;
	   }
	
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final LinkText       linkText,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_s1_o1);
		   return O_retval_by_css;
	 }
	 
	  public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]       using,
    		   final LinkText       linkText,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   
		   O_retval_by_css = loc(
				   locator,
				   variant,
				   using,
				   DEFAULT_TAG,
				   linkText,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e2_as_o1_i1);
		   return O_retval_by_css;
	 }
	
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String         using,
    		   final String         expectedTagname,
    		   final LinkText       linkText,
    		   final int            index,
    		   final int[]          domOffsets
			 ) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_s2_o1_i1_ai);
		   return O_retval_by_css;
	 } 
	 
	 public static ByCssS loc(
    		   final Locator        locator,
    		   final String[]       using,
    		   final String         expectedTagname,
    		   final LinkText       linkText,
    		   final int            index,
    		   final int[]          domOffsets) {
		 
		   ByCssS O_retval_by_css;
		   DomOffset AO_dom_offsets[];
		   
		   AO_dom_offsets = XpathGenerators.FAO_create_DOM_offsets(domOffsets);
		   O_retval_by_css = loc(
				   locator,
				   LocatorVariant.regular,
				   using,
				   expectedTagname,
				   linkText,
				   index,
				   AO_dom_offsets,
				   CssSGenerators.DEFAULT_PREFIX);
		   
		   O_retval_by_css.O_loc_sel_css.M_ctor = 
				   ConstructorUtils.getAccessibleConstructor(
				   Loc.class,
				   ByCssS.AT_e1_as_s1_o1_i1_ai);
		   return O_retval_by_css;
	 }  
	 
	// ---  basic routines
	  
	public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final String         expectedTagName,
    		   final LinkText       linkText,
    		   final int            index,
    	       final DomOffset      domOffsets[],
    	       final String         prefix)     {
        	
        	ByCssS O_retval_by_css;
        	
        	O_retval_by_css = loc_poly(
					locator,        
					variant,        
					using,          
					expectedTagName,
					linkText,
					index,
					domOffsets,
					prefix); 	
  
        	return O_retval_by_css;
        }	   
		
	    public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using[],
    		   final String         expectedTagName,
    		   final LinkText       linkText,
    		   final int            index,
    	       final DomOffset      domOffsets[],
    	       final String         prefix) {
        	
        	ByCssS O_retval_by_css;
        	
        	O_retval_by_css = loc_poly(
					locator,        
					variant,        
					using,          
					expectedTagName,
					linkText,
					index,             
					domOffsets,
					prefix); 	
  
        	return O_retval_by_css;
        }
	
	protected static ByCssS loc_poly(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final Object         using,
    		   final String         expectedTagName,
    		   final LinkText       linkText,
    		   final int            index,
    		   final DomOffset      domOffsets[],
    		   final String         prefix
    	      ) {
    	   
    	   final LocatorRegularity E_min_loc_regularity = XpathGenerators.LocatorRegularity.xpathgen;
    	   final int I_min_regularity                   = E_min_loc_regularity.ordinal();
    	   Exception    E_cause = null;
    	 
    	   Constructor         M_ctor;
    	   Class<?>            T_clazz /*, T_clazz_super */;
    	   NullPointerException     E_np;
    	   AssertionError           E_assert;
    	   ClassCastException       E_cls_cast;
    	   IllegalArgumentException E_ill_arg;
    	   RuntimeException        E_rt;
    
    	   LocatorVariant E_locator_variant;
    	   Object O_res_by_css;
    	   String  S_msg_1, S_msg_2, S_msg_3, S_tag_name, S_prefix, S_selector, AS_selectors[];
    	   int i1, I_nbr_selectors_f1;
    	    
    	   ByCssS O_retval_by_css = null;
    	   T_clazz              = Loc.class;
    
    	   try {
    	      if (locator == null) {
    		     S_msg_1 = "First argument of type \'" + Locator.class.getSimpleName() + "\' must not be null";
    		     E_cause = new NullPointerException(S_msg_1);
    	         throw E_cause;
    	         }
    	      
    	      LocatorRegularity E_act_loc_regularity;
    	      int I_act_regularity;
    	      
    	      E_act_loc_regularity = locator.E_regularity;
    	      I_act_regularity = E_act_loc_regularity.ordinal();
    	      
    		  if (I_act_regularity < I_min_regularity) {
    			  S_msg_1 = "Actual regularity: \'" + E_act_loc_regularity.name() + "\'(" +  I_act_regularity + 
    				") less than minimum required regularity: \'" + E_min_loc_regularity.name() + "\'"+
    				"(" + I_min_regularity + ").";
    			  E_assert = new AssertionError(S_msg_1);
    			  S_msg_2 = "Implementation restriction: Operation \'" + locator.name() + "\' not (yet) eligible for regular class generation.";
    			  E_cause = new IllegalArgumentException(S_msg_2, E_assert);
    			  throw E_cause;
    		      }
    		   
    		   if (variant == null) {
    			   E_locator_variant = LocatorVariant.regular;
    		       }
    	       else {
    	    	   E_locator_variant = variant;
    	           }
    		   
    		   if (expectedTagName == null) {
    			   S_tag_name = DEFAULT_TAG; 
    		      }
    		   else if (StringUtils.isBlank(expectedTagName)) {
    			   S_msg_1 = "Invalid tag name: \'" + expectedTagName + "\'";
    			   E_cause = new IllegalArgumentException(S_msg_1);
  		    	   throw E_cause;  
    		       }
    		   else {
  		          S_tag_name = expectedTagName;
    		      }
  		       
  		       if (prefix == null) {
  			      S_prefix = DEFAULT_PREFIX; 
  		          }
  		       else if (prefix.equals("")) {
  		    	  S_prefix = prefix;    
  		          }
  		       else if (StringUtils.isBlank(prefix)) {
  			      S_msg_1 = "Invalid prefix: \'" + prefix + "\'";
  			      E_cause = new IllegalArgumentException(S_msg_1);
		          throw E_cause;  
  		          }
  		       else {
		          S_prefix = prefix;
  		          }
		       
		       if (using == null) {
		    	  S_msg_1 = "Argument for selector(s) must not be null"; 
		    	  E_cause = new NullPointerException(S_msg_1); 
		    	  throw E_cause;
		             }
		       if (using instanceof String) {
    			   S_selector = (String)using; 
    			   AS_selectors = new String[1];
    			   AS_selectors[0] = S_selector;
    			   M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_o1_i1_ao_s1);
                   }
		       else {
		          AS_selectors = (String[])using;
		          M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_o1_i1_ao_s1);
		          }
		        
		       I_nbr_selectors_f1 = AS_selectors.length;
		       for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		    	   S_selector = AS_selectors[i1];
	    		   if (StringUtils.isBlank(S_selector)) {
	    			   S_msg_1 = "Invalid selector at index: " + i1 + "\'" + using + "\'";
	    			   E_cause = new IllegalArgumentException(S_msg_1);
	  		    	   throw E_cause;  
	    		       }
		            }
		     
		       if (M_ctor == null) {
		    	   S_msg_1 = "Unable to get new constructor of class \'" + T_clazz.getName() + "\'."; 
		    	   E_np = new NullPointerException(S_msg_1);
		    	   throw E_np;
		           }
		       O_res_by_css = M_ctor.newInstance(locator, E_locator_variant, using, S_tag_name, linkText, index, domOffsets, S_prefix);
		       if (O_res_by_css == null) {
		    	   S_msg_1 = "Unable to get new instance of class \'" + T_clazz.getName() + "\'";
		    	   E_np = new NullPointerException(S_msg_1);
		    	   throw E_np; 
		           }
		       if (!(O_res_by_css instanceof Loc)) {
		    	   S_msg_1 = "\'" + O_res_by_css.getClass().getName() + "\' not a subclass of \'" + T_clazz.getName() + "\'";
		    	   E_cls_cast = new ClassCastException(S_msg_1);
		    	   throw E_cls_cast;
		           }
		       O_retval_by_css = (Loc)O_res_by_css;
    	       }
    	    catch (Exception PI_E_cause) {
			    S_msg_3 = "unable to create instance of subclass of \'" + ByCssS.class.getName() + "\'.";
			    E_rt = new RuntimeException(S_msg_3, PI_E_cause);
			    throw E_rt;
    	   }
    	   // O_retval_by_loc.O_loc_sel_xp.M_ctor = M_ctor;
    	   return O_retval_by_css;   
       }         
         
	public static class Loc extends ByCssS {
		
		 public  Object O_selector;
		 private static final Class<? extends ByCssS> T_clazz = Loc.class;
		  
		 public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using,
				  final String tag,
				  final LinkText linkText,
				  final int      index,
				  final DomOffset[] domOffsets,
				  final String prefix) {
			 
			  LocatorEnums O_loc_enums;
			 
			  this.O_selector = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_css = this.FO_get_loc_sel_css(O_loc_enums);	
		      this.O_loc_sel_css.S_tag_expected = tag;
		      this.O_loc_sel_css.O_lnk_txt = linkText;
		      this.O_loc_sel_css.I_idx_f0  = index;
		      this.O_loc_sel_css.S_prefix  = prefix;
		      this.O_loc_sel_css.M_ctor    = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_o1_i1_ao_s1);
		      this.O_loc_sel_css.SBO_using = CssSGenerators.FSBO_get_csss(
		    		  O_loc_enums,
		    		  (String)this.O_selector,
		    		  tag,
		    		  linkText,
		    		  index,
		    		  domOffsets,
		    		  prefix);
		 }
		 
		 public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String[] using,
				  final String tag,
				  final LinkText linkText,
				  final int    index,
				  final DomOffset[] domOffsets,
				  final String prefix
				) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_css = this.FO_get_loc_sel_css(O_loc_enums);	
		      this.O_loc_sel_css.S_tag_expected = tag;
		      this.O_loc_sel_css.O_lnk_txt = linkText;
		      this.O_loc_sel_css.I_idx_f0  = index;
		      this.O_loc_sel_css.S_prefix  = prefix;
		      this.O_loc_sel_css.M_ctor    = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_o1_i1_ao_s1);
		      this.O_loc_sel_css.SBO_using = CssSGenerators.FSBO_get_csss(
		    		  O_loc_enums,
		    		  (String[])this.O_selector,
		    		  tag,
		    		  linkText,
		    		  index,
		    		  domOffsets,
		    		  prefix);
		      return;
		    }
		
		@Override
		  public List<WebElement> findElements(SearchContext context) {	
			    	
	            List<WebElement> AO_retval_web_elems;
	            Locator E_locator;
	 
	            this.O_loc_sel_css.I_idx_f0 = XpathGenerators.ALL_IDX;
	            E_locator = this.O_loc_sel_css.E_locator;
	            if (E_locator == Locator.idOrName) {
	            	NoSuchElementException E_cause;
	            	 List<WebElement> AO_res_web_elems_by_id, AO_res_web_elems_by_name;
	            	 AO_retval_web_elems = new ArrayList<WebElement>();
	            	 int I_nbr_elems_found_f1;
	            	 
	            	 E_cause = null;
	            	 this.O_loc_sel_css.E_locator = Locator.id;
	            	 try {
	            	    AO_res_web_elems_by_id   = RemoteWebElementCssS.FAO_find_elements_by_css(this);
	            	    }
	            	 catch (NoSuchElementException PI_E_no_such_elem) {
	            		 E_cause = PI_E_no_such_elem;
	            		 AO_res_web_elems_by_id = new ArrayList<WebElement>(); 
	            	   }
	            	 
	            	 this.O_loc_sel_css.E_locator = Locator.name;
	            	 try {
	            	    AO_res_web_elems_by_name  = RemoteWebElementCssS.FAO_find_elements_by_css(this);
	            	    }
	            	 catch (NoSuchElementException PI_E_no_such_elem) {
	            		 E_cause = PI_E_no_such_elem;
	            		 AO_res_web_elems_by_name = new ArrayList<WebElement>(); 
	            	     }
	            	 AO_retval_web_elems.addAll(AO_res_web_elems_by_id);
			         AO_retval_web_elems.addAll(AO_res_web_elems_by_name);
			         I_nbr_elems_found_f1 = AO_retval_web_elems.size();
			         if (I_nbr_elems_found_f1 == 0) {
			        	 if (E_cause != null) {
			        		 throw E_cause;
			        	    }
			             }
	                  }
	            else {
	                AO_retval_web_elems = RemoteWebElementCssS.FAO_find_elements_by_css(this);
	               }
	            return AO_retval_web_elems;	
			    }

			@Override
			public WebElement findElement(SearchContext context) {
			    
		        Locator E_locator;
			    
		        WebElement  O_retval_web_elem;
		        
		        E_locator = this.O_loc_sel_css.E_locator;
		        if (E_locator == Locator.idOrName) {
		           boolean B_use_by_name = false;
		           
		           O_retval_web_elem = null;
		           this.O_loc_sel_css.E_locator = Locator.id;
		           try {
					    O_retval_web_elem = RemoteWebElementCssS.FO_find_element_by_css(this);
				   } catch (NoSuchElementException PI_no_such_elem) {
					   B_use_by_name = true;
				       }
		           if (O_retval_web_elem == null) {
				      B_use_by_name = true;
				      }
		           if (B_use_by_name) {
		        	  this.O_loc_sel_css.E_locator = Locator.name;
		        	  O_retval_web_elem = RemoteWebElementCssS.FO_find_element_by_css(this);
		              }
		           }
		        else {
		        	 O_retval_web_elem = RemoteWebElementCssS.FO_find_element_by_css(this);
		           }
			    return O_retval_web_elem;
			    }
		}
	
	  @Override
	  public boolean equals(final Object PI_O_other) {
		  return true;
	  }
	}
