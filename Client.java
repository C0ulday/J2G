/*
 * 
 * Client
 * Se sert de la classe Abstraite Runnable pour pouvoir utliser des threads
 * 
 */

import java.io.PrintWriter;
import java.net.Socket;

 /* Implémentation de la classe Runnable afin d'utiliser des Threads et d'utiliser séparément plusieurs fonctionnalités de la classe
  * client sans soucis
  */
public class Client implements Runnable {
    
    private String hostname;
    private int PORT;
    private Socket clientSocket;
    

    public Client(String hostname,int PORT){

        this.hostname = hostname;
        this.PORT = PORT;

        try { 
            clientSocket = new Socket(hostname,PORT);
        } catch (Exception e) {
            System.out.println("Impossible de créer un Socket pour le Client " + e.getMessage());
        }
    }

    public Client(Socket clienSocket){
        try { 
            clientSocket = new Socket(hostname,PORT);
        } catch (Exception e) {
            System.out.println("Impossible de créer un Socket pour le Client " + e.getMessage());
        }
    }

    public Socket getSocket(){
        return clientSocket;
    }

    public int getPORT(){
        return PORT;
    }

    public String getHostname(){
        return hostname;
    }

    /*Le client envoie ses informations au serveur  */
    public boolean CONNECTION(){
        System.out.print("Je suis prêt à jouer");
        return true;
    }

    /*Le client dit au serveur qu' il est prêt à jouer */
    public boolean READY(){
        System.out.println("Je suis prêt à jouer");
        return true;
    }



    @Override
    public void run(){


        // Connexion au réseau
        try{
            /*Le client se connecte au serveur */
            System.out.println("Je suis le client, je me connecte au réseau");

            // Crée un PrintWriter pour envoyer des messages au client
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
 
    
            for(int i=0; i<10; i++){
                Thread.sleep(500);
                writer.println("nombre" + i);
            }

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
        Client client = new Client("localhost",8585);
        Thread clientThread = new Thread(client);
        clientThread.start();

    }

}