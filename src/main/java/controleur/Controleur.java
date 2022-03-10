package controleur;

import facadeCreaFilm.FacadeScreen;
import facadeCreaFilm.GenreNotFoundException;
import facadeCreaFilm.NomFilmDejaExistantException;
import javafx.stage.Stage;
import modeleCreaFilm.Film;
import modeleCreaFilm.GenreFilm;
import vues.*;

import java.util.*;
import java.util.function.Consumer;

public class Controleur implements LanceurOrdre {

    private GestionnaireVue gestionnaireVue;
    private FacadeScreen facadeScreen;
    private Map<TypeOrdre,Collection<EcouteurOrdre>> abonnes;

    public Controleur(FacadeScreen facadeScreen, GestionnaireVue gestionnaireVue) {
        this.facadeScreen = facadeScreen;
        this.gestionnaireVue=gestionnaireVue;
        abonnes= new HashMap<>();
        for(TypeOrdre typeOrdre:TypeOrdre.values()){
            abonnes.put(typeOrdre,new ArrayList<>());
        }
        gestionnaireVue.setControleur(this);
        gestionnaireVue.setAbonnement(this);
    }





    public void run() {
        fireOrdre(TypeOrdre.CHARGER_FILMS);
        fireOrdre(TypeOrdre.CHARGER_GENRES);
        gotoMenu();
    }

    public void gotoFilms() {

        fireOrdre(TypeOrdre.SHOW_FILMS);
    }


    public void gotoConsulter() {

        fireOrdre(TypeOrdre.CHARGER_GENRES);
    }

    public void gotoMenu() { fireOrdre(TypeOrdre.SHOW_MENU);}

    /*
    public void creerFilm(String titre, GenreFilm genre, String realisateur)  {
        if (Objects.isNull(titre)||Objects.isNull(genre)||Objects.isNull(realisateur)||titre.equals("")||realisateur.equals("")){
            ajout.afficherErreur("Erreur saisie","Les champs ne peuvent être vides !");
            showAjout();
        }
        else {
            try {
                facadeScreen.creerFilm(titre, realisateur, genre);
                ajout.viderChamps();
                showMenu();

            } catch (GenreNotFoundException e) {
                ajout.afficherErreur("Erreur de genre", "Genre inexistant !");
                ajout.viderChamps();
                showAjout();
            } catch (NomFilmDejaExistantException e) {
                ajout.afficherErreur("Erreur de film", "Le titre du film existe déjà !");
                ajout.viderChamps();
                showAjout();
            }
        }
    }
    */

    public void gotoAjout() {
        fireOrdre(TypeOrdre.SHOW_AJOUT);
    }

    public Collection<GenreFilm> getGenres() {
        return this.facadeScreen.getAllGenres();
    }

    public Collection<Film> getLesFilms() {
        return  facadeScreen.getAllFilms();
    }

    @Override
    public void abonnement(EcouteurOrdre ecouteurOrdre, TypeOrdre... types) {
        for(TypeOrdre t:types){
            abonnes.get(t).add(ecouteurOrdre);
        }
    }

    @Override
    public void fireOrdre(TypeOrdre e) {
        /*
        abonnes.get(e).forEach(new Consumer<EcouteurOrdre>() {
            @Override
            public void accept(EcouteurOrdre ecouteurOrdre) {
                ecouteurOrdre.traiter(e);
            }
        });
         */
        abonnes.get(e).forEach(ecouteurOrdre -> ecouteurOrdre.traiter(e));
    }
}
