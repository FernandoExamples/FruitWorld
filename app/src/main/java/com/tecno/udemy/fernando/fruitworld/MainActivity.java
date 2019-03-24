package com.tecno.udemy.fernando.fruitworld;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.tecno.udemy.fernando.fruitworld.adapters.GridViewAdapter;
import com.tecno.udemy.fernando.fruitworld.adapters.ListViewAdapter;
import com.tecno.udemy.fernando.fruitworld.models.Fruit;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ArrayList<Fruit> fruits;
    ViewStub stubGrid;
    ViewStub stubList;
    GridView gridView;
    ListView listView;
    GridViewAdapter gridViewAdapter;
    ListViewAdapter listViewAdapter;
    int cont;

    String[][] frutas = {{"Sandia","Mexico"}, {"Mango","Guadalajara"}, {"Piña","Yucatan"}, {"Melon", "Quintana Roo"}, {"Durazno", "El Salvador"}};

    private final byte VIEW_MODE_LIST = 0;
    private final byte VIEW_MODE_GRID = 1;
    private byte currentViewMode = VIEW_MODE_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = 1;
        fruits = new ArrayList<>();

        for(String fruta[] : frutas)
            fruits.add(new Fruit(R.mipmap.ic_launcher, fruta[0], fruta[1]));

        stubGrid = findViewById(R.id.stub_grid);
        stubList = findViewById(R.id.stub_list);
        stubList.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.listView);
        gridView = findViewById(R.id.gridView);

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = (byte)sharedPreferences.getInt("CurrentViewMode", VIEW_MODE_LIST);

        listViewAdapter = new ListViewAdapter(this, fruits);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(myItemClickListener);

        gridViewAdapter = new GridViewAdapter(this, fruits);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(myItemClickListener);

        registerForContextMenu(listView);
        registerForContextMenu(gridView);

        switchView();
    }

    private void switchView(){
        if(currentViewMode == VIEW_MODE_LIST){
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        }else{
            stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }
    }

    private String itemClisckListener(String name){
        for(String fruta[] : frutas)
            if(name.equals(fruta[0]))
                return "This fruit is Already precharged";

        return "You just added this fruit";
    }

    AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String currentName = fruits.get(position).getName();
            String message = itemClisckListener(currentName);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    //creacion de menus-----------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_new_fruit:
                cont++;
                fruits.add(new Fruit(R.mipmap.ic_launcher,("Fruta "+cont), ("Origen "+cont)));
                gridViewAdapter.notifyDataSetChanged();
                listViewAdapter.notifyDataSetChanged();
                break;

            case R.id.mnu_change_layout:
                if(currentViewMode == VIEW_MODE_LIST)
                    currentViewMode = VIEW_MODE_GRID;
                else
                    currentViewMode = VIEW_MODE_LIST;

                switchView();

                //save de preferences
                SharedPreferences preferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("CurrentViewMode", currentViewMode);
                editor.apply();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(fruits.get(info.position).getName());
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.mnu_delete:
                    fruits.remove(menuInfo.position);
                    listViewAdapter.notifyDataSetChanged();
                    gridViewAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
