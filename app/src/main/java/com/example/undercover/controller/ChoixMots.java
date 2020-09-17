package com.example.undercover.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.undercover.R;
import com.example.undercover.modele.gestionJoueurs.Groupe;
import com.example.undercover.modele.gestionJoueurs.Joueur;
import com.example.undercover.modele.gestionMots.BankMots;
import com.example.undercover.modele.gestionMots.DuoMots;
import com.example.undercover.modele.popUp.PopUpJoueurs;
import com.example.undercover.modele.popUp.PopUpMot;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class ChoixMots extends AppCompatActivity implements View.OnClickListener{

    private static final int GAME_ACTIVITY_VOTE_JOUEURS = 1312;
    private static final int GAME_ACTIVITY_MINUTER = 420;

    private int _activite_fini;

    private Groupe _groupePartie;

    private TableLayout _contenueCarte;

    private Vector<TableRow> _vectorRow;

    private Vector<Button> _vectorButton;

    private PopUpJoueurs _popUpJoueurs;
    private PopUpMot _popUpMot;

    private int _nbUndercoverDisponible;
    private int _nbWhiteDisponible;
    private int _nbCivilsDisponible;
    private int _nbJoueursDisponible;

    private Vector<Joueur> _vectorJoueurs;

    private TextView _choixCarte;

    private BankMots _banqueDeMots;

    private DuoMots _duoMots;

    private Vector<DuoMots> _templistMots;

    private int _joueursEnCours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_mots);

        _choixCarte = (TextView) findViewById(R.id.activity_choix_nb_joueurs_text_choix_carte);

        _vectorJoueurs = new Vector<>();
        _vectorButton = new Vector<>();
        _vectorRow = new Vector<>();

        _banqueDeMots = new BankMots();

        _groupePartie = this.createGroupe();
        _duoMots = this.createDuoMots();

        _vectorButton.add((Button) findViewById(R.id.activity_choix_nb_joueurs_carte_1));
        _vectorButton.get(0).setOnClickListener(this);
        _vectorButton.get(0).setTag(0);
        _vectorButton.add((Button) findViewById(R.id.activity_choix_nb_joueurs_carte_2));
        _vectorButton.get(1).setOnClickListener(this);
        _vectorButton.get(1).setTag(1);
        _vectorButton.add((Button) findViewById(R.id.activity_choix_nb_joueurs_carte_3));
        _vectorButton.get(2).setOnClickListener(this);
        _vectorButton.get(2).setTag(2);

        _contenueCarte = (TableLayout) findViewById(R.id.activity_contenue_carte);


        _popUpJoueurs = new PopUpJoueurs(this);
        _popUpMot = new PopUpMot(this);
        gestionChoixMots();

    }

    private void gestionChoixMots(){
        final Intent minuteur = new Intent(ChoixMots.this, MinuteurDiscussion.class);
        this.gestionCarte();
        _popUpJoueurs.afficherText("Joueur " + (_joueursEnCours + 1) + " : ");
        _popUpJoueurs.show();
        _popUpJoueurs.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String nomJoueurs;
                String test;
                nomJoueurs = _popUpJoueurs.getNomJoueurs();
                createJoueurs(nomJoueurs);
                test = "Choisi une carte " + (_vectorJoueurs.get(_joueursEnCours)).getName() + " :";
                _choixCarte.setText(test);
            }
        });

        _popUpMot.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                _joueursEnCours++;
                if(_joueursEnCours == _groupePartie.getNbJoueurs()){
                    lancerMinuteur(minuteur);
                    return;
                }
                _popUpJoueurs.afficherText("Joueur " + (_joueursEnCours + 1) + " : ");
                _popUpJoueurs.show();
            }
        });
    }

    private void lancerMinuteur(Intent minuteur) {
        startActivityForResult(minuteur, GAME_ACTIVITY_MINUTER);
    }

    private void lancerVote() {

        Intent voteJoueurs = new Intent(ChoixMots.this, VoteJoueurs.class);
        Bundle bundle = new Bundle();

        bundle.putInt("nbJoueurs", _groupePartie.getNbJoueurs());

        ArrayList<String> joueurs = new ArrayList<String>();
        joueurs.add(0,"default");
        joueurs.add(1,"default");
        String id;
        for(int i = 0; i < _vectorJoueurs.size(); i++){
            ArrayList<String> tempJoueurs = new ArrayList<String>();
            tempJoueurs.add(0,"default");
            tempJoueurs.add(1,"default");
            joueurs.set(0, _vectorJoueurs.get(i).getName());

            if(_vectorJoueurs.get(i).getRole() == Joueur.Role.Undercover){
                joueurs.set(1, "Undercover");
            }
            else if(_vectorJoueurs.get(i).getRole() == Joueur.Role.Civils){
                joueurs.set(1, "Civils");
            }
            else{
                joueurs.set(1, "White");
            }
            id = "joueurs" + i;
            tempJoueurs.set(0, joueurs.get(0));
            tempJoueurs.set(1, joueurs.get(1));
            bundle.putStringArrayList(id, tempJoueurs);
        }
        joueurs.set(0, _duoMots.getMotCivils());
        joueurs.set(1, _duoMots.getMotUndercover());
        bundle.putStringArrayList("duoMots", joueurs);
        voteJoueurs.putExtras(bundle);
        startActivityForResult(voteJoueurs, GAME_ACTIVITY_VOTE_JOUEURS);
    }

    private void gestionCarte() {
        double tempRow;
        int nbRow;
        int nbButton;
        int tempButton;
        tempRow = ((float)_groupePartie.getNbJoueurs()) / 3;
        nbRow = (int) Math.ceil(tempRow) - 1;
        nbButton = _groupePartie.getNbJoueurs();
        for(int i = 0; i < nbRow; i++){
            _vectorRow.add(new TableRow(this));
            _contenueCarte.addView(_vectorRow.get(i));
        }
        for(int j = 3; j < nbButton; j++){
            _vectorButton.add(new Button(this));
            _vectorButton.get(j).setText("CARTE");
            _vectorButton.get(j).setOnClickListener(this);
            _vectorButton.get(j).setTag(j);
            tempButton = (int) Math.floor( (j - 3) / 3);
            _vectorRow.get(tempButton).addView(_vectorButton.get(j));
        }
    }

    private Groupe createGroupe(){
        int nbJoueurs = 5;
        int nbCivils = 3;
        int nbUndercover = 1;
        int nbWhite = 1;

        Bundle groupeText = getIntent().getExtras();

        if (groupeText != null) {
            nbJoueurs = groupeText.getInt("nbJoueurs");
            nbCivils = groupeText.getInt("nbCivils");
            nbUndercover = groupeText.getInt("nbundercover");
            nbWhite = groupeText.getInt("nbWhite");
            _nbUndercoverDisponible = nbUndercover;
            _nbWhiteDisponible = nbWhite;
            _nbCivilsDisponible = nbCivils;
            _nbJoueursDisponible = nbJoueurs;
        }
        return new Groupe(nbJoueurs, nbCivils, nbUndercover, nbWhite);
    }

    private void createJoueurs(String nomJoueurs){
        String mots;
        Joueur.Role role;

        role = definirRole(_nbJoueursDisponible);

        if(role == Joueur.Role.Undercover){
            mots = _duoMots.getMotUndercover();
        }
        else if(role == Joueur.Role.Civils){
            mots = _duoMots.getMotCivils();
        }
        else{
            mots = "Vous êtes Mr. White";
        }
        _vectorJoueurs.add(new Joueur(nomJoueurs, role, mots, 0, "TEST LOGO"));
        _groupePartie.setListJoueurs(_vectorJoueurs);
    }

    private void gestionResultats(Intent data){

        Bundle joueursResultats = data.getExtras().getBundle("BUNDLE_VOTE_JOUEURS");
        ArrayList<String> listResultats;
        String key;
        int nbJoueurs;
        String nomJoueurs;
        int pointTour;


        if (joueursResultats != null) {

            nbJoueurs = joueursResultats.getInt("nbJoueurs");

            for(int i = 0; i < nbJoueurs; i++){
                key = "joueurs" + i;
                listResultats = joueursResultats.getStringArrayList(key);
                nomJoueurs = listResultats.get(0);
                pointTour = Integer.parseInt(listResultats.get(1));
                _groupePartie.ajouterJoueurPointTour(nomJoueurs, pointTour);
            }
        }
    }

    private Joueur.Role definirRole(float nbJoueurs){
        Random random = new Random();
        int nb;
        float probaUndercover;
        float probaWhite;
        float probaCivils;
        nb = random.nextInt(101);

        probaUndercover =(_nbUndercoverDisponible / nbJoueurs) * 100;
        probaWhite = probaUndercover + ((_nbWhiteDisponible / nbJoueurs) * 100);
        probaCivils = probaWhite + ((_nbCivilsDisponible / nbJoueurs) * 100);

        if(nb <= probaUndercover && _nbUndercoverDisponible != 0){
            _nbUndercoverDisponible--;
            _nbJoueursDisponible--;
            return Joueur.Role.Undercover;
        }
        else if(nb > probaUndercover && nb <= probaWhite && _nbWhiteDisponible != 0){
            _nbWhiteDisponible--;
            _nbJoueursDisponible--;
            return Joueur.Role.White;
        }
        else if(nb > probaWhite && nb <= probaCivils && _nbCivilsDisponible != 0){
            _nbCivilsDisponible--;
            _nbJoueursDisponible--;
            return Joueur.Role.Civils;
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        int tagButton = (int)view.getTag();
        _popUpMot.setAfficherMots((_vectorJoueurs.get(_joueursEnCours)).getMots());
        _popUpMot.show();
        _vectorButton.get(tagButton).setText("CHOISI");
        _vectorButton.get(tagButton).setEnabled(false);
    }

    public DuoMots createDuoMots(){
        DuoMots tempDuoMots;
        String motCivils;
        String motUndercover;
        /************************* PLUS TARD DANS CLASSE BANKMOTS ****************************/
        _templistMots = new Vector<>();
        _templistMots.add(new DuoMots("Coca", "Pepsi"));
        _templistMots.add(new DuoMots("PC", "Console"));
        _templistMots.add(new DuoMots("One Piece", "Pokemon"));
        _templistMots.add(new DuoMots("Burger", "Pizza"));
        _banqueDeMots.setListMots(_templistMots);
        /************************************************************************************/

        tempDuoMots = _banqueDeMots.getDuoMots();
        motCivils = tempDuoMots.getMotCivils();
        motUndercover = tempDuoMots.getMotUndercover();
        return new DuoMots(motCivils, motUndercover);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_MINUTER == requestCode && RESULT_OK == resultCode) {
            lancerVote();
        } else if (GAME_ACTIVITY_VOTE_JOUEURS == requestCode && RESULT_OK == resultCode) {
            gestionResultats(data);
            // TODO LANCER CHOITS MOT
            clearVector();
            gestionChoixMots();
        }
    }

    private void clearVector() {
        int nbButton = _vectorButton.size();
        int tempButton;

        _joueursEnCours = 0;

        for(int i = 0; i < nbButton; i++){
            if(i < 3){
                this._vectorButton.get(i).setEnabled(true);
                continue;
            }
            tempButton = (int) Math.floor((i - 3) / 3);
            this._vectorButton.get(i).setEnabled(true);
            _vectorRow.get(tempButton).removeView(_vectorButton.get(i));
        }
        for(int j = nbButton - 1; j > 2; j--){
            _vectorButton.remove(j);
        }
        int nbRow = _vectorRow.size();
        for(int k = 0; k < nbRow; k++){
            this._contenueCarte.removeView(this._vectorRow.get(k));
        }
        _vectorRow.clear();
    }
}

