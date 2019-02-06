package ca.benoitmignault.myfristapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    TextView textEmailFB, textBirthdayFB, textnbFriends;
    Button returnLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        returnLoginPage = findViewById(R.id.button);
        textEmailFB = findViewById(R.id.emailFB);
        textBirthdayFB = findViewById(R.id.birthdayFB);
        textnbFriends = findViewById(R.id.numberFriendsFB);

        textEmailFB.setText(getIntent().getStringExtra("testEmail"));
        textBirthdayFB.setText(getIntent().getStringExtra("test2"));
        textnbFriends.setText(getIntent().getStringExtra("test3"));

        returnLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAffinity(); // permet de fermer l'activité en cours pour caller la prochaine
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

}
