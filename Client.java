/*
 * 
 * Client
 * Se sert de la classe Abstraite Runnable pour pouvoir utliser des threads
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private static int PORT = 8585;
    private String hostname;

    public Client(String hostname){
        this.hostname = hostname;
    }

    @Override
    public void run(){

        Socket clientSocket = null;
        // Connexion au réseau
        try{
            clientSocket = new Socket(hostname,PORT);
            System.out.println("Je suis le client, je me connecte au réseau");

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
    
            for(int i=0; i<10; i++){
                writer.println("nombre" + i);
                Thread.sleep(500);
                System.out.println("nombre" + i);
            }


            reader.close();
            writer.close();

        } catch(Exception e){
            System.out.println("Erreur" + e);
        }

        finally {
            try{
                clientSocket.close();
            } catch(Exception e){
                System.out.println("erreur" + e);
            }
        }
        
    }

    public static void main(String[] args) {
        
        Thread clientThread1 = new Thread(new Client("localhost"));
        Thread clientThread2 = new Thread(new Client("localhost"));
        clientThread1.start();
        clientThread2.start();
    }


}