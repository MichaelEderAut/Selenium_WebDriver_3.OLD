package com.github.michaelederaut.selenium3.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import regexodus.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.UselessFileDetector;

import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.basics.props.PropertyContainer;
import com.github.michaelederaut.basics.props.PropertyContainerArrArrStr;
import com.github.michaelederaut.basics.props.PropertyContainerUtils;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;

/**
* @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium">Mr. Michael Eder</a>
* 
*/
public class RemoteWebElementXp extends RemoteWebElement {
	
	protected static final String  S_Re_driver_info = "\\A\\s*\\[([^\\]]+)\\]\\s+\\-\\Q>\\E\\s+" +
			  "(" + NavigationUtils.S_re_locator_alternatives + ")" +
			  "\\:\\s*(.*?)\\Z";
	protected static final Pattern P_driver_info = new Pattern(S_Re_driver_info);

	protected static final String S_field_name_found_by      = "foundBy";
	public    static final String S_field_xpath_expression   = "xpathExpression";
	protected static final String S_field_name_parent        = "parent";
	protected static final String S_field_name_file_detector = "fileDetector";
	protected static final String S_field_name_id            = "id";

	protected static final String S_key_name_vectors     =   "vectors";
	protected static final String S_key_name_elemcount   =   "elemcount";
	
	public static final String S_clazz_ByXP = ByXp.class.getName();
	
	public DomVectorExtendedSelector SBO_xpath;
	public StringBuffer SB_xpath_cummulated = new StringBuffer();
	public FoundBy      O_found_by;
	public int          I_nbr_duplicates_f1; // nbr elems with idential xpath
	public String       S_tag_received;
	public String       S_inner_txt;
	public String       S_txt_content; // Selenium: lintText, html >xxxxx< - but doesn't work for html-functions
	public String       S_inner_html;
	public String       AAS_attrs[][], AAS_comp_styles[][], AAS_styles[][];
	public PropertyContainer O_attrs, O_comp_styles, O_styles;
	
	public static class LocatorSelector extends LocatorEnums {
       public DomVectorExtendedSelector SBO_using;
	
	public static void FV_ctor(
			final LocatorSelector     PO_O_by_locator,
			final Locator             PI_E_locator,
			final LocatorVariant      PI_E_locator_variant,
			DomVectorExtendedSelector PI_O_using
			) {
		
		PO_O_by_locator.E_locator         = PI_E_locator;
		PO_O_by_locator.E_locator_variant = PI_E_locator_variant;
		PO_O_by_locator.SBO_using         = PI_O_using;
		return;
	    }
	
	public static void FV_ctor(
			final LocatorSelector PO_O_by_locator,
			final String          PI_S_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector PI_O_using
			) {
		
		 RuntimeException E_rt;
		 Locator          E_locator;
		 LocatorVariant   E_locator_variant;
		 MutableObject<LocatorVariant> OM_locator_variant;
		 String           S_msg_1, S_locator;
	    
		 if (PI_S_locator.equals(S_field_xpath_expression)) {
			S_locator = XpathConcatenator.S_xpath;
		    }
		 else {
			S_locator =  PI_S_locator;
		     }
		 OM_locator_variant = new MutableObject<LocatorVariant>(PI_E_locator_variant);
		 E_locator = XpathGenerators.LocatorEnums.FE_get_locator(PI_S_locator, OM_locator_variant);
		 E_locator_variant =  OM_locator_variant.getValue();
		 
		 FV_ctor(
			PO_O_by_locator,
			E_locator,
		    E_locator_variant,
			PI_O_using);
		 return;
	    }
	
	// java constructors
	
	public LocatorSelector (
	   final String             PI_S_locator,
	   final String             PI_S_using) {
    super (PI_S_locator, LocatorVariant.regular);
	this.SBO_using = new DomVectorExtendedSelector(PI_S_using) ;	
    return;
	}
	
	public LocatorSelector(
	   final String             PI_S_locator,
	   final LocatorVariant     PI_E_locator_variant,
	   final DomVectorExtendedSelector PI_O_using) {
		
	super (PI_S_locator, PI_E_locator_variant);
	this.SBO_using = PI_O_using;	
    return;
	}
       
   public LocatorSelector(
	   final Locator        PI_E_locator,
	   final LocatorVariant PI_E_locator_variant,
	   final DomVectorExtendedSelector PI_O_using) {
	   
	   super (PI_E_locator, PI_E_locator_variant);
	   this.SBO_using = PI_O_using; 
       return;
	}
   
   public LocatorSelector(
		   final LocatorEnums              PI_E_locator_enums,
		   final DomVectorExtendedSelector PI_O_using) {
	   
	   this(PI_E_locator_enums.E_locator, PI_E_locator_enums.E_locator_variant, PI_O_using);
	   return;
   }
   
   public LocatorSelector(
		   final LocatorEnums PI_E_locator_enums) {
	   
	   this(
	      PI_E_locator_enums.E_locator,
	      PI_E_locator_enums.E_locator_variant, 
	      (DomVectorExtendedSelector)null);
	   return;
   }
   
       /**
        * @see <a href="https://stackoverflow.com/questions/29140402/how-do-i-print-my-java-object-without-getting-sometype2f92e0f4">get string representation of some object</a>
        */
       @Override
       public String toString() {
    	   
    	   ToStringBuilder SBO_dump;
    	   String S_retval, S_super;
    	   
    	   S_super = super.toString();  
    	   SBO_dump = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    	   SBO_dump = SBO_dump.appendSuper(S_super);
    	   
    	   if (this.SBO_using != null) {
 	          SBO_dump = SBO_dump.append("xpath-equivalent", this.SBO_using.toString());
 	          }
    	   
//    	   SB_dump.append("locator",  this.E_locator); 
//    	   SB_dump.append("variant",  this.E_locator_variant); 
//    	   SB_dump.append("selector", this.O_using.FS_get_buffer());
    	   S_retval = SBO_dump.toString();
    	   return S_retval;
       }
       
}
	
	public static class LocatorSelectorXp extends LocatorSelector {
		public String                      S_tag_expected;
		public int                         I_idx_f0;
		public String                      S_prefix;
		public Constructor<? extends ByXp> M_ctor;
		
	    protected static void FV_ctor (
				 final LocatorSelectorXp           PO_O_loc_sel_xp,
				 final String                      PI_S_tag_expected,
				 final int                         PI_I_idx_f0,
				 final String                      PI_S_prefix,
				 final Constructor<? extends ByXp> PI_M_ctor) {

			 PO_O_loc_sel_xp.S_tag_expected     =  PI_S_tag_expected;
			 PO_O_loc_sel_xp.I_idx_f0           =  PI_I_idx_f0;
			 PO_O_loc_sel_xp.S_prefix           =  PI_S_prefix;
			
			 PO_O_loc_sel_xp.M_ctor              = PI_M_ctor;
		 }
		 
	    /**
	     * 
	     * @param PI_S_locator {@link String} representation of the {@link Locator}
	     * @param PI_E_locator_variant {@link LocatorVariant}
	     * @param PI_O_using {@link String} selector
	     * @param PI_S_tag_expected {@link String} html tag, defaults to "*"
	     * @param PI_I_idx_f0 <tt>int</tt> - 0 based index of the returned element, defaults to 0
	     * @param PI_S_prefix {@link String} prefix of the xpath expression, defaults to ".//"
	     * @param PI_M_ctor {@link Constructor} for subtype of {@link ByXp}
	     *  the method {@link ConstructorUtils#getAccessibleConstructor(Class, Class[])}<br>
	     *  where: <ul>
	     *  <li>the first {@link Class} argument is a subclass of {@link ByXp}.</li>
	     *  <li>the second {@link Class}[] argument list represent the types for the {@link Constructor Constructor's} parameter list.<br>
	     *  e.g.:
	     *  <ul>
	     *  <li>{@link ByXp#AT_s1}</li>
	     *  <li>{@link ByXp#AT_s2}</li>
	     *  <li>{@link ByXp#AT_strings_3}</li>
	     *  <li>{@link ByXp#AT_strings_4}</li>
	     *  </ul>
	     *  </li>
	     *  </ul> 
	     * @param PI_SB_xpath_equivalent {@link IndexedStrBuilder} of the xpath equivalent 
	     */
		 public LocatorSelectorXp (
					final String         PI_S_locator,
					final LocatorVariant PI_E_locator_variant,
					final DomVectorExtendedSelector   PI_O_using,
					final String         PI_S_tag_expected,
					final int            PI_I_idx_f0,
					final String         PI_S_prefix,
					final Constructor<? extends ByXp> PI_M_ctor) {
			 super (PI_S_locator, PI_E_locator_variant, PI_O_using);
			 FV_ctor(this, PI_S_tag_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
			 return;
		 }
		
		 /**
		     * 
		     * @param PI_E_locator {@link Enum} representation of the {@link Locator}
		     * @param PI_E_locator_variant {@link LocatorVariant}
		     * @param PI_S_using {@link String} selector
		     * @param PI_S_tag_expected {@link String} html tag, defaults to "*"
		     * @param PI_I_idx_f0 <tt>int</tt> - 0 based index of the returned element, defaults to 0
		     * @param PI_S_prefix {@link String} prefix of the xpath expression, defaults to ".//"
		     * @param PI_M_ctor {@link Constructor} for a subtype of {@link ByXp} obtained by<br>
		     *  the method {@link ConstructorUtils#getAccessibleConstructor(Class, Class[])}<br>
		     *  where: <ul>
		     *  <li>the first {@link Class} argument is a subclass of {@link ByXp}.</li>
		     *  <li>the second {@link Class}[] argument list represent the types for the {@link Constructor Constructor's} parameter list.<br>
		     *  e.g.:
		     *  <ul>
		     *  <li>{@link ByXp#AT_s1}</li>
		     *  <li>{@link ByXp#AT_s2}</li>
		     *  <li>{@link ByXp#AT_strings_3}</li>
		     *  <li>{@link ByXp#AT_strings_4}</li>
		     *  </ul>
		     *  </li>
		     *  </ul>
		     * @param PI_SB_xpath_equivalent {@link IndexedStrBuilder} of the xpath equivalent 
		     */
	      public  LocatorSelectorXp (
	     			final Locator        PI_E_locator,
	     			final LocatorVariant PI_E_locator_variant,
	     			final DomVectorExtendedSelector         PI_O_using,
	     			final String         PI_S_tag_expected,
	     			final int            PI_I_idx_f0,
					final String         PI_S_prefix,
					final Constructor<? extends ByXp> PI_M_ctor) { 
	    	  
	   			 super (PI_E_locator, PI_E_locator_variant, PI_O_using);
	   			 FV_ctor(this, PI_S_tag_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
	   			 return;
	      }

	      @Override
	       public String toString() {
	       ToStringBuilder SBO_dump;  
	       String S_retval, S_super;
	       
	       SBO_dump = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	       
	       S_super = super.toString();
	       SBO_dump = SBO_dump.appendSuper(S_super);

	       SBO_dump = SBO_dump.append("tag", this.S_tag_expected);
	       SBO_dump = SBO_dump.append("idx", this.I_idx_f0);
	       SBO_dump = SBO_dump.append("prefix", this.S_prefix); 
	       S_retval = SBO_dump.toString();
	       return S_retval;
	      }
	}
	            
	public static class FoundBy {
		public SearchContext O_driver_info;
		// public Stack<? extends LocatorSelector> AO_by_locators;
		public Stack<LocatorSelector> AO_by_locators;
	
	protected static void FV_ctor(
			final FoundBy         PO_O_found_by,
			final SearchContext   PI_O_driver_info,
			final Object          PI_O_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector  PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final Constructor<? extends ByXp> PI_M_ctor) {
		 LocatorSelector O_loc_sel_xp;
		
		
	    if (PI_O_driver_info != null) {
	    	PO_O_found_by.O_driver_info = PI_O_driver_info;
	       }
	    PO_O_found_by.AO_by_locators = new Stack<LocatorSelector>();
		if (PI_O_locator instanceof Locator) {
		    O_loc_sel_xp = new LocatorSelectorXp((Locator)PI_O_locator, PI_E_locator_variant, PI_O_using, PI_S_tag_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
		    }
		else {  // locator as String eg "xpath", "id", "name", "value" ...
		    O_loc_sel_xp = new LocatorSelectorXp((String)PI_O_locator, PI_E_locator_variant, PI_O_using, PI_S_tag_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
		    }
		PO_O_found_by.AO_by_locators.push(O_loc_sel_xp);
	    return;
	}
	
	public FoundBy (
			final SearchContext   PI_O_driver_info,
			final String          PI_S_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector  PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final Constructor<? extends ByXp> PI_M_ctor) {
		
		FV_ctor(
			this,
			PI_O_driver_info,
			PI_S_locator,
			PI_E_locator_variant,
			PI_O_using,
			PI_S_tag_expected,
			PI_I_idx_f0,
			PI_S_prefix,
			PI_M_ctor);
		return;
	    }
	
	public FoundBy (
			final SearchContext   PI_O_driver_info,
			final Locator         PI_E_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final Constructor<? extends ByXp> PI_M_ctor) {
		
		FV_ctor(
			this,
			PI_O_driver_info,
			PI_E_locator,
			PI_E_locator_variant,
			PI_O_using,
			PI_S_tag_expected,
		    PI_I_idx_f0,
			PI_S_prefix,
			PI_M_ctor);
		return;
	    }
	}
	
	public static RemoteWebDriver FO_get_parent_driver(
	final RemoteWebElement PI_O_remote_web_element) {

	WebDriverException   E_rt;
	NullPointerException E_np;
	String S_msg_1;
	
	WebDriver O_parent;
	RemoteWebDriver O_retval_remote_web_drv = null;
	
	O_parent = PI_O_remote_web_element.getWrappedDriver();
	
	try {
	    O_retval_remote_web_drv = (RemoteWebDriver)O_parent;
	} catch (ClassCastException | NullPointerException PI_E_class_cast) {
	   S_msg_1 = "Unable to determine value of Field \'" +  S_field_name_found_by + "\'  from argument of type: \'" + WebDriver.class.getName() + "\'"; 
	   E_rt = new WebDriverException(S_msg_1, PI_E_class_cast);
	   throw E_rt;
	   }
	return O_retval_remote_web_drv;
    }
		
	public RemoteWebElement FO_get_reduced_web_element() {
			
		RemoteWebElement O_retval_remote_web_element;
	
		O_retval_remote_web_element = this.FO_get_reduced_web_element((String[][])null);
		return O_retval_remote_web_element;
	}
	
	public RemoteWebElement FO_get_reduced_web_element(
			final String PI_S_selector,
			final String PI_S_locator) {
		
		RemoteWebElement O_retval_remote_web_element;
		String AAS_sel_loc[][] = new String[][] {{PI_S_selector, PI_S_selector}};
		O_retval_remote_web_element = this.FO_get_reduced_web_element(AAS_sel_loc);
		return O_retval_remote_web_element;
	}
	
	public RemoteWebElement FO_get_reduced_web_element(
			final String[][] PI_AAS_loc_sel) {
			
		String AAS_loc_sel[][], AS_loc_sel[];
		
		final String S_set_found_by = "setFoundBy";
		
	    RuntimeException E_rt_1;
		NullPointerException E_np;
		InvalidArgumentException E_inv_arg;
		
		SearchContext O_parent;
		
		// RemoteWebDriver  O_parent_web_driver;
		String S_locator, S_selector, S_msg_1, S_msg_2;
		int i1, I_nbr_loc_sel_f1, I_size_AS_loc_sel;
		RemoteWebElement O_retval_web_element = new RemoteWebElement();
		
		O_retval_web_element.setId(this.id);
		O_retval_web_element.setFileDetector(new UselessFileDetector());
		O_retval_web_element.setParent(this.parent);
		S_locator  = null;
		S_selector = null;
		i1         = -1;
		if ((PI_AAS_loc_sel == null) || (PI_AAS_loc_sel.length == 0))  {
			S_locator = Locator.xpath.toString();
			S_selector = this.SB_xpath_cummulated.toString();
			AAS_loc_sel = new String[][] {{S_locator, S_selector}};
		    }
		else {
			AAS_loc_sel = PI_AAS_loc_sel;
		    }
		I_nbr_loc_sel_f1 = AAS_loc_sel.length;
		try {
			for (i1 = 0; i1 < I_nbr_loc_sel_f1; i1++) {
				AS_loc_sel = AAS_loc_sel[i1];
				if (AS_loc_sel == null) {
					S_msg_1 = "Loctor - selector with index " + i1 + " must not be null";
					E_np = new NullPointerException(S_msg_1);
					throw E_np;
				    }
				I_size_AS_loc_sel = AS_loc_sel.length;
				if (I_size_AS_loc_sel != 2) {
					S_msg_1 = "Invalid size " + I_size_AS_loc_sel + " of array containing the locator and the selector at index: " + i1 + ".";
					E_inv_arg = new InvalidArgumentException(S_msg_1);
					throw E_inv_arg;
				    }
				if (StringUtils.isAnyBlank(AS_loc_sel)) {
				   S_msg_1 = "Invalid combination of locator: \'" + AS_loc_sel[0] + "\', selector: \'" + AS_loc_sel[1] + "\'";
				   E_inv_arg = new InvalidArgumentException(S_msg_1);
					throw E_inv_arg;
				    }
				S_locator  = AS_loc_sel[0];
				S_selector = AS_loc_sel[1];
				if (i1 == 0) {
					O_parent = this.parent;
				    }
				else {
					O_parent = O_retval_web_element;
				    }
			    MethodUtils.invokeMethod(O_retval_web_element, true, S_set_found_by, O_parent, S_locator, S_selector);
			}
	        //	} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException PI_E_invo) {
			} catch (Exception PI_E_invo) {
			S_msg_2 = PI_E_invo.getClass().getName() + " occured at index " + i1 + " when invoking method \'" + S_set_found_by + "\'" +
					 "with arguments: \'" + 
					((this.parent == null) ? null : this.parent.getClass().getName()) + "\', \'" + S_locator + "\', \'" + S_selector + "\'.";  
			E_rt_1 = new RuntimeException(S_msg_2, PI_E_invo);
			throw E_rt_1;
		    }
		
		return O_retval_web_element;
	}
	
	
	public String[] FAS_get_attr_names() {
		
		int i1, I_nbr_keys_f1;
		String S_key, AS_key_val[];
		String AS_retval_keys[] = null;
		
		I_nbr_keys_f1 = this.AAS_attrs.length;
		AS_retval_keys = new String[I_nbr_keys_f1];
		for (i1 = 0; i1 < I_nbr_keys_f1; i1++) {
			 AS_key_val = this.AAS_attrs[i1];
			 S_key = AS_key_val[0];
			 AS_retval_keys[i1] = S_key;
		    }
		
		return AS_retval_keys;
	    }
	
    public String[] FAS_get_comp_style_names() {
		
		int i1, I_nbr_keys_f1;
		String S_key, AS_key_val[];
		String AS_retval_keys[] = null;
		
		I_nbr_keys_f1 = this.AAS_comp_styles.length;
		AS_retval_keys = new String[I_nbr_keys_f1];
		for (i1 = 0; i1 < I_nbr_keys_f1; i1++) {
			 AS_key_val = this.AAS_comp_styles[i1];
			 S_key = AS_key_val[0];
			 AS_retval_keys[i1] = S_key;
		    }
		return AS_retval_keys;
	}

    public String[] FAS_get_style_names() {
	
	int i1, I_nbr_keys_f1;
	String S_key, AS_key_val[];
	String AS_retval_keys[] = null;
	
	I_nbr_keys_f1 = this.AAS_styles.length;
	AS_retval_keys = new String[I_nbr_keys_f1];
	for (i1 = 0; i1 < I_nbr_keys_f1; i1++) {
		 AS_key_val = this.AAS_styles[i1];
		 S_key = AS_key_val[0];
		 AS_retval_keys[i1] = S_key;
	    }
	return AS_retval_keys;
}
    
	public String FS_get_attr_val(final String PI_S_attr_key) {
		
		String S_retval_attr_val;
		
		if (this.O_attrs == null) {
			this.O_attrs = new PropertyContainer();
		    }
		
		S_retval_attr_val = this.O_attrs.FS_get(PI_S_attr_key);
		return S_retval_attr_val;
	    }
	
public String FS_get_comp_style(final String PI_S_comp_style_key) {
		
		String S_retval_comp_style;
		
		if (this.O_comp_styles == null) {
			this.O_comp_styles = new PropertyContainerArrArrStr(this.AAS_comp_styles);
		    }
		
		S_retval_comp_style = this.O_comp_styles.FS_get(PI_S_comp_style_key);
		return S_retval_comp_style;
	    }
	
public String FS_get_style(final String PI_S_style_key) {
	
	String S_retval_comp_style;
	
	if (this.O_styles == null) {
		this.O_styles = new PropertyContainerArrArrStr(this.AAS_styles);
	    }
	
	S_retval_comp_style = this.O_styles.FS_get(PI_S_style_key);
	return S_retval_comp_style;
    }

	public void FV_add (
			final String         PI_S_locator,
			final LocatorVariant PI_E_locator_variant,
			final DomVectorExtendedSelector PI_O_using,
			final String         PI_S_tag_expected,
			final int            PI_I_idx_f0,
			final String         PI_S_prefix,
			final Constructor<? extends ByXp> PI_M_ctor,
			final IndexedStrBuilder    PI_SB_xpath_equivalent) {
	
		LocatorSelectorXp O_by_locator_xp;
		String S_xpath_cummulated_old, S_xpath_cummulated_new;
		
		O_by_locator_xp = new LocatorSelectorXp(
				PI_S_locator, PI_E_locator_variant, PI_O_using, PI_S_tag_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
		S_xpath_cummulated_old = this.SB_xpath_cummulated.toString();
		S_xpath_cummulated_new = XpathConcatenator.FS_append(S_xpath_cummulated_old, PI_SB_xpath_equivalent.FS_get_buffer()); 
		this.SB_xpath_cummulated = new StringBuffer(S_xpath_cummulated_new);
		this.O_found_by.AO_by_locators.push(O_by_locator_xp);
	}
	
	public void FV_add(final FoundBy PI_O_found_by) {
	//	Stack<LocatorSelectorXp> AO_by_locator_xp_dest, AO_by_locator_xp_dest_bak;
		Stack<LocatorSelector> AO_by_locator_xp_dest, AO_by_locator_xp_dest_bak;
	//	List<LocatorSelectorXp> AO_by_locator_xp_source;
		List<LocatorSelector> AO_by_locator_xp_source;
	    FoundBy O_found_by_dest;
	    LocatorSelectorXp O_by_locator_xp_source, O_by_locator_xp_dest;
	    String S_xpath_new, S_xpath_cummulated;
	    int i1, I_nbr_locators_source_f1, I_nbr_locators_dest_f1;
	    
	    O_found_by_dest = this.O_found_by;
	    AO_by_locator_xp_source = PI_O_found_by.AO_by_locators;
	    if (AO_by_locator_xp_source == null) {
	    	return;
	       }
	   
	    AO_by_locator_xp_dest = O_found_by_dest.AO_by_locators;
	    if (AO_by_locator_xp_dest == null) {
	    	O_found_by_dest.AO_by_locators = new Stack<LocatorSelector>();
	    	AO_by_locator_xp_dest = O_found_by_dest.AO_by_locators;
	        }
	    AO_by_locator_xp_dest_bak = AO_by_locator_xp_dest;
	  
	    AO_by_locator_xp_dest     = new Stack<LocatorSelector>();
	    I_nbr_locators_source_f1 = AO_by_locator_xp_source.size();
	    for (i1 = 0; i1 < I_nbr_locators_source_f1; i1++) {
	    	O_by_locator_xp_source = (LocatorSelectorXp)AO_by_locator_xp_source.get(i1);
	    	O_by_locator_xp_dest = new LocatorSelectorXp(
	    			O_by_locator_xp_source.E_locator, 
	    			O_by_locator_xp_source.E_locator_variant, 
	    			O_by_locator_xp_source.SBO_using,
	    			O_by_locator_xp_source.S_tag_expected    == null ? null : new String(O_by_locator_xp_source.S_tag_expected),
	    		    O_by_locator_xp_source.I_idx_f0,		
	    			O_by_locator_xp_source.S_prefix	== null ? null : new String(O_by_locator_xp_source.S_prefix),
	    			O_by_locator_xp_source.M_ctor
	    		//	new IndexedStrBuilder(O_by_locator_xp_source.SB_xpath_equivalent)
	    			);	
	    	AO_by_locator_xp_dest.add(O_by_locator_xp_dest);
	        }
	    I_nbr_locators_dest_f1    = AO_by_locator_xp_dest_bak.size();
	    for (i1 = 0; i1 < I_nbr_locators_dest_f1; i1++) {
	    	O_by_locator_xp_source = (LocatorSelectorXp)AO_by_locator_xp_dest_bak.get(i1);
	    	O_by_locator_xp_dest = new LocatorSelectorXp(
	    			O_by_locator_xp_source.E_locator, 
	    			O_by_locator_xp_source.E_locator_variant, 
	    			O_by_locator_xp_source.SBO_using,
	    			O_by_locator_xp_source.S_tag_expected    == null ? null : new String(O_by_locator_xp_source.S_tag_expected),
	    		    O_by_locator_xp_source.I_idx_f0,	
	    	        O_by_locator_xp_source.S_prefix	== null ? null : new String(O_by_locator_xp_source.S_prefix),
	    	        O_by_locator_xp_source.M_ctor);	
	    	AO_by_locator_xp_dest.add(O_by_locator_xp_dest);
	       }
	    O_found_by_dest.AO_by_locators = AO_by_locator_xp_dest;
	    O_by_locator_xp_dest = (LocatorSelectorXp)AO_by_locator_xp_dest.get(0);
	    S_xpath_cummulated = O_by_locator_xp_dest.SBO_using.FS_get_buffer();
	   
	    I_nbr_locators_dest_f1 = AO_by_locator_xp_dest.size();
	    for (i1 = 1; i1 < I_nbr_locators_dest_f1; i1++) {
	    	 O_by_locator_xp_dest = (LocatorSelectorXp)AO_by_locator_xp_dest.get(i1);
	    	 S_xpath_new = O_by_locator_xp_dest.SBO_using.FS_get_buffer();
	    	 S_xpath_cummulated = XpathConcatenator.FS_append(S_xpath_cummulated, S_xpath_new);
	         }
	    this.SB_xpath_cummulated = new StringBuffer(S_xpath_cummulated);
	    
	    AO_by_locator_xp_dest_bak = null;  // for garbage collection
	    AO_by_locator_xp_source   = null;  //  == " ==
	    S_xpath_cummulated        = null;  //  == " ==
	    return;
	}
	
	public RemoteWebElementXp() {
		super();
		return;
	    }
	
	public RemoteWebElementXp(
			final WebElement      PI_O_web_ele,
			final Locator         PI_E_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final Constructor<? extends ByXp> PI_M_ctor,
			final DomOffset PI_AO_dom_offset_vector[],
			final int       PI_I_nbr_duplicates_f1,
			final String    PI_S_tag_received,
			final String    PI_S_inner_txt,
			final String    PI_S_txt_content,
			final String    PI_S_inner_html,
			final ArrayList<ArrayList<String>> PI_AAS_attrs,
			final ArrayList<ArrayList<String>> PI_AAS_comp_style, // computed style
			final ArrayList<ArrayList<String>> PI_AAS_comp) {
		
		super();

		FV_ctor(
		   this,
		   PI_O_web_ele,
		   PI_E_locator,
		   PI_E_locator_variant,		
		   PI_O_using,	
		   PI_S_tag_expected,
		   PI_I_idx_f0,
		   PI_S_prefix,
		   PI_M_ctor,
		   PI_AO_dom_offset_vector,
		   PI_I_nbr_duplicates_f1,
		   PI_S_tag_received,
		   PI_S_inner_txt,
		   PI_S_txt_content,
		   PI_S_inner_html,
		   PI_AAS_attrs,
		   PI_AAS_comp_style,  // computed style
		   PI_AAS_comp);
	}

	protected static void FV_ctor(
			final RemoteWebElementXp PO_O_rem_web_elem_xp,
			final WebElement         PI_O_web_elem,
			final Locator            PI_E_locator,
			final LocatorVariant     PI_E_locator_variant,
			final DomVectorExtendedSelector PI_O_using,
			final String             PI_S_tag_expected,
			final int                PI_I_idx_f0,
			final String             PI_S_prefix,
			final Constructor<? extends ByXp> PI_M_ctor, 
			final DomOffset PI_AO_dom_offset_vector[],
			final int       PI_I_nbr_duplicates_f1,
			final String    PI_S_tag_received,
			final String    PI_S_inner_txt,
			final String    PI_S_txt_content,
			final String    PI_S_inner_html,
			final ArrayList<ArrayList<String>> PI_AAS_attrs,
			final ArrayList<ArrayList<String>> PI_AAS_comp_style,  // computed style
			final ArrayList<ArrayList<String>> PI_AAS_style) {
		
		Exception                E_cause;
		RuntimeException         E_rt;
		ClassCastException       E_clsc;
		IllegalArgumentException E_ill_arg;
		NullPointerException     E_np;
		
		FileDetector  O_file_detector;
		RemoteWebDriver   O_parent;
		ArrayList<String> AS_attr, AS_style;
		String        S_id, S_found_by, S_attr_name, S_attr_val, S_style_name, S_style_val;
		
		RemoteWebElement   O_remote_web_elem;
		Object             O_intermediary;
		String S_msg_1, S_msg_2;
		int i1, I_nbr_attrs_f1, I_nbr_comp_styles_f1, I_nbr_styles_f1;
		
		E_cause = null;
		if (PI_O_web_elem == null) {
			S_msg_1 = "Element of type: " +  WebElement.class.getName()  + " must not be null";
			E_cause = new IllegalArgumentException(S_msg_1);
		   }
		else if (!(PI_O_web_elem instanceof RemoteWebElement)) {
			S_msg_1 = "Unable to cast " + PI_O_web_elem.getClass().getName() + " to " + RemoteWebElement.class.getName();
			E_cause = new ClassCastException(S_msg_1);
		    }
		if (E_cause != null) {
			S_msg_2 = "Error obtaining object of type: " + WebElement.class.getName() + " from first method argument \'" + PI_O_web_elem + "\'." ;
			E_rt = new RuntimeException(S_msg_2, E_cause);
			throw E_rt;
		   }
		
		O_remote_web_elem = (RemoteWebElement)PI_O_web_elem;
		O_parent = FO_get_parent_driver(O_remote_web_elem);
		
		E_cause = null;
		if (PI_E_locator == null) {
			S_msg_1 = "Argument for locator must not be null";
			E_cause = new NullPointerException(S_msg_1); 
			}
		else { 	
			PO_O_rem_web_elem_xp.O_found_by = new FoundBy(
					O_parent, 
					PI_E_locator, 
					PI_E_locator_variant, 
					PI_O_using, 
					PI_S_tag_expected, 
					PI_I_idx_f0,
					PI_S_prefix,
					PI_M_ctor); 
		   }
		
		 if (E_cause != null) {  
			S_msg_2 = "Unable to determine type of locator." + System.lineSeparator() +
					  "must be a " + String.class.getName() + " or an enum " + Locator.class.getName() + ",";
            E_rt = new RuntimeException(S_msg_2, E_cause);
            throw E_rt;
		    }

		O_intermediary = null;
		try {
			O_intermediary = FieldUtils.readDeclaredField(O_remote_web_elem, S_field_name_id, true);
		} catch (IllegalArgumentException | IllegalAccessException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field: \'" + S_field_name_id + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		
		 if (O_intermediary == null) {
			 S_msg_1 = "Result field of type: \'" + String.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" + S_field_name_id + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		 
		 try {
			S_id = (String)O_intermediary;
		} catch (ClassCastException PI_E_clsc) {
			S_msg_1 = "Unable to obtain field: \'" + S_field_name_id + "\' from object of type: \'" + O_intermediary.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_clsc);
			throw E_rt;
		    } 
		 
		try {
			O_intermediary = FieldUtils.readDeclaredField(O_remote_web_elem, S_field_name_file_detector, true);
		} catch (IllegalArgumentException | IllegalAccessException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field: \'" + S_field_name_file_detector + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		
		 if (O_intermediary == null) {
			 S_msg_1 = "Result field of type: \'" + FileDetector.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" + S_field_name_file_detector + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		 
		 try {
			O_file_detector = (FileDetector)O_intermediary;
		 } catch (ClassCastException PI_E_clsc) {
			S_msg_1 = "Unable to obtain field: \'" + S_field_name_file_detector + "\' from object of type: \'" + O_intermediary.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_clsc);
			throw E_rt;
		    } 

		 S_found_by = FS_get_found_by(O_remote_web_elem);
		 O_parent = FO_get_parent_driver(O_remote_web_elem);
		 
		 PO_O_rem_web_elem_xp.setId(S_id);
		 PO_O_rem_web_elem_xp.setFileDetector(O_file_detector);
		 PO_O_rem_web_elem_xp.setParent(O_parent);
		 FV_set_found_by(PO_O_rem_web_elem_xp, S_found_by);
	     PO_O_rem_web_elem_xp.SBO_xpath             = new DomVectorExtendedSelector(PI_AO_dom_offset_vector);
		
		 PO_O_rem_web_elem_xp.SB_xpath_cummulated  = new StringBuffer(PI_O_using.FS_get_buffer());
		 
		 PO_O_rem_web_elem_xp.I_nbr_duplicates_f1  = PI_I_nbr_duplicates_f1;
		 
		 PO_O_rem_web_elem_xp.S_tag_received       = PI_S_tag_received;
		 PO_O_rem_web_elem_xp.S_inner_txt          = PI_S_inner_txt;
		 PO_O_rem_web_elem_xp.S_txt_content        = PI_S_txt_content;
		 PO_O_rem_web_elem_xp.S_inner_html         = PI_S_inner_html;
		 if (PI_AAS_attrs != null) {
			PO_O_rem_web_elem_xp.AAS_attrs = PropertyContainerUtils.FLLS_to_array(PI_AAS_attrs);
//			I_nbr_attrs_f1 = PI_AAS_attrs.size();
//			PO_O_rem_web_elem_xp.AAS_attrs = new String[I_nbr_attrs_f1][2];
//			for (i1 = 0; i1 < I_nbr_attrs_f1; i1++) {
//				AS_attr = PI_AAS_attrs.get(i1);
//				S_attr_name = AS_attr.get(0);
//				S_attr_val = AS_attr.get(1);
//				PO_O_rem_web_elem_xp.AAS_attrs[i1][0] = S_attr_name;
//			    PO_O_rem_web_elem_xp.AAS_attrs[i1][1] = S_attr_val;
//			 }
		 }
		 if (PI_AAS_comp_style != null) {
			 PO_O_rem_web_elem_xp.AAS_comp_styles = PropertyContainerUtils.FLLS_to_array(PI_AAS_comp_style);
			 }
		 if (PI_AAS_style != null) {
			 PO_O_rem_web_elem_xp.AAS_styles = PropertyContainerUtils.FLLS_to_array(PI_AAS_style); 
			}
		 
	  return;
	}	
	
	public static List<RemoteWebElementXp> FAO_ctor (
				List<WebElement> PI_AO_web_elems) {
    	List<RemoteWebElementXp> AO_retval_remote_web_ele_xp;
    	
    	AO_retval_remote_web_ele_xp = (List<RemoteWebElementXp>)(List<? extends WebElement>)PI_AO_web_elems;
    	return AO_retval_remote_web_ele_xp;
	    }
		
	public static StringBuilder FS_get_root_element (final int PI_AI_DOM_offset_vector[]) {
		
		StringBuilder SB_retval_document_root = new StringBuilder("document.body");
		if (PI_AI_DOM_offset_vector == null) {
			return SB_retval_document_root;
		    }
		
		InvalidArgumentException E_ill_arg;
    	/* WebDriverException */ RuntimeException E_rt;
		
		String S_msg_1, S_msg_2;
		int i1, I_len_offset_vector_f1, I_dom_idx_f0;
		
		I_len_offset_vector_f1 = PI_AI_DOM_offset_vector.length;
		for (i1 = 0; i1 < I_len_offset_vector_f1; i1++) {
			 
			   I_dom_idx_f0 = PI_AI_DOM_offset_vector[i1];
			   if (I_dom_idx_f0 < 0) {
				   S_msg_1 = "Invalid negative offset: " + I_dom_idx_f0 + " at index: " + i1;
	    		   E_ill_arg = new InvalidArgumentException(S_msg_1);
	    		   S_msg_2 = "Unable to evaluate parameter of type array of \'" + int.class.getName() + "\'";
	    		   E_rt = new RuntimeException(S_msg_2, E_ill_arg);
	    		   throw E_rt;
			       }
			   SB_retval_document_root.append(".children[" + I_dom_idx_f0 + "]");
			   }
		return SB_retval_document_root;
	}
	
	public static StringBuilder FS_generate_root_element (final DomOffset PI_AO_dom_offsets[]) {
		StringBuilder SB_retval_document_root;
		int AI_DOM_offsets[];
		
		AI_DOM_offsets = XpathGenerators.FAI_reduce_DOM_offset_vector(PI_AO_dom_offsets);
		SB_retval_document_root = FS_get_root_element(AI_DOM_offsets);
		
		return SB_retval_document_root;
	}
	
	public static List<WebElement> FAO_find_elements_by_xpath(
			final ByXp PI_O_locator) {
	
		InvalidArgumentException E_ill_arg;
    	WebDriverException E_rt;
    	
		RemoteWebElement O_res_web_element;
		RemoteWebElementXp O_res_web_element_xp;
		RemoteWebDriver O_web_driver_parent;
		AbstractMap<String, ? extends Object>  HO_res_exec;
		Object O_res_exec, O_res_exec_dummy, O_res_extended_element, O_res_exec_elements_extended, 
		       O_res_element_count, O_res_DOM_offset_vector, O_res_attrs, O_res_style, O_res_comp_style;
		ArrayList<Object>  AO_res_exec_elements_extended;  
		ArrayList<ArrayList<Object>> AA_DOM_offset_vector;
		ArrayList<ArrayList<String>> AAS_attrs, AAS_style, AAS_comp_style; 

		ArrayList<Object> A_DOM_offset, AO_extended_element;
		DomOffset AO_DOM_offset_vector_received[], O_DOM_offset_received, AO_DOM_offset_vector_requested[];
		
		LocatorSelectorXp O_by_locator_xp;
		StringBuilder     SB_document_root;
		DomVectorExtendedSelector SB_xpath_equivalent;
		String S_xpath_unindexed, S_cmd_js_multiple, S_web_driver_parent, S_found_by, 
		       S_dom_node_name, S_err_msg, S_tag_received, S_inner_txt, S_txt_content, S_inner_html;
		boolean B_retry_needed;
		long    L_dom_idx_f0, L_nbr_elems_f1;
		Integer IO_requested_idx_f0;
		int     I_requested_idx_f0, I_nbr_returned_elems_f1, i1, i2_up, i2_down, I_len_offset_vector_f1, I_dom_idx_f0; 
		
		Stack<WebElement> AO_retval_web_element = new Stack<WebElement>();
		
		O_by_locator_xp = PI_O_locator.O_loc_sel_xp;
		AO_DOM_offset_vector_requested = O_by_locator_xp.SBO_using.AO_dom_offsets;
		SB_document_root = FS_generate_root_element(AO_DOM_offset_vector_requested);
		
		SB_xpath_equivalent =  O_by_locator_xp.SBO_using;
		S_xpath_unindexed = XpathConcatenator.FS_unindex(SB_xpath_equivalent);
		I_requested_idx_f0 = O_by_locator_xp.I_idx_f0;
		if (I_requested_idx_f0 == XpathGenerators.IGNORED_IDX) {
			I_requested_idx_f0 = 0;
		    }
		IO_requested_idx_f0 = new Integer(I_requested_idx_f0);
		// developer.mozilla.org/en-US/docs/Web/API/Window/getComputedStyle
		// about javascript-object-properties see: 
		// stackoverflow.com/questions/7306669/how-to-get-all-properties-values-of-a-javascript-object-without-knowing-the-key
		S_cmd_js_multiple = 
				"var H_retval = {}; " + 
				"var AAA_vectors = []; " +
				"var I_req_idx_f0 = arguments[0]; "	+
				"var AO_attrs  = [];" +
				"var AS_attr    = [];" +  // pair: [attr-key, attr-val]
//				"var HS_props  = {};" +
//				"var AS_prop_keys = [];" +
                "var O_comp_style = {}; " +  // computed style
                "var O_style = {}; " +       // .style property
                "var AS_style = []; " +       // pair
                "var S_style_key, S_style_val, S_style_val_str; " +
                "var AAS_comp_styles = []; " +
                "var AAS_styles = []; " +
				"var AAS_attrs  = [];" +
			    "var O_iterator, " + 
				"O_node_current, O_node_parent, O_node_prev, O_element_current, " +
			    "B_cont_loop_elems, B_cont_loop_prv, B_add_this_node, AA_offs_vector, " + 
			    "O_elemt_parent, " + 
			    "O_node_retval, O_node_last, S_node_name, " + 
			    "i1, i2, I_nbr_prev_f1, I_node_type_current, " +
			    "I_nbr_attrs_f1, I_nbr_styles_f1, " +
			    "S_tag_name, S_inner_txt, S_txt_content, S_inner_html, S_attr_name, S_attr_val; " +
				"O_iterator = document.evaluate(\"" + S_xpath_unindexed + "\"" +
				", " + SB_document_root + ", null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
		        "i1 = 0; " +
		        "O_node_retval = null; " +
		        "O_node_last = null; " +
		        "if (O_iterator) {" +
		            "B_cont_loop_elems = true; " +
//		            "console.log('Start loop elems:'); " +
		            "LOOP_FOUND_ELEMS: while(B_cont_loop_elems) {" +
		               "B_add_this_node = false; " +
		               "O_node_last = O_iterator.iterateNext(); " +
//		               "console.log('i1.1: ' + i1); " +
//                       "if (O_node_last == '[object HTMLDocument]') {" + 
//                          "O_node_last = null; }" +
		               "if (O_node_last) {" +
                           "O_node_retval = O_node_last; " +
//		                   "console.log('i1.2: ' + i1); " +
		                   "if (I_req_idx_f0 >= 0) { " +
		                      "if (i1 == I_req_idx_f0) {" +
//		                          "console.log('i1.3: ' + i1); " +
		                          "B_add_this_node = true; }" +
		                      "}" +
		                   "else if (I_req_idx_f0 == " + XpathGenerators.ALL_IDX + ") { " + 
		                       "B_add_this_node = true; " +
//	                           "console.log('i1.4: ' + i1); " +
		                       "} " +
		                   "i1++; } " +
		               "else {" +   // already past the end of last qualifiying elem
		                   "if (I_req_idx_f0 == " + XpathGenerators.LAST_IDX + ") { " + 
		                       "B_add_this_node = true; } " +
//		                   "console.log('last loop'); " +
		                   "B_cont_loop_elems = false; } " +
		                   
		               "if (B_add_this_node) { " +
//		                   "console.log('Start loop parents of element :' + i1); " +
		                   "AA_offs_vector = []; " +
		                   "AAS_styles = []; " +
                           "AAS_comp_styles = []; " +
		                   "O_node_current = O_node_retval; " +
		                   "S_tag_name    = O_node_current.tagName; " +
		                   "S_inner_txt   = O_node_current.innerText; " +
		                   "S_txt_content = O_node_current.textContent; " +
		                   "S_inner_html  = O_node_current.innerHTML; " +
		                   "AO_attrs      = O_node_current.attributes; " +
	//	                   "AS_prop_keys  = Object.keys(O_node_current); " +
		                   "O_comp_style  = window.getComputedStyle(O_node_current); " +
              	           "O_style = O_node_current.style; " +
		                   "LOOP_PARENTS: while (O_node_current) { " +
		                       "I_node_type = O_node_current.nodeType; "  +
//		                       "console.log('Node Type 1: ' + I_node_type); " +
		                       "if (I_node_type != 1) {"  +
//		                           "console.log('Node Type 2: ' + I_node_type); " +
		                           "break LOOP_PARENTS; } " +
		                       "S_node_name = O_node_current.nodeName; " +
//		                       "console.log('Node-Name 1: ' + S_node_name); " +    
		                       "if (S_node_name == 'BODY') { " +
		                           "break LOOP_PARENTS; } " +
		                       "if (S_node_name == 'HTML') { " +
		                           "break LOOP_PARENTS; } " +
		                       "if (S_node_name == '#document') {" +
		                           "break LOOP_PARENTS; } " +
		                       "B_cont_loop_prv = true; " +
		                       "I_nbr_prev_f1 = 0; " +
		                       "O_node_prev = O_node_current; " +   
		                       "LOOP_PRV_SIBLINGS: while (B_cont_loop_prv) { " + 
		                           "O_node_prev = O_node_prev.previousElementSibling; " +
//		                           "console.log('I_nbr_prev 1: ' + I_nbr_prev_f1); " +
		                           "if (!O_node_prev) {" +
//		                               "console.log('I_nbr_prev 2: ' + I_nbr_prev_f1); " +
		                               "break LOOP_PRV_SIBLINGS; } " +
		                           "I_nbr_prev_f1++; }" +  
		                       "AA_offs_vector.push([I_nbr_prev_f1, S_node_name]); " +
//		                       "console.log('Node-Type: ' + I_node_type); " +
//		                       "console.log('Node-Name: ' + S_node_name); " +
		                       "O_node_current = O_node_current.parentNode; " +
		                       "} " +   // END LOOP_PARENTS
//		                     "console.log('After LOOP_PARENTS of selected elem'); " +
                             "I_nbr_attrs_f1 = AO_attrs.length; " +  
//                             "console.log('nbr attrs: ' + I_nbr_attrs_f1); " +
                             "AAS_attrs = []; " +
             	             "for (i2 = 0; i2 < I_nbr_attrs_f1; i2++) { " +
        	                     "O_attr = AO_attrs[i2]; " +
        	                     "S_attr_name  = O_attr.name; " +
        	                     "S_attr_value = O_attr.value; " +
//        	                     "console.log('name: ' + S_attr_name + ' - value: ' + S_attr_value); " +
        	                     "AS_attr = [S_attr_name, S_attr_value]; " +
        	                     "AAS_attrs.push(AS_attr); " +
        	                     "} " +
        	                  "I_nbr_styles_f1 = O_comp_style.length; " +
//        	                  "console.log('Number of comp-styles: ' + I_nbr_styles_f1); " + 
                              "AAS_comp_styles = []; " +
        	                  "for (i2 = 0; i2 < I_nbr_styles_f1; i2++) { " +
        	                     "S_style_key = O_comp_style[i2]; " +
//         	                     "console.log('Style-Key: ' + S_style_key); " + 
        	                    "S_style_val = O_comp_style.getPropertyValue(S_style_key); " +
//     	                        "console.log('name comp: ' + S_style_key + ' - value: ' + S_style_val); " +
//        	                     "console.log('Style-Val-Comp: ' + S_style_val); " + 
        	                     "AS_style = [S_style_key, S_style_val]; " +
                                 "AAS_comp_styles.push(AS_style); " +    // computed styles
        	                  "} " +  
        	                  "AAS_styles = []; " +
                              "for (S_style_key in O_style) {" +
                                 "S_style_val = O_style[S_style_key]; " +
                                 "if (S_style_val) {" +
                                    "S_style_val_str = S_style_val.toString(); " +
//        	                        "console.log('name: ' + S_style_key + ' - value: ' + S_style_val_str); " +
//        	                        "console.log('src: ' + S_style_val.toSource()); " +
                                    "AS_style = [S_style_key, S_style_val_str]; " +
                                    "AAS_styles.push(AS_style); " + 
                                 "}} "+
		                  "AAA_vectors.push([O_node_retval, AA_offs_vector, S_tag_name, S_inner_txt, S_txt_content, S_inner_html, AAS_attrs, AAS_comp_styles, AAS_styles]); " + 
		            "}}} " +  // end of LOOP_FOUND_ELEMS
//		         "console.log('After if (O_iterator)'); " +
//		         "console.log('Node-Retval 1: ' + O_node_retval); "+    
                 "H_retval = {" + S_key_name_vectors + " : AAA_vectors, " + S_key_name_elemcount + " : i1}; " +
//		         "console.log('H_retval = ...'); " +
		         "return H_retval;";
		
		    // Workaround for an occasional bug in the Selenium InternetExplorer-Driver-Server 3.9.x.x .
		    // Before executing a script, a dummy findByXpath(...) operation has to be executed at least once
		    B_retry_needed = false;
		    O_res_exec = null;
		    try {
				O_res_exec = NavigationUtils.O_rem_drv.executeScript(
						S_cmd_js_multiple, IO_requested_idx_f0);
			} catch (Exception PI_E_jscript) {
				if (NavigationUtils.O_rem_drv instanceof InternetExplorerDriver) {
				   if (PI_E_jscript instanceof JavascriptException) {
					   S_err_msg = PI_E_jscript.getMessage();
					   if (S_err_msg.startsWith("Error from JavaScript: 'XPathResult' is undefined") || // new error message
					       S_err_msg.startsWith("Error executing JavaScript") ||  // intermediate error message
						   S_err_msg.startsWith("JavaScript error")) {            // old error message
				          B_retry_needed = true;
					      }
					   }
				    }
				if (B_retry_needed) {
					O_res_exec_dummy = NavigationUtils.O_rem_drv.findElementsByXPath("/html");
					O_res_exec = NavigationUtils.O_rem_drv.executeScript(
							     S_cmd_js_multiple, IO_requested_idx_f0);
				    }
				else {
					throw PI_E_jscript;
				}
			}
			HO_res_exec = (AbstractMap<String, ? extends Object>)O_res_exec;
			O_res_element_count  =  HO_res_exec.get(S_key_name_elemcount);
		    L_nbr_elems_f1       = (Long)O_res_element_count; 
		    
			O_res_exec_elements_extended  =  HO_res_exec.get(S_key_name_vectors);
			AO_res_exec_elements_extended = (ArrayList<Object>)O_res_exec_elements_extended;
			S_found_by = null;
			I_nbr_returned_elems_f1 = AO_res_exec_elements_extended.size();
			for (i1 = 0; i1 < I_nbr_returned_elems_f1; i1++) {
				O_res_extended_element = AO_res_exec_elements_extended.get(i1);
				AO_extended_element = (ArrayList<Object>)O_res_extended_element;
				O_res_web_element   = (RemoteWebElement)AO_extended_element.get(0);
				if (i1 == 0) {
			       O_web_driver_parent = FO_get_parent_driver(O_res_web_element);
			       S_web_driver_parent = O_web_driver_parent.toString();
			       S_found_by = String.format("[%s] -> %s: %s", S_web_driver_parent, XpathConcatenator.S_xpath, SB_xpath_equivalent.FS_get_buffer());
				   }
    		    FV_set_found_by(O_res_web_element, S_found_by);
			    O_res_DOM_offset_vector = AO_extended_element.get(1);
			    AA_DOM_offset_vector = (ArrayList<ArrayList<Object>>)O_res_DOM_offset_vector;
			    I_len_offset_vector_f1 = AA_DOM_offset_vector.size();
			    AO_DOM_offset_vector_received = new DomOffset[I_len_offset_vector_f1];
				i2_down = I_len_offset_vector_f1 - 1;
				for (i2_up = 0; i2_up < I_len_offset_vector_f1; i2_up++) { 
					A_DOM_offset = AA_DOM_offset_vector.get(i2_up);
					L_dom_idx_f0 = (Long)(A_DOM_offset.get(0));
					I_dom_idx_f0 = (int)L_dom_idx_f0;
					S_dom_node_name = (String)(A_DOM_offset.get(1));
					O_DOM_offset_received = new DomOffset(I_dom_idx_f0, S_dom_node_name);
					AO_DOM_offset_vector_received[i2_down] = O_DOM_offset_received;
					i2_down--;
				    }
				S_tag_received    = (String)AO_extended_element.get(2);
				S_inner_txt   = (String)AO_extended_element.get(3);
				S_txt_content = (String)AO_extended_element.get(4);
				S_inner_html  = (String)AO_extended_element.get(5);
				O_res_attrs   = AO_extended_element.get(6);
				AAS_attrs     = (ArrayList<ArrayList<String>>)O_res_attrs;
				O_res_comp_style  = AO_extended_element.get(7);
				AAS_comp_style    = (ArrayList<ArrayList<String>>)O_res_comp_style;
				O_res_style   = AO_extended_element.get(8);
				AAS_style     = (ArrayList<ArrayList<String>>)O_res_style;

				O_res_web_element_xp = new RemoteWebElementXp(
						O_res_web_element,
						O_by_locator_xp.E_locator,
						O_by_locator_xp.E_locator_variant,
						O_by_locator_xp.SBO_using,
						O_by_locator_xp.S_tag_expected,
						O_by_locator_xp.I_idx_f0,
						O_by_locator_xp.S_prefix,
						O_by_locator_xp.M_ctor,
						AO_DOM_offset_vector_received,
						(int)L_nbr_elems_f1,
						S_tag_received,
						S_inner_txt,
						S_txt_content,
						S_inner_html,
						AAS_attrs,
						AAS_comp_style,
						AAS_style
						);
				AO_retval_web_element.push(O_res_web_element_xp);
			}	    
		return AO_retval_web_element; 
	}
		
	public static WebElement FO_find_element_by_xpath(
			final ByXp PI_O_locator) {
	
	List<WebElement> AO_web_elements;
	int I_nbr_web_elements;
	WebElement O_retval_web_element = null;
	
	AO_web_elements = FAO_find_elements_by_xpath(PI_O_locator);
	if (AO_web_elements != null) {
		I_nbr_web_elements = AO_web_elements.size();
		if (I_nbr_web_elements >= 1) {
			O_retval_web_element = AO_web_elements.get(0);
		    }
	    }
	
	return O_retval_web_element;
	}
	
	@Override
	public WebElement findElement(By by) {
		
		NoSuchElementException E_nse;
		
		ByXp O_by_xp;
		ByCssS O_by_css;
		
		WebElement O_web_element;
		RemoteWebElementXp O_rem_web_element_xp;
		RemoteWebElementCssS O_rem_web_element_css;
		String S_msg_1, S_starting_out_from_element, S_starting_out_from_element_xp_cumm, S_looking_for_element;
        
	    WebElement O_retval_web_element = null;
	    
		if (by == null) {
			return O_retval_web_element;
		    }
		boolean B_is_xp = false; 
        boolean B_is_css = false;
		if (by instanceof ByXp) {
			B_is_xp = true;
		    }
		else if (by instanceof ByCssS) {
			B_is_css = true;
		}
		
		if (B_is_xp || B_is_css) {
		   if (B_is_xp) {
			   O_by_xp = (ByXp)by;
			   O_by_xp.O_loc_sel_xp.SBO_using.AO_dom_offsets = this.SBO_xpath.AO_dom_offsets;
			   O_web_element = FO_find_element_by_xpath(O_by_xp);
			   O_by_css = null; }
		   else {
				O_by_css = (ByCssS)by;
				O_by_css.O_loc_sel_css.SBO_using.AO_dom_offsets = this.SBO_xpath.AO_dom_offsets;
				O_web_element = RemoteWebElementCssS.FO_find_element_by_css(O_by_css);
			    O_by_xp = null; 
			    }
		   if (O_web_element == null) {
			   S_starting_out_from_element = this.SBO_xpath.toString();
			   S_starting_out_from_element_xp_cumm = this.SB_xpath_cummulated.toString();
			   if (B_is_xp) {
			      S_looking_for_element  = O_by_xp.O_loc_sel_xp.toString();
			      }
			   else {
			       S_looking_for_element = O_by_css.O_loc_sel_css.toString();	  
			       }
			   S_msg_1 = System.lineSeparator() + System.lineSeparator() +
					     "Unable to find element:" + System.lineSeparator() +
					     S_looking_for_element + System.lineSeparator() +
					     "Starting from element:"  + System.lineSeparator() +
					     S_starting_out_from_element_xp_cumm + System.lineSeparator() + 
					     S_starting_out_from_element + System.lineSeparator();
			   E_nse = new NoSuchElementException(S_msg_1);
			   throw E_nse;	     
			   }
			
		   if (B_is_xp) {
			  O_rem_web_element_xp = (RemoteWebElementXp)O_web_element;
			  O_rem_web_element_xp.FV_add(this.O_found_by);
			  O_retval_web_element = O_rem_web_element_xp;
		      }
		   else {
			   O_rem_web_element_css = (RemoteWebElementCssS)O_web_element;
			//   O_rem_web_element_css.FV_add(this.O_found_by);  // TODO
			   O_retval_web_element = O_rem_web_element_css; 
		       }
		   }
		else {
			O_retval_web_element = by.findElement(this); // native Selenium
		    }	    
		return O_retval_web_element;    
		  }
	
	@Override
	public List<WebElement> findElements(By by) {
		
        ByXp   O_by_xp;
        ByCssS O_by_css;
		
		LocatorSelectorXp O_by_locator_xp;
		RemoteWebElementXp O_web_element_xp;
		Stack<WebElement> AO_web_elements_xp;
		List<WebElement> AO_res_web_elements;
		WebElement O_res_web_element;
		
		int i1, I_nbr_found_elems;
	
		List<WebElement> AO_retval_web_elements = new Stack<WebElement>();
		if (by == null) {
			return AO_retval_web_elements;
		    }
		boolean B_is_xp = false; 
        boolean B_is_css = false;
		if (by instanceof ByXp) {
			B_is_xp = true;
		    }
		else if (by instanceof ByCssS) {
			B_is_css = true;
		}

		
		if (B_is_xp || B_is_css) {
		   if (B_is_xp) {
			   O_by_xp = (ByXp)by;
			   O_by_locator_xp = O_by_xp.O_loc_sel_xp;
			   O_by_locator_xp.SBO_using.AO_dom_offsets = this.SBO_xpath.AO_dom_offsets;
			   AO_res_web_elements = FAO_find_elements_by_xpath(O_by_xp);
			   O_by_css = null;
		       }
			else {
				O_by_css = (ByCssS)by;
				O_by_css.O_loc_sel_css.SBO_using.AO_dom_offsets = this.SBO_xpath.AO_dom_offsets;
				AO_res_web_elements = RemoteWebElementCssS.FAO_find_elements_by_css(O_by_css);
			    O_by_xp = null; 
			   }
			if (AO_res_web_elements == null) {
				return AO_retval_web_elements;
			    }
			AO_web_elements_xp = new Stack<WebElement>();
			I_nbr_found_elems = AO_res_web_elements.size();
			for (i1 = 0; i1 < I_nbr_found_elems; i1++) {
				O_res_web_element = AO_res_web_elements.get(i1);
				O_web_element_xp = (RemoteWebElementXp)O_res_web_element;
				O_web_element_xp.FV_add(this.O_found_by);
			    }  
			 AO_retval_web_elements = AO_web_elements_xp;
	         }
		else {  // By
			AO_retval_web_elements = by.findElements(this);
		    }
		return AO_retval_web_elements;
	}
	
	
	@Override
	// see also:
	// stackoverflow.com/questions/4176560/webdriver-get-elements-xpath
    // stackoverflow.com/questions/2631820/im-storing-click-coordinates-in-my-db-and-then-reloading-them-later-and-showing/2631931#2631931
	protected void setFoundBy(SearchContext PI_O_found_from, String PI_S_locator, String PI_S_using) {
		FoundBy O_found_by;
		DomVectorExtendedSelector O_using;
		
		super.setFoundBy(PI_O_found_from, PI_S_locator, PI_S_using);
		O_using = new DomVectorExtendedSelector(PI_S_using);
		if (this.O_found_by == null) {
		   O_found_by = new FoundBy(
				   PI_O_found_from, 
				   PI_S_locator, 
				   LocatorVariant.regular, 
				   O_using,                            // selector
				   (String)null,                       // tag
				   XpathGenerators.IGNORED_IDX,        // idx
				   (String)null,                       // prefix
				   (Constructor<? extends ByXp>)null);  // ctor
		   this.O_found_by = O_found_by;
		   }
		else {
		   this.FV_add(
				   PI_S_locator, 
				   LocatorVariant.regular, 
				   O_using,                         // selector
				   (String)null,                       // tag
				   XpathGenerators.IGNORED_IDX,        // idx
				   (String)null,                       // prefix
				   (Constructor<? extends ByXp>)null,  // ctor 
				   (IndexedStrBuilder)null);	       // xpath-equivalent
		}
	}
		
	protected static void FV_set_found_by(
			final WebElement PB_O_web_ele,
			final String PI_S_set_found_by) {
		
		IllegalArgumentException E_ill_arg;
		RuntimeException E_rt;
		Throwable E_cause;
		
		String           S_msg_1, S_msg_2;
	
	E_cause = null;
	if (PB_O_web_ele == null) {
		S_msg_1 = "Argument of type\'" + RemoteWebElement.class.getName() + "\' must not be null";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		E_cause = E_ill_arg;
	   }
		
	try {
		FieldUtils.writeField(PB_O_web_ele, S_field_name_found_by, PI_S_set_found_by, true);
	} catch (IllegalAccessException PI_E_ill_acc) {
		E_cause = PI_E_ill_acc;
	    }
	
	if (E_cause != null)	{
		S_msg_2 = "Unable to write value: \"" + PI_S_set_found_by + "\" to field named\'" + S_field_name_found_by + "\' to an object of type: \'" + PB_O_web_ele.getClass().getName() + "\'";
		E_rt = new RuntimeException(S_msg_2, E_cause);
		throw E_rt;
	   }
	}
	
	public static String FS_get_found_by(final RemoteWebElement PI_O_remote_web_element) {
		RuntimeException     E_rt;
		NullPointerException E_np;
		String S_msg_1, S_msg_2;
		
		Object O_found_by;
		String S_retval_found_by = null;
		
		if (PI_O_remote_web_element == null) {
			S_msg_1 = "Argument of type: \'" + RemoteWebElement.class.getName() + "' must not be null";
			E_np = new NullPointerException(S_msg_1);
			S_msg_2 = "Unable to determine value of field \'" + S_field_name_found_by + "\'.";
			E_rt = new RuntimeException(S_msg_2, E_np);
			throw E_rt;
		    }
		
		try {
			O_found_by = FieldUtils.readField(PI_O_remote_web_element, S_field_name_found_by, true);
		} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field \'" + S_field_name_found_by + "\'  from argument of type: \'" + PI_O_remote_web_element.getClass().getName() + "\'"; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		
		 if (O_found_by == null) {
			 S_msg_1 = "Result field of type: \'" + String.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" + S_field_name_found_by + "\' from object of type: \'" + PI_O_remote_web_element.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		
		try {
			S_retval_found_by = (String)O_found_by;
		} catch (ClassCastException PI_E_class_cast) {
			S_msg_1 = "Unable to determine value of Field \'" +  S_field_name_found_by + "\'  from argument of type: \'" + O_found_by.getClass().getName() + "\'"; 
			E_rt = new RuntimeException(S_msg_1, PI_E_class_cast);
			throw E_rt;
		    }
		return S_retval_found_by;	
	}
	
	public static LocatorSelector FO_get_loc_sel(
			final String PI_S_found_by) {
		LocatorSelector  O_retval_by_locator;
		
		O_retval_by_locator = RemoteWebElementXp.FO_get_loc_sel(
				PI_S_found_by,
				null); // PO_SB_drv_info
		return O_retval_by_locator;
	}
	
	public static LocatorSelector FO_get_loc_sel(
			final String PI_S_found_by,
			final StringBuffer PO_SB_drv_info) {
		
		IllegalArgumentException E_ill_arg;
		RuntimeException E_rt;
		
		DomVectorExtendedSelector O_using;
		String S_msg_1, S_msg_2, S_driver_info, S_locator, S_using, AS_numbered_groups[];
		LocatorSelectorXp  O_retval_by_locator = null;
		
		GroupMatchResult O_grp_match_result;
		
		O_grp_match_result = RegexpUtils.FO_match(PI_S_found_by, P_driver_info);
		if (O_grp_match_result.I_array_size_f1 < 4) {
		   S_msg_1 = "Argument string: \'" + PI_S_found_by + "\' doesn't match the Pattern: \'" + P_driver_info.toString() + "\'";
		   E_ill_arg = new IllegalArgumentException(S_msg_1);
		   S_msg_2 = "Unable to extract the locator and selector from String: \'" + PI_S_found_by + "\'.";
		   E_rt = new RuntimeException(S_msg_2, E_ill_arg);
	       E_rt.printStackTrace(System.out);
		   return O_retval_by_locator;
		   }
		  
		AS_numbered_groups = O_grp_match_result.AS_numbered_groups;
		S_driver_info     = AS_numbered_groups[1];
		if (PO_SB_drv_info != null) {
			PO_SB_drv_info.append(S_driver_info);
		    }
		S_locator         = AS_numbered_groups[2];
		S_using           = AS_numbered_groups[3];
		
		O_using = new DomVectorExtendedSelector(S_using);
		O_retval_by_locator = new LocatorSelectorXp(
				S_locator, 
				LocatorVariant.regular, 
				O_using, 
				(String)null,    // tag
				XpathGenerators.IGNORED_IDX, 
				(String)null,    // prefix
				null);            // ctor
		return O_retval_by_locator;
	}
	
	public static LocatorSelector FO_get_by_locator(
			final RemoteWebElement PI_O_found_by,
			final StringBuffer PO_drv_info) {
		
		LocatorSelector O_retval_found_by = null;
		String S_found_by;
		
		S_found_by = RemoteWebElementXp.FS_get_found_by(PI_O_found_by);
		O_retval_found_by = FO_get_loc_sel(S_found_by, PO_drv_info);
		
		return O_retval_found_by;
	}
}