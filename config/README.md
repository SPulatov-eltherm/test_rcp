# Setup Apache Batik 1.19 for NetBeans RCP Module



This guide explains how to configure and use Apache Batik 1.19 in a NetBeans Platform RCP (`nbm`) module. After following these steps, classes like `GraphicsNode` will be accessible and you can render SVGs in your module.



## Step 1 — Install Apache Maven



1\. Download Apache Maven from https://maven.apache.org/download.cgi, choose the \*\*Binary zip archive\*\*.

2\. Extract the zip to a folder, for example:

C:\\tools\\apache-maven-3.9.4

3\. Set environment variables:

&nbsp;  - MAVEN\_HOME → C:\\tools\\apache-maven-3.9.4

&nbsp;  - Add C:\\tools\\apache-maven-3.9.4\\bin to your PATH

4\. Open a terminal or command prompt and verify:

mvn -v

You should see Maven version and Java version.



## Step 2 — Extract Batik JARs



1\. Unzip batik-1.19.zip to a folder, e.g.:

C:\\Users\\Samir\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib

2\. Inside the folder, you should have at least the following JARs:

batik-gvt-1.19.jar

batik-awt-util-1.19.jar

batik-dom-1.19.jar

batik-util-1.19.jar

batik-xml-1.19.jar

> ⚠️ If you only have batik-all-1.19.jar, you can try it, but using the individual JARs is safer for RCP modules.



## Step 3 — Install Batik JARs into Local Maven Repository



Run the following commands for each JAR:



mvn install:install-file -Dfile=C:\\Users\\YOUR\_USER\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib\\batik-gvt-1.19.jar -DgroupId=org.apache.xmlgraphics -DartifactId=batik-gvt -Dversion=1.19 -Dpackaging=jar

mvn install:install-file -Dfile=C:\\Users\\YOUR\_USER\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib\\batik-awt-util-1.19.jar -DgroupId=org.apache.xmlgraphics -DartifactId=batik-awt-util -Dversion=1.19 -Dpackaging=jar

mvn install:install-file -Dfile=C:\\Users\\YOUR\_USER\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib\\batik-dom-1.19.jar -DgroupId=org.apache.xmlgraphics -DartifactId=batik-dom -Dversion=1.19 -Dpackaging=jar

mvn install:install-file -Dfile=C:\\Users\\YOUR\_USER\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib\\batik-util-1.19.jar -DgroupId=org.apache.xmlgraphics -DartifactId=batik-util -Dversion=1.19 -Dpackaging=jar

mvn install:install-file -Dfile=C:\\Users\\YOUR\_USER\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib\\batik-xml-1.19.jar -DgroupId=org.apache.xmlgraphics -DartifactId=batik-xml -Dversion=1.19 -Dpackaging=jar



✅ After this, all Batik JARs are installed in your local Maven repository (~/.m2/repository/...).



## Step 4 — Update Your Module POM



In your module maven\_test\_module/pom.xml, replace any system scoped dependencies with standard Maven dependencies:



<dependency>

&nbsp;   <groupId>org.apache.xmlgraphics</groupId>

&nbsp;   <artifactId>batik-gvt</artifactId>

&nbsp;   <version>1.19</version>

</dependency>

<dependency>

&nbsp;   <groupId>org.apache.xmlgraphics</groupId>

&nbsp;   <artifactId>batik-awt-util</artifactId>

&nbsp;   <version>1.19</version>

</dependency>

<dependency>

&nbsp;   <groupId>org.apache.xmlgraphics</groupId>

&nbsp;   <artifactId>batik-dom</artifactId>

&nbsp;   <version>1.19</version>

</dependency>

<dependency>

&nbsp;   <groupId>org.apache.xmlgraphics</groupId>

&nbsp;   <artifactId>batik-util</artifactId>

&nbsp;   <version>1.19</version>

</dependency>

<dependency>

&nbsp;   <groupId>org.apache.xmlgraphics</groupId>

&nbsp;   <artifactId>batik-xml</artifactId>

&nbsp;   <version>1.19</version>

</dependency>



> This ensures that GraphicsNode and other Batik classes are visible to your NetBeans RCP module.



## Step 5 — Build and Verify



1\. Open NetBeans and select your module.

2\. Run Clean and Build.

3\. If there are no errors, import Batik classes in your module:



import org.apache.batik.gvt.GraphicsNode;



4\. You can now render SVGs on your panels or visual components.



## Step 6 — Optional: Using batik-all.jar



\- You can install batik-all-1.19.jar if you prefer:



mvn install:install-file -Dfile=C:\\Users\\Samir\\NetBeansProjects\\test\_rcp\\libs\\batik\\lib\\batik-all-1.19.jar -DgroupId=org.apache.xmlgraphics -DartifactId=batik-all -Dversion=1.19 -Dpackaging=jar



\- Then in POM:



<dependency>

&nbsp;   <groupId>org.apache.xmlgraphics</groupId>

&nbsp;   <artifactId>batik-all</artifactId>

&nbsp;   <version>1.19</version>

</dependency>



> ⚠️ Note: GraphicsNode might not be recognized in some RCP modules using batik-all.jar, so using individual JARs is safer.



## Notes



\- Keep a copy of Batik JARs in your project or local Maven repository for portability.

\- When upgrading Batik versions, repeat steps 2–4 with the new version.

\- For advanced usage, consider wrapping Batik JARs as OSGi bundles for better NetBeans Platform integration.

