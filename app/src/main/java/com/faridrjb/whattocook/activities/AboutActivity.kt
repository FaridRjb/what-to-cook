package com.faridrjb.whattocook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.faridrjb.whattocook.BuildConfig;
import com.faridrjb.whattocook.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView versionTV = findViewById(R.id.versionTV);
        versionTV.setText("نسخه " + BuildConfig.VERSION_NAME);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.icon_back:
                finish();
                break;
            case R.id.emailBtn:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "faridrajabi7@gmail.com"));
                startActivity(intent);
                break;
            case R.id.linkedinBtn:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/in/faridrjb/"));
                startActivity(intent);
                break;
            case R.id.twitterBtn:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/FaridRjb/"));
                startActivity(intent);
                break;
        }
    }
}