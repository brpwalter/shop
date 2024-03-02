/**
 * Program name: EXT-SHOP
 * This program is a servlet-based webshop
 * Classname: Customer (represents an Customer)
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
import javax.servlet.http.*;
import org.apache.regexp.*;


/**
 * this class represents a customer for the shop.
 * the customer can either be created with a given ResultSet
 * or a request which contains the neccessary parameters.
 * toElement() returns an element representation of the customer.
 * toDatabase(Connection) inserts it into the database or updates
 * the database.
 *
 */
public class Customer implements java.io.Serializable {

    /**
     * all fields for the customer
     * array forced says, if field_identifier[i] has to be filled
     * out by the user or not
     */
    private int id;
    private String email, password, confirm_password;

    private String[] field_identifier = {"name", "forename", "address", "city",
					  "zip", "deliv_name", "deliv_forename",
					  "deliv_address", "deliv_city",
					  "deliv_zip", "phone", "fax"};
    private String[] field_content;

    private boolean[] forced = {true, true, true, true, true, false, false,
				false, false, false, false, false};

    private boolean isnew, has_changed;

    /**
     * creates a customer from a given ResultSet
     *
     * @param ResultSet a result of a select for the customer
     */
    public Customer (ResultSet rs) throws SQLException {

	this.id = rs.getInt ("id");
	this.email = noNull(rs.getString ("email"));
	this.password = noNull(rs.getString("password"));
	this.confirm_password = password;
	
	field_content = new String[field_identifier.length];

	for (int i=0; i<field_content.length; i++)
	    field_content[i] = noNull(rs.getString(field_identifier[i]));

	 isnew = false;
    }

    /**
     * creates a customer from the request which contains
     * all neccessary parameters
     *
     * @param Request the request which contains the parameters
     */
    public Customer (HttpServletRequest req) {
	field_content = new String[field_identifier.length];
	update(req);
	isnew = true;
    }


    /**
     * this method updates all fields of the customer
     */
    public void update (HttpServletRequest req) {

	this.email = noNull(req.getParameter("email"));

	this.password = noNull(req.getParameter("password"));
	this.confirm_password = noNull(req.getParameter("confirm_password"));

	for (int i=0; i< field_content.length; i++)
	    field_content[i] = noNull(req.getParameter(field_identifier[i]));

	has_changed = true;
    }

    public int getId() {
	return(id);
    }

    public boolean isNew() {
	return(isnew);
    }

    public boolean hasChanged() {
	return(has_changed);
    }

    public String getEmail() {
	return(email);
    }

    public String getPassword() {
	return(password);
    }

    public String getConfirmPassword() {
	return(confirm_password);
    }

    /**
     * writes or updates the customer to the database.
     * if the customer was new the unique customer id
     * is requested
     */
    public void toDatabase(Connection con) throws SQLException {
	
	Statement stmt = con.createStatement();

	if (isnew) {
	    String insert = "INSERT INTO customer (id, email, password, ";
	    
	    for (int i=0; i<field_content.length;i++) {
		insert += field_identifier[i];
		if (i!=field_content.length-1)
		    insert += ", ";
		else
		    insert += ")";
	    }

	    insert += "VALUES ('', '" +email +"', '" +password +"', ";

	    for (int i=0; i<field_content.length; i++) {
		insert += "'" +field_content[i];
		if (i!=field_content.length-1)
		    insert += "', ";
		else
		    insert += "')";
	    }
	    
	    stmt.executeUpdate(insert);

	    // Get the Id of the new Customer
	    Statement stmt1 = con.createStatement();

	    String select = "SELECT id FROM customer WHERE ";

	    for (int i=0; i<field_content.length; i++) {
		select += field_identifier[i] +"='" +field_content[i];
		if (i!=field_content.length-1)
		    select += "' AND ";
		else
		    select += "'";
	    }

	    ResultSet rs = stmt1.executeQuery(select);		    
		
	    if (rs.next())
		id = rs.getInt("id");

	    rs.close();
	    stmt1.close();
	    
	} else {

	    String update = "UPDATE customer set email ='" +email +"', password='" +password +"', ";;

	    for (int i=0; i<field_content.length; i++) {
		update += field_identifier[i] +"='" +field_content[i];
		if (i!=field_content.length-1)
		    update += "', ";
		else
		    update += "' WHERE id=" +id;
	    }
	    stmt.executeUpdate(update);
	    
	}

	stmt.close();

	isnew = false;
	has_changed = false;
    }
    /**
     * this method checks if all fields of the customer are
     * correct.<BR>
     * this is checked by the method isStringValid(String) and
     * isEmailValid().
     */
    public boolean isValid() {

	if ((isEmailValid())&&(comparePasswords())) {
	   	for (int i=0; i<field_content.length; i++) {
		    if ((!isStringValid(field_content[i]))&&(!(noNull(field_content[i]).equals("")))) {
			return(false);
		    } else if ((forced[i])&&((noNull(field_content[i]).equals("")))) {
			return(false);
		    }
		}
	} else
	    return(false);

	return(true);
    }

    /**
     * checks if a given string is valid.<BR>
     * valid means that it begins with some alphanumeric character
     * followed by minimum 2 alphanumeric character, a point, a double point,
     * a dash or a whitespace. The case of the characters doesn't matter.
     */
    public boolean isStringValid(String s) {
	RE r=null;
	try {
	    r = new RE("^[:alnum:]([:alnum:]|\\.|\\:|\\/|\\-|[:space:]){2,}$", RE.MATCH_CASEINDEPENDENT);
	} catch (RESyntaxException e) {}
	return(r.match(s));
    }

    /**
     * this method checks of the entered EMail adress of the
     * customer is valid.
     */
    public boolean isEmailValid() {
	RE r=null;
	try {
	    r = new RE("^[:alnum:]+((\\.|!|_|\\+|\\-)[:alnum:]+)*@([:alnum:]+(\\.|\\-))+[:alnum:]{2,}$",RE.MATCH_CASEINDEPENDENT);
	} catch (RESyntaxException e) {}
	return(r.match(this.email));
    }

    public boolean comparePasswords() {
	return((password.equals(confirm_password))&&(!password.equals(""))&&(!confirm_password.equals("")));
    }
    
    /**
     * returns a element representation of the customer.<BR>
     * all values are appended to a given element.
     * each child of this element has to children.
     * one child contains the value. The other child
     * is just appended if the value is missing or wrong.
     */
    public Element toElement(Element e) {
	
	Document doc = e.getOwnerDocument();

	Element id = doc.createElement("id");
	id.appendChild(doc.createTextNode(String.valueOf(this.id)));
	e.appendChild(id);

	Element email = doc.createElement("email");
	email.appendChild(doc.createTextNode(this.email));
	if (!isEmailValid()) {
	    Element error = doc.createElement("error");
	    email.appendChild(error);
	}
	e.appendChild(email);


	Element password = doc.createElement("password");
	password.appendChild(doc.createTextNode(this.password));	
	if (!comparePasswords()) {
	    Element error = doc.createElement("error");
	    password.appendChild(error);
	}
	e.appendChild(password);

	Element confirm_password = doc.createElement("confirm_password");
	confirm_password.appendChild(doc.createTextNode(this.confirm_password));
	e.appendChild(confirm_password);

	for (int i=0; i<field_content.length; i++) {
	    Element element  = doc.createElement(field_identifier[i]);
	    element.appendChild(doc.createTextNode(field_content[i]));

	    if ((!isStringValid(field_content[i]))&&(!(noNull(field_content[i]).equals("")))) {
		// if a forced value has not been entered
		Element error = doc.createElement("error");
		element.appendChild(error);
	    } else if ((forced[i])&&((noNull(field_content[i]).equals("")))) {
		Element error = doc.createElement("error");
		element.appendChild(error);
	    }	    
	    e.appendChild(element);
	}
	return(e);
    }

    /**
     * This Method returns the trimmed String
     * entered as Parameter or "" if this String was null
     *
     * @param String the String
     * @returns String the trimmed String or "" if this String was null
     */
    private String noNull(String s) {
	if (s==null)
	    return("");
	else
	    return(s.trim());
    }
}
