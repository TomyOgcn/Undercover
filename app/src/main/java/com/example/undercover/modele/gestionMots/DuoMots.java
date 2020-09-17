package com.example.undercover.modele.gestionMots;

public class DuoMots {

    private String _motCivils;
    private String _motUndercover;

    public DuoMots(String motCivils, String motUndercover) {
        this._motCivils = motCivils;
        this._motUndercover = motUndercover;
    }

    public String getMotCivils() {
        return _motCivils;
    }

    public void setMotCivils(String motCivils) {
        this._motCivils = motCivils;
    }

    public String getMotUndercover() {
        return _motUndercover;
    }

    public void setMotUndercover(String motUndercover) {
        this._motUndercover = motUndercover;
    }
}
