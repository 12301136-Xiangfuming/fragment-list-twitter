package com.deitel.twittersearches;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.deitel.twittersearches.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAddBox.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentAddBox extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText queryEditText; // EditText where user enters a query
    private EditText tagEditText; // EditText where user tags a query
    private ImageButton saveButton;


    public FragmentAddBox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_box, container, false);

        queryEditText = (EditText)view.findViewById(R.id.queryEditText);
        tagEditText = (EditText)view.findViewById(R.id.tagEditText);

        saveButton = (ImageButton)view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.sendSearAndTagToFragList(queryEditText.getText().toString(),
                        tagEditText.getText().toString());

                queryEditText.setText("");
                tagEditText.setText("");
            }
        });


        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //System.out.println("onButtonPress method in FragmentAddBox");
            mListener.sendSearAndTagToFragList(queryEditText.getText().toString(),
                    tagEditText.getText().toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void editEditText(String tag,String keyword){
        tagEditText.setText(tag);
        queryEditText.setText(keyword);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void sendSearAndTagToFragList(String search,String tag);
    }

}
