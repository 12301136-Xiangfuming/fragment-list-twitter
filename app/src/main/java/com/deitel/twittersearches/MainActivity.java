// MainActivity.java
// Manages your favorite Twitter searches for easy  
// access and display in the device's web browser
package com.deitel.twittersearches;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;


public class MainActivity extends Activity
        implements FragmentList.OnFragmentInteractionListener,FragmentAddBox.OnFragmentInteractionListener
{
   // name of SharedPreferences XML file that stores the saved searches 
   private static final String SEARCHES = "searches";
   


   private FragmentList listfragm;
   private  FragmentAddBox addfragm;

    // SOME Changes _ called when MainActivity is first created
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);


       FragmentTransaction ft = getFragmentManager().beginTransaction();

       addfragm = new FragmentAddBox();
       listfragm = new FragmentList();


       ft.add(R.id.addBoxHolder,addfragm);
       ft.add(R.id.listHolder,listfragm);

       ft.commit();

   } // end method onCreate

   // ADDED to set up the ListFragment
   //public ArrayAdapter<String> getAdapter(){return adapter;}
   public void sendUrlToFragWebAndAdd(int type,String url,String tag,String keyword){
       if(type == 1){
           getFragmentManager().beginTransaction()
                   .replace(R.id.listHolder,FragmentWeb.newInstance(url))
                   .addToBackStack(null)
                   .commit();
       }else{
            addfragm.editEditText(tag,keyword);
       }

   };

    public void sendSearAndTagToFragList(String search,String tag){
        //System.out.println("this is main sendSear......!!!!!!");
          listfragm.addRes(search,tag);
    };
} // end class MainActivity


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
