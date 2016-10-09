package view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.super_movies.br.supermovies.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "Super Movies";

    private Button btnFacebook;

    private String nomeFacebook;
    private String emailFacebook;
    private String idFacebook;
    private Bitmap bitmapFotoFacebook;
    private byte[] byteFotoFacebook;


    private CallbackManager mCallbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private URL url = null;
    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startComponents();
        acoes();
        startFacebook();


    }


    private void startComponents() {


        btnFacebook = (Button) findViewById(R.id.btnFacebook);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    private void startFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());

        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

            }
        };

        accessToken = AccessToken.getCurrentAccessToken();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            sharedPreferences = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();


                                            if (object.has("name")) {
                                                nomeFacebook = object.getString("name");
                                                prefsPrivateEditor.putString("nameFacebook", nomeFacebook);
                                            }
                                            if (object.has("email")) {
                                                emailFacebook = object.getString("email");
                                                prefsPrivateEditor.putString("emailFacebook", emailFacebook);
                                            }

                                            if (object.has("id")) {
                                                idFacebook = object.getString("id");
                                                prefsPrivateEditor.putString("idFacebook", idFacebook);
                                            }

                                            if (object.has("picture")) {
                                                try {

                                                    url = new URL("https://graph.facebook.com/" + idFacebook + "/picture");
                                                    prefsPrivateEditor.putString("picture", url.toString());

                                                } catch (Exception e) {
                                                    Log.e(TAG, e.getMessage());
                                                }
                                            }

                                            prefsPrivateEditor.commit();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);


                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();


                    }


                });

    }

    private void acoes() {
        btnFacebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("teste", "teste");
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile",
                        "email"));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
//        accessTokenTracker.stopTracking();

    }

}
