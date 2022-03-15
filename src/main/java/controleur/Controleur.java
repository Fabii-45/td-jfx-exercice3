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
        this.fireOrdre(TypeOrdre.CHARGER_GENRES);
        this.gotoMenu();
    }

    public void gotoFilms() {

        fireOrdre(TypeOrdre.SHOW_FILMS);
    }


    public void gotoConsulter() {
        fireOrdre(TypeOrdre.CHARGER_FILMS);
        fireOrdre(TypeOrdre.SHOW_FILMS);
    }

    public void gotoMenu() { fireOrdre(TypeOrdre.SHOW_MENU);}


    public void creerFilm(String titre, GenreFilm genre, String realisateur)  {
        if (Objects.isNull(titre)||Objects.isNull(genre)||Objects.isNull(realisateur)||titre.equals("")||realisateur.equals("")){
            fireOrdre(TypeOrdre.CHAMPS_VIDE);
            fireOrdre(TypeOrdre.SHOW_AJOUT);
        }
        else {
            try {
                facadeScreen.creerFilm(titre, realisateur, genre);
                fireOrdre(TypeOrdre.CHARGER_FILMS);
                fireOrdre(TypeOrdre.VIDER_CHAMPS);
                fireOrdre(TypeOrdre.SHOW_MENU);

            } catch (GenreNotFoundException e) {
                fireOrdre(TypeOrdre.VIDER_CHAMPS);
                fireOrdre(TypeOrdre.GENRE_INEXISTANT);
                fireOrdre(TypeOrdre.SHOW_AJOUT);
            } catch (NomFilmDejaExistantException e) {
                fireOrdre(TypeOrdre.VIDER_CHAMPS);
                fireOrdre(TypeOrdre.NOM_EXISTANT);
                fireOrdre(TypeOrdre.SHOW_AJOUT);
            }
        }
    }




    public void gotoAjout() {
        fireOrdre(TypeOrdre.SHOW_AJOUT);
    }

    public Collection<GenreFilm> getGenres() {
        return this.facadeScreen.getAllGenres();
    }

    public Collection<Film> getLesFilms() {
        return  facadeScreen.getAllFilms();
    }

    public Collection<Film> getLesFilmsDuGenre() throws GenreNotFoundException {
        String genre =
        return facadeScreen.getFilmsDuGenre(genre);
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

    public void gotoConsulterFilmGenre() {
        fireOrdre(TypeOrdre.SHOW_FILMSGENRE);
    }
}
