<%@include file="/libs/foundation/global.jsp"%><%
%><%@page import="com.day.translation.google.TranslationService" %><%

    String translateText = slingRequest.getParameter("translateText");
    TranslationService svc = sling.getService(TranslationService.class);
    if (translateText != null && translateText.length() > 0) {
        String fromLanguage = slingRequest.getParameter("fromLanguage");
        String toLanguage = slingRequest.getParameter("toLanguage");

        if (fromLanguage != null && fromLanguage.length() > 0 && toLanguage != null && toLanguage.length() > 0) {
            String translatedText = svc.translate(translateText, fromLanguage, toLanguage);

%>
From Text: <%=translateText %> (<%=fromLanguage %>)<br/>
To Text: <%=translatedText %> (<%=toLanguage %>)<br/>
<br/>
<%
        }
    } else {
        translateText = "";
    }
%><div class="form">

    <form action="${currentPage.path}.html">

	<div class="dropdown section">
	    <div class="form_row">
	       <div class="form_leftcol"><div class="form_leftcollabel"><label for="fromLanguage">From Language</label></div><div class="form_leftcolmark">&nbsp;</div></div>
	       <div class="form_rightcol">
	        <select class="form_field form_field_select" id="fromLanguage" name="fromLanguage" >
	           <option value="en">English</option>
	           <option value="de">German</option>
	           <option value="fr">French</option>
	           <option value="sv">Swedish</option>
	        </select>
	       </div>
	       </div> 
	    <div class="form_row_description"></div>
	</div>

    <div class="dropdown section">
        <div class="form_row">
           <div class="form_leftcol"><div class="form_leftcollabel"><label for="toLanguage">To Language</label></div><div class="form_leftcolmark">&nbsp;</div></div>
           <div class="form_rightcol">
            <select class="form_field form_field_select" id="toLanguage" name="toLanguage" >
               <option value="en">English</option>
               <option selected value="de">German</option>
               <option value="fr">French</option>
               <option value="sv">Swedish</option>
            </select>
           </div>
           </div>
        <div class="form_row_description"></div>
    </div>

	<div class="text section"><div class="form_row">
	        <div class="form_leftcol"><div class="form_leftcollabel"><label for="_content_geometrixx_en_services_mytest_jcr_content_par_start_text">Text</label></div><div class="form_leftcolmark">&nbsp;</div></div>
	        <div class="form_rightcol" id="text_rightcol"><div id="text_0_wrapper" class="form_rightcol_wrapper">
	        <textarea class="form_field form_field_textarea" id="translateText" name="translateText" rows="5" cols="35" ><%=translateText %></textarea>
	        </div></div></div><div class="form_row_description"></div>
	</div>

	<div class="submit section">
	    <div class="form_row">
	      <div class="form_rightcol">
	        <input type="submit" class="form_button_submit" name="Translate" value="Translate">
	      </div>
	    </div>
	    <div class="form_row_description"></div>
	</div>

    <div class="section end"></div>

    </form>

</div>