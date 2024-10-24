import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Plateau {
    private Piece[][] cases;

    public Plateau() {
        cases = new Piece[8][8];
    }

    public void initialiserPlateau(GridPane grid) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button();
                button.setPrefSize(50, 50);
                if ((i + j) % 2 == 0) {
                    button.setStyle("-fx-background-color: white;");
                } else {
                    button.setStyle("-fx-background-color: gray;");
                }
                grid.add(button, j, i);
                
                // Initialiser les pièces (exemple pour les tours)
                if (i == 0 || i == 7) {
                    if (j == 0 || j == 7) {
                        cases[i][j] = new Tour(i == 0 ? "noir" : "blanc");
                        button.setText(cases[i][j].getSymbole());
                    }
                    // Ajoute d'autres pièces ici
                }
            }
        }
    }
}
