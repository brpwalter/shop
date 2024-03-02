<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns="http://www.w3.org/TR/REC-html40">
<xsl:output method="html" indent="yes"/>

<xsl:template match="*|@*">
  <xsl:copy><xsl:apply-templates select='@*|node()'/></xsl:copy>
</xsl:template>

</xsl:stylesheet>
