public class SudokuSolver {

    private Grille grille;

    public SudokuSolver(Grille grille) {
        this.grille = grille;
    }

    public boolean resoudre() {
        return backtrack(0, 0);
    }

    private boolean backtrack(int row, int col) {
        int[] caseVide = prochaineCaseVide(row, col);
        
        // Si aucune case vide, la grille est résolue
        if (caseVide == null) {
            return true;
        }
        
        int caseRow = caseVide[0];
        int caseCol = caseVide[1];
        
        // Essayer les valeurs de 1 à 9
        for (int val = 1; val <= 9; val++) {
            if (estPossible(caseRow, caseCol, val)) {
                grille.setValeur(caseRow, caseCol, val);
                
                if (backtrack(caseRow, caseCol)) {
                    return true;
                }
                
                // Retour en arrière: remettre à 0
                grille.setValeur(caseRow, caseCol, 0);
            }
        }
        
        // Aucune valeur ne convient
        return false;
    }

    private boolean estPossible(int row, int col, int val) {
        // Vérifier la ligne
        for (int c = 0; c < 9; c++) {
            if (grille.getValeur(row, c) == val) {
                return false;
            }
        }
        
        // Vérifier la colonne
        for (int r = 0; r < 9; r++) {
            if (grille.getValeur(r, col) == val) {
                return false;
            }
        }
        
        // Vérifier la sous-grille 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (grille.getValeur(r, c) == val) {
                    return false;
                }
            }
        }
        
        return true;
    }

    private int[] prochaineCaseVide(int row, int col) {
        for (int r = row; r < 9; r++) {
            int startCol = (r == row) ? col : 0;
            for (int c = startCol; c < 9; c++) {
                if (grille.getValeur(r, c) == 0) {
                    return new int[]{r, c};
                }
            }
        }
        return null;
    }
}