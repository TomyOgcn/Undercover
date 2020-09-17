package com.example.undercover.modele.popUp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.undercover.R;
import com.example.undercover.controller.VoteJoueurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class PopUpVote extends Dialog implements View.OnClickListener {

    private TextView _messageVote;

    private int _idView;

    private Vector<Button> _vectorButton;

    private Vector<TableRow> _vectorRow;

    private TableLayout _contenueJoueurs;

    private Button _voteOk;

    private Vector<ArrayList<String>> _joueurs;

    private String _joueurCourant;
    private String _joueurVote;
    private int _indexJoueurCourant = 0;

    private Context _thisContext;

    private HashMap<String, String> _voteUndercover;
    private HashMap<String, String> _voteWhite;

    private boolean _isWhite;
    private boolean _isUndercover;
    private boolean _isVoteWhite;
    private boolean _isVoteUndercover;

    public PopUpVote(Context context) {
        super(context);
        _thisContext = context;
        _idView = R.layout.layout_pop_up_vote;
        setContentView(_idView);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        _isVoteWhite = false;
        _isWhite = false;

        _joueurs = new Vector<>();
        _vectorRow = new Vector<>();
        _vectorButton = new Vector<>();
        _voteUndercover = new HashMap<String, String>();
        _voteWhite = new HashMap<String, String>();

        _vectorRow.add(((TableRow) findViewById(R.id.pop_up_vote_row_1)));

        _messageVote = (TextView) findViewById(R.id.pop_up_text_view);
        _contenueJoueurs = (TableLayout) findViewById(R.id.pop_up_contenue_joueurs);
        _voteOk = (Button) findViewById(R.id.pop_up_button_ok_vote);

        _voteOk.setOnClickListener(this);
        _voteOk.setTag(420);
    }

    public void setJoueurCourant(String joueurCourant){
        _joueurCourant = joueurCourant;
    }

    public void ajouterJoueurs(HashMap<String, ArrayList<String>> joueurs) {
        int nbJoueurs;
        String key;

        nbJoueurs = joueurs.size();
        for(int i = 0; i < nbJoueurs; i++){
            key = "joueurs" + i;
            _joueurs.add(joueurs.get(key));
        }
        this.gestionJoueurs();
    }

    private void gestionJoueurs() {
        double tempRow;
        int nbRow;
        int nbButton;
        int tempButton;
        int nbJoueurs;
        Vector<ArrayList<String>> joueurs;
        joueurs = new Vector<>();
        joueurs.addAll(_joueurs);

        joueurs.remove(_indexJoueurCourant);
        nbJoueurs = joueurs.size();

        tempRow = ((float)nbJoueurs) / 3;
        nbRow = (int) Math.ceil(tempRow) - 1;
        nbButton = nbJoueurs;

        this.gestionTexteVote();

        for(int i = 1; i <= nbRow; i++){
            _vectorRow.add(new TableRow(_thisContext));
            _contenueJoueurs.addView(_vectorRow.get(i));
        }
        for(int j = 0; j < nbButton; j++) {
            _vectorButton.add(new Button(_thisContext));
            _vectorButton.get(j).setText(joueurs.get(j).get(0));
            _vectorButton.get(j).setOnClickListener(this);
            _vectorButton.get(j).setTag(j);
            tempButton = (int) Math.floor(j / 3);
            _vectorRow.get(tempButton).addView(_vectorButton.get(j));
        }
    }

    public void gestionTexteVote(){
        if(_isUndercover){
            preparationVoteUndercover();
            _isVoteUndercover = true;
            _isVoteWhite = false;
        }
        else if(_isWhite){
            preparationVoteWhite();
            _isVoteUndercover = false;
            _isVoteWhite = true;
        }
    }

    @Override
    public void onClick(View view) {
        int tagButton = (int)view.getTag();
        int nbButton;

        if(tagButton == 420){
            if(_isVoteUndercover) {
                _voteUndercover.put(_joueurs.get(_indexJoueurCourant).get(0), _joueurVote);
                if (_isWhite) {
                    this.supprimerButtonVoteUndercover();
                    _isVoteWhite = true;
                    _isVoteUndercover = false;
                    this.preparationVoteWhite();
                    return;
                } else if (_isUndercover) {
                    this.enableButtonVote();
                    _indexJoueurCourant++;
                    this.clearVector();
                    this.dismiss();
                    return;
                }
            }
            else if(_isVoteWhite = true){
                _voteWhite.put(_joueurs.get(_indexJoueurCourant).get(0), _joueurVote);
                this.enableButtonVote();
                _indexJoueurCourant++;
                this.clearVector();
                this.dismiss();
                return;
            }
        }

        nbButton = _vectorButton.size();
        int tempTag;

        for(int i = 0; i < nbButton; i++) {
            tempTag = (int) _vectorButton.get(i).getTag();
            if(tempTag == tagButton) {
                _joueurVote = (String) _vectorButton.get(i).getText();
                setBoutonDesactiver(tempTag);
            }
        }
    }

    private void enableButtonVote() {
        int nbButton = _vectorButton.size();
        for(int j = 0; j < nbButton; j++){
            if(!_vectorButton.get(j).isEnabled()){
                _vectorButton.get(j).setEnabled(true);
                return;
            }
        }
    }

    private void clearVector() {
        this._joueurs.clear();
        int nbButton = _vectorButton.size();
        int tempButton;

        for(int i = 0; i < nbButton; i++){
            tempButton = (int) Math.floor(i / 3);
            this._vectorButton.get(i).setEnabled(true);
            _vectorRow.get(tempButton).removeView(_vectorButton.get(i));
        }
        this._vectorButton.clear();
        int nbRow = _vectorRow.size();
        for(int i = 1; i < nbRow; i++){
            this._contenueJoueurs.removeView(_vectorRow.get(i));
            this._vectorRow.remove(i);
        }
    }

    private void supprimerButtonVoteUndercover() {
        int nbButton = _vectorButton.size();
        int tempButton;
        for(int j = 0; j < nbButton; j++){
            if(!_vectorButton.get(j).isEnabled()){
                tempButton = (int) Math.floor(j / 3);
                _vectorRow.get(tempButton).removeView(_vectorButton.get(j));
                _vectorButton.remove(j);
                return;
            }
        }
    }

    private void preparationVoteWhite() {
        _messageVote.setText("Qui est le mec qui a pas eu de chance? (White)");
    }

    private void preparationVoteUndercover(){
        _messageVote.setText("Qui est ce petit pd d'undercover?");
    }

    private void setBoutonDesactiver(int i){
        int nbButton = _vectorButton.size();
        for(int j = 0; j < nbButton; j++){
            if(i == (int) _vectorButton.get(j).getTag()){
                _vectorButton.get(j).setEnabled(false);
                continue;
            }
            _vectorButton.get(j).setEnabled(true);
        }
    }

    public void setIsWhite(boolean isWhite){
        _isWhite = isWhite;
    }

    public void setIsUndercover(boolean isUndercover){
        _isUndercover = isUndercover;
    }

    public int getIndexJoueurCourant(){
        return _indexJoueurCourant;
    }

    public HashMap<String, String> getVoteUndercover(){
        return this._voteUndercover;
    }

    public HashMap<String, String> getVoteWhite(){
        return this._voteWhite;
    }

}
