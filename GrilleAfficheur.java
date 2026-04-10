/**
 * Développeur 4 — branche : feature/affichage-et-main
 * Classe responsable de l'affichage en console de la grille de Sudoku
 * en utilisant les caractères Unicode de dessin de boîte (box-drawing).
 */
public class GrilleAfficheur {

    private Grille grille;

    // ─── Caractères box-drawing ───────────────────────────────────────────────

    // Bord supérieur
    private static final String H_TOP    = "╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗";
    // Séparateur normal (entre deux lignes ordinaires)
    private static final String H_NORM   = "╟───┼───┼───╫───┼───┼───╫───┼───┼───╢";
    // Séparateur milieu (entre les blocs 3×3)
    private static final String H_MID    = "╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣";
    // Bord inférieur
    private static final String H_BOT    = "╚═══╧═══╧═══╩═══╧═══╧═══╩═══╧═══╧═══╝";

    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Initialise l'afficheur avec la grille à représenter.
     */
    public GrilleAfficheur(Grille grille) {
        this.grille = grille;
    }

    /**
     * Permet de remplacer la grille courante par une nouvelle.
     * Utilisée par SudokuApp pour passer de la grille initiale
     * à la grille résolue sans créer un nouvel afficheur.
     */
    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    /**
     * Méthode principale : affiche la grille complète en console.
     */
    public void afficher() {
        afficherSeparateur("haut");

        for (int row = 0; row < 9; row++) {
            afficherLigne(row);

            if (row == 8) {
                afficherSeparateur("bas");
            } else if (row == 2 || row == 5) {
                afficherSeparateur("milieu");
            } else {
                afficherSeparateur("normal");
            }
        }
    }

    /**
     * Affiche une ligne de séparation horizontale selon le type demandé.
     * @param type "haut" | "milieu" | "bas" | "normal"
     */
    private void afficherSeparateur(String type) {
        switch (type) {
            case "haut":   System.out.println(H_TOP);  break;
            case "milieu": System.out.println(H_MID);  break;
            case "bas":    System.out.println(H_BOT);  break;
            default:       System.out.println(H_NORM); break;
        }
    }

    /**
     * Affiche une ligne de la grille avec valeurs et séparateurs verticaux.
     * Les cases vides (valeur 0) sont remplacées par un espace.
     * Les séparateurs doubles (║) sont placés tous les 3 blocs.
     */
    private void afficherLigne(int row) {
        StringBuilder sb = new StringBuilder();

        for (int col = 0; col < 9; col++) {
            // Séparateur vertical à gauche de chaque cellule
            if (col == 0) {
                sb.append("║");
            } else if (col % 3 == 0) {
                sb.append("║");
            } else {
                sb.append("│");
            }

            int val = grille.getValeur(row, col);
            String cellule = (val == 0) ? " " : String.valueOf(val);
            sb.append(" ").append(cellule).append(" ");
        }

        sb.append("║"); // bord droit
        System.out.println(sb.toString());
    }
}
