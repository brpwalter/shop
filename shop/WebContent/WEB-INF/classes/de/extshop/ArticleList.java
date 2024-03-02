/**
 * Program name: EXT-SHOP
 * This program is a servlet-based webshop
 * Classname: ArticleList (represents an Shopping-Cart or Articlelist)
 * Packagename: de.extshop
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
 * this class represents an articlelist for the shop.
 * the method toElement can return a element containing
 * all articles
 * 
 */
public class ArticleList implements java.io.Serializable {

    java.util.Vector list; // vector containing all articles

    /**
     * constructs an empty articlelist
     */
    public ArticleList()  {
	list = new java.util.Vector();
    }

    /**
     * returns the number of articles in the list
     */
    public int getSize() {
	return(list.size());
    }

    /**
     * deletes the article given as parameter from the list
     */
    public void deleteArticle(Article article) {
	list.removeElement(article);
    }

    /**
     * deletes the article with the given id from the list
     */
    public void deleteArticle(int id) {
	list.removeElement(getArticle(id));
    }

    /**
     * returns the article with the given id
     * or null if the article is not found
     */
    public Article getArticle(int id) {

	for (int i=0; i<list.size(); i++) {
	    Article article;
	    if (((article = (Article)list.elementAt(i)).getId())==id)
		return(article);
	}
	return (null);
    }

    /**
     * returns the article at a given position
     */       
    public Article getArticleAt(int i) {
	return((Article) list.elementAt(i));
    }

    /**
     * adds a given article to the list
     */
    public void addArticle(Article article) {
	list.add(article);
    }

    /**
     * this method returns an representation of the articlelist appended
     * to the given element. Each appended article is named article
     */
    public Element toElement(Element e) {

	for (int i=0; i<list.size(); i++) {
	    e.appendChild(((Article)list.elementAt(i)).toElement(e.getOwnerDocument()));
	}
	return (e);
    }
}
