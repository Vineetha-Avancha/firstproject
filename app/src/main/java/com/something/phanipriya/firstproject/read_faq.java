package com.something.phanipriya.firstproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class read_faq extends AppCompatActivity {

    private String mPost_key = null;
EditText edit;
    private DatabaseReference mDatabase;
    private DatabaseReference ref1;
    private EditText writeanswer;
    private Button addingbutton;
    RecyclerView answerlist;
    private TextView mfaqtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_faq);
        answerlist = (RecyclerView) findViewById(R.id.answer_list);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        answerlist.setLayoutManager(mLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        writeanswer = (EditText) findViewById(R.id.youranswer);
        addingbutton = (Button) findViewById(R.id.answerok);


        addingbutton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                postblog();
                                            }
                                        }
        );

        mDatabase = FirebaseDatabase.getInstance().getReference().child("query");

        mPost_key = getIntent().getExtras().getString("faq_id");
        ref1 = FirebaseDatabase.getInstance().getReference().child("queries").child("'" + mPost_key + "'");

        mfaqtitle = (TextView) findViewById(R.id.faqtitleview);


        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String faq_title = (String) dataSnapshot.child("querytitle").getValue();
                mfaqtitle.setText(faq_title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }






    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<answerfaq, answerfaqViewHolder> firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<answerfaq, answerfaqViewHolder>(
                answerfaq.class,
                R.layout.answerfaq_row,
                answerfaqViewHolder.class,
                ref1
        ) {
            @Override
            protected void populateViewHolder(answerfaqViewHolder viewHolder, answerfaq model, int position) {
                final String post_key= getRef(position).getKey();
                viewHolder.setAnswer(model.getAnswer());
                final String answerfinal=model.getAnswer();
                viewHolder.fView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        PopupMenu popup = new PopupMenu(read_faq.this,v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.onlyedit, popup.getMenu());
                        popup.show();

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.onlyeditmenu:

                                        final AlertDialog.Builder d=new AlertDialog.Builder(read_faq.this);
                                        d.setTitle("Are you sure you want to edit this answer?");

                                        d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(read_faq.this);
                                                edit=new EditText(read_faq.this);
                                                dialog1.setTitle("Edit the Text");
                                                dialog1.setView(edit);

                                                dialog1.setPositiveButton("Save Text", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        String s= String.valueOf(edit.getText());

                                                        if(!TextUtils.isEmpty(s))
                                                        {
                                                            ref1.child(post_key).child("answer").setValue(s);
                                                            Toast.makeText(read_faq.this,"Updated",Toast.LENGTH_LONG ).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(read_faq.this,"empty field is not considered", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                });



                                                dialog1.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                });


                                               edit.setText(answerfinal);
                                               dialog1.show();








                                            }
                                        });

                                       d.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });



                                        d.show();


                                 default:return false;

                                }


                            }
                        });


                           return false;
                    }


                });

            }
        };
        answerlist.setAdapter(firebaseRecyclerAdapter2);

    }
    public static class answerfaqViewHolder extends RecyclerView.ViewHolder{
        View fView;
        public answerfaqViewHolder(View itemView) {
            super(itemView);
            fView=itemView;
        }
        public void setAnswer(String title1){
            TextView faqpost_title=(TextView) fView.findViewById(R.id.faq_answer);
            faqpost_title.setText(title1);
        }
    }



    private void postblog()

    {
        String answer_val = writeanswer.getText().toString().trim();
        if (!TextUtils.isEmpty(answer_val)) {


            DatabaseReference newpost = ref1.push();

            newpost.child("answer").setValue(answer_val);

            Toast.makeText(this, "answer posted", Toast.LENGTH_LONG).show();
            writeanswer.setText("");

          // startActivity(new Intent(read_faq.this, read_faq.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        } else {
            Toast.makeText(this, "enter answer", Toast.LENGTH_LONG).show();
        }
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
