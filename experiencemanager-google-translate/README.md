Google Translate 2.0
====================

This provides a package to perform translation using Google Translation
The translation is done through a workflow and will translate the page according to the language that is part of the content page. Eg. /content/geometrixx/de, /content/geometrixx/de/support, /content/geometrixx/de/support/hello will translate it to German.
It assumes that the language field is always the third position. These can be configured in the workflow scripts.

This includes:
* Bundle to perform translation
* Workflow script to perform a workflow translation process
* A simple workflow model (Google Translation) for activating the translation
* Component to do adhoc translation

Note: This version uses the new Google Translate API V2

Building
--------

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a AEM instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a AEM instance.

Using with VLT
--------------

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

Specifying CRX Host/Port
------------------------

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=5502 <goals>
