//package com.example.bookcornerapp_java;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    BottomNavigationView bottomNavigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            Intent intent;
//
//            switch (item.getItemId()){
//
//                case R.id.home:
//                    intent=new Intent(this,HomeFragment.class);
//                    break;
//                case R.id.favorite:
//                    intent=new Intent(this,FavoriteFragment.class);
//                    break;
//                case R.id.cart:
//                    intent=new Intent(this,CartFragment.class);
//                    break;
//                case R.id.profile:
//                    intent=new Intent(this,ProfileFragment.class);
//                    break;
//                default:
//                    intent= new Intent(this,HomeActivity.class);
//            }
//            startActivity(intent);
//
//
//            return true;
//
//
//        });
//
//
//
//
//
//    }
//
//
//}








package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bookcornerapp_java.databinding.ActivityMainBinding;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.services.FirestoreManager;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home:

                    replaceFragment(new HomeFragment());
                    break;
                case R.id.favorite:
                    replaceFragment(new FavoriteFragment());
                    break;
                case R.id.cart:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;

            }

            return true;


        });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

}