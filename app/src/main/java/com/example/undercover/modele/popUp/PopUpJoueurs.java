package com.example.undercover.modele.popUp;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.undercover.R;

public class PopUpJoueurs extends Dialog implements View.OnClickListener{

    public Integer _idView;

    public String _nomJoueurs;

    private TextView _numero_joueurs;
    private EditText _input_nomJoueurs;
    private Button _import_joueurs;
    private Button _ok_joueurs;

    public PopUpJoueurs(Context context){
        super(context);
        _idView = R.layout.layout_pop_up_joueurs;
        setContentView(_idView);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        _numero_joueurs = (TextView) findViewById(R.id.pop_up_text_numero_joueurs);
        _input_nomJoueurs = (EditText) findViewById(R.id.pop_up_joueurs_name_input);
        _import_joueurs = (Button) findViewById(R.id.pop_up_joueurs_importer);
        _ok_joueurs = (Button) findViewById(R.id.pop_up_joueurs_ok);

        _ok_joueurs.setEnabled(false);

        _input_nomJoueurs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _ok_joueurs.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _ok_joueurs.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        _nomJoueurs = _input_nomJoueurs.getText().toString();
        this.dismiss();
    }

    public String getNomJoueurs(){
        return _nomJoueurs;
    }

    public void afficherText(String text){
        _numero_joueurs.setText(text);
    }
}
