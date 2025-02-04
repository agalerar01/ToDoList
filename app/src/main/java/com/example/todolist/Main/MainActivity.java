package com.example.todolist.Main;

import static androidx.core.app.PendingIntentCompat.getActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.todolist.R;
import com.example.todolist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        setSupportActionBar(binding.toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.listaFragment, R.id.busquedaFragment, R.id.configuracionFragment
        ).build();

        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment)).getNavController();

        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);

        binding.bottomNavView.setItemIconTintList(null);

        getSupportActionBar().setTitle("Lista De Tareas");
    }
}