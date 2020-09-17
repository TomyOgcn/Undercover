package com.example.undercover.modele.popUp;

import android.app.Dialog;
import android.content.Context;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.undercover.R;

public class PopUpMot extends Dialog implements View.OnClickListener{


    private int _idView;

    private Button _ok_joueurs_suivant;

    private TextView _afficher_mots;

    public PopUpMot(Context context) {
        super(context);
        _idView = R.layout.layout_pop_up_mot;
        setContentView(_idView);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        _afficher_mots = (TextView) findViewById(R.id.pop_up_mot_affich_mot);
        _ok_joueurs_suivant = (Button) findViewById(R.id.pop_up_mot_joueur_suivant);

        _ok_joueurs_suivant.setOnClickListener(this);
    }

    public void setAfficherMots(String mot){
        _afficher_mots.setText(mot);
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }
}
