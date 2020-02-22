package com.pixelswordgames.fgdz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixelswordgames.fgdz.Books.BooksList;
import com.pixelswordgames.fgdz.Favorite.FavoriteList;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    private FavoriteList favoriteList;
    private BooksList booksList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getDirectorySize(getCacheDir()) > 200000000)
            trimCache(this);

        fragmentManager = getSupportFragmentManager();
        navigationView = ((BottomNavigationView)findViewById(R.id.mNavigationView));

        favoriteList = new FavoriteList();
        booksList = new BooksList();

        navigationView.inflateMenu(R.menu.navigation_menu);
        navigationView.setSelectedItemId(R.id.action_favorite);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                replaceFragment(menuItem.getItemId());
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        replaceFragment(navigationView.getSelectedItemId());
    }

    private void replaceFragment(int actionId){
        switch (actionId){
            case R.id.action_favorite:
                setTitle(R.string.nav_favorite);
                fragment = favoriteList;
                break;

            case R.id.action_books:
                setTitle(R.string.nav_books);
                fragment = booksList;
        }

        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragment).commit();
    }

    private void trimCache(Context context){
        try {
            File dir = context.getCacheDir();
            if(dir != null && dir.isDirectory())
                deleteDir(dir);
        } catch (Exception e){

        }
    }

    void deleteDir(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory() && fileOrDirectory.listFiles() != null)
            for (File child : fileOrDirectory.listFiles())
                deleteDir(child);

        fileOrDirectory.delete();
    }

    private long getDirectorySize(File dir){
        long size = 0;
        if(dir.listFiles() != null)
            for(File file : dir.listFiles()){
                if(file != null && file.isDirectory())
                    size += getDirectorySize(file);
                else if(file != null && file.isFile())
                    size += file.length();
            }
        return size;
    }
}
