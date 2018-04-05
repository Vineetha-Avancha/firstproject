package com.something.phanipriya.firstproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class read_blog extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private TextView mblogtitle;
    private TextView mblogdesc;
    private TextView mbname;
    private TextView mbbranch;
    private TextView mbrollnumber;
    EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_blog);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mPost_key = getIntent().getExtras().getString("blog_id");
        mblogdesc = (TextView) findViewById(R.id.blogdescpritionview);
        mblogtitle = (TextView) findViewById(R.id.blogtitleview);
        mbname = (TextView) findViewById(R.id.nameview);
        mbbranch = (TextView) findViewById(R.id.branchview);
        mbrollnumber = (TextView) findViewById(R.id.rollview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("description").getValue();
                String post_name = (String) dataSnapshot.child("name").getValue();
                String post_branch = (String) dataSnapshot.child("branch").getValue();
                String post_rollnumber = (String) dataSnapshot.child("rollnumber").getValue();


                mblogtitle.setText(post_title);
                mblogdesc.setText(post_desc);
                mbname.setText(post_name);
                mbbranch.setText(post_branch);
                mbrollnumber.setText(post_rollnumber);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


}



