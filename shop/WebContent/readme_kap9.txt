****************************************
****************************************
*Installation der Beispiele - Kapitel 9*
****************************************
*Voraussetzung ist die korrekte        *
*Installation von Tomcat, MySQL und Ant*
****************************************
*Beachten Sie bitte hierzu             *
*die Hinweise in Kapitel 3!            *
****************************************

1) Kopieren Sie das Verzeichnis kapitel9 auf die Festplatte (z.B. 
als C:\servlets\kapitel9 oder /home/user/servlets/kapitel9). 

2) Entfernen Sie den Schreibschutz von den Dateien. 
(Windows: attrib -R /s c:\kapitel9; Linux: chmod -R 777 
/home/user/servlets/kapitel9) 

3) Starten Sie die Datenbank MySQL (mehr dazu steht in Kapitel 3)

4) Verbinden Sie sich mit der Datenbank MySQL nach Anleitung 
aus Kapitel 3. Führen Sie folgende Befehle zum einrichten der 
Datenbank shop und allen dazugehörigen Tabellen aus. 
ACHTUNG: falls der User servlets aus anderen Beispielen schon 
angelegt ist, kommentieren Sie die zweite Zeile in der Datei 
database.sql mit einem # aus. 
Unter Windows: 
mysql -u -p source C:\eclipse-oxygon-workspace\Shop\WebContent\database.sql; exit; 

mysql -u -p source C:\eclipse-oxygon-workspace\shop\WebContent\database.sql; exit; 
Unter Linux: 
mysql -u -p < /home/user/servlets/kapitel9/database.sql 

5) Passen Sie den Inhalt der Datei build.xml auf Ihre Pfade an. 
Achten Sie dabei darauf, unter Windows die Laufwerksangabe 
zu ergänzen (z.B. c:). Wichtig ist, dass Sie alle Pfade zu Tomcat, 
zur Servlet API ändern. 

6) Kompilieren Sie die Anwendung mit Ant durch Eingabe von ant. 
Zur Installation und Konfiguration siehe Kapitel 3. 

7) Starten Sie Tomcat wie in Kapitel 3 beschrieben. 

8) Sie können nun im Shop einkaufen, wenn Sie folgende URL 
in ihrem Browser eingeben: 

http://localhost:8080/shop/shop/search/

oder das XSLTDemo Beispiel unter folgender URL: 
http://localhost:8080/shop/XSLTDemo 


*********************************************
*********************************************
**     Viel Spaß beim Einkaufen im Shop    **
*********************************************
*********************************************
