import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe responsable du chargement d'une grille de Sudoku.
 *
 * Elle peut lire la grille depuis un fichier texte ou via
 * une saisie manuelle de l'utilisateur dans la console.
 * Elle effectue toutes les validations avant de construire la Grille.
 */
public class GrilleLoader {

    /**
     * Charge une grille de Sudoku depuis un fichier texte.
     *
     * Le fichier doit contenir exactement 9 lignes,
     * chacune avec 9 chiffres séparés par des espaces (0 = case vide).
     *
     * @param chemin Chemin vers le fichier texte.
     * @return Un objet Grille initialisé avec les valeurs du fichier.
     * @throws SudokuException Si le fichier est introuvable, mal formaté ou contient des valeurs invalides.
     */
    public Grille chargerDepuisFichier(String chemin) throws SudokuException {
        int[][] data = new int[9][9];

        try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = reader.readLine()) != null) {
                // Ignorer les lignes vides
                if (ligne.trim().isEmpty()) continue;

                if (numeroLigne >= 9) {
                    throw new SudokuException(
                        "Le fichier contient plus de 9 lignes.",
                        TypeErreur.MAUVAIS_FORMAT
                    );
                }

                data[numeroLigne] = parseLigne(ligne, numeroLigne + 1);
                numeroLigne++;
            }

            if (numeroLigne < 9) {
                throw new SudokuException(
                    "Le fichier contient seulement " + numeroLigne + " ligne(s) au lieu de 9.",
                    TypeErreur.MAUVAIS_FORMAT
                );
            }

        } catch (FileNotFoundException e) {
            throw new SudokuException(
                "Fichier introuvable : " + chemin,
                TypeErreur.FICHIER_INTROUVABLE
            );
        } catch (IOException e) {
            throw new SudokuException(
                "Erreur de lecture du fichier : " + e.getMessage(),
                TypeErreur.MAUVAIS_FORMAT
            );
        }

        validerFormat(data);
        validerValeurs(data);

        return new Grille(data);
    }

    /**
     * Charge une grille de Sudoku via la saisie manuelle de l'utilisateur.
     *
     * Affiche des instructions et lit 9 lignes depuis la console.
     * En cas d'erreur de format sur une ligne, demande de la ressaisir.
     *
     * @return Un objet Grille initialisé avec les valeurs saisies.
     * @throws SudokuException Si les valeurs saisies sont invalides.
     */
    public Grille chargerManuellement() throws SudokuException {
        int[][] data = new int[9][9];
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Saisie manuelle de la grille ===");
        System.out.println("Entrez 9 lignes de 9 chiffres séparés par des espaces.");
        System.out.println("Utilisez 0 pour les cases vides. Exemple : 5 3 0 0 7 0 0 0 0");
        System.out.println();

        for (int i = 0; i < 9; i++) {
            boolean ligneValide = false;

            while (!ligneValide) {
                System.out.print("Ligne " + (i + 1) + " : ");
                String ligne = scanner.nextLine();

                try {
                    data[i] = parseLigne(ligne, i + 1);
                    ligneValide = true;
                } catch (SudokuException e) {
                    System.out.println("  Erreur : " + e.getMessage() + " Veuillez ressaisir.");
                }
            }
        }

        validerValeurs(data);

        return new Grille(data);
    }

    /**
     * Découpe une ligne texte en tableau de 9 entiers.
     *
     * @param ligne       La ligne à parser (valeurs séparées par des espaces).
     * @param numeroLigne Le numéro de la ligne (pour les messages d'erreur).
     * @return Un tableau de 9 entiers.
     * @throws SudokuException Si la ligne contient un mauvais nombre de valeurs ou des caractères non numériques.
     */
    private int[] parseLigne(String ligne, int numeroLigne) throws SudokuException {
        String[] parties = ligne.trim().split("\\s+");

        if (parties.length != 9) {
            throw new SudokuException(
                "Ligne " + numeroLigne + " : attendu 9 valeurs, trouvé " + parties.length + ".",
                TypeErreur.MAUVAIS_FORMAT
            );
        }

        int[] valeurs = new int[9];

        for (int j = 0; j < 9; j++) {
            try {
                valeurs[j] = Integer.parseInt(parties[j]);
            } catch (NumberFormatException e) {
                throw new SudokuException(
                    "Ligne " + numeroLigne + ", colonne " + (j + 1) +
                    " : valeur non numérique '" + parties[j] + "'.",
                    TypeErreur.MAUVAIS_FORMAT
                );
            }
        }

        return valeurs;
    }

    /**
     * Vérifie que le tableau contient exactement 9 lignes de 9 colonnes.
     *
     * @param data Le tableau 2D à vérifier.
     * @throws SudokuException Si le format est incorrect.
     */
    private void validerFormat(int[][] data) throws SudokuException {
        if (data.length != 9) {
            throw new SudokuException(
                "La grille doit contenir exactement 9 lignes.",
                TypeErreur.MAUVAIS_FORMAT
            );
        }

        for (int i = 0; i < data.length; i++) {
            if (data[i].length != 9) {
                throw new SudokuException(
                    "La ligne " + (i + 1) + " doit contenir exactement 9 colonnes.",
                    TypeErreur.MAUVAIS_FORMAT
                );
            }
        }
    }

    /**
     * Vérifie que toutes les valeurs du tableau sont comprises entre 0 et 9 inclus.
     *
     * @param data Le tableau 2D à vérifier.
     * @throws SudokuException Si une valeur est hors de la plage autorisée.
     */
    private void validerValeurs(int[][] data) throws SudokuException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (data[i][j] < 0 || data[i][j] > 9) {
                    throw new SudokuException(
                        "Valeur invalide " + data[i][j] +
                        " à la ligne " + (i + 1) + ", colonne " + (j + 1) +
                        ". Les valeurs doivent être comprises entre 0 et 9.",
                        TypeErreur.GRILLE_INVALIDE
                    );
                }
            }
        }
    }
}
