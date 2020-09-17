package com.example.undercover.modele.gestionJoueurs;

import java.util.Vector;

public class Groupe {

    private int _nbJoueurs;
    private int _nbCivils;
    private int _nbUndercover;
    private int _nbWhite;
    private Vector<Joueur> _listJoueurs;

    public Groupe(int nbJoueurs, int nbCivils, int nbUndercover, int nbWhite, Vector<Joueur> listJoueurs) {
        this._nbJoueurs = nbJoueurs;
        this._nbCivils = nbCivils;
        this._nbUndercover = nbUndercover;
        this._nbWhite = nbWhite;
        this._listJoueurs = listJoueurs;
    }

    public Groupe(int nbJoueurs, int nbCivils, int nbUndercover, int nbWhite) {
        this._nbJoueurs = nbJoueurs;
        this._nbCivils = nbCivils;
        this._nbUndercover = nbUndercover;
        this._nbWhite = nbWhite;
        this._listJoueurs = _listJoueurs;
    }

    public int getNbJoueurs() {
        return _nbJoueurs;
    }

    public void setNbJoueurs(int nbJoueurs) {
        this._nbJoueurs = nbJoueurs;
    }

    public int getNbCivils() {
        return _nbCivils;
    }

    public void setNbCivils(int nbCivils) {
        this._nbCivils = nbCivils;
    }

    public int getNbUndercover() {
        return _nbUndercover;
    }

    public void setNbUndercover(int nbUndercover) {
        this._nbUndercover = nbUndercover;
    }

    public int getNbWhite() {
        return _nbWhite;
    }

    public void setNbWhite(int nbWhite) {
        this._nbWhite = nbWhite;
    }

    public Vector<Joueur> getListJoueurs() {
        return _listJoueurs;
    }

    public void setListJoueurs(Vector<Joueur> listJoueurs) {
        this._listJoueurs = listJoueurs;
    }


    public void ajouterJoueurPointTour(String nomJoueur, int pointTour){
        int nbJoueur = _listJoueurs.size();
        String tempJoueurs = "";
        int tempScore = 0;

        for(int i = 0; i < nbJoueur; i++){
            tempJoueurs = _listJoueurs.get(i).getName();
            if(nomJoueur.equals(tempJoueurs)){
                tempScore = _listJoueurs.get(i).getPoints();
                tempScore += pointTour;
                _listJoueurs.get(i).setPoints(tempScore);
            }
        }
    }
}
