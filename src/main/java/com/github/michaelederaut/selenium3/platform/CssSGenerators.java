package com.github.michaelederaut.selenium3.platform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
// import javax.xml.xpath.XPathException;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.text.TextStringBuilder;

import regexodus.Pattern;
import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.basics.StreamUtils.EndCriterion;
import com.github.michaelederaut.basics.xpath2cssselector.Cssify;
import com.github.michaelederaut.basics.xpath2cssselector.CssifyCached;
import com.github.michaelederaut.basics.xpath2cssselector.DomNavigator;
import com.github.michaelederaut.basics.xpath2cssselector.DomRootElements.DomOffset;
import com.github.michaelederaut.selenium3.framework.ByXp;
// import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

import static org.apache.commons.lang3.StringUtils.LF;
// import static com.github.michaelederaut.basics.ToolsBasics.FS;

/**
* @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;CssSelector-Generators">Mr. Michael Eder</a>
* 
*/
public class CssSGenerators {
	
	public static final String ALL_ELEMS      = "*";
	public static final String DEFAULT_TAG    = "";
	public static final String DEFAULT_PREFIX = "";
	public static final String EMPTY_PREFIX = DEFAULT_PREFIX;
	public static final String S_re_tag_name = "^(\\*|[a-z]*)$";
	public static final /*regexodus.*/Pattern P_tag_name = /*regexodus.*/Pattern.compile(S_re_tag_name);
	
	protected enum ParsingState {init, dot1, dot2, slash, invalid};
	
	public static CssifyCached O_cssify_cached = null;
	
	// public static ConversionResults HS_conversion_results = new ConversionResults();
	
//	public static final EndCriterion O_end_crit_where = 
//			new EndCriterion(EndCriterion.L_timeout_dflt, P_end_criterion_where);
	
	public static class LinkText {
		public String         S_selector;  // using
		public LocatorVariant E_variant;
		
		public LinkText() {
		   this (null, LocatorVariant.regular);
           return;
		   }
		
		public LinkText (final String using) {
		   this (using, LocatorVariant.regular);
		   return;
		   }
		
		public LinkText (
		   final String         using,
		   final LocatorVariant PI_E_variant) {
			
			RuntimeException E_rt;
			String S_msg_1;
			
			if ((using == null) || using.equals(""))  {
			// if ((using != null) && using.equals("")) 	
			   S_msg_1 = "Empty String for link-text not (yet) implemented.";
			   E_rt = new RuntimeException(S_msg_1);
			   throw E_rt;
			   }
			this.S_selector     = using;
			this.E_variant      = PI_E_variant;
		    }
	}
		
	public static class ExtendedCssSelector extends DomVectorExtendedSelector {
		public DomNavigator O_dom_navigator = null;
	//	public int I_dom_element_hier_ups_f0 = -1;
	    public LinkText O_lnk_txt;
	   
	   public ExtendedCssSelector() {
			super();
			return;
	   }
	  
	    public ExtendedCssSelector(final LinkText PI_O_link_text) {
			super();
			this.O_lnk_txt = PI_O_link_text;
			return;
	   }
	    
	    public ExtendedCssSelector(final String PI_S_using) {
			super (PI_S_using);
			return;
	    }
	
	    public ExtendedCssSelector(
	    		final String PI_S_using,
	    		final LinkText PI_O_link_text) {
			super (PI_S_using);
			this.O_lnk_txt = PI_O_link_text;
			return;
	    }
	    
      public ExtendedCssSelector(
    		  final String PI_S_using, 
    		  final int PI_I_starting_pos_of_idx) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			return;
		}
      
       public ExtendedCssSelector(
    		  final String PI_S_using,
    		  final LinkText PI_O_link_text,
    		  final int PI_I_starting_pos_of_idx) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			this.O_lnk_txt = PI_O_link_text;
			return;
		}
      
      public ExtendedCssSelector(final IndexedStrBuilder PI_SB_indexed) {
			super(PI_SB_indexed);
			return;
		    }
      
      public ExtendedCssSelector(
    		  final IndexedStrBuilder PI_SB_indexed,
    		  final LinkText PI_O_link_text
    		  ) {
			super(PI_SB_indexed);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
      
      public ExtendedCssSelector(final DomOffset PI_AO_dom_offsets[]) {
    	    super(PI_AO_dom_offsets);
			return;
		    }
      
        public ExtendedCssSelector(
        		final LinkText PI_O_link_text,
        		final DomOffset PI_AO_dom_offsets[]) {
			super(PI_AO_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
      
      public ExtendedCssSelector(
    		  final String PI_S_using, 
    		  final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_AO_dom_offsets);
			return;
		    }
		
       public ExtendedCssSelector(
    		  final String PI_S_using, 
    		  final LinkText PI_O_link_text,
    		  final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_AO_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
      
		public ExtendedCssSelector(
				final String PI_S_using, 
				final int PI_I_starting_pos_of_idx, 
				final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AO_dom_offsets);
			return;
		}
		
		public ExtendedCssSelector(
				final String PI_S_using,
				final LinkText PI_O_link_text, 
				final int PI_I_starting_pos_of_idx, 
				final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AO_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		}
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed, 
				final DomOffset PI_AO_dom_offsets[]) {
				super(PI_SB_indexed,  PI_AO_dom_offsets);
				return;
		}
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed,
				final LinkText PI_O_link_text, 
				final DomOffset PI_AO_dom_offsets[]) {
			
				super(PI_SB_indexed,  PI_AO_dom_offsets);
				this.O_lnk_txt = PI_O_link_text;
				return;
		}
		
		public ExtendedCssSelector(final int PI_AI_dom_offsets[]) {
			super(PI_AI_dom_offsets);
			return;
		    }
		
		public ExtendedCssSelector(
			    final LinkText PI_O_link_text, 
				final int PI_AI_dom_offsets[]) {
			super(PI_AI_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
		
		public ExtendedCssSelector(
				final String PI_S_value, 
				final int PI_AI_dom_offsets[]) {
			super(PI_S_value, PI_AI_dom_offsets);
			return;
		    }
		
		public ExtendedCssSelector(
				final String PI_S_value,
				final LinkText PI_O_link_text, 
				final int PI_AI_dom_offsets[]) {
			super(PI_S_value, PI_AI_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
		
		public ExtendedCssSelector(
				final String PI_S_using,
				final int PI_I_starting_pos_of_idx,
				final int PI_AI_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AI_dom_offsets);
			return;
		}
		
		public ExtendedCssSelector(
				final String PI_S_using,
				final LinkText PI_O_link_text, 
				final int PI_I_starting_pos_of_idx,
				final int PI_AI_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AI_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		}
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed, 
				final int PI_AI_dom_offsets[]) {
			
				super(PI_SB_indexed, PI_AI_dom_offsets);
				return;
		        }
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed,
				final LinkText PI_O_link_text, 
				final int PI_AI_dom_offsets[]) {
			
				super(PI_SB_indexed, PI_AI_dom_offsets);
				this.O_lnk_txt = PI_O_link_text;
				return;
		        }	
	}
	
//	public static class ConversionResult {
//		
//		 String S_value;
//		 String S_err_msg;
//		 
//		public ConversionResult(final String PI_S_value) {
//			this(PI_S_value, (String)null);
//				return;
//			}
//		 
//	    public ConversionResult(final String PI_S_value, final String PI_S_err_msg) {
//	    	this.S_value   = PI_S_value;
//	    	this.S_err_msg = PI_S_err_msg;
//	     }
//	}
	
//	@Deprecated
//	public static class ConversionResults extends LinkedHashMap<String, Cssify.ConversionResult>  {
//		public static final int I_init_capacity = 16;
//		public static final int I_max_nbr_elements_dflt = 1024;
//		public static       int I_max_nbr_elements;
//		
//		protected boolean RemoveEldestEntry(Map.Entry<String, Cssify.ConversionResult> PI_O_eledest) {
//			
//			boolean B_retval_remove;
//			if (this.size() > I_max_nbr_elements) {
//			   B_retval_remove = true; }
//			else {
//			   B_retval_remove = false;
//			   }
//			return B_retval_remove;
//		}
//		
//		public ConversionResults() {
//			 this(I_max_nbr_elements_dflt);
//		     }
//		
//		public ConversionResults(final int PI_I_capacity) {
//			super(PI_I_capacity < I_init_capacity ? PI_I_capacity : I_init_capacity, (float)0.75, true);
//			I_max_nbr_elements = PI_I_capacity;
//		    }	
//	}
	
	public static /* DomVectorExtendedSelector */ ExtendedCssSelector FSBO_get_csss(
		final LocatorEnums PI_O_locator_enums,
		final String    PI_S_using, 
		final String    PI_S_tag, 
		final LinkText  PI_O_link_text,
		final int       PI_I_idx_f0,
		final DomOffset PI_AO_dom_offsets[],
		final String    PI_S_prefix) {
		
		String AS_using[];
		/* DomVectorExtendedSelector */ ExtendedCssSelector  SBO_retval_csss;
		
		AS_using = new String[] {PI_S_using};
		SBO_retval_csss = FSBO_get_csss(
				PI_O_locator_enums,
				AS_using,
				PI_S_tag,
				PI_O_link_text,
	            PI_I_idx_f0,
	            PI_AO_dom_offsets,
	            PI_S_prefix
	            );
		return SBO_retval_csss;
	}
	
	public static /* DomVectorExtendedSelector */ ExtendedCssSelector FSBO_get_csss(
		final LocatorEnums PI_O_locator_enums,
		final String    PI_AS_using[], 
		final String    PI_S_tag, 
		final LinkText  PI_O_link_text,
		final int       PI_I_idx_f0,
		final DomOffset PI_AO_dom_offsets[],
		final String    PI_S_prefix) {
		
		RuntimeException         E_rt;
		AssertionError           E_assert;
	    IllegalArgumentException E_ill_arg;
	    NullPointerException     E_np;
	    IndexOutOfBoundsException E_ind_out_of_boundary; 
	    IOException              E_io;
	    
	    GroupMatchResult O_grp_match_result;
	    String S_msg_1, S_msg_2, S_csss, S_prefix,
	           S_using, S_bool_op, S_tag_name;
	    Locator        E_locator;
	    LocatorVariant E_locator_variant;
	    ExtendedCssSelector SBO_retval_csss;
	    boolean B_has_valid_tag_name;
	    int I_nbr_dom_navi_elems_f1;
	    DomNavigator O_dom_navigator;
	    boolean B_to_dom_convertible_xpath;
	    
	    if (PI_O_locator_enums == null) {
	    	E_locator         = Locator.domOffsets;
	    	E_locator_variant = LocatorVariant.regular;
	        }
	    else {
	    	E_locator = PI_O_locator_enums.E_locator;
	    	E_locator_variant = PI_O_locator_enums.E_locator_variant;
	        }
	    if (PI_S_tag == null) {
	    	S_tag_name = "";
	        }
	    else {
	        O_grp_match_result = RegexpUtils.FO_match(PI_S_tag, CssSGenerators.P_tag_name);
	        if (O_grp_match_result.I_array_size_f1 > 0) { 
	    	   B_has_valid_tag_name = true; 
	    	   S_tag_name = PI_S_tag;
	           }
	       else {
    	       S_msg_1 = "Invalid value for tag: \'" + PI_S_tag + "\'";
    	       E_ill_arg = new IllegalArgumentException(S_msg_1);
    	       throw E_ill_arg;
               }
	       }

	 B_to_dom_convertible_xpath = false;
	 O_dom_navigator = null;
	 if (PI_O_locator_enums.E_locator == E_locator.domOffsets) {
		SBO_retval_csss = new ExtendedCssSelector((String)null, PI_O_link_text, PI_I_idx_f0, PI_AO_dom_offsets);  
		return SBO_retval_csss;
	    }
	   
	 if (PI_S_prefix == null) {
		S_prefix = "";
	    }
	 else {
		S_prefix = PI_S_prefix; 
	    }
	 
	  int I_nbr_selectors_f1, I_nbr_selectors_f0, i1;
	  String S_csss_single_term;
	  StringBuffer SB_csss_tail;
	
	if (PI_AS_using == null) {
	   I_nbr_selectors_f1 = 0;
	   }
	else {
	    I_nbr_selectors_f1 = PI_AS_using.length;
	    }
	if (I_nbr_selectors_f1 == 0) {
		S_msg_1 = "No selectors provided.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		S_msg_2 = "Unable to create css-selector with the arguments given.";
		E_rt = new RuntimeException(S_msg_2, E_ill_arg);
		throw E_rt;
	}
	 
	// E_locator = PI_O_locator_enums.E_locator;
	S_csss    = "";
	S_using   = null;
	
	switch (E_locator) {
	   case className:
		  ArrayList<String>        AS_class_names;
		  TextStringBuilder        SB_csss;
		//  LinkedHashMap<String, String>  HS_css;
		  int                      I_idx_old_f0;  
		
		  if (I_nbr_selectors_f1 == 1) {
		     E_locator_variant = LocatorVariant.regular;   
	         }	
		  else if (E_locator_variant == LocatorVariant.regular) {
			  if (I_nbr_selectors_f1 > 1) {
				 E_locator_variant = LocatorVariant.and;
			     }
		      }
		  else if (!((E_locator_variant == LocatorVariant.and) || (E_locator_variant == LocatorVariant.or))) {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		  AS_class_names = new ArrayList<String>();	
		  for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {   // check for duplicate class names
		      S_using = PI_AS_using[i1];
		    //--- https://benfrain.com/when-and-where-you-can-use-numbers-in-id-and-class-names/
		    // ^\p{Alpha}[\w\-]*$
		    O_grp_match_result = RegexpUtils.FO_match(S_using, XpathGenerators.P_clazz_name);
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
		  
		  if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
			 SB_csss = new TextStringBuilder(PI_S_prefix);
			 S_bool_op = ",";
		     }
		  else if (E_locator_variant == LocatorVariant.or) {
			 SB_csss = new TextStringBuilder();
			 S_bool_op = ",";
		     }
		  else {  // and OR regular
			 SB_csss = new TextStringBuilder(PI_S_prefix + S_tag_name);
			 S_bool_op = "";
		     }
		 
	      for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		     S_using = PI_AS_using[i1];
		     
		     if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
		        SB_csss.append(S_tag_name + "." + S_using);
		        }
		     else if (E_locator_variant == LocatorVariant.or) {   
		         SB_csss.append(S_prefix + S_tag_name + "." + S_using);
		       }
		     else { // and OR regular
		    	 SB_csss.append("." + S_using);  
		         }
		     if (i1 < I_nbr_selectors_f0) {
		    	 SB_csss.append(S_bool_op);  // "" or ","
	            }
	         }
	      S_csss = SB_csss.toString();        
		 
	      break; // classname
	   
	   case cssSelector:
		   if (I_nbr_selectors_f1 > 1) {
	    	   S_msg_1 = "Number of selectors exceeding 1";
	    	   E_ill_arg = new IllegalArgumentException(S_msg_1);
	    	   S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not (yet) eligible for multiple (css-)selectors.";
			   E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
			   throw E_rt;
		       }
		   S_csss = PI_AS_using[0];
		   break; // css-selector
	   case id:
		  ArrayList<String>        AS_id_names;
		 	
		  if (I_nbr_selectors_f1 == 1) {
		         E_locator_variant = LocatorVariant.regular;   
	            }	
		  else if (E_locator_variant == LocatorVariant.regular) {
			  if (I_nbr_selectors_f1 > 1) {
				E_locator_variant = LocatorVariant.or;
			    }
		      }
		  else if (E_locator_variant != LocatorVariant.or) {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		   AS_id_names = new ArrayList<String>();
		   for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {   // check for duplicate class names
		      S_using = PI_AS_using[i1];
		    //--- https://benfrain.com/when-and-where-you-can-use-numbers-in-id-and-class-names/
		    // ^\p{Alpha}[\w\-]*$
		     O_grp_match_result = RegexpUtils.FO_match(S_using, XpathGenerators.P_clazz_name);
		     if (O_grp_match_result.I_array_size_f1 == 0) { 
		        S_msg_1 = "id-name parameter " + i1 + " \'" + S_using + "\' must be alpha-numeric with - or _";
		        E_ill_arg = new IllegalArgumentException(S_msg_1);
		        throw E_ill_arg;
		        }
		     if (i1 > 0) {
			    I_idx_old_f0 = AS_id_names.indexOf(S_using);
			    if (I_idx_old_f0 > 0) {
			       S_msg_1 = "Duplicate id-name parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
			       E_ill_arg = new IllegalArgumentException(S_msg_1);
			       throw E_ill_arg;
			       }}
		    AS_id_names.add(S_using);
		   }
		   
		   I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		  
		   S_bool_op = ",";
		   if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
			 SB_csss = new TextStringBuilder(PI_S_prefix);
		     }
		   else if (E_locator_variant == LocatorVariant.or) {
			 SB_csss = new TextStringBuilder();
		     }
		   else {  // and OR regular
			 SB_csss = new TextStringBuilder(PI_S_prefix + S_tag_name);
		     }
		 
	       for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		     S_using = PI_AS_using[i1];
		     
		     if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
		        SB_csss.append(S_tag_name + "." + S_using);
		        }
		     else if (E_locator_variant == LocatorVariant.or) {   
		         SB_csss.append(S_prefix + S_tag_name + "." + S_using);
		       }
		     if (i1 < I_nbr_selectors_f0) {
		    	 SB_csss.append(S_bool_op);  // "" or ","
	            }
	         }
	      S_csss = SB_csss.toString();        
		  break; // id
	   case linkText:
		     S_msg_1  = "Implementation restriction: Current version of css doesn't support Operation \'" + E_locator.name() + "\'." + LF +
					     "Use separate parameters tag_name == '*' and link-text instead.";
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
			  S_msg_2 = "Unable to convert: " + S_using + "' to a valid css-selector";
			  E_rt = new RuntimeException(S_msg_2,  E_ill_arg);
		      throw E_rt;
		   // break;
		case tagName:
			  if (I_nbr_selectors_f1 > 1) {
		    	 S_msg_1 = "Number of selectors exceeding 1";
		    	 E_ill_arg = new IllegalArgumentException(S_msg_1);
		    	 S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not eligible for multiple selectors.";
		    
				 E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
				 throw E_rt;
		         }
			  S_csss = PI_S_prefix + PI_AS_using[0]; 
			 break;  
			 
	   case xpath:
		   Cssify.DomNavExtendedConversionResult O_conversion_result;
		  /* XPathException */ RuntimeException E_xp;

		   char C_using;
		//   int I_len_using_f1;
		//   String /* S_pna_script,*/ S_pnr_script, AS_cmd[];
		   
		   S_using = PI_AS_using[0];

		   if (S_using == null) {
			    S_csss = null;
			    break;
		      } 

		    if (CssSGenerators.O_cssify_cached == null) {
			    S_msg_1 = "Locator " + E_locator.name() + " is discouraged in this context " + LF +
			          	  "Use " + ByXp.Loc.class.getName() + " to use the native xpath browser api, instead.";
			    E_ill_arg = new IllegalArgumentException(S_msg_1);
			    E_ill_arg.printStackTrace(System.out);  	
			    CssSGenerators.O_cssify_cached = new CssifyCached(true); // try to convert to DOM path first == true
			   }
			O_conversion_result = (Cssify.DomNavExtendedConversionResult)CssSGenerators.O_cssify_cached.FO_convert(S_using);
			O_dom_navigator = O_conversion_result.O_dom_navgator;
			S_csss = O_conversion_result.S_value;
			   
		   //-----------------------	   
			   
//		   HS_conversion_results.put(S_using, O_conversion_result);
		   break; // xpath.
	   default:
		   String S_locator, S_op;
		   ArrayList<String>        AS_selector_names;
		   
		   if ((E_locator_variant == LocatorVariant.regular) || 
			   (E_locator_variant == LocatorVariant.or) || 
			   (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos)) {
		       S_op = "="; }
		  
		    else if (E_locator_variant == LocatorVariant.prefix) {
		       S_op = "^="; 
		       }
		    else if (E_locator_variant == LocatorVariant.prefixWord) {
		       S_op = "|="; 
		       }
		    else if (E_locator_variant == LocatorVariant.partial) {
		    	S_op = "*="; 
		        }
		    else if (E_locator_variant == LocatorVariant.partialWord) {
		    	S_op = "~=";
		        }
		    else if (E_locator_variant == LocatorVariant.suffix) {
		    	S_op = "$=";
		        }		   
		   else  {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		   if (E_locator == Locator.forLabel) {
				S_locator = "for";
			    }
			else {
			   S_locator = E_locator.name();
			   }
		    AS_selector_names = new ArrayList<String>();
		    for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) { // check for duplicate class names
		        S_using = PI_AS_using[i1];
			    if (i1 > 0) {
			       I_idx_old_f0 = AS_selector_names.indexOf(S_using);
			       if (I_idx_old_f0 > 0) {
			           S_msg_1 = "Duplicate selector parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
			           E_ill_arg = new IllegalArgumentException(S_msg_1);
			           throw E_ill_arg;
			           }}
			    AS_selector_names.add(S_using);
		    }
		    I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		//    S_bool_op = ",";
		    if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
			   SB_csss = new TextStringBuilder(PI_S_prefix);
		       }
		    else if (E_locator_variant == LocatorVariant.or) {
			   SB_csss = new TextStringBuilder();
		       }
		    else {  // and OR regular
			   SB_csss = new TextStringBuilder(PI_S_prefix + S_tag_name);
		       }
		    for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		       S_using = PI_AS_using[i1];
		     
		       if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
		          SB_csss.append(S_tag_name + "[" + S_locator + "=" + S_using + "]");
		           }
		       else if (E_locator_variant == LocatorVariant.or) {   
		          SB_csss.append(S_prefix + S_tag_name + "[" + S_locator + S_op + S_using + "]");
		       }
		    if (i1 < I_nbr_selectors_f0) {
		       SB_csss.append(",");  // or
	           }
	         }
	      S_csss = SB_csss.toString();
		   
	   break; // default
	      }
 
	if (StringUtils.equals(S_csss, "")) {
	   S_csss = ALL_ELEMS;
	   }   
	SBO_retval_csss = new ExtendedCssSelector(S_csss, PI_O_link_text, PI_I_idx_f0, PI_AO_dom_offsets);
//	if (B_to_dom_convertible_xpath) {
	SBO_retval_csss.O_dom_navigator = O_dom_navigator;
	//    }
	return SBO_retval_csss;
	}

}