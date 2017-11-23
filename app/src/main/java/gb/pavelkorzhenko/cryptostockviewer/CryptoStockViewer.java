package gb.pavelkorzhenko.cryptostockviewer;

/**
 * @done 1. Изменить приложение таким образом, чтобы при возврате из второй Activity в первую мы получали
 * @done    результат. Какой результат и куда его выводить — решите самостоятельно. Цель: научиться не только
 * @done    передавать данные через Intent, но и получать какой-то результат;
 * @see  line 99
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class CryptoStockViewer extends AppCompatActivity implements OnClickListener,Spinner.OnItemSelectedListener, IConstants {
    protected Spinner spinnerViewPairs;
    protected Button btnGetPairsData;
    protected Resources cryptoResData;
    protected String[] strCryptoPairData;
    protected Intent detailIntent;
    protected EditText backEditText;
    protected CheckBox chkNoGraph, chkNoShare, chkNoBack;
    protected boolean[] chkKeysArr = {false, false, false};

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_stock_viewer);

        if (savedInstanceState != null) {
            strCryptoPairData = savedInstanceState.getStringArray(KEY_SAVE_CRYPTO_PAIR_DATA);
        }
        // crypto pairs data array
        cryptoResData = getResources();
        strCryptoPairData = cryptoResData.getStringArray(R.array.cryptoData);

        // spinner and listener
        spinnerViewPairs= findViewById(R.id.spinnerPairChoose);
        spinnerViewPairs.setOnItemSelectedListener(this);

        // checkboxes
        chkNoGraph = findViewById(R.id.chkNoGraph);
        chkNoShare = findViewById(R.id.chkNoShare);
        chkNoBack = findViewById(R.id.chkNoBack);

        // loading setting
        LoadPreferences();


        // button and listener
        btnGetPairsData = findViewById(R.id.getBtnPairs);
        btnGetPairsData.setOnClickListener(this);

        // back edit tet
        backEditText = findViewById(R.id.backEditText);
        //imageGraph = findViewById((R.id.imgGraphID));
        //imageGraph.setBackground(new GraphCharsDrawable());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("MAINMYACTIVITY","onSaveInstanceState");
        outState.putStringArray(KEY_SAVE_CRYPTO_PAIR_DATA, strCryptoPairData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        long cryptoPairsID = spinnerViewPairs.getSelectedItemId();
        int cryptoIndex = (int) (cryptoPairsID % strCryptoPairData.length);
        if (chkNoGraph.isChecked()) { chkKeysArr[0] = true; } else { chkKeysArr[0] = false; }
        if (chkNoShare.isChecked()) { chkKeysArr[1] = true; } else { chkKeysArr[1] = false; }
        if (chkNoBack.isChecked()) {  chkKeysArr[2] = true; } else { chkKeysArr[2] = false; }
        // save preferences
        SavePreferences(KEY_SPINNER_INDEX, (int) cryptoPairsID);
        SavePreferences(KEY_CHK_NOGRAPH, chkKeysArr[0] ? 1 : 0);
        SavePreferences(KEY_CHK_NOSHARE, chkKeysArr[1] ? 1 : 0);
        SavePreferences(KEY_CHK_NOBACK,  chkKeysArr[2] ? 1 : 0);

        Log.d("MAINMYLACTIVITY","chkbox keys: "  + chkKeysArr[0] + " " + chkKeysArr[1] + " " + chkKeysArr[2]);

        detailIntent = new Intent(CryptoStockViewer.this, DetailActivity.class);
        detailIntent.putExtra(CRYPTODATA, strCryptoPairData[cryptoIndex]);
        detailIntent.putExtra(CRYPTOPAIR, spinnerViewPairs.getSelectedItem().toString());
        detailIntent.putExtra(CHECKBOXARRAY, chkKeysArr);

        Log.d("MAINMYACTIVITY","cryptodata: " + strCryptoPairData[cryptoIndex] + " cryptopair:"
                    + spinnerViewPairs.getSelectedItem().toString());
        startActivityForResult(detailIntent, guardCode);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // l = id of element , i = position
        long cryptoPairsID = l;
        // id of element of arrayData % arrayData.length
        int cryptoIndex = (int) (cryptoPairsID % strCryptoPairData.length);
        // check checkboxes
        if (chkNoGraph.isChecked()) { chkKeysArr[0] = true; } else { chkKeysArr[0] = false; }
        if (chkNoShare.isChecked()) { chkKeysArr[1] = true; } else { chkKeysArr[1] = false; }
        if (chkNoBack.isChecked()) {  chkKeysArr[2] = true; } else { chkKeysArr[2] = false; }
        // save preferences
        SavePreferences(KEY_SPINNER_INDEX, (int) cryptoPairsID);
        SavePreferences(KEY_CHK_NOGRAPH, chkKeysArr[0] ? 1 : 0);
        SavePreferences(KEY_CHK_NOSHARE, chkKeysArr[1] ? 1 : 0);
        SavePreferences(KEY_CHK_NOBACK,  chkKeysArr[2] ? 1 : 0);

        Log.d("MAINMYLACTIVITY","chkbox keys: "  + chkKeysArr[0] + " " + chkKeysArr[1] + " " + chkKeysArr[2]);

        detailIntent = new Intent(CryptoStockViewer.this, DetailActivity.class);
        detailIntent.putExtra(CRYPTODATA, strCryptoPairData[cryptoIndex]);
        detailIntent.putExtra(CRYPTOPAIR, spinnerViewPairs.getSelectedItem().toString());
        detailIntent.putExtra(CHECKBOXARRAY, chkKeysArr);

        Log.d("MAINMYACTIVITY","cryptodata: " + strCryptoPairData[cryptoIndex] + " cryptopair:"
                + spinnerViewPairs.getSelectedItem().toString());
        startActivityForResult(detailIntent, guardCode);
//        textViewData.setText(strCryptoPairData[cryptoIndex]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // @done TODO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == guardCode){
            if(resultCode == RESULT_OK){
                String backString = data.getStringExtra(BACKINFO);
                Log.d("MAINMYACTIVITY","backString: " + backString);
                backEditText.setText(backString);
            }

        }
    }

    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        // spinner
        int savedSpinnerID = sharedPreferences.getInt(KEY_SPINNER_INDEX, 0);
        spinnerViewPairs.setSelection(savedSpinnerID);

        // load preferences for checkboxes
        if (sharedPreferences.getInt(KEY_CHK_NOGRAPH, 0) == 1) {
            chkNoGraph.setChecked(true);
        }
        if (sharedPreferences.getInt(KEY_CHK_NOSHARE, 0) == 1) {
            chkNoShare.setChecked(true);
        }
        if (sharedPreferences.getInt(KEY_CHK_NOBACK, 0) == 1) {
            chkNoBack.setChecked(true);
        }
    }

    /**
     * Override states
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MAINMYACTIVITY", "Main onRestart()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MAINMYACTIVITY", "Main onStart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MAINMYACTIVITY", "Main onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MAINMYACTIVITY", "Main onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MAINMYACTIVITY", "Main onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MAINMYACTIVITY", "Main onDestroy()");
    }
}
