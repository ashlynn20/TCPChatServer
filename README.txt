The Server program takes in three command line arguments, setting the first as the port number, the second as the seed, and the third
as the number of messages. The seed is used to create a Random object. The port is used to create a serversocket, if the port is not 
greater than 1024 or less than/equal to 65535 an error message is printed. The program then, if the port was valid, creates a 
client and a connection with that client (does this twice). The program then sends the client two numbers, a random number generated 
from the random object and the number of messages. After that the clients are closed and the program finishes. 

The Client program takes in two command line arguments, setting the first as the server host and the second as the port number. These make sure the client is 
connected to the right server. Then the client reads in two values from the server. The first is the number of messages the client will send to the server 
and the second is a seed. The seed is used to seed a Random object. Then for the number of messages times, a random number generated from the random object 
is added to a sum. A counter keeping track of the number of messages sent on the client side is also iterated. Then the random number is sent to the server 
during each iteration.