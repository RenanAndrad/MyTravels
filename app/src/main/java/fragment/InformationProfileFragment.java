package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.super_movies.br.supermovies.R;


public class InformationProfileFragment extends Fragment {

    private TextView txtProfileName, txtProfileEmail;
    private ImageView ivProfileImage;

    private static final String TAG = "Super Movies";
    private String profileName, profileEmail, profileImage;
    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        txtProfileName = (TextView) view.findViewById(R.id.txtProfileName);
        txtProfileEmail = (TextView) view.findViewById(R.id.txtProfileEmail);
        ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);

        loadPreferencess();
        showProfile();
    }

    private void loadPreferencess() {
        try {
            sharedPreferences = this.getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            profileName = sharedPreferences.getString("nameFacebook", "");
            profileEmail = sharedPreferences.getString("emailFacebook", "");
            profileImage = sharedPreferences.getString("picture", "");
        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }

    private void showProfile() {
        try {
            txtProfileName.setText(profileName.toString());
            txtProfileEmail.setText(profileEmail.toString());
            Picasso.with(this.getActivity())
                    .load(profileImage.toString())
                    .into(ivProfileImage);

        } catch (Exception e) {
            Log.e(TAG, "Erro" + e.getMessage());
        }
    }

}