package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.calmable.databinding.ActivityChallengeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class Challenge extends AppCompatActivity {

    ActivityChallengeBinding binding;
    RecyclerView CategoryList;
    //Toolbar ToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_challenge);

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.content, new ChallengeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content, new LeaderboardsFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content, new WalletFragment());
                        transaction.commit();
                        break;
//                    case 3:
//                        transaction.replace(R.id.content, new ProfileFragment());
//                        transaction.commit();
//                        break;
                }
                return false;
            }
        });


        CategoryList = (RecyclerView) findViewById(R.id.categoryList);
        //ToolBar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(ToolBar);

        ArrayList<CategoryModel> categories = new ArrayList<>();
        categories.add(new CategoryModel("", "Calm Quiz","https://i.pinimg.com/originals/fa/85/e1/fa85e1a23242cb18e246bce75f920969.png"));
        categories.add(new CategoryModel("", "Quiz about Heart","https://cdn.dribbble.com/users/1171903/screenshots/4835633/fire-app-09.gif"));
        categories.add(new CategoryModel("", "Quiz about Stress","https://i.pinimg.com/originals/fa/85/e1/fa85e1a23242cb18e246bce75f920969.png"));
        categories.add(new CategoryModel("", "Quiz about Sleep","https://i.pinimg.com/originals/fa/85/e1/fa85e1a23242cb18e246bce75f920969.png"));

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        CategoryList.setLayoutManager(new GridLayoutManager(this,2));
        CategoryList.setAdapter(adapter);
        //NavigationBar();



    }

    /*private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.challenge);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.relax:
                        startActivity(new Intent(getApplicationContext(), Relax.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.journal:
                        startActivity(new Intent(getApplicationContext(), Journal.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.challenge:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileMain.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.wallet) {
            Toast.makeText(this, "user Prof.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ResultActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}