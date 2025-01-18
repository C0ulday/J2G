import java.util.Random;

/**
 * Classe représentant une IA pour jouer aux échecs avec différents niveaux de difficulté.
 */
public class AIPlayer {

    private int difficultyLevel; // Niveau de difficulté de l'IA

    /**
     * Constructeur de l'IA.
     *
     * @param difficultyLevel Niveau de difficulté de l'IA (1 = Facile, 2 = Moyen, 3 = Difficile)
     */
    public AIPlayer(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Méthode pour obtenir le prochain coup de l'IA.
     *
     * @param game L'état actuel du jeu.
     * @return Le coup choisi par l'IA.
     */
    public String getNextMove(ChessGame game) {
        switch (difficultyLevel) {
            case 1:
                return getRandomMove(game);
            case 2:
                return getBestMoveWithDepth(game, 2);
            case 3:
                return getBestMoveWithDepth(game, 4);
            default:
                throw new IllegalArgumentException("Niveau de difficulté invalide : " + difficultyLevel);
        }
    }

    /**
     * Génère un coup aléatoire (niveau facile).
     *
     * @param game L'état actuel du jeu.
     * @return Un coup aléatoire valide.
     */
    private String getRandomMove(ChessGame game) {
        Random random = new Random();
        String[] validMoves = game.getValidMoves(); // Suppose une méthode getValidMoves() existante
        return validMoves[random.nextInt(validMoves.length)];
    }

    /**
     * Génère le meilleur coup basé sur un algorithme de minimax avec une certaine profondeur.
     *
     * @param game L'état actuel du jeu.
     * @param depth La profondeur de recherche.
     * @return Le meilleur coup calculé.
     */
    private String getBestMoveWithDepth(ChessGame game, int depth) {
        String bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        for (String move : game.getValidMoves()) {
            ChessGame clonedGame = game.clone(); // Clone l'état du jeu
            clonedGame.makeMove(move); // Joue le coup temporairement
            int moveValue = minimax(clonedGame, depth - 1, false);

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /**
     * Algorithme minimax pour évaluer les coups.
     *
     * @param game L'état actuel du jeu.
     * @param depth La profondeur restante.
     * @param isMaximizing True si c'est le tour de maximisation, False sinon.
     * @return La valeur évaluée du coup.
     */
    private int minimax(ChessGame game, int depth, boolean isMaximizing) {
        if (depth == 0 || game.isGameOver()) {
            return evaluateBoard(game);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (String move : game.getValidMoves()) {
                ChessGame clonedGame = game.clone();
                clonedGame.makeMove(move);
                int eval = minimax(clonedGame, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (String move : game.getValidMoves()) {
                ChessGame clonedGame = game.clone();
                clonedGame.makeMove(move);
                int eval = minimax(clonedGame, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    /**
     * Évalue la position actuelle sur l'échiquier.
     *
     * @param game L'état actuel du jeu.
     * @return Un score représentant la position (positif pour avantage, négatif pour désavantage).
     */
    private int evaluateBoard(ChessGame game) {
        int score = 0;
        String[][] board = game.getboardPieces();
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = board[i][j];
                if (piece != null && !piece.isEmpty()) {
                    // Vérifier la compatibilité des types et ajuster en conséquence
                    try {
                        score += getPieceValue(piece);
                    } catch (ClassCastException e) {
                        System.err.println("Erreur de conversion pour la pièce en position (" + i + ", " + j + "): " + e.getMessage());
                    }
                }
            }
        }
        return score;
    }
    
    private int getPieceValue(String piece) {
        switch (piece) {
            case "\u2654": case "\u265A": return 100; // Roi
            case "\u2655": case "\u265B": return 9;  // Reine
            case "\u2656": case "\u265C": return 5;  // Tour
            case "\u2657": case "\u265D": return 3;  // Fou
            case "\u2658": case "\u265E": return 3;  // Cavalier
            case "\u2659": case "\u265F": return 1;  // Pion
            default: return 0;
        }
    }
    

    /**
     * Définit un nouveau niveau de difficulté pour l'IA.
     *
     * @param newDifficultyLevel Nouveau niveau de difficulté.
     */
    public void setDifficultyLevel(int newDifficultyLevel) {
        this.difficultyLevel = newDifficultyLevel;
    }

    /**
     * Récupère le niveau de difficulté actuel.
     *
     * @return Le niveau de difficulté.
     */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
}
