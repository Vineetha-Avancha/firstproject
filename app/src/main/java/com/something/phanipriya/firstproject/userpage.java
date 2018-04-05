package com.something.phanipriya.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class userpage extends AppCompatActivity {
    private static Button blogs_btn;
    private static Button faq_btn;
    private static Button events_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        blogButton();
        faqButton();
        eventsButton();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int pid=android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int pid=android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    public void blogButton() {
        blogs_btn = (Button) findViewById(R.id.b_blogs);
        blogs_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent blogintent = new Intent("com.something.phanipriya.firstproject.blog_page");
                        startActivity(blogintent);
                    }
                }
        );
    }
    public void faqButton() {
        faq_btn = (Button) findViewById(R.id.b_faq);
        faq_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent faqintent = new Intent("com.something.phanipriya.firstproject.inside_faq");
                        startActivity(faqintent);
                    }
                }
        );
    }
    public void eventsButton() {
        events_btn = (Button) findViewById(R.id.b_events);
        events_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent eventsintent = new Intent("com.something.phanipriya.firstproject.events_page");
                        startActivity(eventsintent);
                    }
                }
        );
    }



}