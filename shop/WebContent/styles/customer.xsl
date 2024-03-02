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
				  
				  <form action="/shop/shop/customer/" method="post">
				    <center>
				      <table border="0" width="170">
					<tr>
					  <td width="100%" background="/shop/images/background_1.jpg">
					    <b>
					      <font size="-1" face="Arial" color="#FFFFFF">
						Quick Login
					      </font>
					    </b>
					  </td>
					</tr>
					<tr>
					  <td width="100%" bgcolor="#EAEAFF">
					    <font size="-1" face="Arial">
					      EMail&nbsp;
						<input type="text" name="quickemail" value="{shop/customer/email}" size="20"/>
					    </font>
					  </td>
					</tr>
					<tr>
					  <td width="100%" bgcolor="#EAEAFF">
					    <font size="-1" face="Arial">
					      Password&nbsp;
						<input type="password" name="quickpasswd" size="16">
					       <xsl:if test="not(shop/customer/password/error)">
   			                         <xsl:attribute name="value">
	                                             <xsl:value-of select="shop/customer/password"/>
	                                         </xsl:attribute>
					       </xsl:if>
					      </input>
					    </font>
					  </td>
					</tr>
					<tr>
					  <td width="100%" align="center" bgcolor="#EAEAFF">
					    <input type="image" src="/shop/images/change.gif" border="0"/>
					  </td>
					</tr>
				      </table>	
				    </center>	
				  </form>
				  
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
				</div>
			      </td>
			      
			      <td align="left" width="500" height="95%" valign="top" colspan="2">
				<form action="/shop/shop/customer/" method="post">
				  <input type="hidden" name="action" value="change"/>
				    
				    <table border="0" width="478">
				      <tr>
					<td width="470" bgcolor="#2C2E7B" colspan="2">
					  <b>
					    <font face="Arial" color="#FFFFFF" size="3">
					      Registration
					    </font>
					  </b>
					</td>
				      </tr>
				      
				      <tr>
					<td width="150" bgcolor="#EAEAFF">
					  <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/email/error">
   				                 <xsl:attribute name="color">
				                    red
				                 </xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">
	                                          black
	                                        </xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
                                            EMail
                                          </font>
					</td>
                                        <td width="314" bgcolor="#EAEAFF">
					  <input type="text" name="email" value="{shop/customer/email}" size="30"/>
					</td>
				      </tr>
				      <tr>
					<td width="470" bgcolor="#2C2E7B" colspan="2">
                                          <font color="#FFFFFF" size="2" face="Arial">
                                            Password
                                          </font>
                                        </td>
				      </tr>
                                      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/password/error">
 				                <xsl:attribute name="color">
		                                   red
		                                </xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Password
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="password" name="password" value="{shop/customer/password}" size="30"/>
                                        </td>
                                      </tr>
                                      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/password/error">
 				                <xsl:attribute name="color">
							red
						</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Confirm Password
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="password" name="confirm_password" value="{shop/customer/confirm_password}" size="30"/>
                                        </td>
				      </tr>
 <tr>
					<td width="470" bgcolor="#2C2E7B" colspan="2">
                                          <font color="#FFFFFF" size="2" face="Arial">
                                            invoice address
                                          </font>
                                        </td>
				      </tr>
                                      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/forename/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Forename
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="forename" value="{shop/customer/forename}" size="30"/>
                                        </td>
				      </tr>

				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/name/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Name
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="name" value="{shop/customer/name}" size="30"/>
                                        </td>
				      </tr>

				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/address/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Address
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="address" value="{shop/customer/address}" size="30"/>
                                        </td>
				      </tr>

				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/zip/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    ZIP
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="zip" value="{shop/customer/zip}" size="30"/>
                                        </td>
				      </tr>

				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                           <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/city/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    City
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="city" value="{shop/customer/city}" size="30"/>
                                        </td>
				      </tr>
                                      <tr>
					<td width="470" bgcolor="#2C2E7B" colspan="2">
                                          <font size="2" face="Arial" color="#FFFFFF">
                                            delivery address
                                          </font>
                                        </td>
				      </tr>
                                      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/deliv_forename/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Forename
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="deliv_forename" value="{shop/customer/deliv_forename}" size="30"/>
                                        </td>
				      </tr>
				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/deliv_name/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Name
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="deliv_name" value="{shop/customer/deliv_name}" size="30"/>
                                        </td>
				      </tr>

				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/deliv_address/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    Address
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="deliv_address" value="{shop/customer/deliv_address}" size="30"/>
 
                                        </td>
				      </tr>
				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/deliv_zip/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    ZIP
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="deliv_zip" value="{shop/customer/deliv_zip}" size="30"/>
                                        </td>
				      </tr>
				      <tr>
					<td width="150" bgcolor="#EAEAFF">
                                          <font face="Arial" size="2">
					    <xsl:choose>	
					      <xsl:when test="shop/customer/deliv_city/error">
 				                <xsl:attribute name="color">red</xsl:attribute>
	                                      </xsl:when>
	                                      <xsl:otherwise>
	                                        <xsl:attribute name="color">black</xsl:attribute>
	                                      </xsl:otherwise>
		                            </xsl:choose>
					    City
					  </font>
                                        </td>
					<td width="314" bgcolor="#EAEAFF">
                                          <input type="text" name="deliv_city" value="{shop/customer/deliv_city}" size="30"/>
                                        </td>
				      </tr>
                                      <tr>
	                                <td width="314" bgcolor="#EAEAFF" colspan="2" align="center">
                                          <input type="image" src="/shop/images/change.gif" size="30" border="0"/>
                                        </td>
                                     </tr>
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