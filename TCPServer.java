package cs280a1.hw3;

import java.io.*;    
import java.net.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TCPServer{
        static DataInputStream[] din = new DataInputStream[2]; //make an array DataIn[] din = new DataIn
        static DataOutputStream[] dout = new DataOutputStream[2]; //make an array DataOut[] dout = new DataIn
        static Scanner userInput = new Scanner(System.in);
        static Socket[] clientSocket = new Socket[2]; //make an array Socket[] clientSocket = new Socket[some num]
        static ServerSocket serverSocket;
        //static int messIndex = 0;
    
        public static int receiveNum(int clientNum){
            try {
                int response = din[clientNum].readInt();
                return response;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
    
            return -1;
        }
    
        public static void sendNumber(int numToSend, int clientNum){
            try {
                dout[clientNum].writeInt(numToSend);
                dout[clientNum].flush();
                //dout[messIndex].writeInt(numToSend);
                //dout[messIndex].flush(); // By flushing the stream, it means to clear the stream of any element that may be or maybe not inside the stream
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
    
        }

    
        public static void cleanUp(int randOne, int randTwo){
            try {
                
                for(int i = 0; i < 2; i++){
                    clientSocket[i].close();
                    dout[i].close();
                    din[i].close();
                    
                }
                serverSocket.close();
    
                //System.out.println("Finished sending config to clients.");
                System.exit(0);
    
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    
    
        public static void main(String[] args){
            if(args.length != 3)
                System.exit(1);     
            final int port =  Integer.parseInt(args[0]); 
            final int seed = Integer.parseInt(args[1]);
            final int numMessages = Integer.parseInt(args[2]);
            Random random = new Random(seed);
            int randOne = random.nextInt();
            int randTwo = random.nextInt();
            //if(port <= 1024 || port > 65535){ //need to catch error or something
            //    System.exit(1);
           // }
    
            try{
                System.out.println("IP Address: " + InetAddress.getLocalHost() + "\nPort Number " + port);  
                // Initialize Necessary Objects
            
                serverSocket = new ServerSocket(port);
                //serverSocket = new ServerSocket(port);
                System.out.println("waiting for client...");
                clientSocket[0] = serverSocket.accept(); // Blocking call --> waits here until a request comes in from a client
                dout[0] = new DataOutputStream(clientSocket[0].getOutputStream()); // Instantiates dout so we can then use it to send data to the client
                din[0] = new DataInputStream(clientSocket[0].getInputStream()); // Instantiates din so we can then use it to receive data from the client

                clientSocket[1] = serverSocket.accept(); // Blocking call --> waits here until a request comes in from a client
                dout[1] = new DataOutputStream(clientSocket[1].getOutputStream()); // Instantiates dout so we can then use it to send data to the client
                din[1] = new DataInputStream(clientSocket[1].getInputStream()); // Instantiates din so we can then use it to receive data from the client
                
                System.out.println("Clients Connected!");
                System.out.println("Sending config to clients...");
                System.out.println(clientSocket[0].getInetAddress().getHostName() + " " + randOne);
                System.out.println(clientSocket[1].getInetAddress().getHostName() + " " + randTwo);
                
                
                sendNumber(numMessages, 0);
                sendNumber(randOne, 0);
                sendNumber(numMessages, 1);
                sendNumber(randTwo, 1);

                System.out.println("Finished sending config to clients.");
                System.out.println("Starting to listen for client messages...");

                long sumOne = 0;
                long sumTwo = 0;
                int counter = 0;
                for(int i = 0; i < numMessages; i++){
                int receiveOne = receiveNum(0);
                int receiveTwo = receiveNum(1);
                sendNumber(receiveTwo, 0);
                sendNumber(receiveOne, 1);
                sumOne += receiveOne;
                sumTwo += receiveTwo;
                counter++;
            }

                System.out.println("Finished listening for client messages.");
                System.out.println(clientSocket[0].getInetAddress().getHostName());
                System.out.println("        Messages received: " + counter);
                System.out.println("        Sum received: " + sumOne);
                System.out.println(clientSocket[1].getInetAddress().getHostName());
                System.out.println("        Messages received: " + counter);
                System.out.println("        Sum received: " + sumTwo);
    
                cleanUp(randOne, randTwo);
            }
            catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
    
    
    
}