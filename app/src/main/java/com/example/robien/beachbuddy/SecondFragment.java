package com.example.robien.beachbuddy;

/**
 * Created by Robien on 5/4/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class SecondFragment extends Fragment {

    /**
     * fields
     */
    private static SecondFragment instance = null;

    /**
     * Create fragment view when paginated.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag_layout, container, false);

        TextView textView = (TextView) v.findViewById(R.id.tvFragSecond);
        textView.setText(getArguments().getString("msg"));
        //textView.setVisibility(View.INVISIBLE);

        WebView web = (WebView) v.findViewById(R.id.addClass);
        web.loadUrl("http://52.25.144.228/addclass.png");
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        return v;
    }

    /**
     * Returns new instance.
     *
     * @param text
     * @return
     */
    public static SecondFragment newInstance(String text){

        if(instance == null){
            // new instance
            instance = new SecondFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {
            return instance;
        }
    }
}
