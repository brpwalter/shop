/**
 * Program name: EXT-SHOP
 * This program is a servlet-based webshop
 * Classname: Shop (the main class for the shop)
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
 */
package de.extshop;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.sql.Timestamp;
import java.security.*;
import javax.servlet.*;
import javax.servlet.http.*;

//import Connection Broker
import com.javaexchange.dbConnectionBroker.*;

import org.w3c.dom.*; 
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 * this is the main class of the Webshop<BR>
 */
public class Shop extends HttpServlet {


    /**
     * This Method is called once from the Servlet-Container to instantiate    
     * the servlet.<BR>
     * The Database Connection-Pool ist created here.
     *
     * @throws UnavailableException if any configuration error occures
     */
    public void init() throws ServletException, UnavailableException {

	/**
	 * create the Connection-Pool an put it in the Servlet-Context.
	 */
	createPool();

    } // end init()


    /**
     * This method is called by doGet when a request for article search
     * is sent to the servlet.
     * Either a detailed description of one article is shown or
     * a special selection is shown in a article list.<BR>
     * The stylesheet for all this is named articlelist.xsl and is
     * in the /styles directory of this weballication.
     */
    private void doSearch (HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
	
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession(true);

	Connection con = getConnection ();

	/**
	 * get parameters for article_group and
	 * article id from the request
	 */
	String group = decode(req.getParameter("group"));
	String id = noNull(req.getParameter("id"));
	
       	Document doc = createDocument (); // the root of the sourcetree
	Element shop = doc.createElement("shop"); // first element named shop

	if (id.equals("")) {
	    /** 
	     * display articles by group or all
	     * articles if group=% or %25 (encoded)
	     */
	    try {
		
		// empty articlelist
		ArticleList list = new ArticleList(); 
		
		// sql select to get all articles or all of one group
		String sql = "SELECT * FROM article WHERE article_group LIKE '" +group +"' ORDER BY article";

 		Statement stmt = con.createStatement();
	 	ResultSet rs = stmt.executeQuery(sql);
 
	 	while (rs.next ()) {
		    /**
		     * as long es there are more
		     * articles in the result set
		     * fill the articlelist with them
		     */
		    list.addArticle (new Article(rs));
		}

		// close the result set and statement
 		rs.close();
	 	stmt.close();
		
		/**
		 * append an element representation of the
		 * articlelist to the first element shop
		 */
		shop.appendChild(list.toElement(doc.createElement("articlelist")));
		
	    } catch( SQLException e ) {log(e.getMessage());} 

	} else {
	    /**
	     * display a detailed description
	     * of an article searched by id
	     */
	    try {

		/**
		 * select the searched article
		 */
 		String sql = "SELECT * FROM article WHERE id =" +id +"";
		
	 	Statement stmt = con.createStatement();
 		ResultSet rs = stmt.executeQuery(sql);
		
 		if (rs.next()) {
		    // article found
 		 
		    Article article = new Article(rs);
		    rs.close();
		    stmt.close();

		    /**
		    * append an element representation of the article
		    * to the first element
		    */
		    shop.appendChild(article.toElement(doc));
		}
		
	    } catch( SQLException e ) {log(e.getMessage());}
	}

	/**
	 * append the article_groups and the customer
	 * to the first element
	 */
	shop.appendChild(getGroups(doc, con));
	Customer customer = (Customer) session.getAttribute("customer");
	if (customer!=null)
	    shop.appendChild(customer.toElement(doc.createElement("customer")));

	// put Connection back into the pool
	freeConnection (con);

	/**
	 * append the first element to the document
	 * and transform it with the stylesheet
	 */
	doc.appendChild(shop);
	transform(doc, out, "articlelist.xsl");

    } // end doSearch(HttpServletRequest req, HttpServletResponse res) 


    /**
     * This Method is called by doGet and adds, changes or deletes
     * a chosen article to or in the shopping cart. At the end
     * the shopping cart is appended to a element named shop
     * and transformed with cart.xsl in the /styles directory of
     * this webapplication.
     */
    private void doCart (HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {

	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession(true);

	// get the control parameter from the request
	String action = noNull(req.getParameter("action"));
	
	// get the shopping cart from the session
	ArticleList cart = (ArticleList) session.getAttribute("cart");

	// create the document and a first element named shop
	Document doc = createDocument();
	Element shop = doc.createElement("shop");

	// getDatabaseConnection from Pool
	Connection con = getConnection();

	boolean error = false; // shows if an error occures

	if (cart==null) 
	    // no shopping cart in session => createone
	    cart = new ArticleList();


	if (action.equalsIgnoreCase("add")) {
	    // add something to card
	    
	    // get chosen article_id from request
	    String article_id = noNull(req.getParameter("id"));

	    // check if article already in cart
	    Article articleInCart =  cart.getArticle(parseInt(article_id));

	    if (articleInCart == null) {
		// article not in card => put it in
	    
		try {
		    String sql = "SELECT * FROM  article WHERE id='" +article_id +"'";
		    
		    Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    if (rs.next ()) {
			
			// create Article
			Article article = new Article(rs);

			// put it to cart
			cart.addArticle (article);
		    }
		    
		} catch (SQLException e) {log(e.getMessage());}
		
	    } else {
		// article already in cart => increment quantity
		articleInCart.setQuantity(articleInCart.getQuantity() +1);
	    }
	    
	} else if (action.equalsIgnoreCase("change")) {
	    // change the Quantity of one or more Articles

	    Article article;

	    for (int i=0; i<cart.getSize(); i++) {
		// do for all articles in shopping cart
		
		// get the next article
		article = cart.getArticleAt(i);
		
		int newQuantity = 0;
		
		try {
		    // get the entered quantity by Parametername "quantity_id"
		    newQuantity = Integer.parseInt ((req.getParameter ("quantity_" +article.getId())).trim());


		    if (newQuantity == 0) {
			// if quantity = 0, delete article
			cart.deleteArticle(article);
		    } else {
			if (newQuantity > 0)
			    // change quantity if not < 0
			    article.setQuantity(newQuantity);
		    }
		} catch (NumberFormatException e) {}
	    		
	
	    }
	} else if (action.equalsIgnoreCase("delete")) {
	    // delete article from cart
	    
	    // get article_id to delete
	    String article_id = req.getParameter("id");

	    // delete article
	    cart.deleteArticle(Integer.parseInt(article_id));
	    
	} // ende action=delete

	// set the new article in the session
	session.setAttribute("cart", cart);

	// append the article groups,the shopping cart and the customer
	shop.appendChild(getGroups(doc, con));
	shop.appendChild(cart.toElement(doc.createElement("cart")));
	Customer customer = (Customer) session.getAttribute("customer");
	if (customer!=null)
	    shop.appendChild(customer.toElement(doc.createElement("customer")));

	doc.appendChild(shop);

	// put the Connection back in the pool
	freeConnection(con);

	if (error)
	    transform(doc, out, "error.xsl");
	else
	    // transform the document with stylesheet cart.xsl
	    transform(doc, out, "cart.xsl");

    } // end doCart (HttpServletRequest req, HttpServletResponse res) 
    

    /**
     * This method is called from doGet, when either the user requested
     * a change of the customer or the doOrder Method redirected the
     * request, because the customer is not or not correct. At the
     * end of this method the template customer.xsl from the /styles
     * directory of this webapplication is transformed.
     */
    private void doCustomer (HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
	
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession(true);

	// get Customer from Session
	Customer customer = (Customer) session.getAttribute("customer");

	// get action Parameter
	String action = noNull(req.getParameter("action"));

	Document doc = createDocument();
	Element shop = doc.createElement("shop");

	if (customer==null) {
	    // if the customer does not exists yet => create it from the request
	    customer = new Customer(req);
	} else if (action.equalsIgnoreCase("change")) {
	    // change the customer
	    customer.update(req); // get the new values from the request
	}

	// put the customer into the session
	session.setAttribute("customer", customer);

	Connection con = getConnection();

	if (((customer.isNew())||(customer.hasChanged()))&&(customer.isValid())) {
	    // the customer is new or has changed his values and is valid
	    
	    // write the customer to the database
	    try {
		customer.toDatabase(con);
	    } catch (SQLException e) {log(e.getMessage());}
	}
	
	// append the article_groups and the customer to the shop element

	shop.appendChild(getGroups(doc, con));
	if (customer!=null)
	    shop.appendChild(customer.toElement(doc.createElement("customer")));
	freeConnection(con);
    
	doc.appendChild(shop);

	// transform the document with the stylesheet customer.xsl
	transform(doc, out, "customer.xsl");

    } // end doCustomer(HttpServletRequest req, HttpServletResponse res)
    

    /**
     * this method is called from doGet, when a request with
     * pathinfo /order is sent to this servlet.<BR>
     * The customer can view his full order with all information.<BR>
     * He can confirm something and definetly confirm the order.
     * then the order is written to the database
     */
    private void doOrder (HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
	
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession();

	// get the customer and shopping cart from the useres session
	Customer customer = (Customer) session.getAttribute("customer");
	ArticleList cart = (ArticleList) session.getAttribute("cart");

	Document doc = createDocument();
	Element shop = doc.createElement("shop");

	// get the control parameter from the request
	String action = noNull(req.getParameter("action"));

	Connection con= getConnection();

	if ((customer!=null)&&(customer.isValid())) {
	    // all fields for existing customer are correct
	    
	    if ((cart!=null)&&(cart.getSize()!=0)) {
		// shopping cart contains some articles and exists

		if (action.equalsIgnoreCase("view")) {
		    // just view the complete order to confirm it

		    // append the groups, customer and cart to element shop
		    shop.appendChild(getGroups(doc, con));
		    shop.appendChild(customer.toElement(doc.createElement("customer")));
		    shop.appendChild(cart.toElement(doc.createElement("cart")));
		    doc.appendChild(shop);
		    
		    transform(doc, out, "order.xsl");
		    
		} else if (action.equalsIgnoreCase("order")) {
		    // finish the confrimed order

		    // get some parameters from the request
		    String tac = noNull(req.getParameter("tac"));
		    
		    if (!tac.equals("")) {
			// all neccessary fields are correct

			// create a timestamp for the insert of the order_data
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			// create a order id
			String order_id = "OR";
			order_id += System.currentTimeMillis();

			// insert for the order data
			String order = "INSERT INTO order_data VALUES ('" +order_id +"', '" +customer.getId() +"', '" +timestamp +"')";

			// prepare a Statement for the articles
			String article = "INSERT INTO orders VALUES(?, ?, ?)";

			try {
			    
			    // make sure that AutoCommit is off
			    con.setAutoCommit(false);
			    /**
			     * commit just to make sure because of the
			     * connection pool
			     */
			    con.commit(); 

			    // insert the order data
			    Statement stmt = con.createStatement();
			    stmt.execute(order);
			    stmt.close();
			    
			    // prepare a statement to insert the articles
			    PreparedStatement pstmt = con.prepareStatement(article);
			    
			    for (int i=0; i< cart.getSize(); i++) {
				// insert all articles with the prepared statement
				pstmt.setString(1, order_id);
				pstmt.setInt(2, (cart.getArticleAt(i)).getId());
				pstmt.setInt(3, (cart.getArticleAt(i)).getQuantity());
				pstmt.execute();
			    }

			    // commit the insert if no errors occured
			    con.commit();
			    con.setAutoCommit(true); // set autocommit

			} catch (SQLException e) {
			    // if an error occures, reject the hole order
			    try {
				con.rollback();
			    } catch (SQLException sqle) {}
			    log(e.getMessage());
			}
			
			// destroy the session
			session.invalidate();
			shop.appendChild(getGroups(doc, con));
			doc.appendChild(shop);
			transform(doc, out, "confirmation.xsl");
		    } else {
			/**
			 * not all parameter in the order id have been set
			 * correct => view the order again
			 */
			res.sendRedirect("../order/?action=view"); 
		    }
		}
	    } else {
		// cart is empty or something is wrong => show the cart
		res.sendRedirect("../cart/");
	    }
	} else {
	    // customer has blank or wrong fields
	    res.sendRedirect("../customer/");
	}
	freeConnection(con); // put the connection back into pool
	
    } //end doOrder(HttpServletRequest req, HttpServletResponse res)

    
    /**
     * transforms a stylesheet with a node and writes it to
     * the printwriter or the error message if one occures.
     *
     * @param node the node to transform the stylesheet with
     * @param out the PrintWriter to write the transformed document to
     * @param stylesheet the Stylesheet to transform
     */
    private void transform (Node node, PrintWriter out, String stylesheet) {
	try {
	    TransformerFactory tFactory = 
		TransformerFactory.newInstance();
	    
	    Transformer transformer = tFactory.newTransformer
		(new StreamSource(getServletContext().getRealPath("/styles/" +stylesheet)));
	    
	    transformer.transform (new DOMSource (node), new StreamResult(out));
	} catch (TransformerConfigurationException e) {
	    out.println ("Error in stylesheet<BR>");
	    out.println (e.getMessage ());
	} catch (Exception e) {
	    out.println ("Error while processing<BR>");
	    out.println (e.getMessage ());
	}
	
    } // end transform (Node node, PrintWriter out, String stylesheet)


    /**
     * this method is for debugging purpose only.
     * A Node or Document passed as parameter will be
     * serialized an written on the PrintWriter to
     * view a generated Domtree.
     *
     * @param node the Node or Document to serialize
     * @param out the PrintWriter to write the output
     */
    private void serialize (Node node, PrintWriter out) {
	try {
	    TransformerFactory tFactory = 
		TransformerFactory.newInstance();
	    
	    Transformer transformer = tFactory.newTransformer();
	    
	    transformer.transform (new DOMSource (node),new StreamResult(out));
	    
	} catch (TransformerConfigurationException e) {
	    out.println ("Error in stylesheet<BR>");
	    out.println (e.getMessage ());
	} catch (Exception e) {
	    out.println ("Error while processing<BR>");
	    out.println (e.getMessage ());
	}
    } // end serialize (Node node, PrintWriter out)

    /**
     * this method is either called from the servlet-container, if a
     * http get request is sent to this servlet, or called from the
     * doPost Method if a http post request is sent.
     * 
     * @param HttpServletRequest the request passed from the servlet-container
     * @param HttpServletResponse the response to send to the client
     *
     * @returns ServletException
     * @returns IOException
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {

	// set the ContentType for all following outputs to text/html
	res.setContentType("text/html");

	/**
	 * Look for customer Login Data in Request
	 * create the Customer from EMail and Password
	 * and put him into the session
	 */
	String entered_email = noNull(req.getParameter("quickemail"));
	String entered_password = noNull(req.getParameter("quickpasswd"));

	if ((!entered_email.equals(""))&&(!entered_password.equals(""))) {
	    try {
		Connection con = getConnection();
		String customer_select = "SELECT * FROM customer WHERE email='"+entered_email +"'";
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(customer_select);
		
		
		if (rs.next()) {
		    // found a customer, now check password
		    String password = noNull(rs.getString("password"));

		    if (password.equals(entered_password)) {
			Customer customer = new Customer(rs);
			req.getSession().setAttribute("customer", customer);
		    } else {
			// password is wrong => do nothing
		    }
		} else {
		    // Customer not in Database => do nothing
		}
		freeConnection(con);
	    } catch (SQLException sqle) {log(sqle.getMessage());}
	}

	String path = noNull(req.getPathInfo ()).toLowerCase ();
	
	/** get command from the calling URI
	 * and dispatch to the corresponding
	 * method
	 */
	if ((path.startsWith ("/search"))||(path.equals(""))) {
	    doSearch (req, res); // start article search
	} else if (path.startsWith ("/cart")) {
	    doCart (req, res); // change the shopping cart or/and view it
	} else if (path.startsWith ("/customer")) {
	    doCustomer (req, res); // do something with the customer
	} else if (path.startsWith ("/order")) {
	    doOrder (req, res); // view or confirm the order
	} else {
	    // print an error page
	    Connection con = getConnection();
	    Document doc = createDocument();
	    Element shop = doc.createElement("shop");
	    shop.appendChild(getGroups(doc, con));
	    freeConnection(con);
	    doc.appendChild(shop);
	    transform(doc, res.getWriter(), "error.xsl");
	}
    }

    /**
     * encodes a given string
     *
     * @return string encoded
     */
    private String encode(String s) {
	if (s!=null)
	    return(java.net.URLEncoder.encode(s.trim()));
	else
	    return("");
    }

    /**
     * decodeds a given string
     *
     * @return string decoded
     */
    private String decode(String s) {
	if (s!=null)
	    return(java.net.URLDecoder.decode(s.trim()));
	else
	    return("");
    }
    
    /**
     * this method is called, if a http post request is sent to this
     * servlet. In this case, the doGet Method is called, because it
     * doesn't matter, what kind of request it is.
     * 
     * @param HttpServletRequest the request passed from the servlet-container
     * @param HttpServletResponse the response to send to the client
     *
     * @returns ServletException
     * @returns IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	doGet(req,res);
    }

    /**
     * this method is called, when the servlet-container shuts down this
     * servlet. The ConnectionPool will be destroyed in this method.
     */
    public void destroy() {

	ServletContext context = getServletContext();
	DbConnectionBroker pool = (DbConnectionBroker) context.getAttribute("pool");
	pool.destroy();
	context.removeAttribute("pool");
    }


    /**
     * Create a DatabaseConnectionPool and put it into the ServletContext
     * labeled by "pool". This Method is called from init().
     * <P>The Pool needs the following Configuration Parameters in
     * the Deployment Descriptor<BR>
     * <ul>
     * <li> DatabaseDriver         JDBC driver. e.g. 'org.gjt.mm.mysql.Driver'
     * <li> DatabaseURL:           JDBC connect string. e.g. 'jdbc:mysql://localhost:3306/shop'
     * <li> DatabaseUser:          Database login name.
     * <li> DatabasePassword:      Database password.
     * <li> MinimumConnections:    Minimum number of connections to start with - default is 3.
     * <li> MaximumConnections:    Maximum number of connections in dynamic pool - default is 15.
     * <li> DatabaseLogFile:       Absolute path name for log file. e.g. '/opt/jakarta-tomcat/logs'
     * <li> MaximumConnectionTime: Time in days between connection reset - default is 1/2 day
     * </ul>
     *
     * @throws UnavailableException if any Parameter is not or wrong specified
     */
    private void createPool() throws UnavailableException {

	// get ServletContext
	ServletContext context = getServletContext();

	// get ConnectionPool from Context
	DbConnectionBroker pool = (DbConnectionBroker) context.getAttribute("pool");

	if (pool==null) {
	// make sure, that the pool is only created once

	    /**
	    * get preferences for the ConnectionPool
	    * throw Unavailable Exceptions or use default values
	    * if an error occures.
	    */
	    String databaseDriver = getInitParameter("DatabaseDriver");
	    if (databaseDriver==null)
		throw new UnavailableException("No DatabaseDriver specified");

	    String databaseURL = getInitParameter("DatabaseURL");
	    if (databaseURL==null)
		throw new UnavailableException("No DatabaseURL specified");

	    String databaseUser = getInitParameter("DatabaseUser");
	    if (databaseUser==null)
		throw new UnavailableException("No DatabaseUser specified");

	    String databasePassword = getInitParameter("DatabasePassword");
	    if (databasePassword==null)
		throw new UnavailableException("No DatabasePassword specified");

	    String logFileString = getInitParameter("DatabaseLogFile");
	    if (logFileString==null)
		throw new UnavailableException("No DatabaseLogFile specified");

	    int minConns, maxConns; 
	    double maxConnectionTime;

	    try {
		minConns = Integer.parseInt(getInitParameter("MinimumConnections"));
	    } catch (NumberFormatException nfe) {
		log("The Number of Minimum DatabaseConnections is either not specified or not a valid number, using default");
		minConns=3;
	    }

	    try {
		maxConns = Integer.parseInt(getInitParameter("MaximumConnections"));
	    } catch (NumberFormatException nfe) {
		log("The Number of Maximum DatabaseConnections is either not specified or not a valid number, using default");
		maxConns=15;
	    }
	    
	    try {
		maxConnectionTime = Double.parseDouble(getInitParameter("MaximumConnectionTime"));
	    } catch (NumberFormatException nfe) {
		log("The MaxiumumConnectionTime is either not specified or not a valid number, using default");
		maxConnectionTime=0.5;
	    }
	   
	    /**
	     * create ConnectionPool
	     */
	    try {

		// create ConnectionPool
		pool = new DbConnectionBroker(databaseDriver, databaseURL, databaseUser, databasePassword, minConns, maxConns, logFileString, maxConnectionTime);
	    } catch(IOException ioe) {
		throw new UnavailableException("DatabaseConnectionPool couldn't be created. Please check the ConnectionPool LogFile (" +logFileString +") for details");
	    }

	    // put the Pool into the Context
	    context.setAttribute("pool", pool);

	} // end if(pool!=null)
    } // end createPool()

    /**
     * this method fetchs a database connection from the
     * connection-pool.
     *
     * @returns a database connection from the pool
     */
    private Connection getConnection () {
	return ((DbConnectionBroker) getServletContext().getAttribute("pool")).getConnection();
    }

    /**
     * this method puts a connection passed as parameter back into
     * the connection-pool.
     *
     * @parameter connection a database connection from the pool
     */
    private void freeConnection (Connection con) {
	((DbConnectionBroker) getServletContext().getAttribute("pool")).freeConnection (con);
    }


    /**
     * creates an empty DOM Document
     *
     * @returns Document the new empty DOM Document or null if an error occures
     */     
    private Document createDocument () {
	try {
	    return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	} catch (javax.xml.parsers.ParserConfigurationException e) {
	    return null;
	}
    }
    
    /**
     * parses a string to return an int 
     *
     * @returns integer an integer with the integer value of the string 
     * or numeric 0 if an error occured or the string was null
     */
    private int parseInt (String s) {
	if (s == null)
	    return 0;
	try {
	    return Integer.parseInt (s);
        } catch (NumberFormatException e) {
	    return 0;
	}
    }

    /**
     * this method returns a trimmed String passed as Parameter or 
     * an empty string if the string was null
     *
     * @param string the string to check
     *
     * @returns string the trimmed string or an empty string if the string was null
     */
     private String noNull(String s) {
	if (s==null)
	    return("");
	else
	    return(s.trim());
     }

     /**
     * this Method select all article_group distinct from database
     * and returns an element representation with parent element
     * named grouplist. The children of grouplist are called group.
     * Each group element has got to children. One with encoded name
     * of the group, one with the decoded name. The encoded name
     * is for use in links as parameters. The decoded name is
     * to display it.
     * @param Document the Document to create the elements
     * @param Connection the databaseconnection to select the groups
     * 
     * @return Element an element representation of all groups
     */
     private Element getGroups(Document doc, Connection con) {

	 Element grouplist=null;
	 try {
	     // create parent element for the list
	     grouplist = doc.createElement("grouplist");
	    
	     // select distinct all groupnames
	     String sql = "SELECT DISTINCT(article_group) FROM article ORDER BY article_group";
	    
	     Statement stmt = con.createStatement();
	     ResultSet rs = stmt.executeQuery(sql);
	    
	     String group;

	     while (rs.next ()) {
		 // as long as there are more groups in the result

		 // get the groupname
		 group = rs.getString("article_group");

		 // create an element 
		 Element group_element = doc.createElement("group");

		 /**
		  * append the encoded groupname as node called encoded
		  * to the element called group
		  */
		 Element group_encoded = doc.createElement ("encoded");
		 group_encoded.appendChild (doc.createTextNode (encode(group)));
		 group_element.appendChild (group_encoded);

		 /**
		  * append the decoded groupname as node called decoded
		  * to the element called group
		  */
		 Element group_decoded = doc.createElement ("decoded");
		 group_decoded.appendChild (doc.createTextNode (decode(group)));
		 group_element.appendChild (group_decoded);
		 
		 // append the group_element to the grouplist
		 grouplist.appendChild(group_element);
	     }
	     rs.close();
	     stmt.close();
	 } catch (SQLException sqle) {log(sqle.getMessage());}
	 
	 return(grouplist);
     } // end getGroups(Document doc, Connection con)
     
} // end class shop
