package vues;

import controleur.TypeOrdre;
import javafx.stage.Stage;

public class GestionnaireVueFilm extends GestionnaireVue {

    private Menu menu;
    private Ajout ajout;


    public GestionnaireVueFilm(Stage stage) {
        super(stage);
        menu=Menu.creerVue(this);
        ajout=Ajout.creerVue(this);
    }

    @Override
    public void traiter(TypeOrdre e) {

    }
}
