/**
 * Enumération des types d'erreurs possibles lors du chargement
 * et de la validation d'une grille de Sudoku.
 *
 * Utilisée par SudokuException pour distinguer les différents cas d'erreur.
 */
public enum TypeErreur {

    /**
     * Le fichier passé en argument est absent ou inaccessible.
     */
    FICHIER_INTROUVABLE,

    /**
     * Le fichier ne contient pas exactement 9 lignes de 9 valeurs.
     */
    MAUVAIS_FORMAT,

    /**
     * Une valeur est hors de la plage autorisée (0-9)
     * ou la grille viole déjà les règles du Sudoku.
     */
    GRILLE_INVALIDE
}
