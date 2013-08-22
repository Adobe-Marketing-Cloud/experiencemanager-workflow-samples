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

package com.adobe.translation.google.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * <code>TestTranslations</code>...
 */
public class TestTranslations {

    private TranslationServiceImpl svc;

    @Before
    public void setup() {
        svc = new TranslationServiceImpl();
        svc.activate();
    }

    @After
    public void teardown() {
        svc.deactivate();
        svc = null;
    }

    @Test
    public void testSimple() {
        String result = svc.translate("Hello, world.", "en", "de");
        assertEquals("Hallo, Welt.", result);
    }
}