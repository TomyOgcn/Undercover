package com.example.undercover.modele.gestionMots;

import java.util.Random;
import java.util.Vector;

public class BankMots {

    private Vector<DuoMots> _listMots;

    public BankMots() {
    }

    public BankMots(Vector<DuoMots> listMots) {
        this._listMots = listMots;
    }

    public Vector<DuoMots> getListMots() {
        return _listMots;
    }

    public void setListMots(Vector<DuoMots> listMots) {
        this._listMots = listMots;
    }

    public int getTailleListeMots(){
        return this._listMots.size();
    }

    public DuoMots getDuoMots(){
        if(this._listMots.isEmpty())
            return null;
        Random random = new Random();
        int nb;
        nb = random.nextInt(this.getTailleListeMots());
        return this._listMots.get(nb);
    }
}
