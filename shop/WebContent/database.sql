USE mysql;
INSERT INTO user (Host, User, Password) VALUES ("localhost","servlets", password("geheim"));
INSERT INTO db (Host, Db, User, Select_priv, Insert_priv, Update_priv, Delete_priv) VALUES ("%", "shop", "servlets", 'Y', 'Y', 'Y', 'Y');

drop database shop;
create database shop;
use shop;

CREATE TABLE article (
	id 		int primary key NOT NULL,
	article 	varchar(50) NOT NULL,
	description	varchar(255),   
	price 	float(10,2) NOT NULL,
	article_group varchar(30) NOT NULL
);

create table customer (
	id		int primary key auto_increment,
	name		varchar(30),
	forename	varchar(30),
	address		varchar(50),
	city		varchar(40),
	zip		varchar(10),
	phone		varchar(30),
	fax		varchar(30),
	deliv_name	varchar(30),
	deliv_forename	varchar(30),
	deliv_address	varchar(50),
	deliv_city	varchar(40),
	deliv_zip	varchar(10),
	email		varchar(40),
	password	varchar(30)
);

create table order_data (
	id		varchar(20),
	customer_id	int,
	order_date	datetime
);	

create table orders (
	id		varchar(20) NOT NULL,
	article_id	int NOT NULL,
	quantity	int NOT NULL
);


#
# Testdatensaetze
#
INSERT INTO article VALUES( '6264', 'Homepage Tuning Java', 'Ohne JAVA läuft im Internet kaum noch etwas. Frischen Schwung in Ihre Homepage bringen die fix und fertigen JAVA-Applets dieses Tuning-Pakets. Oder wollen Sie Programmieren lernen?', '15.31', 'Java');

INSERT INTO article VALUES( '2093', 'Internet Wörterbuch A-Z', 'Perfektionieren Sie Ihren Auftritt im Web und verblüffen Sie andere mit der richtigen Netiquette.', '10.20', 'Internet');

INSERT INTO article VALUES( '2102', 'Cascading Style Sheets', 'Möchten Sie einheitliche Websites erstellen oder HTML-Seiten zeitsparend attraktiver gestalten? Das Buch macht Sie mit dem Profi-Tool CSS anhand konkreter Projekte vertraut.', '35.77', 'Internet Intern');

INSERT INTO article VALUES( '2099', 'Internet intern', 'Jetzt geht\'s ans Eingemachte: Praxisnah, kompetent und am Puls der Zeit präsentieren unsere Top-Autoren alles, was Profis zum Thema Internet wissen sollten.', '45.99', 'Internet Intern');

INSERT INTO article VALUES( '2092', 'Perl & CGI', 'Zwei, die ein starkes Team bilden: Perl und CGI für die Erstellung dynamischer Web-Sites! In diesem Buch finden erfahrene Entwickler viele praktische Tools und Applikationen zum Direkteinsatz.', '35.77', 'Internet Intern');

INSERT INTO article VALUES( '2086',  'Internet-Programmierung mit Java', 'Wer in der Websitegestaltung ganz vorne mit dabei sein will, kommt an Java nicht vorbei! Hier ist der Einsteig in die Profi-Programmierung.', '35.77', 'Internet Intern');

INSERT INTO article VALUES( '2043', 'PHP 4 + MySQL', 'Endlich wieder lieferbar ... mit komplett überarbeiteter und aktualisierter CD-ROM! Umfassendes Know-how zur Entwicklung professioneller Datenbanken und datenbankgestützter Websites.', '35.77', 'Internet Intern');

INSERT INTO article VALUES( '2057', 'Mit Feuer gegen Angreifer', 'Firewall - In diesem Buch erfahren Poweruser, Netzwerkverantwortliche u.a., wie sie sichere Firewalls errichten, Angriffe aus dem WWW frühzeitig erkennen und das Risiko einer gefährlichen "Virus-Infektion" minimieren.', '40.88', 'Internet');

INSERT INTO article VALUES( '2106', 'TCP/IP: Networking für Profis', 'In dieser Referenz finden angehende Netzwerkprofis alltagstaugliches, anhand realer Netze veranschaulichtes Know-how zu optimaler Netzwerkadministration und -pflege.', '40.88', 'Internet');
