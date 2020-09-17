package com.example.undercover.modele.gestionJoueurs;

import com.example.undercover.controller.ChoixMots;

public class Joueur {
    private String _name;
    public enum Role {Civils, Undercover, White};
    private Role _role;
    private String _mots;
    private int _points;
    private String _logo;

    public Joueur(String name, Role role, String mots, int points, String logo) {
        this._name = name;
        this._role = role;
        this._mots = mots;
        this._points = points;
        this._logo = logo;
    }

    public Joueur(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getPoints() {
        return _points;
    }

    public void setPoints(int points) {
        this._points = points;
    }

    public String getLogo() {
        return _logo;
    }

    public void setLogo(String logo) {
        this._logo = logo;
    }

    public Role getRole() {
        return _role;
    }

    public void setRole(Role role) {
        this._role = role;
    }

    public String getMots() {
        return _mots;
    }

    public void setMots(String mots) {
        this._mots = mots;
    }

}
