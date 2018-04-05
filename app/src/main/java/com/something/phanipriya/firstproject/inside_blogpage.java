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


public class inside_blogpage extends AppCompatActivity {

    EditText blogtitles;
    EditText username;
    EditText branch;
    EditText roll;
    EditText story;
    Button addbutton;
EditText password;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_blogpage);
        database=FirebaseDatabase.getInstance();
        ref= FirebaseDatabase.getInstance().getReference("Blog");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        blogtitles=(EditText) findViewById(R.id.blogtitle);
        username=(EditText)findViewById(R.id.editname);
        branch=(EditText)findViewById(R.id.editbranch);
        roll=(EditText)findViewById(R.id.editroll);
        story=(EditText)findViewById(R.id.description) ;
        addbutton=(Button) findViewById(R.id.blogok);
        password=(EditText) findViewById(R.id.editpassword);
        password.setTypeface(Typeface.DEFAULT);
        addbutton.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View v)
                                         {
                                             postblog();

                                         }
                                     }
        );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void postblog()

    {
        String Title=blogtitles.getText().toString().trim();
        String name=username.getText().toString().trim();
        String Branch=branch.getText().toString().trim();
        String Roll=roll.getText().toString().trim();
        String Story=story.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if(!TextUtils.isEmpty(Title)&!TextUtils.isEmpty(name)&!TextUtils.isEmpty(Branch)&!TextUtils.isEmpty(Roll)&!TextUtils.isEmpty(Story)&!TextUtils.isEmpty(pass))
        {
            String id=ref.push().getKey();
            vinny blog =new vinny(Title,name,Branch,Roll,Story,pass);
            ref.child(id).setValue(blog);
            Toast.makeText(this,"blog posted",Toast.LENGTH_LONG ).show();

            startActivity(new Intent(inside_blogpage.this, blog_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


        }else {
            Toast.makeText(this,"enter all details", Toast.LENGTH_LONG).show();
        }
    }

}


