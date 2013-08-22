/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2011 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
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