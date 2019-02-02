package ca.benoitmignault.myfristapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.pm.PackageManager.GET_SIGNATURES;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        loginProgress = findViewById(R.id.login_progrss);
        loginProgress.setVisibility(View.INVISIBLE);

        printKeyHash();

        CallbackManager callbackManager = CallbackManager.Factory.create();


        // Au moment de cliquer sur le bouton, une animation de avancement du login se déclanche
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgress.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                //Toast.makeText(this,"allo", Toast.LENGTH_LONG).show();
            }
        });

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
