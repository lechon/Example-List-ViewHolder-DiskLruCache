package com.jml.example.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jml.example.app.adaptor.ExampleListAdapter;
import com.jml.example.app.data.ItemRow;

import java.util.ArrayList;
import java.util.List;

import util.AndroidVolleySingleton;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ListView listView;
        ExampleListAdapter exampleListAdapter;
        List<ItemRow> itemRowList;

        public PlaceholderFragment() {}

        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            listView = (ListView) rootView.findViewById(android.R.id.list);
            listView.setEmptyView(rootView.findViewById(android.R.id.text1));

            return rootView;
        }


        @Override public void onStart(){
            super.onResume();
            setData();
        }


        @Override public void onStop(){
            super.onStop();
            AndroidVolleySingleton.getInstance(getActivity()).cancelAllRequest();
        }
        /**
         * Whatever you have to do to get your data.
         * up to you.
         */
        private void setData(){
            itemRowList =  new ArrayList<ItemRow>();
            ItemRow itemRow = new ItemRow();

            itemRow.setText("Text 1");
            itemRow.setUrl("http://1.bp.blogspot.com/-9q-8bGURqME/TjZ7zR9wzfI/AAAAAAAAJSc/zMxtNvCg3MI/s1600/chicho01.jpg");
            itemRowList.add(itemRow);
            itemRow = new ItemRow();
            itemRow.setText("Text 2");
            itemRow.setUrl("http://vimg.tu.tv/imagenes/videos/c/h/chicho-terremoto-capitulo-25_imagenGrande3.jpg");
            itemRowList.add(itemRow);
            itemRow = new ItemRow();
            itemRow.setText("Text 3");
            itemRow.setUrl("http://lh6.ggpht.com/-UwP5CNcztNU/TyXPl815TPI/AAAAAAAAB2M/Yy_zqoZTDiU/Chicho-Terremoto_thumb4.jpg%3Fimgmax%3D800");
            itemRowList.add(itemRow);
            itemRow = new ItemRow();
            itemRow.setText("Text 4");
            itemRow.setUrl("http://es-pic1.ciao.com/es/8445330.jpg");
            itemRowList.add(itemRow);

            exampleListAdapter =  new ExampleListAdapter(getActivity(),itemRowList);
            listView.setAdapter(exampleListAdapter);

        }
    }

}
