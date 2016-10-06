package view;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.my_travels.br.mytravels.R;


import java.net.URL;

import fragment.InformationProfileFragment;
import fragment.ListCountriesFragment;
import fragment.VisitedCountriesFragment;
import util.Alert;
import util.IAlertaConfirmacao;


public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MY TRAVELS";

    private Alert alert;
    private FragmentTransaction fragmentTransaction;
    private ImageView ivProfileImage;
    private Bitmap fbAvatarBitmap = null;

    private String nameProfile, imageProfile;
    private int typeFragment = 0;

    private SharedPreferences sharedPreferences;
    private final String PREFS_PRIVATE = "PREFS_PRIVATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPreferencess();
        startComponents();

    }


    private void startComponents() {

        alert = new Alert(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView = navigationView.getHeaderView(0);

        TextView txtProfileName = (TextView) hView.findViewById(R.id.txtProfileName);
        ivProfileImage = (ImageView) hView.findViewById(R.id.ivProfileImage);
        txtProfileName.setText(nameProfile);
        saveImageFacebook();


        Bundle extras = null;
        extras = getIntent().getExtras();

        try {
            if (extras != null) {

                typeFragment = extras.getInt("typeFragment");


                switch (typeFragment) {
                    case 1:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, new InformationProfileFragment());
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, new VisitedCountriesFragment());
                        fragmentTransaction.commit();
                        break;
                    case 3:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, new ListCountriesFragment());
                        fragmentTransaction.commit();
                        break;
                }

            } else {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ListCountriesFragment());
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }


    }


    private void loadPreferencess() {
        try {
            sharedPreferences = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            nameProfile = sharedPreferences.getString("nameFacebook", "");
            imageProfile = sharedPreferences.getString("picture", "");
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }

    private void clearPreferencess() {
        try {
            sharedPreferences = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();
            prefsPrivateEditor.putString("nameFacebook", "");
            prefsPrivateEditor.putString("picture", "");
            prefsPrivateEditor.commit();
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }


    private synchronized void saveImageFacebook() {
        AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {

            @Override
            public Bitmap doInBackground(Void... params) {
                URL url = null;

                try {
                    url = new URL(imageProfile);
                    fbAvatarBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return fbAvatarBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {

                fbAvatarBitmap = result;
                if (fbAvatarBitmap != null) {
                    ivProfileImage.setImageBitmap(fbAvatarBitmap);
                }
            }

        };
        task.execute();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.nav_info) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("typeFragment", 1);
            startActivity(intent);

        } else if (id == R.id.nav_visited) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("typeFragment", 2);
            startActivity(intent);
        } else if (id == R.id.nav_list) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("typeFragment", 3);
            startActivity(intent);
        } else if (id == R.id.nav_out) {
            alert.exibirMensagemConfirmacao(MainActivity.this, getString(R.string.notice),
                    getString(R.string.msg_out),
                    getString(R.string.no),
                    getString(R.string.yes),
                    true,
                    new IAlertaConfirmacao() {
                        @Override
                        public void metodoPositivo(DialogInterface dialog,
                                                   int id) {

                            LoginManager.getInstance().logOut();
                            clearPreferencess();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        @Override
                        public void metodoNegativo(DialogInterface dialog, int id) {
                        }
                    });


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

}
