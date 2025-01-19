import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChessGame {

    int rows; 
    int cols; 
    Color dark; 
    Color light;
    String[][] gameBoard; 
    private Icon[][] pieceIcons;
    int police;
    private boolean isWhiteTurn = true;

    public ChessGame(int rows, int cols, Color dark, Color light, int police){
        this.rows = rows;
        this.cols = cols;
        this.dark = dark;
        this.light = light;
        this.police = police;
        pieceIcons = new Icon[rows][cols];

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

    public String getCurrentPlayer() {
        return isWhiteTurn ? "WHITE" : "BLACK";
    }

    public void toggleTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public String[] getValidMoves() {
        ArrayList<String> validMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty()) {
                    ArrayList<coordonnee> possibleMoves = playGame.obtenirCoupPossible(i, j);
                    for (coordonnee move : possibleMoves) {
                        validMoves.add(i + " " + j + " " + move.getX() + " " + move.getY());
                    }
                }
            }
        }
        return validMoves.toArray(new String[0]);
    }

    public void makeMove(String move) {
        String[] parts = move.split(" ");
        int x1 = Integer.parseInt(parts[0]);
        int y1 = Integer.parseInt(parts[1]);
        int x2 = Integer.parseInt(parts[2]);
        int y2 = Integer.parseInt(parts[3]);
        updateBoard(x1, y1, x2, y2);
        toggleTurn();
    }

    public int getBoardScore() {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty()) {
                    score += getPieceValue(board[i][j]);
                }
            }
        }
        return score;
    }

    public ChessGame clone() {
        ChessGame clonedGame = new ChessGame(rows, cols, dark, light, police);
        clonedGame.board = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(this.board[i], 0, clonedGame.board[i], 0, 8);
        }
        clonedGame.isWhiteTurn = this.isWhiteTurn;
        return clonedGame;
    }

    private int getPieceValue(String piece) {
        switch (piece) {
            case "\u2654": case "\u265A":
                return 100;
            case "\u2655": case "\u265B":
                return 9;
            case "\u2656": case "\u265C":
                return 5;
            case "\u2657": case "\u265D":
                return 3;
            case "\u2658": case "\u265E":
                return 3;
            case "\u2659": case "\u265F":
                return 1;
            default:
                return 0;
        }
    }

    public boolean isGameOver() {
        boolean whiteKingPresent = false;
        boolean blackKingPresent = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ("\u2654".equals(board[i][j])) {
                    whiteKingPresent = true;
                } else if ("\u265A".equals(board[i][j])) {
                    blackKingPresent = true;
                }
            }
        }
        return !(whiteKingPresent && blackKingPresent);
    }

    public void updateBoard(int x1, int y1, int x2, int y2) {
        // Vérifier si la position source contient une pièce
        if (board[x1][y1] == null || board[x1][y1].isEmpty()) {
            System.err.println("Aucune pièce à déplacer depuis (" + x1 + ", " + y1 + ").");
            return;
        }
    
        // Déplacer la pièce et vider l'ancienne position
        board[x2][y2] = board[x1][y1];
        board[x1][y1] = "";
    }

    public void updatePiecesColors(Color newDark, Color newLight) {
        this.dark = newDark;
        this.light = newLight;
        if (boardPanel != null) {
            refreshBoard(boardPanel, rows, cols, dark, light, police);
        }
    }
    
    public void setPieceSymbol(int row, int col, String newSymbol) {
        // Vérifie que row/col sont valides
        if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) {
            System.err.println("Coordonnées invalides : (" + row + ", " + col + ")");
            return;
        }
        // Met à jour le symbole de la pièce
        board[row][col] = newSymbol;
    
        // Actualise l'affichage si le boardPanel est déjà construit
        if (boardPanel != null) {
            refreshBoard(boardPanel, rows, cols, dark, light, police);
        }
    }
    

    public void refreshBoard(JPanel boardPanel, int rows, int cols,
                         Color dark, Color light, int police) {
    // On nettoie le panel avant de le reconstruire
    boardPanel.removeAll();
    
    // On définit la grille (rows x cols)
    boardPanel.setLayout(new GridLayout(rows, cols));

    // Parcours des cases
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            // Crée un bouton pour la case (i, j)
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, police));
            button.setFocusPainted(false);

            // Détermine la couleur de fond (cases alternées)
            if ((i + j) % 2 == 0) {
                button.setBackground(dark);
            } else {
                button.setBackground(light);
            }

            // S’il y a une icône dans pieceIcons, on l’affiche
            if (pieceIcons[i][j] != null) {
                button.setIcon(pieceIcons[i][j]);
                // On peut laisser le texte vide
                button.setText("");
            } else {
                // Pas d’icône → on affiche le symbole texte
                button.setIcon(null);
                button.setText(board[i][j]);
            }

            // Gère l’événement de clic sur la case
            final int row = i;
            final int col = j;
            button.addActionListener(e -> handleMove(row, col, button));

            // Ajoute le bouton au panel
            boardPanel.add(button);
        }
    }
    // Force la mise à jour de l'interface
    boardPanel.revalidate();
    boardPanel.repaint();
}

    public void refreshBoardIA(JPanel boardPanel, int rows, int cols, Color dark, Color light, int police, boolean aiTurn, AIPlayer aiPlayer) {
        boardPanel.removeAll();
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton(board[i][j]);
                button.setFont(new Font("ADLaM Display", Font.PLAIN, police));
                button.setFocusPainted(false);
                button.setBackground((i + j) % 2 == 0 ? dark : light);
                boardPanel.add(button);
    
                int finalI = i;
                int finalJ = j;
                button.addActionListener(e -> {
                    try {
                        if ("BLACK".equals(getCurrentPlayer())) {
                            handleMove(finalI, finalJ, button);
    
                            // L'IA joue après le joueur
                            if (aiTurn && aiPlayer != null && "WHITE".equals(getCurrentPlayer())) {
                                String aiMove = aiPlayer.getNextMove(this);
                                makeMove(aiMove);
                                refreshBoardIA(boardPanel, rows, cols, dark, light, police, false, null);
                            }
                        }
                    } catch (Exception ex) {
                        System.err.println("Erreur dans l'action IA : " + ex.getMessage());
                    }
                });
            }
        }
    
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    

    void handleMove(int x, int y, JButton button) {
        // 1) Premier clic : on sélectionne une pièce
        if (selectedPosition[0] == -1) {
            // Si la case est vide, pas de sélection possible
            if (board[x][y].isEmpty()) {
                return;
            }
    
            // Vérifier si la pièce appartient bien au joueur courant
            String piece = board[x][y];
            if (!isPieceColorValid(piece, getCurrentPlayer())) {
                // On peut éventuellement afficher un message d’erreur
                JOptionPane.showMessageDialog(null, "Ce n'est pas votre pièce !");
                return;
            }
    
            // OK, on sélectionne
            selectedPosition[0] = x;
            selectedPosition[1] = y;
            highlightMoves(x, y);
            button.setBackground(Color.YELLOW);
        } else {
            // 2) Deuxième clic : on tente de déplacer la pièce
            resetHighlights();
    
            int xDepart = selectedPosition[0];
            int yDepart = selectedPosition[1];
    
            // Vérifier à nouveau la couleur de la pièce d'origine (au cas où)
            if (!isPieceColorValid(board[xDepart][yDepart], getCurrentPlayer())) {
                JOptionPane.showMessageDialog(null, "Ce n'est pas votre pièce !");
                selectedPosition[0] = -1;
                selectedPosition[1] = -1;
                return;
            }
    
            if (xDepart == x && yDepart == y) {
                // Si on reclique la même case, on annule la sélection
                selectedPosition[0] = -1;
                selectedPosition[1] = -1;
                resetSelection();
            } else {
                String mouvement = xDepart + " " + yDepart + " " + x + " " + y;
                boolean response = playGame.traiterMouvement(mouvement);
    
                if (response) {
                    updateBoard(xDepart, yDepart, x, y);
                    refreshBoard(boardPanel, 8, 8, dark, light, 39);
                    // Seulement si le coup est réellement validé, on bascule de joueur
                    toggleTurn();
                } else {
                    JOptionPane.showMessageDialog(null, "Coup invalide !");
                }
    
                selectedPosition[0] = -1;
                selectedPosition[1] = -1;
            }
        }
    }
    
    private boolean isPieceColorValid(String pieceSymbol, String currentPlayer) {
    // currentPlayer vaut "WHITE" ou "BLACK"
    // On vérifie le code Unicode
    if (currentPlayer.equals("WHITE")) {
        // Roi blanc = \u2654, Dame blanche = \u2655, etc.
        // On peut vérifier la plage :
        return (pieceSymbol.codePointAt(0) >= 0x2654 && pieceSymbol.codePointAt(0) <= 0x2659);
    } else { 
        // currentPlayer = "BLACK"
        return (pieceSymbol.codePointAt(0) >= 0x265A && pieceSymbol.codePointAt(0) <= 0x265F);
    }
}

    public void highlightMoves(int x, int y) {
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
                component.setBackground(new Color(50, 205, 50));
            }
        }
    }

    public void resetHighlights() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = (JButton) boardPanel.getComponent(i * 8 + j);
                btn.setBackground((i + j) % 2 == 0 ? dark : light);
            }
        }
    }

    public void resetSelection() {
        if (selectedPosition[0] != -1) {
            int x = selectedPosition[0], y = selectedPosition[1];
            JButton btn = (JButton) boardPanel.getComponent(x * 8 + y);
            btn.setBackground((x + y) % 2 == 0 ? dark : light);
        }
    }

    public JPanel BoardInit() {
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

    public JPanel getboardPanel() {
        return boardPanel;
    }

    public String[][] getboardPieces() {
        return ChessPieces;
    }

    public void setPieceIcon(int row, int col, Icon icon) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return;
        pieceIcons[row][col] = icon;
        if (boardPanel != null) {
            refreshBoard(boardPanel, rows, cols, dark, light, police);
        }
    }




    public void run() {
        board = ChessPieces;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        playGame.run();

        boardPanel = new JPanel(new GridLayout(rows, cols));
        refreshBoard(boardPanel, rows, cols, dark, light, 39);
    }


}
