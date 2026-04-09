public class Main {
    public static void main(String[] args) {
        // Grille Sudoku de test (0 = case vide)
        int[][] grilleTest = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println("=== SudokuSolver Test ===");
        System.out.println("Grille initiale :");
        
        Grille grille = new Grille(grilleTest);
        grille.afficher();

        System.out.println("Lancement de la résolution...");
        SudokuSolver solver = new SudokuSolver(grille);
        
        boolean resolu = solver.resoudre();
        
        if (resolu) {
            System.out.println("Grille résolue :");
            grille.afficher();
            System.out.println("✓ Sudoku résolu avec succès !");
        } else {
            System.out.println("✗ Impossible de résoudre ce Sudoku.");
        }
    }
}