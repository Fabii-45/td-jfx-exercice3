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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeleCreaFilm.GenreFilm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Ajout extends Vue implements VueInteractive, EcouteurOrdre {
    private Controleur controleur;

    @FXML
    VBox mainVbox;

    @FXML
    TextField titre;
    @FXML
    ComboBox<GenreFilm> genre;
    @FXML
    TextField realisateur;

    public Parent getTop() {
        return mainVbox;
    }


    public static Ajout creerVue(GestionnaireVue gestionnaireVue)  {
        FXMLLoader fxmlLoader = new FXMLLoader(Ajout.class.getResource("ajout.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Probleme de construction de vue Formulaire");
        }

        Ajout vue = fxmlLoader.getController();
        gestionnaireVue.ajouterVueInteractive(vue);
        gestionnaireVue.ajouterEcouteurOrdre(vue);
        vue.setScene(new Scene(vue.getTop()));
        return vue;
    }

    public void gotomenu(ActionEvent actionEvent) {
        controleur.gotoMenu();
    }

    public  void chargerGenres(){
        this.genre.setItems(FXCollections.observableList(new ArrayList<>(controleur.getGenres())));
    }
    @Override
    public void setControleur(Controleur controleur) {

        this.controleur = controleur;

    }

    public void creerFilm(ActionEvent actionEvent) {
        controleur.creerFilm(titre.getText(), genre.getSelectionModel().getSelectedItem(), realisateur.getText());
    }


    public void viderChamps() {
        titre.setText("");
        genre.getSelectionModel().clearSelection();
        realisateur.setText("");
    }

    public void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message);
        alert.setTitle(titre);
        alert.showAndWait();

    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.CHARGER_GENRES,TypeOrdre.NOM_EXISTANT,TypeOrdre.GENRE_INEXISTANT,TypeOrdre.CHAMPS_VIDE,TypeOrdre.VIDER_CHAMPS);
        //elle ??coute d'autres ordres

    }

    @Override
    public void traiter(TypeOrdre e) {
        switch(e){
            case CHARGER_GENRES:{
                chargerGenres();
                break;
            }
            case VIDER_CHAMPS:{
                viderChamps();
                break;
            }
            case NOM_EXISTANT:{
                afficherErreur("Nom d??j?? existant", "Le nom du film existe d??j??");
                break;
            }
            case GENRE_INEXISTANT:{
                afficherErreur("Genre non existant", "Le genre du film est inconnu");
                break;
            }
            case CHAMPS_VIDE:{
                afficherErreur("Erreur saisie","Les champs ne peuvent ??tre vides !");
                break;
            }
        }
    }
}
