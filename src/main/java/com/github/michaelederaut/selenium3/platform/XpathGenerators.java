package com.github.michaelederaut.selenium3.platform;

import static org.apache.commons.lang3.StringUtils.LF;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.text.StrBuilder;
// import org.apache.commons.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
// import org.joox.selector.CSS2XPath; version 1.6.0
import com.github.michaelederaut.basics.joox.selector.CSS2XPath;
import com.github.michaelederaut.basics.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import com.github.michaelederaut.basics.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebElement;

import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.selenium3.framework.ByCssS;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;

import regexodus.Pattern;

/**
* @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;XpathGenerators">Mr. Michael Eder</a>
* 
*/
public class XpathGenerators {
	
//	public static final String DEFAULT_PREFIX   = ".";
	public static final String DEFAULT_PREFIX   = ".//";
	public static final String EMPTY_PREFIX     = "//";  // xpath-prefix: ".//" or "//" in most cases
	public static final String DEFAULT_TAG      = "*";
	public static final String DEFAULT_TAG_LINK = DEFAULT_TAG;  // "a"
	public static final int    I_offset_dflt  = 1;  // for indices in xpath
	public static final int    IGNORED_IDX   = -1;
	public static final int    ALL_IDX       = -2;
	public static final int    LAST_IDX      = -3;
	
	public static final String S_re_clazz_name = "^\\p{Alpha}[\\w\\-]*$";
	public static final Pattern P_clazz_name = Pattern.compile(S_re_clazz_name);
	public static final String S_re_tag_name_requested = "^(\\*|[a-z]+)$";
	public static final Pattern P_tag_name_requested = Pattern.compile(S_re_tag_name_requested);
	public static final String S_re_tag_name_received = "^[A-Z]+$";
	
	public static final String S_field_name_buffer = "buffer";  // contains the payload chars of StrBuilder
	
	public static final String S_prefix_partial    = "Partial";
	public static final String S_suffix_prefix     = "Prefix";
	public static final String S_suffix_suffix     = "Suffix";
	public static final String S_partial_link_text = "partial link text";
	public static final String S_css_sel           = "css selector";
	
//	public static final String S_clazz_by_class_name = XpathGenerators.className.toString();
	
	public static final String S_re_prefix_partial = "^" + S_prefix_partial + "([A-Z]\\w+)$";
	public static final Pattern P_prefix_partial =  Pattern.compile(S_re_prefix_partial);
	public static final String S_re_suffix = "^([A-Z]\\w+)(" + S_suffix_prefix + "|" + S_suffix_suffix + ")$";
	public static final Pattern P_suffix = Pattern.compile(S_re_suffix);
	
	/**
	 * trailing dom offsets
	 */

	public static CssElementCombinatorPairsToXpath O_css_element_combinator_pairs_to_xpath = null;
	
		
	public enum LocatorRegularity {irregular, /* selectorIsSingleField,*/ xpathgen, regular}
	
	/**
	 * Enumeration elements:
	 * <ul>
	 * <li><i>regular</i>: search for attribute value as it is</li> 
	 * <li><i>partial</i>: search for substring of an attribute value</li> 
	 * <li><i>prefix</i>: search for a leading string of an attribute value <tt>starts-with(...</tt></li> 
	 * <li><i>suffix</i>: search for a trailing string of an attribute value</li>
	 * </ul>
	 * for any of the locators:<ul>
	 *   <li>{@link ByXp.ByAlt <i>alt</i>}</li>
	 *   <li>{@link ByXp.ByFor <i>for</i>}</li>
	 *   <li>{@link ByXp.ByHref <i>href</i>}</li>
	 *   <li>{@link ByXp.ById <i>id</i>}</li>
	 *   <li>{@link ByXp.ByIdOrName <i>idOrName</i>}</li> 
	 *   <li>{@link ByXp.ByLinkText <i>linkText</i>}</li>
	 *   <li>{@link ByXp.ByName <i>name</i>}</li>
	 *   <li>{@link ByXp.ByOnClick <i>onClick</i>}</li>
	 *   <li>{@link ByXp.BySrc <i>src</i>}</li>
	 *   <li>{@link ByXp.ByStyle <i>style</i>}</li>
	 *   <li>{@link ByXp.ByTarget <i>target</i>}</li>
	 *   <li>{@link ByXp.ByTitle <i>title</i>}</li>
	 *   <li>{@link ByXp.ByType <i>type</i>}</li>
	 *   <li>{@link ByXp.ByValue <i>value</i>}</li>
	 * </ul> Enumeration elements:<br>
	 *  &nbsp;&nbsp;&nbsp;<i>and</i>, <i>or</i><br>
	 * for the locator {@link ByXp.ByClassName className}<br>
	 * Enumeration element <i>regular</i> for all others.<p/>
	 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium">Mr. Michael Eder</a>
	 *
	 */
	public static enum LocatorVariant {
		regular, 
		partial,
		partialWord,  // applicable for css selectors only
		prefix, 
		prefixWord,   // prefix, matching a (dash separated) word - applicable for css selectors only
		suffix, 
		regexp, 
		and,
		or,
		orPrefixWithSeparatePos // if a prefix is given it is prepended before the entire boolean expression
		                        // applicalbe for css selectors only
		}
	
	/**
	 * The way to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
	 * Find element by ....<p>
	 * 
	 * Enumeration of the elements:
	 * <ul>
	 * <li><i>xpath</i></li>
	 * <li><i>alt</i></li>
	 * <li><i>className</i></li>
	 * <li><i>cssSelector</i></li>
	 * <li><i>for</i></li>
	 * <li><i>href</i></li>
	 * <li><i>id</i></li>
	 * <li><i>linkText</i></li>
	 * <li><i>name</i></li>
	 * <li><i>onClick</i></li>
	 * <li><i>src</i></li>
	 * <li><i>style</i></li>
	 * <li><i>tagName</i></li>
	 * <li><i>target</i></li>
	 * <li><i>title</i></li>
	 * <li><i>type</i></li>
	 * <li><i>value</i></li>
	 * </ul>
	 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium">Mr. Michael Eder</a>
	 *
	 */
	public static enum Locator {
		 action(LocatorRegularity.regular),
		 alt(LocatorRegularity.regular), 
		 className(LocatorRegularity.xpathgen), 
		 cssSelector(LocatorRegularity.xpathgen), 
		 domOffsets(LocatorRegularity.irregular), 
		 forLabel(LocatorRegularity.regular),  // conflict with java keyword "for" !!!
	     href(LocatorRegularity.regular), 
	     id(LocatorRegularity.regular), 
	     idOrName(LocatorRegularity.xpathgen), 
	     linkText(LocatorRegularity.xpathgen), 
	     name(LocatorRegularity.regular), 
	     onClick(LocatorRegularity.regular), 
	     src(LocatorRegularity.regular),
	     style(LocatorRegularity.regular), 
	     tagName(LocatorRegularity.xpathgen), 
	     target(LocatorRegularity.regular), 
	     title(LocatorRegularity.regular), 
	     type(LocatorRegularity.regular), 
	     value(LocatorRegularity.regular),
	     xpath(LocatorRegularity.xpathgen);
		
		public LocatorRegularity E_regularity;
		
	    Locator(final LocatorRegularity PI_E_reg) {
	    	this.E_regularity = PI_E_reg;
	        };
	    }
	
	public static class LocatorEnums {
	    public Locator        E_locator;
        public LocatorVariant E_locator_variant;
        
        public LocatorEnums(final Locator PI_E_locator) {
        	this.E_locator         = PI_E_locator;
        	this.E_locator_variant = LocatorVariant.regular;
        }
        
		public LocatorEnums(final Locator PI_E_locator, final LocatorVariant PI_E_locator_variant) {
			this.E_locator         = PI_E_locator;
			this.E_locator_variant = PI_E_locator_variant;
		    }
        
		public LocatorEnums(final String PI_S_locator, final LocatorVariant PI_E_locator_variant) {
			
			IllegalArgumentException E_ill_arg;
			RuntimeException         E_rt;
			MutableObject<LocatorVariant> O_locator_variant;
			Locator E_locator;
			String S_msg_1, S_msg_2;
			
			if (StringUtils.isBlank(PI_S_locator)) {
				S_msg_1 = "Invalid locator: \'" + PI_S_locator + "\'";
				E_ill_arg = new IllegalArgumentException(S_msg_1);
				S_msg_2 = "Unable to create new instance of \'" + LocatorEnums.class.getName() + "\'";
				E_rt = new RuntimeException(S_msg_2, E_ill_arg);
				throw E_rt;
			    }
			
			O_locator_variant = new MutableObject<LocatorVariant>(PI_E_locator_variant);
			E_locator              = FE_get_locator(PI_S_locator, O_locator_variant);
			this.E_locator         = E_locator;
			this.E_locator_variant = O_locator_variant.getValue();
		}
	
      public LocatorEnums(final String PI_S_locator) {
			
		  this(PI_S_locator, LocatorVariant.regular);
    	  return;
      }
		
      public static Locator FE_get_locator(final String PI_S_locator, MutableObject<LocatorVariant> PO_E_locatorVariant) {
    	  
    	  RuntimeException E_rt;
    	  String S_msg_1;
    	  Locator E_retval_locator = null;
    	 
    	  try {
			  E_retval_locator = Locator.valueOf(PI_S_locator);
		 } catch (IllegalArgumentException PI_E_ill_arg) {
			  if (PI_S_locator.equals("class name")) {
				  E_retval_locator = Locator.className; 
			      }
			  else if (PI_S_locator.equals("css selector")) {
				  E_retval_locator = Locator.cssSelector; 
			      }
			  else if (PI_S_locator.equals("link text")) {
				  E_retval_locator = Locator.linkText; 
			      }
			  else if (PI_S_locator.equals("for")) {  // conflict with java loop-specfic keyword "for" !!!
		      	 E_retval_locator = Locator.forLabel;
		         }
			  else if (PI_S_locator.equals(RemoteWebElementXp.S_field_xpath_expression)) {
		      	 E_retval_locator = Locator.xpath;
		         }
			  else if ((PO_E_locatorVariant != null) && (PI_S_locator.equals(XpathGenerators.S_partial_link_text))) {
		      	 E_retval_locator = Locator.linkText;
		      	 PO_E_locatorVariant.setValue(LocatorVariant.partial);
		         }
			  else {
				 S_msg_1 = "Unable to find element: \'" + PI_S_locator + "\' in enum of type: \'" + Locator.class.getName() + "\'.";
				 E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
				 throw E_rt;
		         }
		     }
    	  return E_retval_locator;
      }
      
	   /**
        * @see <a href="https://stackoverflow.com/questions/29140402/how-do-i-print-my-java-object-without-getting-sometype2f92e0f4">get string representation of some object</a>
        */
       @Override
       public String toString() {
    	   
    	   ToStringBuilder SBO_dump;
    	   String S_retval, S_super;
    	   
    	   SBO_dump = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    	   
    	   SBO_dump.append("locator",  this.E_locator); 
    	   SBO_dump.append("variant",  this.E_locator_variant); 
    	   S_retval = SBO_dump.toString();
    	   return S_retval;
          }	
	}
	
	public static LocatorEnums FO_create_locator_enums(Class<?> PI_T_clazz) {
	
		NullPointerException     E_np;
		IllegalArgumentException E_ill_arg;
		RuntimeException         E_rt;
		
		LocatorVariant   E_locator_variant;
		Locator          E_locator;
		GroupMatchResult O_grp_match_result;
		String           S_msg_1, S_msg_2, S_class_name, S_locator_raw, S_locator, S_variant, S_class_name_tail;
		int              I_len_class_name_f1;
		LocatorEnums     O_retval_locator_enums = null;
	
		try {
			if (PI_T_clazz == null) {
				S_msg_1 = "Input Parameter for class must not be null";
				E_np    = new NullPointerException(S_msg_1);
				throw E_np;
			    }
			
			S_class_name = PI_T_clazz.getSimpleName();
			I_len_class_name_f1 = S_class_name.length();
			if (I_len_class_name_f1 < 3) {
				S_msg_1 = "Lenght of class-name must be at least 3";
				E_ill_arg = new IllegalArgumentException(S_msg_1);
				throw E_ill_arg;
			    }
				
			 S_class_name_tail = S_class_name.substring(2);
			 O_grp_match_result = RegexpUtils.FO_match(S_class_name_tail, XpathGenerators.P_prefix_partial);
			 
			 if (O_grp_match_result.I_array_size_f1 >= 2) {  // partial
				 E_locator_variant = LocatorVariant.partial;
				 S_locator_raw = O_grp_match_result.AS_numbered_groups[1];
			     }
			  else {  // no partial
				 O_grp_match_result = RegexpUtils.FO_match(S_class_name_tail, XpathGenerators.P_suffix);
				 if (O_grp_match_result.I_array_size_f1 >= 3) {  // prefix or suffix
					 S_locator_raw = O_grp_match_result.AS_numbered_groups[1];
					 S_variant = O_grp_match_result.AS_numbered_groups[2];
					 if (S_variant.equals(XpathGenerators.S_suffix_prefix)) {
						E_locator_variant = LocatorVariant.prefix; 
						 }
					 else {
						E_locator_variant = LocatorVariant.suffix;  
					    }
				     }
				 else {  // none of the following: partial, prefix, suffix
					 S_locator_raw =  StringUtils.uncapitalize(S_class_name_tail);
					 E_locator_variant = LocatorVariant.regular;
				    }
			     }
			S_locator = StringUtils.uncapitalize(S_locator_raw);
			E_locator = LocatorEnums.FE_get_locator(S_locator, (MutableObject<LocatorVariant>)null);
		} catch (Exception PI_E_cause) {
		    S_msg_1 = "Unable to create object of type: " + LocatorEnums.class.getName() + "\' from class:\n" +
		    		((PI_T_clazz == null) ? null : PI_T_clazz.getName()); 
		    E_rt = new RuntimeException(S_msg_1, PI_E_cause);
		    throw E_rt;
		    }
		
		O_retval_locator_enums = new LocatorEnums(E_locator, E_locator_variant);
		return O_retval_locator_enums;
	}
	
//	private static class ByClassNameDummy extends By.ByClassName {
//
//		public ByClassNameDummy(String selector) {
//			super(selector);
//		}
//
//		@Override
//		public List<WebElement> findElements(SearchContext context) {
//			return null;
//		}
//	}
	
	// private static final ByClassNameDummy OT_by_class_name = new ByClassNameDummy(null);
	
	/**
	 * This class can be used to generate a node element in an <a href='https://www.guru99.com/xpath-selenium.html#3'><i>Absolute Xpath</i></a>.<br>
	 * It  contains 2 attributes for the context node<ul>
	 * <li><b>I_idx_f0</b>: <tt>int</tt> number of previous sibling elements<br>
	 *  For second parameter <i>contextNode</i> of method 
	 *  <a href="https://developer.mozilla.org/en-US/docs/Web/API/Document/evaluate"><tt>document.evaluate(...)</tt></a><br>
	 *  or the beginning of the only argument of method <a href='https://developer.mozilla.org/de/docs/Web/API/Document/querySelector'><tt>document.QuerySelector(...)</tt></a>.
	 *  </li>
	 * <li><b>S_node_name</b>: {@link String} HTML-tag of this element, defaults to <tt>null</tt>.</li>
	 * </ul>
	 * 
	 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;DomOffset">Mr. Michael Eder</a>
	 * 
	 **/
public static class DomOffset {
		
		public int    I_idx_any_tag_f0;			  // for DOM root-node
		public String S_node_name;				  // for absolute xpath
		public int    I_idx_same_tag_f0;	  // == " ==
		
		public DomOffset(final int PI_I_idx_f0) {
			this(PI_I_idx_f0, (String)null, -1);
			return;
		}
		
		public DomOffset(
				final int PI_I_idx_any_tag_f0, 
				final String PI_I_S_node_name) {
			this(PI_I_idx_any_tag_f0, PI_I_S_node_name, -1);
			return;
		}
		
	public DomOffset(
				final int PI_I_idx_any_tag_f0, 
				final String PI_I_S_node_name,
				final int PI_I_idx_same_tag_f0) {
			this.I_idx_any_tag_f0   = PI_I_idx_any_tag_f0;
			this.S_node_name        = PI_I_S_node_name;
			this.I_idx_same_tag_f0  = PI_I_idx_same_tag_f0;
			return;
		}	
		
		@Override
		public String toString() {
			String S_retval;
			S_retval = "[" + this.I_idx_any_tag_f0 + "," + this.S_node_name + "," + I_idx_same_tag_f0 + "]";
		    return S_retval;
		}
	}

public static int[] FAI_reduce_DOM_offset_vector (final DomOffset PI_AO_dom_offsets[]) {
	int AI_retval_DOM_offsets[] = null;
	
	if (PI_AO_dom_offsets == null) {
		return AI_retval_DOM_offsets;
	    }
	
	InvalidArgumentException E_ill_arg;
	/* WebDriverException */ RuntimeException E_rt;
	DomOffset O_DOM_offset;
	String S_msg_1, S_msg_2, S_node_name;
	int i1, I_len_offset_vector_f1, I_dom_idx_f0;
	
	I_len_offset_vector_f1 = PI_AO_dom_offsets.length;
	AI_retval_DOM_offsets = new int[I_len_offset_vector_f1];
	
	for (i1 = 0; i1 < I_len_offset_vector_f1; i1++) {
		O_DOM_offset = PI_AO_dom_offsets[i1];
		I_dom_idx_f0 = O_DOM_offset.I_idx_any_tag_f0;
		if (I_dom_idx_f0 < 0) {
		    S_msg_1 = "Invalid negative offset: " + I_dom_idx_f0 + " at index: " + i1;
		        E_ill_arg = new InvalidArgumentException(S_msg_1);
		        S_msg_2 = "Unable to process parameter parameter of type array of \'" + DomOffset.class.getName() + "\'";
		        E_rt = new RuntimeException(S_msg_2, E_ill_arg);
		        throw E_rt;
	       }
		AI_retval_DOM_offsets[i1] = I_dom_idx_f0;
	    }
	
	return AI_retval_DOM_offsets;
}

public static DomOffset[] FAO_create_DOM_offsets(final int PI_AI_DOM_offset_vector[]) {
	DomOffset[] AO_retval_dom_offsets = null;
	
	if (PI_AI_DOM_offset_vector == null) {
		return AO_retval_dom_offsets;
	    }
	
	InvalidArgumentException E_ill_arg;
	/* WebDriverException */ RuntimeException E_rt;
	String S_msg_1, S_msg_2;
	DomOffset O_dom_offset;
	int i1, I_nbr_offsets_f1, I_offset_f0;
	
	I_nbr_offsets_f1 = PI_AI_DOM_offset_vector.length;
	AO_retval_dom_offsets = new DomOffset[I_nbr_offsets_f1];
	for (i1 = 0; i1 < I_nbr_offsets_f1; i1++) {
		I_offset_f0 = PI_AI_DOM_offset_vector[i1];
		if (I_offset_f0 < 0) {
		   S_msg_1 = "Invalid negative offset: " + I_offset_f0 + " at index: " + i1;
		   E_ill_arg = new InvalidArgumentException(S_msg_1);
		   S_msg_2 = "Unable to init actual parameter of type array of \'" + DomOffset.class.getName() + "\'";
		   E_rt = new RuntimeException(S_msg_2, E_ill_arg);
		   throw E_rt;
		   }
		O_dom_offset = new DomOffset(I_offset_f0, null);
		AO_retval_dom_offsets[i1] = O_dom_offset;
	    }
	return AO_retval_dom_offsets;
    }

public static StringBuilder FS_generate_abs_xpath(final DomOffset[] PI_AO_dom_offsets) {
	
		IllegalArgumentException E_ill_arg;
		RuntimeException E_rt;
		
		DomOffset O_dom_offset;
		String S_tag, S_msg_1, S_msg_2, S_elem;
		int i1, I_nbr_dom_offsets_f1, I_idx_same_tag_f0, I_idx_same_tag_f1;
	    StringBuilder SB_retval_abs_xpath = null;
		
		if (PI_AO_dom_offsets == null) {
			return SB_retval_abs_xpath;
		    }
		I_nbr_dom_offsets_f1 = PI_AO_dom_offsets.length;
		SB_retval_abs_xpath = new StringBuilder("/html[1]/body[1]");
		for (i1 = 0; i1 < I_nbr_dom_offsets_f1; i1++) {
			O_dom_offset = PI_AO_dom_offsets[i1];
			S_tag = O_dom_offset.S_node_name;
			S_tag = S_tag.toLowerCase();
			I_idx_same_tag_f0 = O_dom_offset.I_idx_same_tag_f0;
			if (I_idx_same_tag_f0 < 0) {
				S_msg_1 = "Invalid 0 based index " + I_idx_same_tag_f0 +  " for absolute xpath on level " + i1 + ".";
				E_ill_arg = new IllegalArgumentException(S_msg_1);
				throw E_ill_arg;
			    }
			
			I_idx_same_tag_f1 = I_idx_same_tag_f0 + 1;
			S_elem = "/" + S_tag + "[" +  "I_idx_same_tag_f1" + "]";
			SB_retval_abs_xpath.append(S_elem);
		    }
		
		return SB_retval_abs_xpath;
	}

	public static class IndexedStrBuilder extends TextStringBuilder {
		public int I_starting_pos_of_idx = -1;
		
		public IndexedStrBuilder() {
			super();
			return;
		}
		public IndexedStrBuilder(final String PI_S_value) {
			super(PI_S_value);
			return;
		}
		
		/**
		 * 
		 * @param PI_S_value {@link String} xpath
		 * @param PI_I_starting_pos_of_idx <tt>int</tt> 0 based starting<br>
		 *         position of the opening bracket of the last index.<br>
		 *         Defaults to -1.
		 *         
		 */
		public IndexedStrBuilder(final String PI_S_value, final int PI_I_starting_pos_of_idx) {
			super(PI_S_value);
			this.I_starting_pos_of_idx = PI_I_starting_pos_of_idx;
			return;
		}
		public IndexedStrBuilder(final IndexedStrBuilder PI_SB_indexed) {
			this(PI_SB_indexed.FS_get_buffer(), PI_SB_indexed.I_starting_pos_of_idx);
			return;
		}
	
		public char[] FAC_get_buffer() {
			RuntimeException     E_rt;
			NullPointerException E_np;
			
			Object O_buffer;
			String S_msg_1, S_msg_2;
			char AC_retval_buffer[] = null;
			
			try {
			    O_buffer = FieldUtils.readField(this, S_field_name_buffer, true);
		    } catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			   S_msg_1 = "Unable to read field \'" + S_field_name_buffer + "\'  from argument of type: \'" + this.getClass().getName() + "\'"; 
			   E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			   throw E_rt;
		       }
			 
			if (O_buffer == null) {
			   S_msg_1 = "Result field of type: \'" + char[].class.getName() + "\' must not be null.";
			   E_np = new NullPointerException(S_msg_1);
			   S_msg_2 =  "Unable to get field: \'" + S_field_name_buffer + "\' from object of type: \'" + this.getClass().getName() + "\""; 
			   E_rt = new RuntimeException(S_msg_2, E_np);
			   throw E_rt;
			   }
			
			try {
				AC_retval_buffer = (char[])O_buffer;
			} catch (ClassCastException PI_E_class_cast) {
				S_msg_1 = "Unable to determine value of Field \'" +  S_field_name_buffer + "\'  from argument of type: \'" + this.getClass().getName() + "\'"; 
				E_rt = new RuntimeException(S_msg_1, PI_E_class_cast);
				throw E_rt;
			    }
			
			return AC_retval_buffer;
		    }
		
		/**
		 * 
		 * Avoid an infinite recursive call of the the {@link StrBuilder#toString()} method.
		 * 
		 * @return A {@link String} containing the result of the {@link StrBuilder#toString()} function.
		 * 
		 */
		public String FS_get_buffer() {
			
			char AC_buffer[];
			int I_size;
			
			String S_retval_buffer = null;
			
			synchronized(this) {
				AC_buffer = this.FAC_get_buffer();
				if (AC_buffer == null) {
				   return S_retval_buffer;
				  }
				
				I_size = this.size();
				S_retval_buffer = new String(AC_buffer, 0, I_size);
			}
			
			return S_retval_buffer;
		}
		
		@Override
		public String toString() {
			ToStringBuilder SB_dump;  
		    String S_retval, S_buffer_content;
		     
		    SB_dump = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	        S_buffer_content = this.FS_get_buffer();
	        SB_dump.append(S_field_name_buffer, S_buffer_content);
		   
		    SB_dump.append("starting pos of idx", this.I_starting_pos_of_idx);
		    S_retval = SB_dump.build();
		    return S_retval;
		}
	}
	
	public static DomVectorExtendedSelector FSBO_index_xpath(
			  final String PI_S_xpath,
			  final int PI_I_idx_f0) {
		
		DomVectorExtendedSelector SB_retval_xpath;
		int I_idx_xpath;
		boolean B_needs_parenthesing;
		
		  if (PI_I_idx_f0 == IGNORED_IDX) {
		     SB_retval_xpath = new DomVectorExtendedSelector(PI_S_xpath);
		     }
		  else {
		     SB_retval_xpath = new DomVectorExtendedSelector();
		     B_needs_parenthesing = XpathConcatenator.FB_needs_parenthesing(PI_S_xpath, true);
             if (B_needs_parenthesing) {
             	SB_retval_xpath.append("(");
                }
             SB_retval_xpath.append(PI_S_xpath);
             if (B_needs_parenthesing) {
             	SB_retval_xpath.append(")");
                }
             SB_retval_xpath.I_starting_pos_of_idx = SB_retval_xpath.size();
             SB_retval_xpath.append("[");
             if (PI_I_idx_f0 == LAST_IDX) {
             	SB_retval_xpath.append("last()");
                 }
             else {
             	I_idx_xpath = PI_I_idx_f0 + I_offset_dflt;
             	SB_retval_xpath.append(I_idx_xpath);
                  }
             SB_retval_xpath.append("]");
		     }
	     return SB_retval_xpath;   
	  }

	public static class DomVectorExtendedSelector extends IndexedStrBuilder	{
		public DomOffset AO_dom_offsets[];
		
		public DomVectorExtendedSelector() {
			super();
			return;
		}
		
		public DomVectorExtendedSelector(final String PI_S_using) {
			super (PI_S_using);
			return;
		}
		
		public DomVectorExtendedSelector(final String PI_S_using, final int PI_I_starting_pos_of_idx) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			return;
		}
		
		public DomVectorExtendedSelector(final IndexedStrBuilder PI_SB_indexed) {
			super(PI_SB_indexed);
			return;
		    }
		
		public DomVectorExtendedSelector(final DomOffset PI_AO_dom_offsets[]) {
			super();
			this.AO_dom_offsets = PI_AO_dom_offsets;
			return;
		    }
		
		public DomVectorExtendedSelector(final String PI_S_using, final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using);
			this.AO_dom_offsets = PI_AO_dom_offsets;
			return;
		    }
		
		public DomVectorExtendedSelector(
				final String PI_S_using, 
				final int PI_I_starting_pos_of_idx, 
				final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			this.AO_dom_offsets = PI_AO_dom_offsets;
			return;
		}
		
		public DomVectorExtendedSelector(final IndexedStrBuilder PI_SB_indexed, final DomOffset PI_AO_dom_offsets[]) {
				super(PI_SB_indexed);
				this.AO_dom_offsets = PI_AO_dom_offsets;
				return;
		}
		
		public DomVectorExtendedSelector(final int PI_AI_dom_offsets[]) {
			super();
			this.AO_dom_offsets = FAO_create_DOM_offsets(PI_AI_dom_offsets);
			return;
		    }
		
		public DomVectorExtendedSelector(final String PI_S_value, final int PI_AI_dom_offsets[]) {
			super(PI_S_value);
			this.AO_dom_offsets = FAO_create_DOM_offsets(PI_AI_dom_offsets);
			return;
		    }
		
		public DomVectorExtendedSelector(final String PI_S_using, final int PI_I_starting_pos_of_idx, final int PI_AI_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			this.AO_dom_offsets = FAO_create_DOM_offsets(PI_AI_dom_offsets);
			return;
		}
		
		public DomVectorExtendedSelector(final IndexedStrBuilder PI_SB_indexed, final int PI_AI_dom_offsets[]) {
				super(PI_SB_indexed);
				this.AO_dom_offsets = FAO_create_DOM_offsets(PI_AI_dom_offsets);
				return;
		}	
		
		@Override
		public String toString() {
		
			DomOffset O_dom_offset, AO_dom_offsets[];
			ToStringBuilder SBO_dump;
			StringBuffer SB_dom_offsets;
		    String S_retval, S_dom_offset, S_dom_offsets, S_super;
		    int i1, I_nbr_dom_offsets_f1, I_nbr_dom_offsets_f0;
		    
		    S_super = super.toString();
		    SBO_dump = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
		    SBO_dump.appendSuper(S_super);
		    
		    SB_dom_offsets = new StringBuffer();
		    AO_dom_offsets = this.AO_dom_offsets;
		    
		    if (AO_dom_offsets != null) {
		    	SB_dom_offsets.append("[");
			    I_nbr_dom_offsets_f1 = AO_dom_offsets.length;
			    I_nbr_dom_offsets_f0 =  I_nbr_dom_offsets_f1 - 1;
			    
			    for (i1 = 0; i1 < I_nbr_dom_offsets_f1; i1++) {
			    	O_dom_offset = this.AO_dom_offsets[i1];
			    	S_dom_offset = O_dom_offset.toString();
			    	SB_dom_offsets.append(S_dom_offset);
			    	if (i1 < I_nbr_dom_offsets_f0) {
			    	   SB_dom_offsets.append(",");
			    	   }
			        }
			    SB_dom_offsets.append("]");
		        }
		    SBO_dump.append("dom-offsets", SB_dom_offsets);	    
		    S_retval = SBO_dump.toString();
		    return S_retval;
			
		}
	}

// R E G U L A R - S T A R T	

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorSelector PI_O_loc_sel) {
	
	DomVectorExtendedSelector O_retval_xpath;
	String S_using;
	
	S_using = PI_O_loc_sel.SBO_using.FS_get_buffer();
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_loc_sel,
			S_using,
			DEFAULT_TAG,
			IGNORED_IDX,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}		
	
public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String PI_S_using) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_S_using,
			DEFAULT_TAG,
			IGNORED_IDX,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}	

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String[] PI_AS_using) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_AS_using,
			DEFAULT_TAG,
			IGNORED_IDX,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String PI_S_using, 
		final String PI_S_tag) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_S_using,
			PI_S_tag,
			IGNORED_IDX,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String[] PI_AS_using, 
		final String PI_S_tag) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_AS_using,
			PI_S_tag,
			IGNORED_IDX,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String PI_S_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_S_using,
			PI_S_tag,
			PI_I_idx_f0,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String[] PI_AS_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_AS_using,
			PI_S_tag,
			PI_I_idx_f0,
			DEFAULT_PREFIX,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String PI_S_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0,
		final String PI_S_prefix) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_S_using,
			PI_S_tag,
			PI_I_idx_f0,
			PI_S_prefix,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector	FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String[] PI_AS_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0,
		final String PI_S_prefix) {
	
	DomVectorExtendedSelector O_retval_xpath;
	
	O_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_AS_using,
			PI_S_tag,
			PI_I_idx_f0,
			PI_S_prefix,
			(DomOffset[])null);
	
	return O_retval_xpath;				
}

public static DomVectorExtendedSelector FSBO_get_xpath (
		final LocatorEnums PI_O_locator_enums,
		final String PI_S_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0,
		final String PI_S_prefix,
		final int PI_AI_dom_offsets[]) {
	
	DomVectorExtendedSelector SBO_retval_xpath;
		
	SBO_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			PI_S_using,
			PI_S_tag,
			PI_I_idx_f0,
			PI_S_prefix,
			PI_AI_dom_offsets
			);
	
	return SBO_retval_xpath;
}

public static DomVectorExtendedSelector FSBO_get_xpath(
		final LocatorEnums PI_O_locator_enums,
		final String[] PI_AS_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0,
		final String PI_S_prefix,
		final int PI_AI_dom_offsets[]) {
	
	DomVectorExtendedSelector SBO_retval_xpath;
	
	DomOffset AO_dom_offsets[];
	
	AO_dom_offsets = FAO_create_DOM_offsets(PI_AI_dom_offsets);
	
	SBO_retval_xpath = FSBO_get_xpath (
			 PI_O_locator_enums,
			 PI_AS_using, 
			 PI_S_tag, 
			 PI_I_idx_f0,
			 PI_S_prefix,
			 AO_dom_offsets);
	
	return SBO_retval_xpath;
}

public static DomVectorExtendedSelector	FSBO_get_xpath (
		final LocatorEnums PI_O_locator_enums,
		final String PI_S_using, 
		final String PI_S_tag, 
		final int    PI_I_idx_f0,
		final String PI_S_prefix,
		final DomOffset PI_AO_dom_offsets[]) {
	
	String AS_using[];
	DomVectorExtendedSelector SBO_retval_xpath;
	
	AS_using = new String[] {PI_S_using};
	SBO_retval_xpath = FSBO_get_xpath(
			PI_O_locator_enums,
			AS_using,
			PI_S_tag,
			PI_I_idx_f0,
			PI_S_prefix,
			PI_AO_dom_offsets
			); 
	
	return SBO_retval_xpath;
	
}

public static DomVectorExtendedSelector	FSBO_get_xpath (
		final LocatorEnums PI_O_locator_enums,
		final String PI_AS_using[], 
		final String PI_S_tag, 
		final int    PI_I_idx_f0,
		final String PI_S_prefix,
		final DomOffset PI_AO_dom_offsets[]) {
		
	RuntimeException         E_rt;
	IllegalArgumentException E_ill_arg;
	NullPointerException     E_np;
	AssertionError           E_assert;
	GroupMatchResult         O_grp_match_result;
	
	String  S_msg_1, S_msg_2, S_xpath, S_prefix, S_attr, S_locator, S_attr_name_prefix, S_using, S_bool_op;
	Locator        E_locator;
	LocatorVariant E_locator_variant;
	
	if (PI_O_locator_enums == null) {
		S_msg_1 = "1st argument of type \'" + LocatorEnums.class.getName() + "\' must not be null.";
		E_np = new NullPointerException(S_msg_1);
		S_msg_2 = "Unable to create new instance of: \'" + DomVectorExtendedSelector.class.getName() + "\'";
		E_rt = new RuntimeException(S_msg_2 ,E_np) ;
		throw E_rt;
	    }
	
	if (PI_AS_using == null) {
		S_msg_1 = "2nd argument for selector(s) of type \'" + String[].class.getName() + "\' must not be null.";
		E_np = new NullPointerException(S_msg_1);
		S_msg_2 = "Unable to create new instance of: \'" + DomVectorExtendedSelector.class.getName() + "\'";
		E_rt = new RuntimeException(S_msg_2 ,E_np) ;
		throw E_rt;
	    }
	
	O_grp_match_result = RegexpUtils.FO_match(PI_S_tag, P_tag_name_requested);
	if (O_grp_match_result.I_array_size_f1 == 0) { 
    	S_msg_1 = "Invalid value for tag: \'" + PI_S_tag + "\'";
    	E_ill_arg = new IllegalArgumentException(S_msg_1);
    	throw E_ill_arg;
        }
	
	DomVectorExtendedSelector SBO_retval_xpath = new DomVectorExtendedSelector("");
	
	int I_nbr_selectors_f1, I_nbr_selectors_f0, i1;
	StringBuffer SB_xpath_tail;
	
	I_nbr_selectors_f1 = PI_AS_using.length;
	
	if (I_nbr_selectors_f1 == 0) {
		S_msg_1 = "Size of array containing class-names must not be " + I_nbr_selectors_f1;
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }
	
	E_locator_variant = PI_O_locator_enums.E_locator_variant;
	E_locator         = PI_O_locator_enums.E_locator;
	
	switch (E_locator) {
	   case className:
		  ArrayList<String>        AS_class_names;
		  TextStringBuilder        SB_xpath;
		  String                   S_containing_word;
		  int  I_idx_old_f0;  
		
		  AS_class_names = new ArrayList<String>();		
		  S_bool_op = null;
		  if (I_nbr_selectors_f1 == 1) {
		     E_locator_variant = LocatorVariant.regular;   
	         }	
		  else if (E_locator_variant == LocatorVariant.or) {
			  S_bool_op = " or ";
			  } 
		  else if (E_locator_variant == LocatorVariant.and) {
			  S_bool_op = " and ";
			  }
		  else if (E_locator_variant == LocatorVariant.regular) {
			 if (I_nbr_selectors_f1 > 1) {
				 S_bool_op = " and ";
			     }
		      }
		  else {
			  S_msg_1 = "Invalid locator variant: " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		  for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {   // check for duplicate class names
		      S_using = PI_AS_using[i1];
		    //--- https://benfrain.com/when-and-where-you-can-use-numbers-in-id-and-class-names/
		    // ^\p{Alpha}[\w\-]*$
		    O_grp_match_result = RegexpUtils.FO_match(S_using, P_clazz_name);
		    if (O_grp_match_result.I_array_size_f1 == 0) { 
		        S_msg_1 = "Class-name parameter " + i1 + " \'" + S_using + "\' must be alpha-numeric with - or _";
		        E_ill_arg = new IllegalArgumentException(S_msg_1);
		        throw E_ill_arg;
		        }
		    I_idx_old_f0 = AS_class_names.indexOf(S_using);
		    if (I_idx_old_f0 > 0) {
		       S_msg_1 = "Duplicate class-name parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
		       E_ill_arg = new IllegalArgumentException(S_msg_1);
		       throw E_ill_arg;
		       }
		    AS_class_names.add(S_using);
		    }
		  I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		    
		  SB_xpath = new TextStringBuilder(PI_S_prefix + PI_S_tag + "[");

	      for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {
		     S_using = PI_AS_using[i1];
		     S_containing_word = FS_containing_word("class", S_using);
		     SB_xpath.append(S_containing_word);
		     if (i1 < I_nbr_selectors_f0) {
			    SB_xpath.append(S_bool_op);
		        }
	         }
	      SB_xpath.append("]");
	      S_xpath = SB_xpath.toString();        
		   
	      break; // classname
	   case xpath:
	       if (I_nbr_selectors_f1 > 1) {
	    	   S_msg_1 = "Number of selectors exceeding 1";
	    	   E_ill_arg = new IllegalArgumentException(S_msg_1);
	    	   S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not (yet) eligible for multiple selectors.";
			   E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
			   throw E_rt;
	           }
	       S_xpath = PI_AS_using[0];
		   break;
	   case cssSelector:
			   
			   String S_css_selector, AS_css_selector[];
			   
			   if (I_nbr_selectors_f1 > 1) {
		    	   S_msg_1 = "Number of selectors exceeding 1";
		    	   E_ill_arg = new IllegalArgumentException(S_msg_1);
		    	   S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not (yet) eligible for multiple selectors.";
				   E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
				   throw E_rt;
		           }
			   
	           S_css_selector = PI_AS_using[0];
	           if (XpathGenerators.O_css_element_combinator_pairs_to_xpath == null) {
	        	  XpathGenerators.O_css_element_combinator_pairs_to_xpath = new CssElementCombinatorPairsToXpath();
	        	  S_msg_1 = "Locator " + E_locator.name() + " is discouraged in this context " + LF +
					         "Use " + ByCssS.Loc.class.getName() + " to use the native css-selector browser api, instead.";
			      E_ill_arg = new IllegalArgumentException(S_msg_1);
			      E_ill_arg.printStackTrace(System.out);  
	           }
	           AS_css_selector = new String[]{S_css_selector};
	           //    S_xpath = CSS2XPath.css2xpath(S_css_selector);
			   try {
				   S_xpath = XpathGenerators.O_css_element_combinator_pairs_to_xpath.mainGo(AS_css_selector) ;
				} catch (NiceCssSelectorStringForOutputException PI_E_css_err) {
				   S_msg_1 = "Unable to convert css-selector \'" + S_css_selector + "\' to xpath";
				   E_ill_arg = new IllegalArgumentException(S_msg_1, PI_E_css_err);
				   throw E_ill_arg;
				}
	
//	         SBO_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0); 
		     break;
		   
	   case tagName:
			  if (I_nbr_selectors_f1 > 1) {
		    	   S_msg_1 = "Number of selectors exceeding 1";
		    	   E_ill_arg = new IllegalArgumentException(S_msg_1);
		    	   S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not eligible for multiple selectors.";
		    
				   E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
				   throw E_rt;
		           }
			  S_xpath = PI_S_prefix + PI_AS_using[0]; 
			 break;
	   default:
		 if (E_locator_variant == LocatorVariant.regular) {
	    	if (I_nbr_selectors_f1 > 1) {
	    		E_locator_variant = LocatorVariant.or;
	    	    }
	         }
		if (E_locator == Locator.linkText) {
			S_attr_name_prefix = "";
			S_locator          = "text()";
		    }
		else {
			S_attr_name_prefix = "@";
			if (E_locator == Locator.forLabel) {
				S_locator = "for";
			    }
			else {
			   S_locator = E_locator.name();
			   }
		    }
		S_attr = S_attr_name_prefix + S_locator;
		
		if (StringUtils.isBlank(S_locator)) {
		    S_msg_1 = "Invalid locator: \'" + S_locator + "\'";
		    E_assert = new AssertionError(S_msg_1);
		    throw E_assert;
		    }
		
		 if (PI_S_prefix == null) {
		    S_prefix = EMPTY_PREFIX;
		    }
		 else if (PI_S_prefix.equals("")) {
		       S_prefix = PI_S_prefix;
		       }
		 else if (StringUtils.isBlank(PI_S_prefix)) { 
		    	S_msg_1 = "Prefix: \'" + PI_S_prefix + "\' must not be blank.";
		        E_ill_arg = new IllegalArgumentException(S_msg_1);
	 	        throw E_ill_arg;
		        }
		 else {
		     S_prefix = PI_S_prefix;
		     }
		
		 SBO_retval_xpath.AO_dom_offsets = PI_AO_dom_offsets;
		 S_xpath = S_prefix + PI_S_tag;
		 S_using = null;
		 if (I_nbr_selectors_f1 == 0) {
			return SBO_retval_xpath;
		    }
		 else {
			for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
				S_using = PI_AS_using[i1];
				if (StringUtils.isBlank(S_using)) {
		             S_msg_1 = "Invalid argument for locator " + i1 + " \'" + S_locator + ": \'" + S_using + "\'";
		             E_ill_arg = new IllegalArgumentException(S_msg_1);
		             throw E_ill_arg;
		            }
			     }
		     }
		 TABLE_LOC_VARIANT: switch (E_locator_variant) {
		 case regular:
			 S_xpath += "[" + S_attr + " = '" + S_using + "']";
			 break TABLE_LOC_VARIANT;
		 case partial:
			 S_xpath += "[contains(" + S_attr + ", '" + S_using + "')]";
			 break TABLE_LOC_VARIANT;
		 case prefix:
			 S_xpath += "[starts-with(" + S_attr + ", '" + S_using + "')]";
			 break TABLE_LOC_VARIANT;
		 case prefixWord:
		 case partialWord:
		 case regexp:
		 case orPrefixWithSeparatePos:
			 S_msg_1 = E_locator_variant.name() + " is invalid in this context.";
			 E_ill_arg = new IllegalArgumentException(S_msg_1);
			 throw E_ill_arg;
		 case suffix:
			 S_xpath += "[substring('" + S_attr + ", string-length(" + S_attr_name_prefix + S_locator + ") - string-length('" + S_using + "') + 1) = '" + S_using + "']";
			 break TABLE_LOC_VARIANT;
		 case or:
			 I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
			 SB_xpath_tail = new StringBuffer("[");
			 for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
				S_using = PI_AS_using[i1];
				SB_xpath_tail.append("(" +  S_attr + " = '" + S_using + "'");
				if (i1 == I_nbr_selectors_f0) {
					SB_xpath_tail.append(" or ");
				    }
			    }
			 SB_xpath_tail.append("]");
			 S_xpath += SB_xpath_tail;
			 break TABLE_LOC_VARIANT;
		 default:
			 S_msg_1 = "Invalid " + LocatorVariant.class.getSimpleName() +  "\'" + E_locator_variant.name()  + "\'";
			 E_assert = new AssertionError(S_msg_1);
			 throw E_assert;
			
		     }
		 break;
	}
	
	 SBO_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0);
	 SBO_retval_xpath.AO_dom_offsets = PI_AO_dom_offsets;
	
	return SBO_retval_xpath;
	
	}



// R E G U L A R  -  E N D

// I R R E G U L A R - S T A R T 

public static String FS_containing_word(
		final String PI_S_attr_name, 
		final String PI_S_attr_value) {
	
//	final String S_method_name_containing_word = "containingWord"; 
	
	RuntimeException E_rt;
	String /* S_msg_1, */ S_retval_containing_word;
//	Object O_containing_word;
	
    S_retval_containing_word = "contains(concat(' ',normalize-space(@" + PI_S_attr_name + "),' '),' "
                + PI_S_attr_value + " ')";
	
//	try {
//		O_containing_word = MethodUtils.invokeMethod(
//				(By.ByClassName)OT_by_class_name,
//				true, // force access
//				S_method_name_containing_word,
//				new Object[] {PI_S_attr_name, PI_S_attr_value},
//		        new Class[]  {PI_S_attr_name.getClass(), PI_S_attr_value.getClass()});		
//	} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException PI_E_inv_target) {
//		S_msg_1 = "Unable to invoke method \'" + 
//	              OT_by_class_name.getClass().getName() + "." + S_method_name_containing_word + "(\"" +
//	              PI_S_attr_name + "\", \"" + PI_S_attr_value + "\")";
//		E_rt = new RuntimeException(S_msg_1, PI_E_inv_target);
//		throw E_rt;
//	}
//	S_retval_containing_word = (String)O_containing_word;

	return S_retval_containing_word;
  }

// https://www.jooq.org/products/jOOX/javadoc/1.2.0/org/joox/selector/CSS2XPath.html
public static DomVectorExtendedSelector FS_get_xpath_from_css_selector(final String PI_S_using, final int PI_I_idx_f0) {
	
	IllegalArgumentException E_ill_arg;
	DomVectorExtendedSelector SB_retval_xpath;
	
	String  S_xpath, S_msg_1;
	
    if (StringUtils.isBlank(PI_S_using)) {
    	S_msg_1 = "Pararameter for css-selector: \'" + PI_S_using + "\' must not be empty or null";
    	E_ill_arg = new IllegalArgumentException(S_msg_1);
    	throw E_ill_arg;
        }
    
    S_xpath = CSS2XPath.css2xpath(PI_S_using);
    SB_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0);
    
    return SB_retval_xpath;
    }

public static DomVectorExtendedSelector FS_get_xpath_from_css_selector(final String PI_S_using) {
	
	DomVectorExtendedSelector SB_retval_xpath;
	
	SB_retval_xpath =  FS_get_xpath_from_css_selector(PI_S_using, IGNORED_IDX);
	
	return SB_retval_xpath;
}

// ---- CSS-Selector-END
// ----- LinkText Start

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext(
		final String PI_S_using, 
		final String PI_S_tag,
		final int PI_I_idx_f0,
		final String PI_S_prefix) {

	IllegalArgumentException E_ill_arg;
	DomVectorExtendedSelector SB_retval_xpath;
	String S_xpath, S_prefix, S_msg_1;

	if (StringUtils.isBlank(PI_S_using)) {
		S_msg_1 = "Invalid argument for link-text: \'" + PI_S_using + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	}

	if (StringUtils.isBlank(PI_S_tag)) {
		S_msg_1 = "Invalid argument for tag: \'" + PI_S_tag + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	}

	if (PI_S_prefix == null) {
		S_prefix = EMPTY_PREFIX;
	}
	else if (PI_S_prefix.equals("")) {
		S_prefix = PI_S_prefix;
	}
	else if (StringUtils.isBlank(PI_S_prefix)) { 
		S_msg_1 = "Xpath-Prefix: \'" + PI_S_prefix + "\' must not be blank.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	}
	else {
		S_prefix = PI_S_prefix;
	}

	S_xpath = S_prefix + PI_S_tag + "[text() = '"  + PI_S_using + "']";
	SB_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0);
	return SB_retval_xpath;
}

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext(
		final String PI_S_using, 
		final String PI_S_tag, 
		final int PI_I_idx_f0) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext(PI_S_using, PI_S_tag, PI_I_idx_f0, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}		

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext(final String PI_S_using, final String PI_S_tag) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext(PI_S_using, PI_S_tag, IGNORED_IDX, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}		

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext(final String PI_S_using) {

	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext(PI_S_using, DEFAULT_TAG_LINK, IGNORED_IDX, DEFAULT_PREFIX);

	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_partial(
		final String PI_S_using,
		final String PI_S_tag,
		final int PI_I_idx_f0,
		final String PI_S_prefix) {

	IllegalArgumentException E_ill_arg;
	DomVectorExtendedSelector SB_retval_xpath;
	String S_xpath, S_prefix, S_msg_1;

	if (StringUtils.isBlank(PI_S_using)) {
		S_msg_1 = "Invalid argument for partial link-text: \'" + PI_S_using + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	}

	if (StringUtils.isBlank(PI_S_tag)) {
		S_msg_1 = "Invalid argument for tag: \'" + PI_S_tag + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	}

	if (PI_S_prefix == null) {
		S_prefix = EMPTY_PREFIX;
	}
	else if (PI_S_prefix.equals("")) {
		S_prefix = PI_S_prefix;
	}
	else if (StringUtils.isBlank(PI_S_prefix)) { 
		S_msg_1 = "Xpath-Prefix: \'" + PI_S_prefix + "\' must not be blank.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	}
	else {
		S_prefix = PI_S_prefix;
	}

	S_xpath = S_prefix + PI_S_tag + "[contains(text(), '"  + PI_S_using + "')]";
	SB_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0);
	return SB_retval_xpath;
}

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_partial(
		final String PI_S_using, 
		final String PI_S_tag, 
		final int PI_I_idx_f0) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_partial(
			PI_S_using, PI_S_tag, PI_I_idx_f0, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_partial(final String PI_S_using, final String PI_S_tag) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_partial(
			PI_S_using, PI_S_tag, IGNORED_IDX, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_partial (final String PI_S_using) {

	DomVectorExtendedSelector SB_retval_xpath;
	SB_retval_xpath = FS_get_xpath_from_linktext_partial(PI_S_using, DEFAULT_TAG, IGNORED_IDX, DEFAULT_PREFIX);

	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_prefix(
		final String PI_S_using, 
		final String PI_S_tag,
		final int PI_I_idx_f0,
		final String PI_S_prefix) {

	IllegalArgumentException E_ill_arg;
	DomVectorExtendedSelector SB_retval_xpath;
	String  S_xpath, S_prefix, S_msg_1;

	if (StringUtils.isBlank(PI_S_using)) {
		S_msg_1 = "Invalid argument for link-text prefix: \'" + PI_S_using + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }

	if (StringUtils.isBlank(PI_S_tag)) {
		S_msg_1 = "Invalid argument for tag: \'" + PI_S_tag + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }

	if (PI_S_prefix == null) {
		S_prefix = EMPTY_PREFIX;
	    }
	else if (PI_S_prefix.equals("")) {
		S_prefix = PI_S_prefix;
	    }
	else if (StringUtils.isBlank(PI_S_prefix)) { 
		S_msg_1 = "Xpath-Prefix: \'" + PI_S_prefix + "\' must not be blank.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }
	else {
		S_prefix = PI_S_prefix;
	    }

	S_xpath = S_prefix + PI_S_tag + "[starts-with(text(), '"  + PI_S_using + "')]";
	SB_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0);
	return SB_retval_xpath;
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_prefix(
		final String PI_S_using, 
		final String PI_S_tag,
		final int PI_I_idx_f0) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_prefix(
			PI_S_using,
			PI_S_tag,
			PI_I_idx_f0,
			DEFAULT_PREFIX);
	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_prefix(final String PI_S_using, final String PI_S_tag) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_prefix(PI_S_using, PI_S_tag, IGNORED_IDX, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_prefix (final String PI_S_using) {

	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_prefix(PI_S_using, DEFAULT_TAG, IGNORED_IDX, DEFAULT_PREFIX);

	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_suffix(
		final String PI_S_using,
		final String PI_S_tag,
		final int PI_I_idx_f0,
		final String PI_S_prefix) {

	IllegalArgumentException E_ill_arg;
	DomVectorExtendedSelector SB_retval_xpath;
	String S_xpath, S_msg_1, S_prefix;

	if (PI_S_using == null) {
		S_msg_1 = "Invalid argument for link-text suffix: \'" + PI_S_using + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }

	if (StringUtils.isBlank(PI_S_tag)) {
		S_msg_1 = "Invalid argument for tag: \'" + PI_S_tag + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }

	if (PI_S_prefix == null) {
		S_prefix = EMPTY_PREFIX;
	    }
	else if (PI_S_prefix.equals("")) {
		S_prefix = PI_S_prefix;
	    }
	else if (StringUtils.isBlank(PI_S_prefix)) { 
		S_msg_1 = "Xpath-Prefix: \'" + PI_S_prefix + "\' must not be blank.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }
	else {
		S_prefix = PI_S_prefix;
	    }

	S_xpath = S_prefix + PI_S_tag + "[substring(text(), string-length(text()) - string-length('" + PI_S_using + "') + 1) = '" + PI_S_using + "']";
	SB_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0); 
	return SB_retval_xpath;
}

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_suffix(
		final String PI_S_using, 
		final String PI_S_tag, 
		final int PI_I_idx_f0) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_suffix(PI_S_using, PI_S_tag, PI_I_idx_f0, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_suffix(final String PI_S_using, final String PI_S_tag) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_suffix(PI_S_using, PI_S_tag, IGNORED_IDX, DEFAULT_PREFIX);
	return SB_retval_xpath;   
}	

@Deprecated
public static DomVectorExtendedSelector FS_get_xpath_from_linktext_suffix(final String PI_S_using) {
	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_linktext_suffix(PI_S_using, DEFAULT_TAG, IGNORED_IDX, DEFAULT_PREFIX);

	return SB_retval_xpath;   
}

//tagname - START
public static DomVectorExtendedSelector FS_get_xpath_from_tagname(final String PI_S_tag, final int PI_I_idx_f0, final String PI_S_prefix) {

	IllegalArgumentException E_ill_arg;
	DomVectorExtendedSelector SB_retval_xpath;
	String S_xpath, S_prefix, S_msg_1;

	if (PI_S_prefix == null) {
		S_prefix = EMPTY_PREFIX;
	    }
	else if (PI_S_prefix.equals("")) {
		S_prefix = PI_S_prefix;
	    }
	else if (StringUtils.isBlank(PI_S_prefix)) { 
		S_msg_1 = "Xpath-Prefix: \'" + PI_S_prefix + "\' must not be blank.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	    }
	else {
		S_prefix = PI_S_prefix;
	    }

	if (StringUtils.isBlank(PI_S_tag)) {
		S_msg_1 = "Invalid argument for tag: \'" + PI_S_tag + "\'";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		throw E_ill_arg;
	     }

	S_xpath = S_prefix + PI_S_tag;
	SB_retval_xpath = FSBO_index_xpath(S_xpath, PI_I_idx_f0);
	return SB_retval_xpath;
}


public static DomVectorExtendedSelector FS_get_xpath_from_tagname(final String PI_S_tag, final int PI_I_idx_f0) {

	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_tagname(PI_S_tag, PI_I_idx_f0, DEFAULT_PREFIX);

	return SB_retval_xpath;   
}

public static DomVectorExtendedSelector FS_get_xpath_from_tagname(final String PI_S_tag) {

	DomVectorExtendedSelector SB_retval_xpath;

	SB_retval_xpath = FS_get_xpath_from_tagname(PI_S_tag, IGNORED_IDX, DEFAULT_PREFIX);

	return SB_retval_xpath;   
}

//--------- tagname END
// I R R E G U L A R - E N D
}