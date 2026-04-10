/**
 * Développeur 4 — branche : feature/affichage-et-main
 * Classe principale du programme Solveur de Sudoku.
 * Elle orchestre les appels aux autres classes selon le scénario d'exécution.
 */
public class SudokuApp {

    /**
     * Point d'entrée du programme.
     * - Si un argument est fourni en ligne de commande → chemin vers le fichier grille.
     * - Sinon → saisie manuelle guidée.
     */
    public static void main(String[] args) {
        lancerApplication(args);
    }

    /**
     * Logique principale encapsulée dans un bloc try-catch.
     * Instancie GrilleLoader, Grille, SudokuSolver et GrilleAfficheur dans l'ordre.
     * Intercepte les SudokuException et affiche un message clair selon leur type.
     */
    private static void lancerApplication(String[] args) {
        try {
            GrilleLoader loader = new GrilleLoader();
            Grille grille;

            // ── Chargement ──────────────────────────────────────────────────
            if (args.length > 0) {
                System.out.println("Chargement de la grille depuis : " + args[0]);
                grille = loader.chargerDepuisFichier(args[0]);
            } else {
                System.out.println("Aucun fichier fourni. Veuillez saisir la grille manuellement.");
                grille = loader.chargerManuellement();
            }

            // ── Affichage de la grille initiale ─────────────────────────────
            GrilleAfficheur afficheur = new GrilleAfficheur(grille);
            System.out.println("\n=== Grille initiale ===");
            afficheur.afficher();

            // ── Résolution ──────────────────────────────────────────────────
            SudokuSolver solver = new SudokuSolver(grille);
            boolean resolu = solver.resoudre();

            // ── Affichage du résultat ────────────────────────────────────────
            if (resolu) {
                System.out.println("\n=== Grille résolue ===");
                afficheur.setGrille(grille); // la grille a été modifiée en place
                afficheur.afficher();
            } else {
                System.out.println("\nAucune solution trouvée pour cette grille.");
            }

        } catch (SudokuException e) {
            // Personnalisation du message selon la nature de l'erreur
            switch (e.getType()) {
                case FICHIER_INTROUVABLE:
                    System.err.println("[ERREUR] Fichier introuvable : " + e.getMessage());
                    break;
                case MAUVAIS_FORMAT:
                    System.err.println("[ERREUR] Format invalide : " + e.getMessage());
                    break;
                case GRILLE_INVALIDE:
                    System.err.println("[ERREUR] Grille invalide : " + e.getMessage());
                    break;
                default:
                    System.err.println("[ERREUR] " + e.getMessage());
                    break;
            }
        }
    }
}
