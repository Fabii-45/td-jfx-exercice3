package vues;

import controleur.LanceurOrdre;
import controleur.TypeOrdre;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;

public class GestionnaireVueFilm extends GestionnaireVue {

    private Menu menu;
    private Ajout ajout;
    private TousLesFilms tousLesFilms;
    private ChoixGenre choixGenre;


    public GestionnaireVueFilm(Stage stage) {
        super(stage);
        menu=Menu.creerVue(this);
        ajout=Ajout.creerVue(this);
        tousLesFilms=TousLesFilms.creerVue(this);
        choixGenre=ChoixGenre.creerVue(this);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch(e) {
            case SHOW_AJOUT: {
                this.getStage().setScene(ajout.getScene());
                getStage().show();
                break;
            }
            case SHOW_MENU: {
                this.getStage().setScene(menu.getScene());
                getStage().show();
                break;
            }
            case SHOW_FILMS: {
                this.getStage().setScene(tousLesFilms.getScene());
                getStage().show();
                break;
            }
            case SHOW_FILMSGENRE: {
                this.getStage().setScene(choixGenre.getScene());
                getStage().show();
                break;
            }
        }
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.SHOW_AJOUT, TypeOrdre.SHOW_MENU,TypeOrdre.SHOW_FILMS,TypeOrdre.SHOW_FILMSGENRE);
        super.setAbonnement(g);
    }
}
