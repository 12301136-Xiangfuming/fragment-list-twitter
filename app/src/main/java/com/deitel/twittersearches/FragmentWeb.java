package com.deitel.twittersearches;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.deitel.twittersearches.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWeb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWeb extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "url";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String url;
    // String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment FragmentWeb.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWeb newInstance(String url) {
        FragmentWeb fragment = new FragmentWeb();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentWeb() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        WebView webView = (WebView)view.findViewById(R.id.webHolder);
        //final String mimeType = "";
        //final String encoding = "utf-8";
        //webView.loadData(url,mimeType, encoding);
        webView.loadUrl(url);
        webView.setWebViewClient(new HelloWebViewClient ());
        return view;
    }


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
