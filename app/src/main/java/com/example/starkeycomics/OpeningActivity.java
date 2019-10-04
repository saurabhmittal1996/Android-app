package com.example.starkeycomics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;

public class OpeningActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public void falseCognates(View view) {
        Intent intent = new Intent(OpeningActivity.this, FalseCognatesActivity.class);
        startActivity(intent);
    }

    public void dizzyingDoublates(View view) {
        Intent intent = new Intent(OpeningActivity.this, DizzyingDoublatesActivity.class);
        startActivity(intent);
    }

    public void premium(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Bought Membership");
        bundle.putString(FirebaseAnalytics.Param.VALUE, "10");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void logout(View view) {
        reportLogout(view);
        Intent intent = new Intent(OpeningActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void reportLogout(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, ((EditText)findViewById(R.id.editText2)).getText().toString());
        bundle.putString(FirebaseAnalytics.Param.VALUE, "10");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Logout");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
