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

public class inside_events extends AppCompatActivity {

    EditText eventtitles;
    EditText events;
    private static final int img=1;
    EditText password;
    Button addbutton;
    Button image;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_events);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        events = (EditText) findViewById(R.id.event);


        ref = FirebaseDatabase.getInstance().getReference().child("events");

        eventtitles = (EditText) findViewById(R.id.eventtitle);

        addbutton = (Button) findViewById(R.id.eventok);
        password=(EditText) findViewById(R.id.eventpassword);
        password.setTypeface(Typeface.DEFAULT);

        ref.keepSynced(true);
        addbutton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             postevent();

                                         }
                                     }
        );
       /* image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,img);
            }
        });*/

    }





  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case img:
            if(resultCode==RESULT_OK){

                Uri uri=data.getData();
                String[]projection={MediaStore.Images.Media.DATA};


                Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(projection[0]);
                String filepath=cursor.getString(columnIndex);
                cursor.close();
                Bitmap yourselectedimage= BitmapFactory.decodeFile(filepath);
                Drawable d=new BitmapDrawable(yourselectedimage);
                iv.setBackground(d);
            }
            break;
        }

    }
*/
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      int id=item.getItemId();
      if(id==android.R.id.home) {
          this.finish();
      }

      return super.onOptionsItemSelected(item);
  }


    private void postevent()

    {
        String Title = eventtitles.getText().toString().trim();
        String name = events.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if (!TextUtils.isEmpty(Title) &!TextUtils.isEmpty(name) &!TextUtils.isEmpty(pass) ) {

            DatabaseReference newpost = ref.push();


            newpost.child("eventtitle").setValue(Title);
            newpost.child("event").setValue(name);
            newpost.child("password").setValue(pass);
            Toast.makeText(this,"event updated",Toast.LENGTH_LONG ).show();
            startActivity(new Intent(inside_events.this, events_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


        }
        else {
            Toast.makeText(this,"enter all details", Toast.LENGTH_LONG).show();}

    }

}


