import java.awt.*;
import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;


public class Menu{

    int boardType = 1;
    int boardSize = 8;
    ChessGame game;
    AIPlayer aiPlayer;
    Color boardcolors[] = {new Color(173, 255, 47),new Color(173, 216, 230),new Color(222, 184, 135),new Color(34, 139, 34),new Color(25, 25, 142),new Color(139, 69, 19)};
    
    public static void main(String[] args) {
        // Fenêtre principale
        JFrame frame = new JFrame("Jeu d'échecs - Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Panneau principal avec CardLayout
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Ajouter les différentes pages
        Menu menu = new Menu(); // Création d'une instance pour accéder aux méthodes non statiques
        JPanel gamePage = menu.createGamePage(mainPanel, cardLayout);
        JPanel homePage = menu.createHomePage(mainPanel, cardLayout, frame, gamePage);
        JPanel settingsPage = menu.createSettingsPage(mainPanel, cardLayout);
        JPanel boardtypePage = menu.createBoardTypePage(mainPanel, cardLayout);
        JPanel boardsizePage = menu.createBoardSizePage(mainPanel, cardLayout);
        JPanel pieceConfigPage = menu.createPieceConfigPage(mainPanel, cardLayout);
        JPanel gameOnlinePage = menu.createGameOnlinePage(mainPanel, cardLayout);

        mainPanel.add(homePage, "Accueil");
        mainPanel.add(settingsPage, "settings");
        mainPanel.add(boardsizePage, "boardSize");
        mainPanel.add(boardtypePage, "boardType");
        mainPanel.add(pieceConfigPage, "pieceConfig");
        mainPanel.add(gameOnlinePage, "gameOnline");
        mainPanel.add(gamePage, "game");

        // Afficher la page d'accueil
        cardLayout.show(mainPanel, "Accueil");

        // Ajouter le panneau principal à la fenêtre
        frame.add(mainPanel);
        frame.setVisible(true);
    }




    //Page de setting
    public JPanel createSettingsPage(JPanel mainPanel, CardLayout cardLayout){
        JPanel settingsPanel = new JPanel();
        initPage(settingsPanel, "PARAMÈTRES");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 100, 100));

        JButton btnSize = createStyledButton("Taille Echéquier");
        JButton btnType = createStyledButton("Type échequier");
        JButton btnModification = createStyledButton("Modification");
        JButton btnBack = createStyledButton("Retour");

        btnSize.addActionListener(e -> cardLayout.show(mainPanel, "boardSize"));
        btnModification.addActionListener(e -> cardLayout.show(mainPanel, "pieceConfig"));
        btnType.addActionListener(e -> cardLayout.show(mainPanel,"boardType"));
        btnBack.addActionListener(e -> cardLayout.show(mainPanel,"Accueil"));

        JPanel sup1 = new JPanel();
        sup1.setBackground(new Color(240, 217, 181));

        JPanel sup2 = new JPanel();
        sup2.setBackground(new Color(240, 217, 181));

        buttonPanel.add(btnSize);
        buttonPanel.add(btnType);
        buttonPanel.add(btnModification);

        settingsPanel.add(buttonPanel, BorderLayout.CENTER);
        settingsPanel.add(sup1, BorderLayout.EAST);
        settingsPanel.add(sup2, BorderLayout.WEST);
        settingsPanel.add(btnBack, BorderLayout.SOUTH);
        
        return settingsPanel;
    }
   
    //Page pour configurer les pieces ajouter

    public JPanel createPieceConfigPage(JPanel mainPanel, CardLayout cardLayout) {
        JPanel pieceConfigPanel = new JPanel(new BorderLayout());
        initPage(pieceConfigPanel, "Configuration des Pièces");

        JPanel pieceOptions = new JPanel(new GridLayout(4, 1, 20, 20));
        pieceOptions.setOpaque(false);

        JButton btnAddPiece = createStyledButton("Ajouter une Nouvelle Pièce");
        btnAddPiece.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Sélectionner une image de pièce");
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Image sélectionnée: " + selectedFile.getName());
            }
        });

        JButton btnModifyPiece = createStyledButton("Modifier une Pièce Existante");
        btnModifyPiece.addActionListener(e -> {
            cardLayout.show(mainPanel, "pieceConfig");
        });

        JButton btnChooseColor = createStyledButton("Choisir la Couleur des Pièces");
        String[] colors = {"Blanc", "Noir", "Rouge", "Bleu", "Vert"};
        JComboBox<String> colorSelection = new JComboBox<>(colors);
        btnChooseColor.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, colorSelection, "Sélectionner une couleur", JOptionPane.QUESTION_MESSAGE);
        });

        JButton btnBack = createStyledButton("Retour");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "settings"));

        pieceOptions.add(btnAddPiece);
        pieceOptions.add(btnModifyPiece);
        pieceOptions.add(btnChooseColor);

        pieceConfigPanel.add(pieceOptions, BorderLayout.CENTER);
        pieceConfigPanel.add(btnBack, BorderLayout.SOUTH);
        
        return pieceConfigPanel;
    }

    public JPanel createGamePageIA(JPanel mainPanel, CardLayout cardLayout, boolean vsAI) {
        JPanel gamePanel = new JPanel(new BorderLayout());
        initPage(gamePanel, "Game");
    
        final Color dark;
        final Color light;
        if (boardType == 2) { 
            dark = boardcolors[1]; 
            light = boardcolors[4]; 
        } else if (boardType == 3) { 
            dark = boardcolors[2]; 
            light = boardcolors[5]; 
        } else {
            dark = boardcolors[0];
            light = boardcolors[3];
        }
    
        game = new ChessGame(boardSize, boardSize, dark, light, 39);
        aiPlayer = vsAI ? new AIPlayer(2) : null;
    
        game.run();
        gamePanel.add(game.getboardPanel(), BorderLayout.CENTER);
    
        // Chronomètres pour chaque joueur
        Chrono chronoJoueur = new Chrono(0, 10, 0); // 10 minutes pour le joueur
        Chrono chronoIA = new Chrono(0, 10, 0); // 10 minutes pour l'IA
        JLabel chronoJoueurLabel = new JLabel("Temps Joueur : " + chronoJoueur.toString());
        JLabel chronoIALabel = new JLabel("Temps IA : " + chronoIA.toString());
    
        JPanel chronoPanel = new JPanel(new GridLayout(2, 1));
        chronoPanel.add(chronoJoueurLabel);
        chronoPanel.add(chronoIALabel);
    
        gamePanel.add(chronoPanel, BorderLayout.NORTH);
    
        // Déclenchement des chronomètres
        startChrono(chronoJoueur, chronoIA, chronoJoueurLabel, chronoIALabel, vsAI);
    
        JButton bBack = createStyledButton("RETOUR");
        bBack.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));
        gamePanel.add(bBack, BorderLayout.SOUTH);
    
        return gamePanel;
    }
    
    private void startChrono(Chrono chronoJoueur, Chrono chronoIA, JLabel chronoJoueurLabel, JLabel chronoIALabel, boolean vsAI) {
        Timer timer = new Timer(1000, e -> {
            if ("BLACK".equals(game.getCurrentPlayer())) {
                decrementChrono(chronoJoueur, chronoJoueurLabel);
            } else if ("WHITE".equals(game.getCurrentPlayer())) {
                decrementChrono(chronoIA, chronoIALabel);
            }
    
            // Vérifiez si l'un des chronomètres a expiré
            if (chronoJoueur.getHeures() == 0 && chronoJoueur.getMinutes() == 0 && chronoJoueur.getSecondes() == 0) {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "Le joueur a perdu à cause du temps !");
            } else if (chronoIA.getHeures() == 0 && chronoIA.getMinutes() == 0 && chronoIA.getSecondes() == 0) {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(null, "L'IA a perdu à cause du temps !");
            } else {
                // Si c'est au tour de l'IA, elle joue
                if (vsAI && "WHITE".equals(game.getCurrentPlayer())) {
                    jouerCoupIA(chronoIA, chronoIALabel);
                }
            }
        });
        timer.start();
    }
    
    private void jouerCoupIA(Chrono chronoIA, JLabel chronoIALabel) {
        try {
            String aiMove = aiPlayer.getNextMove(game);
            game.makeMove(aiMove);
            game.refreshBoardIA(game.getboardPanel(), boardSize, boardSize, game.dark, game.light, 39, true, aiPlayer);
            decrementChrono(chronoIA, chronoIALabel); // Met à jour le temps après le coup de l'IA
        } catch (Exception e) {
            System.err.println("Erreur lors du coup de l'IA : " + e.getMessage());
        }
    }
    
    private void decrementChrono(Chrono chrono, JLabel label) {
        if (chrono.getSecondes() > 0) {
            chrono.setSecondes(chrono.getSecondes() - 1);
        } else if (chrono.getMinutes() > 0) {
            chrono.setMinutes(chrono.getMinutes() - 1);
            chrono.setSecondes(59);
        } else if (chrono.getHeures() > 0) {
            chrono.setHeures(chrono.getHeures() - 1);
            chrono.setMinutes(59);
            chrono.setSecondes(59);
        }
        label.setText("Temps : " + chrono.toString());
    }
    
    
    // Page pour choisir un type de plateau
    public JPanel createBoardTypePage(JPanel mainPanel, CardLayout cardLayout){

        JPanel boardTypePanel = new JPanel();
        initPage(boardTypePanel, "Board Type");
        JPanel boards = new JPanel(new GridLayout(3, 3, 20, 20));
        boards.setBackground(new Color(240 , 217, 181));

        JButton[][] choice = new JButton[3][3];
        
        Runnable updateBoardTypePanel = () -> {
            
            boards.removeAll();
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    JPanel sample = new JPanel(new BorderLayout());
                    ChessGame sampleBoard = new ChessGame(boardSize, boardSize,boardcolors[j], boardcolors[j+3], 20);
                   
                    sample.add(sampleBoard.BoardInit());

                    int index = i*3+j+1;
                    choice[i][j] = createStyledButton(index+"");
                    choice[i][j].addActionListener(e -> {
                        boardType = index;
                        JOptionPane.showMessageDialog(null, "Type d'échiquier sélectionné : " + index);
                        boardType = index;
                    });
                    
                    sample.add(choice[i][j], BorderLayout.SOUTH);

                    boards.add(sample);
                }    
            }
            boards.revalidate();
            boards.repaint();
        };
    
        
        updateBoardTypePanel.run(); 

        boardTypePanel.add(boards);
        JButton btnBack = createStyledButton("Retour");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "settings"));
        boardTypePanel.add(btnBack, BorderLayout.SOUTH);
        boardTypePanel.putClientProperty("updateBoardTypePanel", updateBoardTypePanel);
        return boardTypePanel;
    }



    //Page pour definir la taille du plateau
    public JPanel createBoardSizePage(JPanel mainPanel, CardLayout cardLayout){
       
        //Principal
        JPanel BoaderSizePanel = new JPanel(cardLayout);
        initPage(BoaderSizePanel, "Board Size");
    
        // Créer un JSlider pour ajuster la taille
        JSlider slider = new JSlider(4, 8, boardSize); // Plage de 4x4 à 16x16
        slider.setMajorTickSpacing(1); // Graduation principale tous les 2
        slider.setPaintTicks(true); // Afficher les graduations
        slider.setPaintLabels(true); // Afficher les labels
        slider.addChangeListener(e -> {
            // Mettre à jour le panneau des types d'échiquiers
            // L'indice 3 correspond à "BoardType"
            if ( boardSize != slider.getValue()) {
                boardSize = slider.getValue();
                JPanel typePanel = (JPanel) mainPanel.getComponent(3);
                Runnable setSize = (Runnable) typePanel.getClientProperty("updateBoardTypePanel");
                setSize.run();
            }
        });

        //Ajouter le jauge a la page
        BoaderSizePanel.add(slider);

        JButton bBack = createStyledButton("Retour");
        bBack.addActionListener(e -> cardLayout.show(mainPanel,"settings"));
        BoaderSizePanel.add(bBack, BorderLayout.SOUTH);

        return BoaderSizePanel;
    }



    //Page de jeu en ligne
    public JPanel createGameOnlinePage(JPanel mainPanel, CardLayout cardLayout){
       // Panneau principal avec un BorderLayout
        JPanel gameOnlinePanel = new JPanel(new BorderLayout());
        initPage(gameOnlinePanel, "Jouer avec un ami");
    
        // Panneau central pour les options
        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 20, 20)); // Trois boutons empilés
        optionsPanel.setOpaque(false);
    
        // Bouton pour héberger une partie
        JButton btnHostGame = createStyledButton("Héberger une Partie");


        btnHostGame.addActionListener(e -> {
            // Démarrer le serveur
            new Thread(() -> {
                Server serveur = new Server();
                try {
            // Obtenir l'adresse IP locale
                InetAddress localHost = InetAddress.getLocalHost();
                
                // Afficher l'adresse IP et le nom d'hôte
                JOptionPane.showMessageDialog(null,"Nom d'hôte : " + localHost.getHostName()+ "\n" + "Adresse IP locale : " + localHost.getHostAddress() + "\n" + "PORT : " + serveur.getPORT() + "\n" + "Serveur démarré ! En attente de joueurs." );
            } catch (UnknownHostException error) {
                System.err.println("Impossible de récupérer l'adresse IP : " + error.getMessage());
            }
            }).start();
        });
    
        // Bouton pour rejoindre une partie
        JButton btnJoinGame = createStyledButton("Rejoindre une Partie");
        btnJoinGame.addActionListener(e -> {

            // Créer un panneau pour les deux champs
            JPanel panel = new JPanel();
        
            // Ajouter des labels et des champs de texte pour l'adresse IP et le port
            JTextField serverAddressField = new JTextField(20);
            JTextField portField = new JTextField(20);
            
            panel.add(new JLabel("Entrez l'adresse IP du serveur :"));
            panel.add(serverAddressField);
            panel.add(new JLabel("Entrez le port du serveur :"));
            panel.add(portField);
        
            // Afficher la boîte de dialogue avec les champs de texte
            int option = JOptionPane.showConfirmDialog(null, panel, "Configuration du serveur", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Récupérer les valeurs des champs
                String serverAddress = serverAddressField.getText();
                String portInput = portField.getText();
                
                // Afficher les résultats (ou les utiliser)
                System.out.println("Adresse IP du serveur : " + serverAddress);
                System.out.println("Port du serveur : " + portInput);

                if (serverAddress != null && portInput != null) {
                    
                    try {
                        int port = Integer.parseInt(portInput);
                        Socket socket = new Socket(serverAddress, port);
                        Client client = new Client(socket);
                        System.out.println("[INFO] Connecté au serveur : " + serverAddress + ":" + port);
                        JOptionPane.showMessageDialog(null, "Connecté au serveur !");
                        client.Ready();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Impossible de se connecter au serveur.");
                    }
                }
            }

        });
    
        // Bouton pour revenir à l'accueil
        JButton btnBack = createStyledButton("RETOUR");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Accueil"));
    
        // Ajouter les boutons au panneau d'options
        optionsPanel.add(btnHostGame);
        optionsPanel.add(btnJoinGame);
        optionsPanel.add(btnBack);
    
        // Ajouter les panneaux au panneau principal
        gameOnlinePanel.add(optionsPanel, BorderLayout.CENTER);
    
        return gameOnlinePanel;
    }



    //Page de jeu avec IA
    public JPanel createGamePage(JPanel mainPanel, CardLayout cardLayout){
        JPanel gamePanel = new JPanel();
       
        //Initialiser la page
        initPage(gamePanel, "Game");
        if(boardType >=1 && boardType<= 3){
            Color dark=boardcolors[0] , light = boardcolors[3];
            if(boardType ==1){ dark = boardcolors[0];  light = boardcolors[3];}
            if(boardType ==2){ dark = boardcolors[1];  light = boardcolors[4];}
            if(boardType ==3){ dark = boardcolors[2];  light = boardcolors[5];}
            ChessGame game = new ChessGame(boardSize, boardSize, dark, light, 39);
            game.run();
            gamePanel.add(game.getboardPanel());
        
        }

        
        JButton bBack = createStyledButton("RETOUR");
        bBack.addActionListener(e -> cardLayout.show(mainPanel,"Accueil"));
        gamePanel.add(bBack, BorderLayout.SOUTH);

        return gamePanel;
    }


    //Page d'accueil
    public JPanel createHomePage(JPanel mainPanel, CardLayout cardLayout, JFrame frame, JPanel gamePageinit) {
        // Panneau principal avec fond personnalisé
        JPanel backgroundPanel = new JPanel();
        initPage(backgroundPanel, "J2G - ECHECS");
        
        //Icon de fond
        backgroundPanel.add(backend("\u265B", new Color(240, 217, 181), 500)); 

        // Panneau de boutons ex-centré
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Rendre transparent pour voir le fond
        buttonPanel.setLayout(new GridLayout(4, 1));

        // Boutons avec style
        JButton btnPlayIA = createStyledButton("JOUER vs l'IA");
        JButton btnPlayOnline = createStyledButton("JOUER vs AMI");
        JButton btnSettings = createStyledButton("PARAMÈTRES");

        /*btnPlay.addActionListener(e -> {
            
            
            mainPanel.remove(gamePageinit); // Supprimer la page actuelle du jeu
            JPanel newGamePage = createGamePage(mainPanel, cardLayout); // Créer une nouvelle page de jeu
            mainPanel.add(newGamePage, "game"); // Ajouter la nouvelle page au CardLayout
            cardLayout.show(mainPanel, "game"); // Afficher la page du jeu
         
           
        });*/

        btnPlayIA.addActionListener(e -> {
            mainPanel.remove(gamePageinit);
            JPanel newGamePage = createGamePageIA(mainPanel, cardLayout, true);
            mainPanel.add(newGamePage, "game");
            cardLayout.show(mainPanel, "game");
        });

       
        btnSettings.addActionListener(e -> cardLayout.show(mainPanel,"settings"));
        btnPlayOnline.addActionListener(e -> cardLayout.show(mainPanel,"gameOnline"));

       // buttonPanel.add(btnPlay);
        buttonPanel.add(btnPlayIA);
        buttonPanel.add(btnPlayOnline);
        buttonPanel.add(btnSettings);

        backgroundPanel.add(buttonPanel, BorderLayout.EAST);   
        return backgroundPanel; 
    }



    //image de garde
    public JPanel backend(String text, Color color, int size){
        JPanel icon = new JPanel(new GridLayout(1,1));
        JLabel lab = new JLabel(text, JLabel.CENTER);
        lab.setFont(new Font( "ADLaM Display", Font.ROMAN_BASELINE, size));
        lab.setForeground(new Color(181, 136, 99));
        icon.setBackground(color);
        icon.add(lab);
        return icon;
    }



    // Méthode pour créer des boutons stylisés
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(new Color(181, 136, 99));
        button.setBackground(new Color(240, 217, 181)); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(181, 136, 99), 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }


    //Initialise les page
    public void initPage(JPanel page, String pageName){
        
        page.setLayout(new BorderLayout());
        page.setBorder(BorderFactory.createLineBorder(new Color(181, 136, 99), 3));
        page.setBackground(new Color(240, 217, 181));
       
       
        // Titre du jeu
        JLabel title = new JLabel(pageName, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(new Color(181, 136, 99)); // Couleur du titre
        title.setBorder(BorderFactory.createLineBorder(new Color(181, 136, 99), 5));
        page.add(title, BorderLayout.NORTH);
    }
}


