<?xml version="1.0"?>

<!DOCTYPE page [
<!ENTITY nbsp "<img src='/shop/images/x.gif' width='1' height='1'/>">
]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns="http://www.w3.org/TR/REC-html40">
<xsl:output method="html" indent="yes"/>


<!--<xsl:template match="*|@*">
<xsl:copy><xsl:apply-templates select='@*|node()'/></xsl:copy>
</xsl:template>-->


<xsl:template match="/">
<html>
  <head>
    <title>WebShop</title>
    <meta http-equiv="expires" content="0"/>
  </head>
  <body>
    
    <div align="center">
      <center>
        <table border="0" heigth="100%" cellspacing="0" cellpadding="2" height="100%">
          <tr>
            <td width="100%" colspan="3" background="/shop/images/background_1.jpg" height="5%">
              <font color="#FFFFFF" size="3" face="Arial">
                <b>EXTShop</b>
              </font>
            </td>
          </tr>
          <tr>
            <td width="6" background="/shop/images/margin_left.jpg" height="90%">
              &nbsp;
            </td>
            <td bgcolor="#FFFFFF" valign="top" align="left" height="90%">
              <div align="center">
                
                <table border="0" cellspacing="0" cellpadding="0" width="700" height="100%">
                  <tr>
                    <td align="left" height="5%" width="15" valign="middle" background="/shop/images/background_left.jpg">
                      &nbsp;
                    </td>
                    <td align="left" height="5%" width="170" valign="middle" background="/shop/images/line.jpg">
                      &nbsp;
                    </td>
                    <td align="left" height="5%" width="15" valign="middle" background="/shop/images/background_right.jpg">
                      &nbsp;
                    </td>
                    <td align="right" background="/shop/images/line1.jpg" height="5%" width="490">
                      <font face="Arial" size="2">
                        <a href="/shop/shop/cart/">
                          <img border="0" src="/shop/images/icon_cart.jpg" width="40" height="40"/></a>
                          <a href="/shop/shop/order/?action=view">
                            <img border="0" src="/shop/images/icon_order.jpg"/>
                          </a>
                          <a href="/shop/shop/customer/">
                            <img border="0" src="/shop/images/icon_customer.jpg"/>
                          </a>
                        </font>
                      </td>
                      
                      <td align="right" background="/shop/images/line2.jpg" height="5%" width="10">
                        &nbsp;
                      </td>
                    </tr>
                    
                    <tr>
                      <td align="left" width="200" valign="top" background="/shop/images/background_2.jpg" colspan="3" height="95%">
                        
                        
                        <div align="center">
                          <table border="0" width="170">
                            <tr>
                              <td width="170" background="/shop/images/background_1.jpg">
                                <font size="2" face="Arial" color="#FFFFFF">
                                  <b>
                                    articlegroups:
                                  </b>
                                </font>
                              </td>	
                            </tr>
                            <xsl:apply-templates select="shop/grouplist/group"/>	
                          </table>
                        </div>
                      </td>
                      
                      <td align="left" width="500" height="95%" valign="top" colspan="2">
                        <xsl:choose>
                          <!-- Beginning of Article Table -->
                          <xsl:when test="shop/articlelist/article">
                            <table border="0" width="100%">
                              <tr>
                                <td width="12%" background="/shop/images/background_1.jpg">
                                  <b>
                                    <font color="#FFFFFF" size="2" face="Arial">
                                      id
                                    </font>
                                  </b>
                                </td>
                                
                                <td width="50%" background="/shop/images/background_1.jpg">
                                  <b>
                                    <font color="#FFFFFF" size="2" face="Arial">
                                      name
                                    </font>
                                  </b>
                                </td>
                                
                                <td width="18%" background="/shop/images/background_1.jpg">
                                  <b>
                                    <font color="#FFFFFF" size="2" face="Arial">
                                      price
                                    </font>
                                  </b>
                                </td>
                                
                                <td width="10%" background="/shop/images/background_1.jpg">
                                  <font color="#FFFFFF" size="2" face="Arial">
                                    <b>in cart</b>
                                  </font>
                                </td>
                              </tr>
                              <!-- show Articles -->
                              <xsl:apply-templates select="shop/articlelist/article"/>
                            </table>
                            <!-- End of the Article Table -->
                          </xsl:when>
                          <!-- Start of Article Description -->
                          <xsl:otherwise>
                            <xsl:if test="shop/article">
                              <table border="0" width="100%">
                                <xsl:apply-templates select="shop/article"/>
                              </table>
                            </xsl:if>
                          </xsl:otherwise>
                          <!-- End of Article Description -->
                        </xsl:choose>
                      </td>
                    </tr>
                    
                    <tr>
                      <td align="left" width="200" valign="top" background="/shop/images/background_3.jpg" colspan="3" height="95%">
                        &nbsp;
                      </td>
                      <td align="left" width="500" height="95%" valign="top" colspan="2">
                        &nbsp;
                      </td>
                    </tr>
                    
                  </table>
                </div>
              </td>
              
              <td width="6" background="/shop/images/margin_right.jpg" height="90%">
                <p>&nbsp;</p>
              </td>
            </tr>
            
            <tr>
              <td width="300%" colspan="3" background="/shop/images/background_1.jpg" align="right" height="5%">
                <font face="Arial" size="1" color="#FFFFFF">
                  this shop is just for demonstration
                </font>
              </td>
            </tr>
          </table>
        </center>
      </div>
    </body>
  </html>
</xsl:template>


<xsl:template match="/shop/articlelist/article">
  <tr>
    <td width="12%" bgcolor="#EAEAFF">
      <font face="Arial" size="2">
        <xsl:value-of select="id"/>
      </font>
    </td>
    <td width="50%" bgcolor="#EAEAFF">
      <font size="2" face="Arial">
        <a href="../search/?id={id}">
          <xsl:value-of select="article"/>
        </a>	
      </font>
    </td>
    <td width="18%" bgcolor="#EAEAFF" align="right">
      <font face="Arial" size="2">
        <xsl:value-of select="price"/> Euro
      </font>
    </td>
    <td width="10%" bgcolor="#EAEAFF" align="center">
      <A HREF="/shop/shop/cart/?action=add&amp;id={id}">
        <img border="0" src="/shop/images/cart.gif"/>
      </A>
    </td>
  </tr>
</xsl:template>	  

<xsl:template match="shop/article">
  <tr>
    <td width="100%" colspan="2" background="/shop/images/background_1.jpg">
      <b>
        <font size="-1" face="Arial" color="#FFFFFF">
          <xsl:value-of select="article"/>
        </font>
      </b>
    </td>
  </tr>
  <tr>
    <td width="40" align="center">
      <img src="/shop/articles/{id}k.gif"/>
    </td>
    <td width="60%" valign="top">
      <font size="-1" face="Arial">
        <xsl:value-of select="description"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colpsan="2">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td bgcolor="#EAEAFF">
      <center>
        <font size="-1" face="Arial" color="#FFFFFF">
          <a href="/shop/shop/search/?group={group_encoded}">
            back
          </a>
        </font>
      </center>
    </td>
    <td width="10%" bgcolor="#EAEAFF" align="center">
      <A HREF="/shop/shop/cart/?action=add&amp;id={id}">
        <img border="0" src="/shop/images/cart.gif"/>
      </A>
    </td>
  </tr>
</xsl:template>

<xsl:template match="shop/grouplist/group">
  <tr>
    <td width="150" bgcolor="#EAEAFF" background="/shop/images/background_4.jpg">
      <font size="2" face="Arial">
        <a HREF="/shop/shop/search/?group={encoded}">
          <xsl:value-of select="decoded"/>
        </a>
      </font>
    </td>
  </tr>
</xsl:template>

</xsl:stylesheet>
