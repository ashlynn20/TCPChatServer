import java.io.*;    
import java.net.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TCPClient {
    static DataInputStream din;
    static DataOutputStream dout;
    static Scanner userInput = new Scanner(System.in);
    static Socket clientSocket;

    public static int receiveNum(){
        try {
            int response = din.readInt();
            return response;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1; 
    }

    public static void sendNumber(int numToSend){
        try {
            dout.writeInt(numToSend); 
            dout.flush(); 
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    
   
    public static void cleanUp(){
        try {
            clientSocket.close();
            dout.close();
            din.close();
            System.exit(0);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        if(args.length != 2){
            System.exit(1);
        }
        final int EXIT_NUM = -1;
        Scanner userInput = new Scanner(System.in);

        final int port =  Integer.parseInt(args[1]);
        final String server_ip = args[0]; 

        try{
            clientSocket = new Socket(server_ip, port); 
            dout = new DataOutputStream(clientSocket.getOutputStream()); 
            din = new DataInputStream(clientSocket.getInputStream()); 
            
            int secretMessagesCount = receiveNum();
            int randomNum = receiveNum();
            System.out.println("Received config");
            System.out.println("number of messages = " + secretMessagesCount);
            System.out.println("seed = " + randomNum);
            System.out.println("Starting to send messages to server...");
            System.out.println("Finished sending messages to server.");
            
            Random random = new Random(randomNum);
            long sum = 0;
            long clientSum = 0;
            int counter = 0;
            for(int i = 0; i < secretMessagesCount; i++){
                int rand = random.nextInt();
                sendNumber(rand);
                sum += rand;
                counter++;
            }


            System.out.println("Total messages sent: " + counter);
            System.out.println("Sum of messages sent: " + sum);

            counter = 0;

            System.out.println("Starting to listen for messages from server...");

            for(int i = 0; i < secretMessagesCount; i++){
                int received = receiveNum();
                clientSum += received;
                counter++;
            }

            System.out.println("Finished listening for messages from server.");
            System.out.println("Total messages received: " + counter);
            System.out.println("Sum of messages received: " + clientSum);
            
            cleanUp();
            
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}

