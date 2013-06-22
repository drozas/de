<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Cartelera</title>
			</head>
			<body>
				El número total de cines es <xsl:value-of select="count(//cine)"/>.
				<br />
				Los cines en los que se proyecta <b> En busca de la felicidad</b> son  : 
				<hr />
				
				<xsl:apply-templates />
			</body>
		</html>
 	</xsl:template>
 	
 		<xsl:template match="//pelicula">
		<xsl:if test="@titulo = 'En busca de la felicidad'">
			<br>Cine: <xsl:value-of select="../@nombre" /></br>
		</xsl:if>
		

	</xsl:template>

</xsl:stylesheet>