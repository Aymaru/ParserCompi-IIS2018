<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:template match="/">
		<html>

			<head>
				<style type="text/css">
					table.tfmt {
					border: 1px ;
					}

					td.colfmt {
					border: 1px ;
					background-color: white;
					color: black;
					text-align:center;
					}

					th.thm {
					background-color: #2E9AFE;
					color: white;
					}
                    
                    table.tfmt1 {
					border: 1px ;
					}
                    
                    td.colfmt1 {
					border: 1px ;
					background-color: white;
					color: black;
					text-align:center;
					}

					th.thm1 {
					background-color: #ff0000;
					color: white;
					}

				</style>
			</head>

			<body>
                <table class="tfmt">
					<tr>
						<th class="thm" style="width:250px">Token</th>
						<th class="thm" style="width:350px">Tipo Token</th>
						<th class="thm" style="width:250px">Apariciones</th>


					</tr>

					<xsl:for-each select="Scanner/Tokens/Token">

						<tr>
							<td class="colfmt">
								<xsl:value-of select="Nombre" />
							</td>
							<td class="colfmt">
								<xsl:value-of select="Tipo" />
							</td>

							<td class="colfmt">
								<xsl:value-of select="Apariciones" />
							</td>
						</tr>
					</xsl:for-each>
				</table>
                <table class="tfmt1">
					<tr>
                        <th class="thm1" style="width:200px">Tipo Error</th>
						<th class="thm1" style="width:350px">Token</th>
						<th class="thm1" style="width:200px">Apariciones</th>
						<th class="thm1" style="width:500px">Detalle</th>
					</tr>
					<xsl:for-each select="Scanner/Errores/Error">
						<tr>
                            <td class="colfmt1">
								<xsl:value-of select="Tipo" />
							</td>
							<td class="colfmt1">
								<xsl:value-of select="Token" />
							</td>

							<td class="colfmt1">
								<xsl:value-of select="Apariciones" />
							</td>
							<td class="colfmt1">
                                
								<xsl:value-of select="Detalle"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>