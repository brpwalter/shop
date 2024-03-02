/**
 * Program name: EXT-SHOP
 * Classname: Article (represents an Article for the shop)
 * Packagename: de.extshop
 * This program is a servlet-based webshop
 * Copyright (C) 2001
 * @author    Matthias Brantner
 * @author    Stefan Schmidt
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You can find the full license in the main directory of this software.
 *
 * Contact:
 * Stefan Schmidt
 * stefan@schmidt1.de
 *
 * Matthias Brantner
 * Oberndorferstr. 14
 * 78713 Schramberg 
 * GERMANY
 * brantner@m-brantner.de
 *
 *
 */
package de.extshop;

import java.sql.*;
import org.w3c.dom.*;

/**
 * this class represents an article for the shop.
 * The article can be constructed with a given
 * ResultSet and the pointer to the beginning article.
 */
public class Article implements java.io.Serializable {

    /**
     * all values for the article
     */
    private String article, article_group, description;
    private float price;
    private int quantity, id;
    
    /**
     * constructs an article from a ResultSet
     */
    public Article(ResultSet rs) throws SQLException {
	this.id = rs.getInt("id");
	this.article = rs.getString("article");
	this.description = rs.getString("description");
	this.price = rs.getFloat("price");
	this.article_group = encode(rs.getString("article_group"));
	this.quantity=1;
    }

    /**
     * returns an element representation of this
     * article as children to the element named article
     */
    public Element toElement(Document doc) {

	Element e = doc.createElement("article");

	Element id_element = doc.createElement("id");
	id_element.appendChild(doc.createTextNode(String.valueOf(id)));
	e.appendChild(id_element);
	
	Element article_element = doc.createElement("article");
	article_element.appendChild(doc.createTextNode(article));
	e.appendChild(article_element);
	
	Element price_element = doc.createElement("price");
	price_element.appendChild(doc.createTextNode(String.valueOf(price)));
	e.appendChild(price_element);

	Element description_element = doc.createElement("description");
	description_element.appendChild(doc.createTextNode(description));
	e.appendChild(description_element);

	Element group_encoded = doc.createElement("group_encoded");
	group_encoded.appendChild(doc.createTextNode(article_group));
	e.appendChild(group_encoded);

	Element group_decoded = doc.createElement("group_decoded");
	group_decoded.appendChild(doc.createTextNode(decode(article_group)));
	e.appendChild(group_decoded);

	Element quantity_element = doc.createElement("quantity");
	quantity_element.appendChild(doc.createTextNode(String.valueOf(quantity)));
	e.appendChild(quantity_element);

	Element total_element = doc.createElement("total");
	total_element.appendChild(doc.createTextNode(java.text.NumberFormat.getInstance(java.util.Locale.ENGLISH).format(price*quantity)));
	e.appendChild(total_element);

	return (e);
    }

    public int getId() { 
	return id;
    }

    public java.lang.String getArticle() { 
	return article;
    }

    public void setArticle (java.lang.String article) {
	this.article = article;
    }

    public java.lang.String getDescription() { 
	return description;
    }

    public void setDescription (java.lang.String description) {
	this.description = description;
    }

    public java.lang.String getArticle_group() { 
	return article_group;
    }
    public void setArticle_group (java.lang.String article_group) {
	this.article_group = article_group;
    }

    public float getPrice() { 
	return price;
    }
    public void setPrice (float price) {
	this.price = price;
    }

    public int getQuantity() { 
	return quantity;
    }
    public void setQuantity (int quantity) {
	this.quantity = quantity;
    }

    private String encode(String s) {
	if (s!=null)
	    return(java.net.URLEncoder.encode(s.trim()));
	else
	    return("");
    }

    private String decode(String s) {
	if (s!=null)
	    return(java.net.URLDecoder.decode(s.trim()));
	else
	    return("");
    }
}
