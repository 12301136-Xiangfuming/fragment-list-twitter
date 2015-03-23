package com.deitel.twittersearches;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.deitel.twittersearches.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "keyWord";
    private static final String ARG_PARAM2 = "tag";
    private static final String SEARCHES = "searches";

    private SharedPreferences savedSearches; // user's favorite searches
    private ArrayList<String> tags; // list of tags for saved searches
    private ArrayAdapter<String> adapter; // binds tags to ListView
    private View root;
    private ListView listView;

    // TODO: Rename and change types of parameters
    private String keyWord;
    private String tag;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param keyWord Parameter 1.
     * @param tag Parameter 2.
     * @return A new instance of fragment FragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentList newInstance(String keyWord, String tag) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, keyWord);
        args.putString(ARG_PARAM2, tag);
        fragment.setArguments(args);

        //addTaggedSearch(keyWord,tag);
        return fragment;
    }



    public FragmentList() {
        // Required empty public constructor
    }


    public void addTaggedSearch(String query, String tag)
    {
        // get a SharedPreferences.Editor to store new tag/query pair
        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
        preferencesEditor.putString(tag, query); // store current search
        preferencesEditor.apply(); // store the updated preferences

        // if tag is new, add to and sort tags, then display updated list
        if (!tags.contains(tag))
        {
            tags.add(tag); // add new tag
            Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
            adapter.notifyDataSetChanged(); // rebind tags to ListView
        }
    }
    public void addRes(String query, String tag){
        this.keyWord = query;
        this.tag = tag;
        if (keyWord.length() > 0 && tag.length() > 0)
        {
            addTaggedSearch(query,tag);
            System.out.println("this is addRes::::::" + query + tag);
            //queryEditText.setText(""); // clear queryEditText
            //tagEditText.setText(""); // clear tagEditText

            /*((InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    tagEditText.getWindowToken(), 0);*/
        }
        else // display message asking user to provide a query and a tag
        {
            // create a new AlertDialog Builder
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this.getActivity());

            // set dialog's message to display
            builder.setMessage(R.string.missingMessage);

            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.OK, null);

            // create AlertDialog from the AlertDialog.Builder
            AlertDialog errorDialog = builder.create();
            errorDialog.show(); // display the modal dialog
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyWord = getArguments().getString(ARG_PARAM1);
            tag = getArguments().getString(ARG_PARAM2);
        }


        //addTaggedSearch(keyWord,tag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_list, container, false);
        savedSearches = getActivity().getSharedPreferences(SEARCHES, getActivity().MODE_PRIVATE);

        // store the saved tags in an ArrayList then sort them
        tags = new ArrayList<String>(savedSearches.getAll().keySet());
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);

        // create ArrayAdapter and use it to bind tags to the ListView
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, tags);
        //setListAdapter(adapter);
        listView = (ListView)root.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);


        return root;
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id)
        {
            // get query string and create a URL representing the search
            /*String tag = ((TextView) view).getText().toString();
            String urlString = getString(R.string.searchURL) +
                    Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

            // create an Intent to launch a web browser
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(urlString));

            startActivity(webIntent);*/ // launches web browser to view results

            String tag = ((TextView) view).getText().toString();
            String urlString = getString(R.string.searchURL) +
                    Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
            mListener.sendUrlToFragWebAndAdd(1,urlString,"","");

        }
    }; // end itemClickListener declaration

    // itemLongClickListener displays a dialog allowing the user to delete
    // or edit a saved search
    AdapterView.OnItemLongClickListener itemLongClickListener =
            new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id)
                {
                    // get the tag that the user long touched
                    final String tag = ((TextView) view).getText().toString();

                    // create a new AlertDialog
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    // set the AlertDialog's title
                    builder.setTitle(
                            getString(R.string.shareEditDeleteTitle, tag));

                    // set list of items to display in dialog
                    builder.setItems(R.array.dialog_items,
                            new DialogInterface.OnClickListener()
                            {
                                // responds to user touch by sharing, editing or
                                // deleting a saved search
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    switch (which)
                                    {
                                        case 0: // share
                                            shareSearch(tag);
                                            break;
                                        case 1: // edit
                                            mListener.sendUrlToFragWebAndAdd(0,"",tag,savedSearches.getString(tag, ""));
                                            break;
                                        case 2: // delete
                                            deleteSearch(tag);
                                            break;
                                    }
                                }
                            } // end DialogInterface.OnClickListener
                    ); // end call to builder.setItems

                    // set the AlertDialog's negative Button
                    builder.setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener()
                            {
                                // called when the "Cancel" Button is clicked
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.cancel(); // dismiss the AlertDialog
                                }
                            }
                    ); // end call to setNegativeButton

                    builder.create().show(); // display the AlertDialog
                    return true;
                } // end method onItemLongClick
            }; // end OnItemLongClickListener declaration

    // allows user to choose an app for sharing a saved search's URL
    private void shareSearch(String tag)
    {
        // create the URL representing the search
        String urlString = getString(R.string.searchURL) +
                Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

        // create Intent to share urlString
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.shareSubject));
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                getString(R.string.shareMessage, urlString));
        shareIntent.setType("text/plain");

        // display apps that can share text
        startActivity(Intent.createChooser(shareIntent,
                getString(R.string.shareSearch)));
    }

    // deletes a search after the user confirms the delete operation
    private void deleteSearch(final String tag)
    {
        // create a new AlertDialog
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());

        // set the AlertDialog's message
        confirmBuilder.setMessage(
                getString(R.string.confirmMessage, tag));

        // set the AlertDialog's negative Button
        confirmBuilder.setNegativeButton( getString(R.string.cancel),
                new DialogInterface.OnClickListener()
                {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel(); // dismiss dialog
                    }
                }
        ); // end call to setNegativeButton

        // set the AlertDialog's positive Button
        confirmBuilder.setPositiveButton(getString(R.string.delete),
                new DialogInterface.OnClickListener()
                {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id)
                    {
                        tags.remove(tag); // remove tag from tags

                        // get SharedPreferences.Editor to remove saved search
                        SharedPreferences.Editor preferencesEditor =
                                savedSearches.edit();
                        preferencesEditor.remove(tag); // remove search
                        preferencesEditor.apply(); // saves the changes

                        // rebind tags ArrayList to ListView to show updated list
                        adapter.notifyDataSetChanged();
                    }
                } // end OnClickListener
        ); // end call to setPositiveButton

        confirmBuilder.create().show(); // display AlertDialog
    } // end method deleteSearch

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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
        //1:send to webView
        //0:edit one tag,display the keyword and tag
        public void sendUrlToFragWebAndAdd(int type,String uri,String tag,String keyword);
    }



}
