package com.b0dz1o.riskcalc;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class BattleParams extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_battle_params);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView wynik = (TextView) findViewById(R.id.textView3);
        wynik.setMovementMethod(new ScrollingMovementMethod());
        Button calc = (Button) findViewById(R.id.button);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcBattle();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void calcBattle() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        EditText yourText = (EditText) findViewById(R.id.editText);
        EditText hisText = (EditText) findViewById(R.id.editText2);
        TextView wynik = (TextView) findViewById(R.id.textView3);
        wynik.scrollTo(0, 0);
        try {
            final int yourStrenth = Integer.parseInt(yourText.getText().toString());
            final int hisStrenth = Integer.parseInt(hisText.getText().toString());
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Wysyłam do NASA...");
            mDialog.setCancelable(false);
            mDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.hide();
                    TextView wynik = (TextView) findViewById(R.id.textView3);
                    wynik.setText(simulate(yourStrenth, hisStrenth));
                    wynik.scrollTo(0, 0);
                }
            }, 3500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String simulate(int your, int his) {
        String res = "";
        while (your > 0 && his > 0) {
            ArrayList<Integer> yourDiceRes = new ArrayList<Integer>(0);
            ArrayList<Integer> hisDiceRes = new ArrayList<Integer>(0);
            Random rnd = new Random();
            int yourDices = your >= 3 ? 3 : your;
            int hisDices = his >= 2 ? 2 : his;
            for (int i = 0; i < yourDices; ++i) {
                yourDiceRes.add(rnd.nextInt(6) + 1);
            }
            for (int i = 0; i < hisDices; ++i) {
                hisDiceRes.add(rnd.nextInt(6) + 1);
            }
            Collections.sort(yourDiceRes);
            Collections.sort(hisDiceRes);
            Collections.reverse(yourDiceRes);
            Collections.reverse(hisDiceRes);
            if (yourDiceRes.get(0) > hisDiceRes.get(0)) {
                --his;
            } else {
                --your;
            }
            if (yourDiceRes.size() >= 2 && hisDiceRes.size() >= 2) {
                if (yourDiceRes.get(1) > hisDiceRes.get(1)) {
                    --his;
                } else {
                    --your;
                }
            }
            String s = "kości ataku: " + yourDiceRes.toString() +"\nkości obrony: " + hisDiceRes.toString();
            s = s.concat(String.format("\npozostało atakujących: %d, obrońców: %d\n", your, his));
            res = res.concat(s);

        }

        return String.format("WYNIK OSTATECZNY: atakujących: %d, obrońców: %d\n_________\n\n", your, his).concat(res);
//        return res;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("BattleParams Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
