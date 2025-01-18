

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ChessGame {

    int rows; 
    int cols; 
    Color dark; 
    Color light;
    String[][] gameBoard; 
    int police;



    public ChessGame(int rows, int cols, Color dark, Color light, int police){
        this.rows = rows;
        this.cols = cols;
        this.dark = dark;
        this.light = light;
       
        this.police = police;
    }

    private String[][] ChessPieces = {    
        {"\u2656", "\u2658", "\u2657", "\u2655", "\u2654", "\u2657", "\u2658", "\u2656"},
        {"\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659"},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F"},
        {"\u265C", "\u265E", "\u265D", "\u265B", "\u265A", "\u265D", "\u265E", "\u265C"}
    };

    
    
    int[] selectedPosition = {-1, -1};

    private JPanel boardPanel;
    private String[][] board = ChessPieces;

    

    
   

    public void updateBoard(int x1, int y1, int x2, int y2) {
        board[x2][y2] = board[x1][y1]; // Déplace la pièce
        board[x1][y1] = "";           // Vide l'ancienne position
    }

    public void refreshBoard(JPanel boardPanel, int rows, int cols, Color dark, Color light, int police) {
       
        boardPanel.removeAll(); // Effacez les anciens boutons

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton(board[i][j]);
                button.setFont(new Font("ADLaM Display", Font.PLAIN, police));
                button.setFocusPainted(false);
                button.setBackground((i + j) % 2 == 0 ? dark : light);
                boardPanel.add(button);

                int finalI = i;
                int finalJ = j;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleMove(finalI, finalJ, button);
                    }
                });
            }
        }

        boardPanel.revalidate(); // Rafraîchit l'interface
        boardPanel.repaint();   // Redessine le plateau
    }

    void handleMove(int x, int y, JButton button) {
        
        if (selectedPosition[0] == -1) {
            if (!board[x][y].isEmpty()) {
                selectedPosition[0] = x;
                selectedPosition[1] = y;
                highlightMoves(x, y);
                button.setBackground(Color.YELLOW); // Marquer la sélection
            }
            
           
            System.out.println("Position de départ sélectionnée : (" + x + ", " + y + ")");
        } else {
            
            // Changer de sélection si une nouvelle pièce est cliquée
            resetHighlights(); // Réinitialiser les coups possibles
            if (selectedPosition[0] == x && selectedPosition[1] == y) {
                // Si l'utilisateur clique à nouveau sur la même pièce, désélectionner
                selectedPosition[0] = -1;
                selectedPosition[1] = -1;
                resetSelection(); 
            } else {
                Piece piece = playGame.plateau.getPiece(x, y);
                // Changer de sélection si une nouvelle pièce est cliquée
                if (!board[x][y].isEmpty() && piece.getCouleur().equals(playGame.joueurActuel)) {
                    selectedPosition = new int[]{x, y};
                    highlightMoves(x, y); // Mettre à jour les déplacements possibles
                    button.setBackground(Color.YELLOW); // Nouvelle sélection
                } else {
                    int xactu = selectedPosition[0];
                    int yactu = selectedPosition[1];
                    System.out.println("Position de destination : (" + x + ", " + y + ")");
        
                    // Envoyer le mouvement au backend
                    String mouvement = xactu + " " + yactu + " " + x + " " + y;
                    boolean response = playGame.traiterMouvement(mouvement);
                    if(response){
                        updateBoard(xactu, yactu, x, y);
                        try {
                            Thread.sleep(2); // Attendre 2 secondes
                            refreshBoard(boardPanel, 8, 8, dark, light,39); // Rafraîchit l'interface
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("Réponse du backend : " + response);
                    selectedPosition[0] = -1; // Réinitialiser la sélection
                    selectedPosition[1] = -1;
                }
        
           
            } 

            
        }
    }

    // Mise en evidence des coup possibles
    public void highlightMoves(int x, int y){
        ArrayList<coordonnee> possibleMoves = playGame.obtenirCoupPossible(x, y);

        if (possibleMoves.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun déplacement possible pour cette pièce.");
            return;
        }

        for (coordonnee move : possibleMoves) {
            int moveX = move.getX();
            int moveY = move.getY();
            Component component = boardPanel.getComponent(moveX * 8 + moveY);
            if (component instanceof JButton) {
                component.setBackground(new Color(50, 205, 50)); // Met en évidence les cases accessibles
            }
        }
    }

    public void resetHighlights() {
        // Réinitialiser les couleurs des cases en fonction de leur position
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = (JButton) boardPanel.getComponent(i * 8 + j);
                btn.setBackground((i + j) % 2 == 0 ? dark : light);
            }
        }
    }

    public void resetSelection() {
        // Réinitialiser la sélection actuelle
        if (selectedPosition[0] != -1) {
            int x = selectedPosition[0], y = selectedPosition[1];
            JButton btn = (JButton) boardPanel.getComponent(x * 8 + y);
            btn.setBackground((x + y) % 2 == 0 ? dark : light);
        }
    }


    public JPanel BoardInit( ){
        JPanel Board = new JPanel(new GridLayout(rows, cols));
       
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton(board[i][j]);
                button.setFont(new Font("ADLaM Display", Font.PLAIN, police));
                button.setFocusPainted(false);
                button.setBackground((i + j) % 2 == 0 ? dark : light);
                Board.add(button);
            }
        }        
        return Board;
    }
    public JPanel getboardPanel(){
        return boardPanel;
    }

    public String[][] getboardPieces(){
        return ChessPieces;
    }

    public void run() {

        board = ChessPieces;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        playGame.run();

        boardPanel = new JPanel(new GridLayout(rows, cols));
        refreshBoard(boardPanel, rows, cols, dark, light,39); // Initialise le plateau

        //frame.add(boardPanel);
       // frame.setVisible(true);

       
    }
}
