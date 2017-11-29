package gb.pavelkorzhenko.cryptostockviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class CryptoDetailFragment extends Fragment implements  IConstants{
    protected TextView textViewData;
    protected TextView textViewPair;
    protected Button btnShare;
    protected String cryptodata;
    protected String cryptopair;
    protected boolean[] chkKeyArr = {false, false, false};
    protected long cryptoPairId;

    public CryptoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("DETAIMYLFRAGMET","onDetFragment onCreateView");
        if (savedInstanceState != null) {
            cryptodata = savedInstanceState.getString(KEY_SAVE_CRYPTO_STRING_DATA);
            cryptopair = savedInstanceState.getString(KEY_SAVE_CRYPTO_PAIR);
            cryptoPairId = savedInstanceState.getLong(KEY_SAVE_CRYPTO_PAIR_ID);
        }
        return inflater.inflate(R.layout.fragment_crypto_detail_fragment, container, false);
    }

    public void setCryptoPairId(long id, boolean[] chkKeyArr) {
        this.cryptoPairId = id;
        this.chkKeyArr = chkKeyArr;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            Log.d("DETAIMYLFRAGMET","cryptodata: " + cryptodata + " cryptopair: " + cryptopair);
            textViewPair = view.findViewById(R.id.textCurrenPair);
            textViewData = view.findViewById(R.id.textCurrentEx);
            if (cryptodata == null && cryptopair == null) {
                CryptoPair cryptoData = CryptoPair.cryptopairs[(int) cryptoPairId];
                textViewPair.setText(cryptoData.getName());
                // show result textView
                textViewData.setText(cryptoData.getCurrentExachage().toString());
            } else {
                // show result textView
                textViewPair.setText(cryptopair);
                // show result textView
                textViewData.setText(cryptodata);
            }
            // button share
            btnShare = view.findViewById(R.id.btnShare);
            if (chkKeyArr[1]) {
                btnShare.setVisibility(view.GONE);
            } else {
                btnShare.setOnClickListener(btnShareListener);
            }

            // imageView and graph drawable
            GraphView graph = view.findViewById(R.id.imgGraphID);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 1),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 2),
                    new DataPoint(4, 6)
            });
            if (chkKeyArr[0]) {
                graph.setVisibility(view.GONE);
            } else {
                graph.addSeries(series);
           }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("DETAILMYFRAGMENT","onSaveInstanceState");
        outState.putLong(KEY_SAVE_CRYPTO_PAIR_ID, this.cryptoPairId);
        super.onSaveInstanceState(outState);
    }

    View.OnClickListener btnShareListener = new View.OnClickListener() {
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
}
