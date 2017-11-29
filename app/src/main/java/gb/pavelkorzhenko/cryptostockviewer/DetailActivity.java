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
    protected TextView textViewData;
    protected TextView textViewPair;
    protected Button btnShare;
    protected Button btnBack;
    protected String cryptodata;
    protected String cryptopair;
    protected boolean[] chkKeyArr;

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

        Log.d("DETAIMYLACTIVITY","cryptodata: " + cryptodata + " cryptopair: " + cryptopair);
        Log.d("DETAIMYLACTIVITY","checkboxes keys: " + chkKeyArr[0] + " " + chkKeyArr[1] + " " + chkKeyArr[2]);

        // show result textView
        textViewPair = findViewById(R.id.textCurrenPair);
        textViewPair.setText(cryptopair);
        // show result textView
        textViewData = findViewById(R.id.textCurrentEx);
        textViewData.setText(cryptodata);

        // button share
        btnShare = findViewById(R.id.btnShare);
        if (chkKeyArr[1]) {
            btnShare.setVisibility(View.GONE);
        } else {
            btnShare.setOnClickListener(btnShareListener);
        }

        // button back
        btnBack = findViewById(R.id.btnBack);
        if (chkKeyArr[2]) {
            btnBack.setVisibility(View.GONE);
        } else {
            btnBack.setOnClickListener(btnBackListener);
        }

        // imageView and graph drawable
        GraphView graph = findViewById(R.id.imgGraphID);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        if (chkKeyArr[0]) {
            graph.setVisibility(View.GONE);
        } else {
            graph.addSeries(series);
        }
    }

    OnClickListener btnShareListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sendShare = new Intent(Intent.ACTION_SEND);
            sendShare.setType("multipart/form-data");
            String sendString = "Pair: " + cryptopair + " = " + cryptodata;
            sendShare.putExtra(sendShare.EXTRA_TEXT, sendString);
            Log.d("DETEALACTIVITY", "sendString: " + sendString);
            String chooserTitle = getString(R.string.chooserTitle);
            Intent chosenIntent = Intent.createChooser(sendShare, chooserTitle);
            try {
                startActivity(chosenIntent);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

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
