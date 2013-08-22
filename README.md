This provides a package to perform translation using Google Translation
The translation is done through a workflow and will translate the page according to the language that is part of the content page. Eg. /content/geometrixx/de, /content/geometrixx/de/support, /content/geometrixx/de/support/hello will translate it to German.
It assumes that the language field is always the third position. These can be configured in the workflow scripts.

This includes:
* Bundle to perform translation
* Workflow script to perform a workflow translation process
* A simple workflow model (Google Translation) for activating the translation
* Component to do adhoc translation

Note: This version uses the new Google Translate API V2
