## ehealth-app-final-prototype

![alt tag](https://github.com/modaclouds-atos/ehealth-app-final-prototype/blob/master/readme/modaclouds.png?raw=true)

| ehealth components  | description  |
| :------------ |:---------------|
| *ehealth-storage-component*      | Responsible of the transactions with database |
| *ehealth-webservices*      | Responsible of the data processing; it uses 'ehealth-storage-component'     |
| *ehealth-webgui* | web application for administrators and clinicians  |
| *ehealth-desktop-app* | desktop client application used by the patients and their caregivers |

#### How to install the ehealth components / applications?
##### database
1. Database scripts can be found in folder 'ehealth-webservices/database'
2. Use MySQL 5.6 and import the file CFv2_encrypted.sql to create the ehealth application database 

##### ehealth-storage-component 
1. Download content of 'ehealth-storage-component' folder
2. Build with maven
3. Use the .jar file in the 'ehealth-webservices' project (ehealth-webservices already comes with the jar)

##### ehealth-webservices
1. Download content of 'ehealth-webservices' folder
2. Configure values in file '/ehealth-webservices/ehealth-modaclouds-ws/src/main/resources/ws.properties'
3. Build with maven
4. Deploy .war file in a Tomcat like server or in a PaaS

##### ehealth-webgui
1. Download content of 'ehealth-webgui' folder
2. Configure values in file '/ehealth-webgui/ehealth-modaclouds-gui/src/main/resources/webgui.properties'
3. Build with maven
4. Deploy .war file in a Tomcat like server or in a PaaS

##### ehealth-desktop-app
1. Download and open with Visual Studio 2012
2. Main application is 'Softcare.ClientApplication'
3. Generate installer application or deploy directly from Visual Studio
