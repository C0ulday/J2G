/*
 * 
 * Client
 * Se sert de la classe Abstraite Runnable pour pouvoir utliser des threads
 * 
 */

import java.net.Socket;

/* La classe Client implémente Runnable afin d'avoir une méthode Run et un Thread par Client */
public class Client implements Runnable {
    
    private String hostname;
    private int PORT;
    private Socket clientSocket;
    

    public Client(String hostname,int PORT){
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



    @Override
    public void run(){


        // Connexion au réseau
        try{
            /*Le client se connecte au serveur */
            System.out.println("Je suis le client, je me connecte au réseau");
    
            for(int i=0; i<10; i++){
                Thread.sleep(500);
                System.out.println("nombre" + i);
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
        client.run();
    }

}