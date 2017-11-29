package gb.pavelkorzhenko.cryptostockviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DetailActivity extends AppCompatActivity implements IConstants{
    protected Button btnBack;
    protected String cryptodata;
    protected String cryptopair;
    protected boolean[] chkKeyArr;
    protected long cryptoPairsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CryptoDetailFragment cryptoDetailFrag = (CryptoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.cryptoDetailFrameId);

        Intent getMainData = getIntent();
        cryptodata = getMainData.getStringExtra(CRYPTODATA);
        cryptopair = getMainData.getStringExtra(CRYPTOPAIR);
        if (savedInstanceState != null) {
            cryptodata = savedInstanceState.getString(KEY_SAVE_CRYPTO_STRING_DATA);
            cryptopair = savedInstanceState.getString(KEY_SAVE_CRYPTO_PAIR);
        }
        chkKeyArr = getMainData.getBooleanArrayExtra(CHECKBOXARRAY);
        cryptoPairsId = getMainData.getLongExtra(CRYPTOPAISID, 0);

        Log.d("DETAIMYLACTIVITY","cryptodata: " + cryptodata + " cryptopair: " + cryptopair);
        Log.d("DETAIMYLACTIVITY","checkboxes keys: " + chkKeyArr[0] + " " + chkKeyArr[1] + " " + chkKeyArr[2]);
        cryptoDetailFrag.setCryptoPairId(cryptoPairsId, chkKeyArr);

        // button back
        btnBack = findViewById(R.id.btnBack);
        if (chkKeyArr[2]) {
            btnBack.setVisibility(View.GONE);
        } else {
            btnBack.setOnClickListener(btnBackListener);
        }
    }

    private void btnBackHandler() {
        Intent backIntent=new Intent();
        String backString = "Pair: " + cryptopair + " = " + cryptodata;
        backIntent.putExtra(BACKINFO, backString);
        Log.d("DETEALACTIVITY", "backString: " + backString);
        setResult(RESULT_OK,backIntent);
        finish();
    }

    OnClickListener btnBackListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            btnBackHandler();
        }
    };

    @Override
    public void onBackPressed() {
        btnBackHandler();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("DETAILMYACTIVITY","onSaveInstanceState");
        outState.putString(KEY_SAVE_CRYPTO_PAIR, cryptopair);
        outState.putString(KEY_SAVE_CRYPTO_STRING_DATA, cryptodata);
        super.onSaveInstanceState(outState);
    }

    /**
     * Override states
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DETAILMYACTIVITY", "Detail onRestart()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DETAILMYACTIVITY", "Detail onStart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DETAILMYACTIVITY", "Detail onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DETAILMYACTIVITY", "Detail onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DETAILMYACTIVITY", "Detail onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DETAILMYACTIVITY", "Detail onDestroy()");
    }
}
