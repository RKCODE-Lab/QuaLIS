package com.agaramtech.qualis.global;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class StringUtilityFunction {

	public String fnDynamicListToString(List<?> lst, String smethodname) throws Exception {
		String sconcatstr = "";
		if (!lst.isEmpty()) {
			List<Object> lstvalue =  (List<Object>) CollectionUtils.collect(lst,
					TransformerUtils.invokerTransformer(smethodname));
			sconcatstr = StringUtils.join(lstvalue.toArray(), ",");
		}
		return sconcatstr;
	}

	/*
	 * Replace Quote Functions
	 */
	public String replaceQuote(String str) {
		if (str != null) {
			str = str.trim().replace("'", "''");
		}
		return str;
	}

	public String removeDoubleQuote(String str) {
		if (str != null) {
			if (str.startsWith("'") && str.endsWith("'")) {
				str = str.substring(1, str.length() - 1);
			}
		}
		return str;
	}

//	public String ReplaceQuote1(String str) {
//		if (str != null) {
//			str = str.replace("'", "&apos;");
//		}
//		return str;
//	}
	
	public String replaceofString(String MainString, String RegExp, String Replacement) throws Exception {
		if (MainString != null) {
			if (RegExp != null && Replacement != null) {
				while (MainString.contains(RegExp)) {
					int length = RegExp.length();
					int index = MainString.indexOf(RegExp);
					MainString = MainString.substring(0, index) + Replacement
							+ MainString.substring(index + (length), MainString.length());
				}
			}
		}
		return MainString;
	}
}
