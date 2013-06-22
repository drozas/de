<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Tienda</title>
			</head>
			<body>
				Tienda <xsl:value-of select="//nombre" />
				El número total de productos es <xsl:value-of select="count(//producto)"/>.
				<xsl:apply-templates />
			</body>
		</html>
 	</xsl:template>
 	
 	<xsl:template match="//producto">
 		<br>
 			<xsl:value-of select="./codigo" />
			<xsl:value-of select="./articulo" />
			<xsl:if test="'9' >= ./cantidad ">
				rebaja!
			</xsl:if>
		</br>	
	</xsl:template>

</xsl:stylesheet>