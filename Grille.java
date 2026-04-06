/**
 * Représente une grille de Sudoku 9x9.
 * C'est le modèle central du projet : elle stocke les valeurs
 * et sait distinguer les cases fixes (données au départ)
 * des cases vides à remplir par le solveur.
 */
public class Grille {

    // Les valeurs actuelles de la grille (0 = case vide)
    private int[][] cellules;

    // Mémorise quelles cases étaient déjà remplies au départ
    private boolean[][] cellulesInitiales;

    /**
     * Construit la grille à partir d'un tableau 9x9 fourni par GrilleLoader.
     * Les cases non nulles sont automatiquement marquées comme initiales.
     */
    public Grille(int[][] valeurs) {
        cellules = new int[9][9];
        cellulesInitiales = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cellules[i][j] = valeurs[i][j];
                // Si la case n'est pas vide, elle est fixe
                cellulesInitiales[i][j] = (valeurs[i][j] != 0);
            }
        }
    }

    /**
     * Retourne la valeur de la case (row, col).
     * 0 signifie que la case est vide.
     */
    public int getValeur(int row, int col) {
        return cellules[row][col];
    }

    /**
     * Affecte une valeur à la case (row, col).
     * Interdit de modifier une case qui était déjà remplie au départ.
     */
    public void setValeur(int row, int col, int val) {
        if (cellulesInitiales[row][col]) {
            throw new IllegalArgumentException(
                "Impossible de modifier la case initiale (" + row + ", " + col + ")."
            );
        }
        cellules[row][col] = val;
    }

    /**
     * Retourne true si la case (row, col) était remplie au chargement.
     */
    public boolean estInitiale(int row, int col) {
        return cellulesInitiales[row][col];
    }

    /**
     * Retourne le tableau 9x9 des valeurs.
     * Utilisé par le solveur et l'afficheur.
     */
    public int[][] getCellules() {
        return cellules;
    }

    /**
     * Vérifie que la grille respecte les règles du Sudoku :
     * pas de doublon sur chaque ligne, colonne et bloc 3x3.
     * Les cases vides (0) sont ignorées.
     */
    public boolean estValide() {
        // Vérification des lignes
        for (int i = 0; i < 9; i++) {
            if (aDoublon(getLigne(i))) return false;
        }

        // Vérification des colonnes
        for (int j = 0; j < 9; j++) {
            if (aDoublon(getColonne(j))) return false;
        }

        // Vérification des 9 blocs 3x3
        for (int blocRow = 0; blocRow < 3; blocRow++) {
            for (int blocCol = 0; blocCol < 3; blocCol++) {
                if (aDoublon(getBloc(blocRow, blocCol))) return false;
            }
        }

        return true;
    }

    /**
     * Crée une copie totalement indépendante de cette grille.
     * Utile pour sauvegarder l'état avant une tentative de backtracking.
     */
    public Grille copier() {
        int[][] copie = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copie[i][j] = cellules[i][j];
            }
        }
        return new Grille(copie);
    }

    // ---------------------------------------------------------------
    // Méthodes privées utilitaires pour estValide()
    // ---------------------------------------------------------------

    /** Extrait les 9 valeurs d'une ligne. */
    private int[] getLigne(int row) {
        return cellules[row];
    }

    /** Extrait les 9 valeurs d'une colonne. */
    private int[] getColonne(int col) {
        int[] valeurs = new int[9];
        for (int i = 0; i < 9; i++) {
            valeurs[i] = cellules[i][col];
        }
        return valeurs;
    }

    /**
     * Extrait les 9 valeurs d'un bloc 3x3.
     * blocRow et blocCol vont de 0 à 2 (index du bloc, pas de la cellule).
     */
    private int[] getBloc(int blocRow, int blocCol) {
        int[] valeurs = new int[9];
        int index = 0;
        int startRow = blocRow * 3;
        int startCol = blocCol * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                valeurs[index++] = cellules[i][j];
            }
        }
        return valeurs;
    }

    /**
     * Retourne true si le tableau contient un doublon (en ignorant les 0).
     */
    private boolean aDoublon(int[] valeurs) {
        boolean[] vus = new boolean[10]; // index 1 à 9
        for (int v : valeurs) {
            if (v == 0) continue; // case vide, on ignore
            if (vus[v]) return true; // déjà vu = doublon
            vus[v] = true;
        }
        return false;
    }
}
