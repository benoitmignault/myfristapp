package ca.benoitmignault.myfristapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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
}
