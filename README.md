# Auto-Publish-to-Tableau-Server
This is a Java code written to help construct an automated publishing system for Tableau workbooks from the local machine to a certain tableau server. 

****Tested for Tableau 8.2****

****Needs tabcmd installed on your PC****

To run this code, first you need to create a subfolder structure for your workbooks as shown below:

--Main Folder
----App1
------twb file 1
------twb file 2
------csv file containing username/password for all twb files in App1
----App2
------twb file 3
------twb file 4
------csv file containing username/password for all twb files in App2

Based on this structure twb file 1 and 2 will be published in App1 folder on tableau server and twb file 3 and 4 will be published in App2 folder on tableau server.

****In the main function please edit two filepaths, 
one is the variable 'pub' which uses tabcmd to establish a connection to the server. The tableau server username and password needs to be edited here.
second is the 'out' variable which stores the filepath for the output of this script which can be used for verification and debugging statuses****

****You need to download and import tabcmd.jar in your java project for this file to run****

I acknowledge there may be issues with this code, since this was based on the structure i wanted to establish on my local machine and tableau server and my user permissions. Please feel free to make changes to this code and report!
Tableau Automation scripts barely exist for free online even though it's possible. Hopefully this script can help you.
