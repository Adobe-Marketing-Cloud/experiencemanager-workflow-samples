/*
 *   Copyright 2013 Adobe
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

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
