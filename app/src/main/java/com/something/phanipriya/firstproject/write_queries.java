package com.something.phanipriya.firstproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class write_queries extends AppCompatActivity {

    EditText write_query;
    Button addbutton;
    EditText password;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_queries);
        ref= FirebaseDatabase.getInstance().getReference().child("query");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        ref.keepSynced(true);
        write_query=(EditText) findViewById(R.id.w_query);
        addbutton=(Button) findViewById(R.id.qok);
        password=(EditText) findViewById(R.id.querypassword);
        password.setTypeface(Typeface.DEFAULT);


        addbutton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v)
                                         {
                                             postblog();
                                         }
                                     }
        );

    }


    private void postblog()

    {
        String title_val=write_query.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if(!TextUtils.isEmpty(title_val) & !TextUtils.isEmpty(pass))
        {
            DatabaseReference newpost=ref.push();
            newpost.child("querytitle").setValue(title_val);
            newpost.child("password").setValue(pass);
            Toast.makeText(this,"question posted",Toast.LENGTH_LONG ).show();

            startActivity(new Intent(write_queries.this, inside_faq.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        }else {
            Toast.makeText(this,"enter all details", Toast.LENGTH_LONG).show();}
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}