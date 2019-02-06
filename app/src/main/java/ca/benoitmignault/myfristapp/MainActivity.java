package ca.benoitmignault.myfristapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    Button returnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        returnLoginPage = findViewById(R.id.button);

        returnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish(); // permet de fermer l'activité en cours pour caller la prochaine
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


    }
}
