<?xml version="1.0"?>

<!DOCTYPE page [
<!ENTITY nbsp "<img src='/shop/images/x.gif' width='1' height='1'/>">
 ]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns="http://www.w3.org/TR/REC-html40">
<xsl:output method="html" indent="yes"/>

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
				<xsl:if test="shop/grouplist/group">
				  <table border="0" width="170">
				    <tr>
				      <td width="170" background="/shop/images/background_1.jpg" colspan="2">
					<font size="2" face="Arial" color="#FFFFFF">
					  <b>articlegroups:</b>
					</font>
				      </td>	
				    </tr>
				      <xsl:apply-templates select="shop/grouplist/group"/>	
				  </table>
				</xsl:if>				  
				</div>
				
			      </td>
			     
			      <td align="left" width="500" height="95%" valign="top" colspan="2">
				<form action="/shop/shop/cart/" method="post">
				  <input type="hidden" name="action" value="change"/>

				    <table border="0" width="478">
				      <tr>
					<td width="470" bgcolor="#2C2E7B" colspan="6">
					  <b>
					    <font face="Arial" color="#FFFFFF" size="3">
					      My Cart
					    </font>
					  </b>
					</td>
				      </tr>
				      <xsl:choose>
				      <xsl:when test="shop/cart/article">
				      <tr>
					<td bgcolor="#2C2E7B" width="67">
					  <font size="2" face="Arial" color="#FFFFFF">
					    <b>
					      id
					    </b>
					  </font>
					</td>
					<td bgcolor="#2C2E7B" width="146">
					  <font size="2" face="Arial" color="#FFFFFF">
					    <b>
					      name
					    </b>
					  </font>
					</td>
					<td bgcolor="#2C2E7B" width="82">
					  <b>
					    <font size="2" face="Arial" color="#FFFFFF">
					      price
					    </font>
					  </b>
					</td>
					<td bgcolor="#2C2E7B" width="45">
					  <b>
					    <font size="2" face="Arial" color="#FFFFFF">
					      quantity
					    </font>
					  </b>
					</td>
					<td bgcolor="#2C2E7B" colspan="2" width="106">
					  <b>
					    <font size="2" face="Arial" color="#FFFFFF">
					      total
					    </font>
					  </b>
					</td>
				      </tr>
				      <xsl:apply-templates select="shop/cart/article"/>

				  
				      <tr>
					<td width="295" bgcolor="#2C2E7B" colspan="3" align="right">
					<font color="#FFFFFF" size="2" face="Arial">
					  <b>
					    change =>
					  </b>
					</font>
					</td>
					<td bgcolor="#2C2E7B" align="center" width="45">
					<input type="image"  border="0" src="/shop/images/change.gif" width="15" height="15"/>
					</td>
					<td bgcolor="#2C2E7B" align="right" width="83" colspan="2">
					  <font color="#FFFFFF" size="2" face="Arial">
					    <b>
					      <xsl:value-of select="sum(shop/cart/article/total)"/> Euro
					    </b>
					  </font>
					</td>
 				      </tr>
				  </xsl:when>
				  
				  <xsl:otherwise>
				    <tr align="center">
				      <td colspan="6">
					no articles in cart;((
				      </td>
				    </tr>
				  </xsl:otherwise>
	                          </xsl:choose>
                                 </table>
				</form>
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
	
<xsl:template match="article">
	  <tr>
	    <td bgcolor="#EAEAFF" width="67">
	      <font size="2" face="Arial">
		<xsl:value-of select="id"/> 
	      </font>
	    </td>
	    <td bgcolor="#EAEAFF" width="146">
	      <font size="2" face="Arial">
		<xsl:value-of select="article"/>  
	      </font>
	    </td>
	    <td bgcolor="#EAEAFF" align="right" width="82">
	      <font size="2" face="Arial">
		<xsl:value-of select="price"/> Euro 
	      </font>
	    </td>
	    <td bgcolor="#EAEAFF" align="center" width="45">
	      <input type="text" name="quantity_{id}" value="{quantity}" maxlength="3" size="3"/>
	    </td>
	    <td bgcolor="#EAEAFF" align="right" width="83">
	      <font size="2" face="Arial">
		<xsl:value-of select="(total)"/>
	      </font>
	    </td>
	    <td bgcolor="#EAEAFF" width="17" align="center">
	      <a HREF="/shop/shop/cart/?action=delete&amp;id={id}">
	         <img border="0" src="/shop/images/delete.gif" width="15" height="15"/>
	      </a>
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