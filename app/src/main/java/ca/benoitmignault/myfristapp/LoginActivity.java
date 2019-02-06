package ca.benoitmignault.myfristapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static android.content.pm.PackageManager.GET_SIGNATURES;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    ProgressBar loginProgress;
    TextView textEmailFB, textBirthdayFB, textnbFriends;
    ImageView avatarFB;
    CallbackManager callbackManager;
    LoginButton btnLoginFacebook;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        loginProgress = findViewById(R.id.login_progrss);
        loginProgress.setVisibility(View.INVISIBLE);
        textEmailFB = findViewById(R.id.emailFB);
        textBirthdayFB = findViewById(R.id.birthdayFB);
        textnbFriends = findViewById(R.id.numberFriendsFB);
        avatarFB = findViewById(R.id.avatarFB);
        callbackManager = CallbackManager.Factory.create();
        btnLoginFacebook = findViewById(R.id.loginButton2);
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("response",response.toString());
                        getData(object);
                    }
                });

                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
                Toast.makeText(LoginActivity.this,"Nous avons réussi !", Toast.LENGTH_LONG).show();

                finish(); // permet de fermer l'activité en cours pour caller la prochaine
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //if aleady login
        if (AccessToken.getCurrentAccessToken() != null){
            textEmailFB.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

        // Au moment de cliquer sur le bouton, une animation de avancement du login se déclanche
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
            }
        });
        // À ne pas laisser ouvert lorsqu'on veut se connecter ! sinon ça ne marche pas !!!!!!!!!!
        //printKeyHash();
    }

    private void getData(JSONObject object) {
        try{
            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=100&height=100");

            Picasso.with(this).load(profile_picture.toString()).into(avatarFB);

            textEmailFB.setText(object.getString("email"));
            textBirthdayFB.setText(object.getString("birthday"));
            textnbFriends.setText("Friends : "+object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Procedure pour récupérer le hash key code
    private void printKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("ca.benoitmignault.myfristapp", GET_SIGNATURES);
            for(Signature signature: info.signatures ){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
