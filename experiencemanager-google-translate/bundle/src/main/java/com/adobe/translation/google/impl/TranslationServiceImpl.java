/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2013 Adobe Systems Incorporated
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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.translation.google.TranslationService;
import com.adobe.translation.google.Translator;

/**
 * <code>TranslatorServiceImpl</code>...
 */
@Component()
@Service(value = TranslationService.class)
public class TranslationServiceImpl implements TranslationService {

    /**
     * default logger
     */
    private static final Logger log = LoggerFactory.getLogger(TranslationServiceImpl.class);

    private static final String GOOGLE_API_V2_URI = "https://www.googleapis.com/language/translate/v2";

    private HttpClient httpClient;

    private MultiThreadedHttpConnectionManager cxMgr;

    // TODO: make configurable
    private final String key = "AIzaSyD726A86aO5bgu2W6Z8YJ603Xaf6dz5Xr8";

    @Activate
    protected void activate() {
        cxMgr = new MultiThreadedHttpConnectionManager();
        httpClient = new HttpClient(cxMgr);
    }

    @Deactivate
    protected void deactivate() {
        if (cxMgr != null) {
            cxMgr.shutdown();
            cxMgr = null;
        }
        httpClient = null;
    }
    
    public Translator getTranslator(String srcLanguage, String dstLanguage) {
        return new TranslatorImpl(this, srcLanguage, dstLanguage);
    }

    public String translate(String text, String src, String dst) {
        GetMethod get = new GetMethod(GOOGLE_API_V2_URI);
        List<NameValuePair> query = new LinkedList<NameValuePair>();
        query.add(new NameValuePair("key", key));
        if (src != null && src.length() > 0) {
            query.add(new NameValuePair("source", src));
        }
        query.add(new NameValuePair("target", dst));
        query.add(new NameValuePair("q", text));
        get.setQueryString(query.toArray(new NameValuePair[query.size()]));
        try {
            int code = httpClient.executeMethod(get);
            if (code != 200) {
                log.error("Unable to translate text. Server responded {}.", code);
                log.error("Response body: {}", get.getResponseBodyAsString());
            } else {
                // we just concatenate the texts
                StringBuilder ret = new StringBuilder();
                JSONObject json = new JSONObject(get.getResponseBodyAsString());
                JSONObject data = json.getJSONObject("data");
                JSONArray translations = data.getJSONArray("translations");
                for (int i=0; i<translations.length(); i++) {
                    JSONObject translation = translations.getJSONObject(i);
                    ret.append(translation.getString("translatedText"));
                }
                return ret.toString();
            }
        } catch (HttpException e) {
            log.error("Error while translating.", e);
        } catch (JSONException e) {
            log.error("Error while translating.", e);
        } catch (IOException e) {
            log.error("Error while translating.", e);
        } finally {
            get.releaseConnection();
        }
        return "";
    }
}