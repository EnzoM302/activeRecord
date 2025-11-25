package ActiveRecord;

public class Personne {
    private int id = -1;
    private String nom;
    private String prenom;

    public  Personne(int id, String nom, String prenom) {
        this.id ++;
        this.nom = nom;
        this.prenom = prenom;
    }

    
}
