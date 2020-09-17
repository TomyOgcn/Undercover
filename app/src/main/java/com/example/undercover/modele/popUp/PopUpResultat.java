package com.example.undercover.modele.popUp;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.undercover.R;
import com.example.undercover.modele.gestionMots.DuoMots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class PopUpResultat extends Dialog implements View.OnClickListener {

    private int _idView;

    private TextView _text_view_resultats;

    private TableRow _pop_up_resultats_row;

    private TableLayout _contenueResultats;

    private Vector<TableRow> _vectorRow;

    private Vector<TextView> _affichageRole;

    private Vector<ImageView> _vectorAvatar;

    private Context _thisContext;

    private Vector<ArrayList<String>> _joueurs;
    private Vector<ArrayList<String>> _joueursResultats;

    private Button _buttonOk;

    private String _nomUndercover;
    private String _nomWhite;

    private String _motWhite;

    private DuoMots _duoMots;

    private boolean _isWhiteMots;
    private boolean _isAffichageRole;
    private boolean _isResultats;

    private boolean _isWhite;
    private boolean _isUndercover;

    private EditText _edit_text_mot_white;

    private HashMap<String, String> _voteUndercover;
    private HashMap<String, String> _voteWhite;

    public PopUpResultat(Context context) {
        super(context);
        _thisContext = context;
        _idView = R.layout.layout_pop_up_resultat;
        setContentView(_idView);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        _joueurs = new Vector<>();
        _vectorRow = new Vector<>();
        _affichageRole = new Vector<>();
        _joueursResultats = new Vector<>();
        _vectorAvatar = new Vector<>();

        _text_view_resultats = (TextView) findViewById(R.id.pop_up_resultats_text_view);
        _contenueResultats = (TableLayout) findViewById(R.id.pop_up_resultats_contenue_resultats);
        _buttonOk = (Button) findViewById(R.id.pop_up_resultats_button_ok);
        _buttonOk.setOnClickListener(this);
        _buttonOk.setTag(420);
    }

    public void ajouterJoueurs(HashMap<String, ArrayList<String>> joueurs){
        int nbJoueurs;
        String key;

        nbJoueurs = joueurs.size();
        for(int i = 0; i < nbJoueurs; i++){
            key = "joueurs" + i;
            _joueurs.add(joueurs.get(key));
        }
        this.getRole();
    }

    public void ajouterDuoMots(DuoMots duoMots){
        _duoMots = new DuoMots(duoMots.getMotCivils(), duoMots.getMotUndercover());
        if(_isWhite) {
            affichageWhite();
        }
        else if(_isUndercover){
            affichageRole();
        }
    }

    public void ajouterVote(HashMap<String, String> voteUndercover, HashMap<String, String> voteWhite){
        _voteUndercover = voteUndercover;
        _voteWhite = voteWhite;
    }

    private void getRole(){
        int nbJoueurs;

        nbJoueurs = _joueurs.size();
        for(int i = 0; i < nbJoueurs; i++){
            if(_joueurs.get(i).get(1).equals("Undercover")){
                _nomUndercover = _joueurs.get(i).get(0);
            }
            else if(_joueurs.get(i).get(1).equals("White")){
                _nomWhite = _joueurs.get(i).get(0);
            }
        }
    }

    private void affichageWhite() {
        int i;

        _vectorRow.add((TableRow) findViewById(R.id.pop_up_resultats_row_1));
        _text_view_resultats.setText("White :");

        _buttonOk.setEnabled(false);

        _isWhiteMots = true;
        _isAffichageRole = false;
        _isResultats = false;

        for(i = 0; i < 3; i++) {
            if (i == 0) {
                _affichageRole.add(new TextView(_thisContext));
                _affichageRole.get(i).setText("Le White etait : ");
            } else if (i == 1) {
                _affichageRole.add(new TextView(_thisContext));
                _affichageRole.get(i).setText(_nomWhite);
            } else if (i == 2) {
                _affichageRole.add(new TextView(_thisContext));
                _affichageRole.get(i).setText("Trouve le mot des civils :");
            }
            _vectorRow.get(i).addView(_affichageRole.get(i));

            _vectorRow.add(new TableRow(_thisContext));
            _contenueResultats.addView(_vectorRow.get(i+1));
        }
        _edit_text_mot_white = new EditText(_thisContext);
        _edit_text_mot_white.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _buttonOk.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        _vectorRow.get(i).addView(_edit_text_mot_white);
        _isWhiteMots = true;
        _isAffichageRole = false;
        _isResultats = false;
    }

    private void affichageRole() {

        _text_view_resultats.setText("Roles des joueurs :");

        _vectorRow.add((TableRow) findViewById(R.id.pop_up_resultats_row_1));

        for(int i = 0; i < 10; i++){
            _affichageRole.add(new TextView(_thisContext));
            if(i == 0 && _isUndercover){
                _affichageRole.get(i).setText("L'Undercover etait : ");
            }
            else if(i == 1 && _isUndercover){
                _affichageRole.get(i).setText(_nomUndercover);
            }
            else if(i == 2 && _isUndercover){
                _affichageRole.get(i).setText("Son mot était :");
            }
            else if(i == 3 && _isUndercover){
                _affichageRole.get(i).setText(_duoMots.getMotUndercover());
            }
            else if(i == 4){
                _affichageRole.get(i).setText("Le mot des civils :");
            }
            else if(i == 5){
                _affichageRole.get(i).setText(_duoMots.getMotCivils());
            }
            else if(i == 6 && _isWhite){
                _affichageRole.get(i).setText("Le white était :");
            }
            else if(i == 7 && _isWhite){
                _affichageRole.get(i).setText(_nomWhite);
            }
            else if(i == 8 && _isWhite){
                _affichageRole.get(i).setText("Il à tenter le mot : ");
            }
            else if(i == 9 && _isWhite){
                _affichageRole.get(i).setText(_motWhite);
            }
            else{
                continue;
            }
            _vectorRow.get(i).addView(_affichageRole.get(i));

            _vectorRow.add(new TableRow(_thisContext));
            _contenueResultats.addView(_vectorRow.get(i+1));
        }
        _isWhiteMots = false;
        _isAffichageRole = true;
        _isResultats = false;
    }

    private void affichageResultats() {
        TableLayout nomResultats;
        TableRow nomRow;
        TableRow resultatsRow;
        TextView nom;
        TextView resultats;
        TextView tempsResTotal;
        TextView test;
        int nbJoueurs;

        nbJoueurs = _joueurs.size();

        _text_view_resultats.setText("Resultats du tour :");

        _vectorRow.add((TableRow) findViewById(R.id.pop_up_resultats_row_1));

        for(int i = 0; i < nbJoueurs; i++){

            nomResultats = new TableLayout(_thisContext);
            nomResultats.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            nomRow = new TableRow(_thisContext);
            resultatsRow = new TableRow(_thisContext);
            nom = new TextView(_thisContext);
            resultats = new TextView(_thisContext);
            tempsResTotal = new TextView(_thisContext);

            _vectorAvatar.add(new ImageView(_thisContext));
            _vectorAvatar.get(i).setBackgroundResource(R.drawable.logomft);
            _vectorAvatar.get(i).setLayoutParams(new TableRow.LayoutParams(0, 0));
            _vectorAvatar.get(i).getLayoutParams().height = 100;
            _vectorAvatar.get(i).getLayoutParams().width = 100;

            nom.setText(_joueursResultats.get(i).get(0));
            resultats.setText(_joueursResultats.get(i).get(1));
            nomRow.addView(nom);
            resultatsRow.addView(resultats);
            nomResultats.addView(nomRow);
            nomResultats.addView(resultatsRow);

            tempsResTotal.setText("300");

            _vectorRow.get(i).addView(_vectorAvatar.get(i));
            _vectorRow.get(i).addView(nomResultats);
            // TODO Rajouter score total
            _vectorRow.get(i).addView(tempsResTotal);

            _vectorRow.add(new TableRow(_thisContext));
            _contenueResultats.addView(_vectorRow.get(i+1));
        }
        _isWhiteMots = false;
        _isAffichageRole = false;
        _isResultats = true;
    }

    @Override
    public void onClick(View view) {
        int tagButton = (int)view.getTag();

        if(tagButton == 420){
            if(_isWhiteMots){
                clearVector();
                _motWhite = _edit_text_mot_white.getText().toString();
                affichageRole();
            }
            else if(_isAffichageRole){
                clearVector();
                calculerResultats();
                affichageResultats();
            }
            else if(_isResultats){
                clearVector();
                this.dismiss();
            }
        }
    }

    private void clearVector(){
        int nbObjet = _affichageRole.size();
        int nbRow = _vectorRow.size();
        int tempButton;
        for(int i = 0; i < nbObjet; i++){
            tempButton = (int) Math.floor(i / 3);
            _vectorRow.get(tempButton).removeView(_affichageRole.get(i));
        }
        for(int j = 1; j < nbRow; j++){
            _contenueResultats.removeView(_vectorRow.get(j));
        }
        _vectorRow.clear();
        _affichageRole.clear();
        _nomUndercover = "";
        _nomWhite = "";
        _motWhite = "";
        _duoMots.setMotCivils("");
        _duoMots.setMotUndercover("");
    }

    private void calculerResultats(){
        int nbVoteUndercover = 0;
        int nbVoteCivils;
        int nbJoueurs;
        int nbPoints;
        String nomVote;
        String nomJoueurs;
        ArrayList<String> resultatsJoueurs;
        ArrayList<String> tempRes;

        nbJoueurs = _joueurs.size();
        nbVoteCivils = _joueurs.size();

        resultatsJoueurs = new ArrayList<String>();

        for(int i = 0; i < nbJoueurs; i++){
            nomVote = _voteUndercover.get(_joueurs.get(i).get(0));
            if(nomVote == _nomUndercover){
                nbVoteUndercover++;
                nbVoteCivils--;
            }
        }

        // RESULTATS UNDERCOVER
        for(int j = 0; j < nbJoueurs; j++){
            tempRes = new ArrayList<String>();
            nomVote = _voteUndercover.get(_joueurs.get(j).get(0));
            nomJoueurs = _joueurs.get(j).get(0);

            resultatsJoueurs.add(0, nomJoueurs);
            if(nomVote == _nomUndercover){
                if(nbVoteUndercover == 1){
                    resultatsJoueurs.add(1,"150");
                }
                else{
                    resultatsJoueurs.add(1,"100");
                }
                tempRes.addAll(resultatsJoueurs);
                _joueursResultats.add(tempRes);
            }
            else if(nomJoueurs == _nomUndercover){
                if(nbVoteUndercover <= nbVoteCivils){
                    if(nbVoteUndercover == 0){
                        resultatsJoueurs.add(1, "200");
                        tempRes.addAll(resultatsJoueurs);
                        _joueursResultats.add(tempRes);
                        continue;
                    }
                    resultatsJoueurs.add(1, "100");
                }
                else{
                    resultatsJoueurs.add(1, "0");
                }
                tempRes.addAll(resultatsJoueurs);
                _joueursResultats.add(tempRes);
            }
            else{
                resultatsJoueurs.add(1, "0");
                tempRes.addAll(resultatsJoueurs);
                _joueursResultats.add(tempRes);
            }
            resultatsJoueurs.clear();
        }

        // RESULTATS WHITE
        for(int k = 0; k < nbJoueurs; k++){
            nomVote = _voteWhite.get(_joueurs.get(k).get(0));
            nomJoueurs = _joueurs.get(k).get(0);
            nbPoints = Integer.parseInt(_joueursResultats.get(k).get(1));
            if(nomVote == _nomWhite){
                nbPoints += 50;
                _joueursResultats.get(k).set(1, String.valueOf(nbPoints));
            }
            else if(nomJoueurs == _nomWhite){
                String tempMotCivils = _duoMots.getMotCivils().toUpperCase();
                String tempMotWhite = _motWhite.toUpperCase();
                if(tempMotWhite.equals(tempMotCivils)){
                    nbPoints += 100;
                    _joueursResultats.get(k).set(1, String.valueOf(nbPoints));
                }
            }
        }
    }

    public Vector<ArrayList<String>> getJoueursResultats(){
        return _joueursResultats;
    }

    public void setIsRole(boolean isWhite, boolean isUndercover){
        this._isWhite = isWhite;
        this._isUndercover = isUndercover;
    }
}
