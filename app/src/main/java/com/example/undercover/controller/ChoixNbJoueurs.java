package com.example.undercover.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.undercover.R;
import com.example.undercover.modele.gestionJoueurs.Groupe;
import com.example.undercover.modele.gestionJoueurs.Joueur;

import java.util.Vector;

public class ChoixNbJoueurs extends AppCompatActivity implements View.OnClickListener {

    private TextView _text_nbJoueurs;
    private Button _plus_nbJoueurs;
    private Button _moins_nbJoueurs;
    private TextView _choix_nbJoueurs;
    private TextView _text_nbCivils;
    private TextView _choix_nbCivils;
    private TextView _text_nbUndercover;
    private Button _plus_nbUndercover;
    private Button _moins_nbUndercover;
    private TextView _choix_nbUndercover;
    private TextView _text_nbWhite;
    private Button _plus_nbWhite;
    private Button _moins_nbWhite;
    private TextView _choix_nbWhite;
    private Button _lancer_partie;

    private int _nbJoueurs;
    private int _nbCivils;
    private int _nbUndercover;
    private int _nbWhite;
    private int _maxJoueurs = 20;
    private int _minJoueurs = 3;
    private int _minRole = 1;
    private int _minCivils = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_nb_joueurs);
        _text_nbJoueurs = (TextView) findViewById(R.id.activity_choix_nb_joueurs_text_nb_joueurs);
        _text_nbCivils = (TextView) findViewById(R.id.activity_choix_nb_joueurs_text_nb_civils);
        _text_nbUndercover = (TextView) findViewById(R.id.activity_choix_nb_joueurs_text_nb_undercover);
        _text_nbWhite = (TextView) findViewById(R.id.activity_choix_nb_joueurs_text_nb_white);
        _plus_nbJoueurs = (Button) findViewById(R.id.activity_choix_nb_joueurs_bouton_plus_joueurs);
        _moins_nbJoueurs = (Button) findViewById(R.id.activity_choix_nb_joueurs_boutton_moins_joueurs);
        _choix_nbJoueurs = (TextView) findViewById(R.id.activity_choix_nb_joueurs_choix_nb_joueurs);
        _choix_nbCivils = (TextView) findViewById(R.id.activity_choix_nb_joueurs_choix_nb_civils);
        _plus_nbUndercover = (Button) findViewById(R.id.activity_choix_nb_joueurs_bouton_plus_undercover);
        _moins_nbUndercover = (Button) findViewById(R.id.activity_choix_nb_joueurs_boutton_moins_undercover);
        _choix_nbUndercover = (TextView) findViewById(R.id.activity_choix_nb_joueurs_choix_nb_undecover);
        _plus_nbWhite = (Button) findViewById(R.id.activity_choix_nb_joueurs_bouton_plus_white);
        _moins_nbWhite = (Button) findViewById(R.id.activity_choix_nb_joueurs_boutton_moins_white);
        _choix_nbWhite = (TextView) findViewById(R.id.activity_choix_nb_joueurs_choix_nb_white);
        _lancer_partie = (Button) findViewById(R.id.activity_choix_nb_joueurs_bouton_lancer_jeux);

        this.createDefaultGroupe();

        this.displayGroupe();
        _plus_nbJoueurs.setOnClickListener(this);
        _moins_nbJoueurs.setOnClickListener(this);
        _plus_nbUndercover.setOnClickListener(this);
        _moins_nbUndercover.setOnClickListener(this);
        _plus_nbWhite.setOnClickListener(this);
        _moins_nbWhite.setOnClickListener(this);
        _lancer_partie.setOnClickListener(this);

        _plus_nbJoueurs.setTag(1);
        _moins_nbJoueurs.setTag(2);
        _plus_nbUndercover.setTag(3);
        _moins_nbUndercover.setTag(4);
        _plus_nbWhite.setTag(5);
        _moins_nbWhite.setTag(6);
        _lancer_partie.setTag(7);

    }

    private void createDefaultGroupe() {
        this._nbJoueurs = 5;
        this._nbCivils = 3;
        this._nbUndercover = 1;
        this._nbWhite = 1;
    }

    private void displayGroupe() {
        _choix_nbJoueurs.setText(String.valueOf(this._nbJoueurs));
        _choix_nbCivils.setText(String.valueOf(this._nbCivils));
        _choix_nbUndercover.setText(String.valueOf(this._nbUndercover));
        _choix_nbWhite.setText(String.valueOf(this._nbWhite));
    }

    @Override
    public void onClick(View view) {
        int tagButton = (int)view.getTag();

        gestionNbJoueurs(tagButton);
        gestionNbUndercover(tagButton);
        gestionNbWhite(tagButton);
        gestionLancerPartie(tagButton);
        displayGroupe();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void gestionNbJoueurs(int tagButton){

        if(tagButton == 1){
            if(this._nbJoueurs == this._maxJoueurs){
                return;
            }
            this._nbJoueurs++;
            gestionRoles();
            gestionCivils();
        }
        else if(tagButton == 2){
            if(this._nbJoueurs == this._minJoueurs){
                return;
            }
            this._nbJoueurs--;
            gestionRoles();
            gestionCivils();
        }
    }

    private void gestionNbUndercover(int tagButton){
        if(tagButton == 3){
            if(this._nbUndercover < (this._nbCivils-2)) {
                this._nbUndercover += 1;
                gestionCivils();
            }
        }
        if (tagButton == 4) {
            if(this._nbUndercover == 0 || (this._nbUndercover == 0 && this._nbWhite == 1))
                return;
            this._nbUndercover -= 1;
            gestionCivils();
        }
    }

    private void gestionNbWhite(int tagButton){

        if(tagButton == 5){
            if(this._nbCivils - 1 <= this._nbUndercover)
                return;
            this._nbWhite++;
            gestionCivils();
        }
        if (tagButton == 6) {
            if(this._nbWhite!=0)
                this._nbWhite--;
            gestionCivils();
        }
    }

    public void gestionRoles(){
        if(this._nbJoueurs<=4){
            this._nbUndercover = 1;
            this._nbWhite = 0;
        }
        else if(this._nbJoueurs <= 6){
            this._nbUndercover = 1;
            this._nbWhite = 1;
        }
        else if(this._nbJoueurs <= 8){
            this._nbUndercover = 2;
            this._nbWhite = 1;
        }
        else if(this._nbJoueurs <= 10){
            this._nbUndercover = 3;
            this._nbWhite = 1;
        }
        else if(this._nbJoueurs <= 12){
            this._nbUndercover = 3;
            this._nbWhite = 2;
        }
        else if(this._nbJoueurs <= 14){
            this._nbUndercover = 4;
            this._nbWhite = 2;
        }
        else if(this._nbJoueurs <= 16){
            this._nbUndercover = 5;
            this._nbWhite = 2;
        }
        else if(this._nbJoueurs <= 18){
            this._nbUndercover = 5;
            this._nbWhite = 3;
        }
        else if(this._nbJoueurs < 20){
            this._nbUndercover = 6;
            this._nbWhite = 3;
        }
    }

    private void gestionCivils(){
        this._nbCivils = this._nbJoueurs - (this._nbUndercover + this._nbWhite);
    }

    private void gestionLancerPartie(int tagButton) {
        if(tagButton == 7){
            Intent choixMots = new Intent(ChoixNbJoueurs.this, ChoixMots.class);
            Bundle bundle = new Bundle();
            bundle.putInt("nbJoueurs", this._nbJoueurs);
            bundle.putInt("nbCivils", this._nbCivils);
            bundle.putInt("nbundercover", this._nbUndercover);
            bundle.putInt("nbWhite", this._nbWhite);
            choixMots.putExtras(bundle);
            startActivity(choixMots);
        }
    }

    public int getNbJoueurs() {
        return _nbJoueurs;
    }

    public void setNbJoueurs(int nbJoueurs) {
        _nbJoueurs = nbJoueurs;
    }

    public int getNbCivils() {
        return _nbCivils;
    }

    public void setNbCivils(int nbCivils) {
        _nbCivils = nbCivils;
    }

    public int getNbUndercover() {
        return _nbUndercover;
    }

    public void setNbUndercover(int nbUndercover) {
        _nbUndercover = nbUndercover;
    }

    public int getNbWhite() {
        return _nbWhite;
    }

    public void setNbWhite(int nbWhite) {
        _nbWhite = nbWhite;
    }

    public int getMaxJoueurs() {
        return _maxJoueurs;
    }

    public void setMaxJoueurs(int maxJoueurs) {
        _maxJoueurs = maxJoueurs;
    }

    public int getMinJoueurs() {
        return _minJoueurs;
    }

    public void setMinJoueurs(int minJoueurs) {
        _minJoueurs = minJoueurs;
    }

    public int getMinRole() {
        return _minRole;
    }

    public void setMinWhite(int minRole) {
        _minRole = minRole;
    }

    public int getMinCivils() {
        return _minCivils;
    }

    public void setMinCivils(int minCivils) {
        _minCivils = minCivils;
    }
}