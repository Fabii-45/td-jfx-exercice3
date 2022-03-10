package vues;

import controleur.Controleur;
import controleur.EcouteurOrdre;
import controleur.LanceurOrdre;
import controleur.TypeOrdre;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GestionnaireVue implements EcouteurOrdre,VueInteractive {
    private Stage stage;
    private Controleur controleur;
    private Collection<EcouteurOrdre> ecouteurOrdres;
    private Collection<VueInteractive> vuesInteractives;

    public GestionnaireVue(Stage stage){
        this.stage = stage;
        ecouteurOrdres= new ArrayList<>();
        vuesInteractives= new ArrayList<>();
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur=controleur;
        for(VueInteractive v : vuesInteractives){
            v.setControleur(controleur);
        }
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        ecouteurOrdres.forEach(e->e.setAbonnement(g));
        for(EcouteurOrdre e : ecouteurOrdres){
            e.setAbonnement(g);
        }
    }

    @Override
    public void traiter(TypeOrdre e) {

    }

    public Stage getStage(){return stage;}

    public void ajouterVueInteractive(VueInteractive vue){
        vuesInteractives.add(vue);
    }

    public void ajouterEcouteurOrdre(EcouteurOrdre ecouteur){
        ecouteurOrdres.add(ecouteur);
    }
}
