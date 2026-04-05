/**
 * Exception vérifiée (checked exception) levée lors du chargement
 * ou de la validation d'une grille de Sudoku.
 *
 * Elle embarque un TypeErreur pour permettre à l'appelant
 * de distinguer les différents cas d'erreur.
 */
public class SudokuException extends Exception {

    /** Type de l'erreur survenue. */
    private TypeErreur type;

    /**
     * Construit une SudokuException avec un message explicatif
     * et le type d'erreur correspondant.
     *
     * @param message Message décrivant l'erreur.
     * @param type    Type de l'erreur (FICHIER_INTROUVABLE, MAUVAIS_FORMAT, GRILLE_INVALIDE).
     */
    public SudokuException(String message, TypeErreur type) {
        super(message);
        this.type = type;
    }

    /**
     * Retourne le type de l'erreur.
     * Permet à SudokuApp d'afficher un message personnalisé selon la cause.
     *
     * @return Le TypeErreur associé à cette exception.
     */
    public TypeErreur getType() {
        return type;
    }
}
