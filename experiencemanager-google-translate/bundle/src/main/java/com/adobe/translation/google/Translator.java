package com.adobe.translation.google;

import com.day.cq.wcm.api.Page;

public interface Translator {

	/**
	 * To translate a given String
	 * @param toTranslateText The string to be translated
	 */
	String translateString(String toTranslateText);
	
	/**
	 * To Translate the given page
	 * @param rootPage The page to be translated
	 * @throws Exception 
	 */
	void translatePage(Page rootPage) throws Exception;

	/**
	 * Add additional property (belonging to nodes) to be translated
	 * @param property Name of property
	 */
	void addPropertyToTranslate(String property);
	
}
