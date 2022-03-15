package vues;

import controleur.Controleur;
import controleur.EcouteurOrdre;
import controleur.LanceurOrdre;
import controleur.TypeOrdre;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import modeleCreaFilm.GenreFilm;

import java.io.IOException;
import java.util.ArrayList;

public class ChoixGenre extends Vue implements VueInteractive, EcouteurOrdre {

    private Controleur controleur;

    @FXML
    VBox mainVbox;

    @FXML
    ComboBox<GenreFilm> genre;

    public Parent getTop() {
        return mainVbox;
    }

    public static ChoixGenre creerVue(GestionnaireVue gestionnaireVue)  {
        FXMLLoader fxmlLoader = new FXMLLoader(Ajout.class.getResource("choixGenre.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Probleme de construction de vue Choix Du Genre");
        }

        ChoixGenre vue = fxmlLoader.getController();
        gestionnaireVue.ajouterVueInteractive(vue);
        gestionnaireVue.ajouterEcouteurOrdre(vue);
        vue.setScene(new Scene(vue.getTop()));
        return vue;
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void showFilmsGenre(ActionEvent actionEvent) {
        controleur.gotoConsulterFilmGenre();
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.CHARGER_GENRES);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch(e){
            case CHARGER_GENRES:{
                chargerGenres();
                break;
            }
        }
    }

    private void chargerGenres() {
        this.genre.setItems(FXCollections.observableList(new ArrayList<>(controleur.getGenres())));
    }
}
