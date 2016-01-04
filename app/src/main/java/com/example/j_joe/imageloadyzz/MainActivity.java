package com.example.j_joe.imageloadyzz;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mainSwipeRefreshLayout;
    private ListView mainListView;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private FloatingActionButton floatActionBtn;

    private Handler handler = new Handler(){

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mainSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe);
        mainListView = (ListView) findViewById(R.id.main_list);
        floatActionBtn = (FloatingActionButton) findViewById(R.id.fab);

        mainListView.setAdapter(new mainAdapter(this,this.getLayoutInflater(),getData()));
        mainSwipeRefreshLayout.setOnRefreshListener(this);
        mainSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
//        floatActionBtn.seto


    }

    private ArrayList<String> getData(){

        arrayList.add("http://img.my.csdn.net/uploads/201508/05/1438760421_2824.jpg");
        arrayList.add("http://img.my.csdn.net/uploads/201508/05/1438760420_7188.jpg");
        arrayList.add("http://img.my.csdn.net/uploads/201508/05/1438760419_4123.jpg");
        arrayList.add("http://img.my.csdn.net/uploads/201508/05/1438760421_2824.jpg");
        arrayList.add("http://img.my.csdn.net/uploads/201508/05/1438760420_7188.jpg");
        arrayList.add("http://img.my.csdn.net/uploads/201508/05/1438760419_4123.jpg");

        return arrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"setting...",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        //TODO
        Toast.makeText(this,"to do...",Toast.LENGTH_SHORT).show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainSwipeRefreshLayout.setRefreshing(false);
            }
        },2000);

    }
}
