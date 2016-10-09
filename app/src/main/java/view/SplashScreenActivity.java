package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.super_movies.br.supermovies.R;


public class SplashScreenActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new CountDownTimer(
                Integer.parseInt(getString(R.string.timeOfSplash)) * 200, 500) {

            @Override
            public void onTick(long millisUn) {

            }

            @Override
            public void onFinish() {
                new CountDownTimer(
                        Integer.parseInt(getString(R.string.timeOfSplash)) * 200, 500) {
                    @Override
                    public void onTick(long millisUn) {

                    }

                    @Override
                    public void onFinish() {
                        startApplication();
                    }

                }.start();
            }
        }.start();


    }


    private void startApplication() {


            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

    }


}
