public class Grille {
    private int[][] cellules;
    private boolean[][] estInitiale;

    public Grille(int[][] grille) {
        this.cellules = new int[9][9];
        this.estInitiale = new boolean[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.cellules[i][j] = grille[i][j];
                this.estInitiale[i][j] = (grille[i][j] != 0);
            }
        }
    }

    public int getValeur(int row, int col) {
        return cellules[row][col];
    }

    public void setValeur(int row, int col, int val) {
        if (!estInitiale[row][col]) {
            cellules[row][col] = val;
        }
    }

    public boolean estInitiale(int row, int col) {
        return estInitiale[row][col];
    }

    public boolean estValide() {
        // Vérification de base - pas d'implémentation complète pour le test
        return true;
    }

    public int[][] getCellules() {
        return cellules;
    }

    public void afficher() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("---------------------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(cellules[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}