<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Cartelera</title>
			</head>
			<body>
				<h1 align="center">Cartelera</h1>
				<xsl:apply-templates />
				<xsl:call-template name="pintar_nombre" />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="//cine" name="pintar_nombre">
		<b><xsl:value-of select="@nombre" /></b>
		<br />
		<select>
		<xsl:for-each select="./pelicula">
			<xsl:sort order="ascending" select="."/>
					<option><xsl:value-of select="./@titulo" /></option>
		</xsl:for-each>
		</select>
		<br />
	</xsl:template>
</xsl:stylesheet>