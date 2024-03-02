<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="catalog">
    <html>
      <head>
        <title>Beispiel Stylesheet</title>
      </head>
      <body>
        <table>
          <tr>
            <td>Name</td>
            <td>Preis</td>
          </tr>
          <xsl:apply-templates/>
        </table>
      </body>
    </html>
  </xsl:template>
  <xsl:template match="article">
    <tr>
      <td>
        <xsl:value-of select="name"/>
      </td>
      <td>
        <xsl:value-of select="price"/>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>
