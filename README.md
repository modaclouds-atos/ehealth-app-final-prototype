## ehealth-app-final-prototype

![alt tag](https://github.com/modaclouds-atos/ehealth-app-final-prototype/blob/master/readme/modaclouds.png?raw=true)

| ehealth components  | description  |
| :------------ |:---------------|
| *ehealth-storage-component*      | Responsible of the transactions with database |
| *ehealth-webservices*      | Responsible of the data processing; it uses 'ehealth-storage-component'     |
| *ehealth-webgui* | web application for administrators and clinicians  |
| *ehealth-desktop-app* | desktop client application used by the patients and their caregivers |

#### How to install the ehealth components / applications?
##### ehealth-storage-component 
1. Download content of 'ehealth-storage-component' folder
2. Build with maven
3. Use the .jar file in the 'ehealth-webservices' project

##### ehealth-webservices
1. Download content of 'ehealth-webservices' folder
2. Build with maven
3. Deploy .war file in a Tomcat like server or in a PaaS

##### ehealth-webgui
1. Download content of 'ehealth-webgui' folder
2. Build with maven
3. Deploy .war file in a Tomcat like server or in a PaaS

##### ehealth-desktop-app
.NET project
Download and open with Visual Studio 2012
