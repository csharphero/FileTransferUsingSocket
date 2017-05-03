# FileTransferUsingSocket
This program allows you to tranfer files using a low level RMI and socket components using JAVA. Of course, there are libraries that are in buit to help you do it, but I wanted to go deep down into the low level details of how to create one from scrach.



****SERVER SIDE **********
START THE SERVER IN A COMMAND WINDOW

java -cp pa2.jar Server start 5555
Server is ready. Please open a client and run the commands


*********CLIENT SIDE*******

SET THE PA2_SERVER VARIABLE

SET PA2_SERVER=localhost:5555

java -cp pa2.jar Client mkdir /newDirectory
DIRECTORY:- newDirectory IS CREATED

java -cp pa2.jar Client rmdir /newDirectory
DIRECTORY: newDirectory IS REMOVED

java -cp pa2.jar Client dir /
PARENT FOLDER: FSS
Client
pa2.jar
server


java -cp pa2.jar Client rm /Client/client.txt
FILE-client.txt IS REMOVED

java -cp pa2.jar Client upload /Client/new.txt /
File uploaded.

java -cp pa2.jar Client upload /Client/new.txt /newserer.txt
File uploaded.

java -cp pa2.jar Client download /newserer.txt /Client/newClient.txt
File Downloaded.

