<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/">
		<html>
			<head>
				<title>Documentación Electrónica - Práctica obligatoria (grupo 3)</title>
				<link rel="stylesheet" type="text/css" href="transparentia.css" />
			</head>
			<body>
				<h1 align="center"> 
					Clusters obtenidos:  <xsl:value-of select="count(//CLUSTER)"/> <br />
					Medida-F = <xsl:value-of select="/CLUSTERS/@medida"/>
				</h1>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="//CLUSTER">
		<h2> Cluster <xsl:value-of select="@id"/> </h2><br />

			<UL>
			<xsl:for-each select="./NOTICIA">
					<li>
					<a>
						<xsl:attribute name="href">
							<xsl:value-of select="./URL" />
						</xsl:attribute>
						<xsl:value-of select="./TITULO" /> 
					</a>
					</li>
		
			</xsl:for-each>	
			</UL>
	</xsl:template>
				

</xsl:stylesheet>