package com.something.phanipriya.firstproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class inside_faq extends AppCompatActivity {

    private Button writefaq_btn;
    RecyclerView mfaqlist;
    DatabaseReference mDatabase;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("query");
        setContentView(R.layout.activity_inside_faq);

        mfaqlist = (RecyclerView) findViewById(R.id.query_list);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mfaqlist.setLayoutManager(mLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        writefaqButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<faq, faqViewHolder> firebaseRecyclerAdapter1 =new FirebaseRecyclerAdapter<faq, faqViewHolder>(

                faq.class,
                R.layout.faq_row,
                faqViewHolder.class,
                mDatabase


        ) {
            @Override
            protected void populateViewHolder(faqViewHolder viewHolder, faq model, int position) {

                final String post_key= getRef(position).getKey();


                viewHolder.setQuerytitle(model.getQuerytitle());
                final String queryfinal=model.getQuerytitle();
                final String passfinal=model.getPassword();
                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent read_faqIntent=new Intent(inside_faq.this, read_faq.class);
                        read_faqIntent.putExtra("faq_id",post_key);
                        startActivity(read_faqIntent);


                    }
                });

                viewHolder.fView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {




                        PopupMenu popup = new PopupMenu(inside_faq.this,v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.popup_menu, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.editmenu:

                                        final AlertDialog.Builder b3 = new AlertDialog.Builder(inside_faq.this);
                                        edit=new EditText(inside_faq.this);
                                        b3.setTitle("If you are the author of this query, enter your password");
                                        b3.setView(edit);

                                        b3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String s= String.valueOf(edit.getText());
                                                if(s.equals(passfinal))
                                                {
                                                    LinearLayout layout=new LinearLayout(inside_faq.this);
                                                    final AlertDialog.Builder b4 = new AlertDialog.Builder(inside_faq.this);
                                                    layout.setOrientation(LinearLayout.VERTICAL);

                                                    TextView test1=new TextView(inside_faq.this);
                                                    test1.setText("Query");
                                                    layout.addView(test1);

                                                    final EditText querybox=new EditText(inside_faq.this);
                                                    layout.addView(querybox);


                                                    b4.setView(layout);

                                                    b4.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            String a= String.valueOf(querybox.getText());


                                                            if(!TextUtils.isEmpty(a)){
                                                                mDatabase.child(post_key).child("querytitle").setValue(a);


                                                                Toast.makeText(inside_faq.this,"Updated",Toast.LENGTH_LONG ).show();



                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(inside_faq.this,"empty field is not considered", Toast.LENGTH_LONG).show();
                                                            }






                                                        }
                                                    });

                                                    b4.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });


                                                    querybox.setText(queryfinal);

                                                    b4.show();

                                                }
                                                else
                                                {
                                                    finish();
                                                    Toast.makeText(inside_faq.this,"wrong password", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });
                                        b3.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });

                                        edit.setText("");
                                        b3.show();















                                        return true;
                                    case R.id.deletemenu:
                                        final AlertDialog.Builder b5 = new AlertDialog.Builder(inside_faq.this);
                                        edit=new EditText(inside_faq.this);
                                        b5.setTitle("If you are the author of this query, enter your password");
                                        b5.setView(edit);

                                        b5.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String s= String.valueOf(edit.getText());
                                                if(s.equals(passfinal))
                                                {

                                                    final AlertDialog.Builder b6 = new AlertDialog.Builder(inside_faq.this);

                                                    b6.setTitle("Are you sure you want to delete?");
                                                    b6.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {


                                                            mDatabase.child(post_key).removeValue();





                                                        }
                                                    });

                                                    b6.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });



                                                    b6.show();

                                                }
                                                else
                                                {
                                                    finish();
                                                    Toast.makeText(inside_faq.this,"wrong password", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });
                                        b5.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });

                                        edit.setText("");
                                        b5.show();










                                        return true;
                                    case R.id.reportmenu:

                                        final AlertDialog.Builder b10 = new AlertDialog.Builder(inside_faq.this);

                                        b10.setMessage("Do you want to report this query?");
                                        b10.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String[] TO ={"collegediariesteam@gmail.com"};
                                                String[] CC ={""};
                                                Intent emailIntent=new Intent(Intent.ACTION_SEND);
                                                emailIntent.setData(Uri.parse("mailto:"));
                                                emailIntent.setType("message/rfc822");

                                                emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
                                                emailIntent.putExtra(Intent.EXTRA_CC,CC);
                                                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Reporting this post");
                                                emailIntent.putExtra(Intent.EXTRA_TEXT,"*Do not remove this data\nQuery "+post_key+".\nYour request will be confidential.\nWe will respond within 24 hours.\nPlease mention your college and your reason below.*\n\n");
                                                try{
                                                    startActivity(Intent.createChooser(emailIntent,"want to send mail..."));
                                                    finish();
                                                }catch (android.content.ActivityNotFoundException ex){
                                                    Toast.makeText(inside_faq.this, "Mail is not initialized", Toast.LENGTH_SHORT).show();
                                                }






                                            }
                                        });

                                        b10.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });

                                        AlertDialog alertDialog2 = b10.create();
                                        alertDialog2.show();


                                        return true;
                                    default:
                                        return false;
                                }
                            }


                        });


                        return false;
                    }


                });


            }
        };

        mfaqlist.setAdapter(firebaseRecyclerAdapter1);
    }



    public static class faqViewHolder extends RecyclerView.ViewHolder{

        View fView;

        public faqViewHolder(View itemView) {
            super(itemView);
            fView=itemView;


        }
        public void setQuerytitle(String title1){

            TextView faqpost_title=(TextView) fView.findViewById(R.id.faq_title);
            faqpost_title.setText(title1);
        }
    }
    public void writefaqButton() {
        writefaq_btn = (Button) findViewById(R.id.b_write_faq);
        writefaq_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent writeintent = new Intent("com.something.phanipriya.firstproject.write_queries");
                        startActivity(writeintent);
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


}





