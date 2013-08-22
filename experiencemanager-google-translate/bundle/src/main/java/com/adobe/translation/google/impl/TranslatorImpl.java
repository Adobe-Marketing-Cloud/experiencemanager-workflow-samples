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

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.foundation.Paragraph;
import com.day.cq.wcm.foundation.ParagraphSystem;
import com.adobe.translation.google.Translator;

/**
 * <code>TranslatorImpl</code>...
 */

public class TranslatorImpl implements Translator {

    /** Default log. */
    /**
     * default logger
     */
    private static final Logger log = LoggerFactory.getLogger(TranslatorImpl.class);

    private String srcLang = "";
    private String dstLang = "";
    private ArrayList<String> propertiesToTranslate;

    private final TranslationServiceImpl svc;

    /**
     * Constructor
     *
     * @param srcLang The source language to translate
     * @param dstLang   The target language to translate
     */
    protected TranslatorImpl(TranslationServiceImpl svc, String srcLang, String dstLang) {
        this.svc = svc;

        this.srcLang = srcLang;
        this.dstLang = dstLang;

        propertiesToTranslate = new ArrayList<String>();
    }

    /**
     * To translate a given String
     *
     * @param toTranslateText The string to be translated
     */
    public String translateString(String toTranslateText) {
        String translatedText = "";
        try {
            translatedText = svc.translate(toTranslateText, srcLang, dstLang);
        } catch (Exception e) {
            log.info("translateString: Error translating", e);
            e.printStackTrace();
        }
        return translatedText;
    }

    /**
     * To Translate the given page
     *
     * @param rootPage The page to be translated
     * @throws Exception
     */
    public void translatePage(Page rootPage) throws Exception {
        //Translate the page properties
        translateNode(rootPage.getContentResource().adaptTo(Node.class));

        // Translate all child node, this take care of the case when the child component is not a parsys
        translateAllChildNodes(rootPage.getContentResource().adaptTo(Node.class));

        // retrieve the paragraph system object from the page
        ParagraphSystem parsys = new ParagraphSystem(rootPage.getContentResource());

        List<Paragraph> pars = parsys.paragraphs();

        for (Paragraph par : pars) {
            // Translate the paragraph
            translateParagraph(par);
        }
    }

    /**
     * Add additional property (belonging to nodes) to be translated
     *
     * @param property Name of property
     */
    public void addPropertyToTranslate(String property) {
        if (property != null && property.length() > 0)
            propertiesToTranslate.add(property);
    }

    /**
     * Translate the given paragraph
     *
     * @param par The paragraph to translate
     * @throws Exception
     */
    private void translateParagraph(Paragraph par) throws Exception {
        Node parNode = par.adaptTo(Node.class);

        try {
            NodeIterator inodes = parNode.getNodes();

            // iterate through all the nodes of the paragraph system
            while (inodes.hasNext()) {
                // translate the node
                translateNode(inodes.nextNode());
            }
        } catch (Exception e) {
            log.debug("TranslateParagraph: Error translating", e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Translate the given node
     *
     * @param node Node to translate
     */
    private void translateNode(Node node) {
        try {
            for (int index = 0; index < propertiesToTranslate.size(); index++) {
                String value = getNodeProperty(node, propertiesToTranslate.get(index), "");

                if (value != null && value.length() > 0) {
                    String translatedText = svc.translate(value, srcLang, dstLang);

                    // update the translated text and save
                    if (translatedText != null && translatedText.length() > 0) {
                        node.setProperty(propertiesToTranslate.get(index), translatedText);
                    }
                }

                // save the changes
                if (index > 0)
                    node.getSession().save();
            }
        } catch (Exception e) {
            log.debug("translateNode: Error translating", e);
            e.printStackTrace();
            //throw e;
        }

    }

    /**
     * Translate all the child nodex
     *
     * @param parNode
     */
    private void translateAllChildNodes(Node parNode) {
        try {
            NodeIterator inodes = parNode.getNodes();

            // iterate through all the nodes of the paragraph system
            while (inodes.hasNext()) {
                // translate the node
                translateNode(inodes.nextNode());
            }
        } catch (Exception e) {
            log.debug("translateAllChildNodes: Error translating", e);
            e.printStackTrace();
            //throw e;
        }
    }

    private String getNodeProperty(Node node, String propertyString,
                                   String defaultString) {
        try {
            Property prop = node.getProperty(propertyString);
            if (prop == null)
                return defaultString;
            return prop.getString();
        } catch (Exception ex) {
            return defaultString;
        }
    }

}