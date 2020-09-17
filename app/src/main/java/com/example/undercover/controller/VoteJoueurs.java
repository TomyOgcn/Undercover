package com.example.undercover.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.undercover.R;
import com.example.undercover.modele.gestionMots.DuoMots;
import com.example.undercover.modele.popUp.PopUpJoueurs;
import com.example.undercover.modele.popUp.PopUpResultat;
import com.example.undercover.modele.popUp.PopUpVote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class VoteJoueurs extends AppCompatActivity implements View.OnClickListener{

    public static final String BUNDLE_VOTE_JOUEURS = "BUNDLE_VOTE_JOUEURS";

    private TextView _text_vote_joueurs;
    private Button _passer_au_vote;

    private HashMap<String, ArrayList<String>> _joueurs;
    private Vector<ArrayList<String>> _joueursResultats;

    private String _phraseJoueurs;

    private int _nbJoueurs;

    private PopUpVote _popUpVote;

    private PopUpResultat _popUpResultat;

    private boolean _isWhite;
    private boolean _isUndercover;

    private DuoMots _duoMots;

    private HashMap<String, String> _voteUndercover;
    private HashMap<String, String> _voteWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_joueurs);

        _isUndercover = false;
        _isWhite = false;

        _text_vote_joueurs = (TextView) findViewById(R.id.activity_vote_joueurs_text_view);
        _passer_au_vote = (Button) findViewById(R.id.activity_vote_joueurs_passer_au_vote);

        _joueurs = new HashMap<String, ArrayList<String>>();
        _joueursResultats = new Vector<>();

        this.recupererJoueurs();

        _popUpVote = new PopUpVote(this);
        _popUpResultat = new PopUpResultat(this);

        this.afficherJoueurs();


        _popUpVote.setIsWhite(_isWhite);
        _popUpVote.setIsUndercover(_isUndercover);
        _popUpVote.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(_popUpVote.getIndexJoueurCourant() ==  _nbJoueurs){
                    _popUpResultat.ajouterJoueurs(_joueurs);
                    _popUpResultat.setIsRole(_isWhite, _isUndercover);
                    _popUpResultat.ajouterDuoMots(_duoMots);
                    _voteUndercover = _popUpVote.getVoteUndercover();
                    _voteWhite = _popUpVote.getVoteWhite();
                    _popUpResultat.ajouterVote(_voteUndercover, _voteWhite);
                    _popUpResultat.show();
                    return;
                }
                afficherJoueurs();
            }
        });
        _passer_au_vote.setOnClickListener(this);

        _popUpResultat.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                _joueursResultats.addAll(_popUpResultat.getJoueursResultats());
                envoieResultats();
            }
        });
    }

    private void envoieResultats() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        int nbJoueurs = _joueurs.size();
        String id = "joueurs";
        bundle.putInt("nbJoueurs", nbJoueurs);

        for(int i = 0; i < nbJoueurs; i++){
            id = "joueurs" + i;
            bundle.putStringArrayList(id, _joueursResultats.get(i));
        }
        intent.putExtra(BUNDLE_VOTE_JOUEURS, bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void recupererJoueurs() {

        Bundle groupeText = getIntent().getExtras();
        ArrayList<String> listJoueurs;
        String key;
        String role;
        String motUndercover;
        String motCivils;

        if (groupeText != null) {

            _nbJoueurs = groupeText.getInt("nbJoueurs");

            for(int i = 0; i <_nbJoueurs; i++){
                key = "joueurs" + i;
                listJoueurs = groupeText.getStringArrayList(key);
                role = (String) listJoueurs.get(1);
                if(role.equals("White")){
                    _isWhite = true;
                }
                else if(role.equals("Undercover")){
                    _isUndercover = true;
                }
                _joueurs.put(key, listJoueurs);
            }
        }
        listJoueurs = groupeText.getStringArrayList("duoMots");
        motCivils = listJoueurs.get(0);
        motUndercover = listJoueurs.get(1);
        _duoMots = new DuoMots(motCivils, motUndercover);
    }

    @Override
    public void onClick(View view) {
        _popUpVote.setJoueurCourant(_joueurs.get("joueurs0").get(0));
        _popUpVote.ajouterJoueurs(_joueurs);
        _popUpVote.show();
    }

    public void afficherJoueurs(){
        _phraseJoueurs = _joueurs.get("joueurs" + _popUpVote.getIndexJoueurCourant()).get(0) + " à toi de voter !";
        _text_vote_joueurs.setText(_phraseJoueurs);
    }
}