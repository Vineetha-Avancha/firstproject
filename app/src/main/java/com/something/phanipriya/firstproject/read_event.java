package com.something.phanipriya.firstproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class read_event extends AppCompatActivity {

    private String mPost_key=null;
    private DatabaseReference mDatabase;
    private TextView meventtitle;
    private TextView meventdesc;
EditText edit;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_event);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("events");
        mPost_key=getIntent().getExtras().getString("event_id");
        meventtitle=(TextView) findViewById(R.id.eventtitleview);
        meventdesc=(TextView) findViewById(R.id.eventdescpritionview);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title=(String) dataSnapshot.child("eventtitle").getValue();
                String post_desc=(String) dataSnapshot.child("event").getValue();
                meventtitle.setText(post_title);
                meventdesc.setText(post_desc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        meventtitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog.Builder b1 = new AlertDialog.Builder(read_event.this);
                final AlertDialog.Builder b2 = new AlertDialog.Builder(read_event.this);
                final DatabaseReference data = FirebaseDatabase.getInstance().getReference("Blog").child(mPost_key);
                b1.setMessage("Do you want to edit this title?");
                b1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b2.setMessage("Are you sure you want to edit this title? ");
                        b2.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(read_event.this);
                                edit = new EditText(read_event.this);
                                dialog1.setTitle("Edit the Text");
                                dialog1.setView(edit);

                                dialog1.setPositiveButton("Save Text", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String s = String.valueOf(edit.getText());
                                        if(!TextUtils.isEmpty(s))
                                        {
                                            mDatabase.child(mPost_key).child("eventtitle").setValue(s);
                                            Toast.makeText(read_event.this,"Updated",Toast.LENGTH_LONG ).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(read_event.this,"empty fields are not considered", Toast.LENGTH_LONG).show();
                                        }



                                    }
                                });

                                edit.setText(meventtitle.getText());
                                dialog1.show();

                            }
                        });
                        b2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog1 = b2.create();
                        alertDialog1.show();

                    }
                });

                b1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog2 = b1.create();
                alertDialog2.show();


                return false;
            }
        });

        meventdesc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog.Builder b1 = new AlertDialog.Builder(read_event.this);
                final AlertDialog.Builder b2 = new AlertDialog.Builder(read_event.this);
                final DatabaseReference data = FirebaseDatabase.getInstance().getReference("Blog").child(mPost_key);
                b1.setMessage("Do you want to edit this title?");
                b1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        b2.setMessage("Are you sure you want to edit this title? ");
                        b2.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(read_event.this);
                                edit = new EditText(read_event.this);
                                dialog1.setTitle("Edit the Text");
                                dialog1.setView(edit);

                                dialog1.setPositiveButton("Save Text", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String s = String.valueOf(edit.getText());
                                        if(!TextUtils.isEmpty(s))
                                        {
                                            mDatabase.child(mPost_key).child("event").setValue(s);
                                            Toast.makeText(read_event.this,"Updated",Toast.LENGTH_LONG ).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(read_event.this,"empty fields are not considered", Toast.LENGTH_LONG).show();
                                        }



                                    }
                                });

                                edit.setText(meventdesc.getText());
                                dialog1.show();

                            }
                        });
                        b2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog1 = b2.create();
                        alertDialog1.show();

                    }
                });

                b1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog2 = b1.create();
                alertDialog2.show();


                return false;
            }
        });


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




