package ActiveRecord.src.main.java.ActiveRecord;

import java.util.ArrayList;

public class Film {

    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne personne) {
        this.titre = titre;
        this.id = -1;
        this.id_real = personne.getId();
    }

    private Film(int idF, int idR, String titre) {
        this.titre = titre;

    }
}
