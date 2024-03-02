<?xml version="1.0"?>

<!DOCTYPE page [
<!ENTITY nbsp "<IMG SRC='/shop/images/x.gif' WIDTH='1' HEIGHT='1'/>">
 ]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns="http://www.w3.org/TR/REC-html40">
<xsl:output method="html" indent="yes"/>

  
<xsl:template match="/">
    <html>
      <head>
	<title>WebShop</title>
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
			    <!-- START Article Grouplist -->
			      <table border="0" width="170">
 			        <tr>
				  <td width="170" background="/shop/images/background_1.jpg">
				    <font size="2" face="Arial" color="#FFFFFF">
				      <b>articlegroups:</b>
				    </font>
				  </td>	
				</tr>
                                <xsl:apply-templates select="shop/grouplist/group"/>	
			      </table>
			     <!-- END Article Grouplist --> 
			    </div>
			</td>
			
			<td align="left" width="500" height="95%" valign="top" colspan="2">
			      <table border="0" width="100%">
				<tr>
				 <td width="100%" background="/shop/images/background_1.jpg">
				   <b>
				    <font color="#FFFFFF" size="2" face="Arial">
					an error has occured
				    </font>
				   </b>
				 </td>
				</tr>
			      </table>
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
		    &nbsp;
		  </font>
		</td>
	      </tr>
	    </table>
	  </center>
	</div>
      </body>
    </html>
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
