<?xml version="1.0"?>
<recipe>



	<instantiate from="src/app_package/View.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/View${className}.java" />
	<instantiate from="src/app_package/Controller.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/Controller${className}.java" />
	<instantiate from="src/app_package/Presenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/Presenter${className}.java" />

   <#if includeLayout>
        <instantiate from="res/layout/controller_blank.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(controllerName)}.xml" />

        <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(controllerName)}.xml" />
    </#if>


	<open file="${srcOut}/Controller${className}.java"/>
</recipe>