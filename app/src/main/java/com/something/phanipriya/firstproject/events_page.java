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

public class events_page extends AppCompatActivity {
    private static Button ev_btn;
    RecyclerView meventlist;
    DatabaseReference mDatabase;
EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_page);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        meventlist = (RecyclerView) findViewById(R.id.event_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        meventlist.setLayoutManager(mLayoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        evButton();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<events, eventViewHolder> firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<events, eventViewHolder>(

                events.class,
                R.layout.event_row,
                eventViewHolder.class,
                mDatabase


        ) {
            @Override
            protected void populateViewHolder(eventViewHolder viewHolder, events model, int position) {



                final String post_key = getRef(position).getKey();


                viewHolder.setEventtitle(model.getEventtitle());
                viewHolder.setEvent(model.getEvent());
                final String titleeventfinal=model.getEventtitle();
                final String eventfinal=model.getEvent();
                final String passeventfinal=model.getPassword();

                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent read_faqIntent = new Intent(events_page.this, read_event.class);
                        read_faqIntent.putExtra("event_id", post_key);
                        startActivity(read_faqIntent);


                    }
                });


                viewHolder.fView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        PopupMenu popup = new PopupMenu(events_page.this,v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.popup_menu, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.editmenu:

                                        final AlertDialog.Builder b3 = new AlertDialog.Builder(events_page.this);
                                        edit=new EditText(events_page.this);
                                        b3.setTitle("If you are the author of this event information, enter your password");
                                        b3.setView(edit);

                                        b3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String s= String.valueOf(edit.getText());
                                                if(s.equals(passeventfinal))
                                                {
                                                    LinearLayout layout=new LinearLayout(events_page.this);
                                                    final AlertDialog.Builder b4 = new AlertDialog.Builder(events_page.this);
                                                    layout.setOrientation(LinearLayout.VERTICAL);

                                                    TextView test1=new TextView(events_page.this);
                                                    test1.setText("Event title");
                                                    layout.addView(test1);

                                                    final EditText titlebox=new EditText(events_page.this);
                                                    layout.addView(titlebox);

                                                    TextView test2=new TextView(events_page.this);
                                                    test2.setText("Description");
                                                    layout.addView(test2);

                                                    final EditText descbox=new EditText(events_page.this);
                                                    layout.addView(descbox);


                                                    b4.setView(layout);

                                                    b4.setPositiveButton("Save Details", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            String a= String.valueOf(titlebox.getText());
                                                            String b= String.valueOf(descbox.getText());

                                                            if(!TextUtils.isEmpty(a) & !TextUtils.isEmpty(b)){
                                                                mDatabase.child(post_key).child("eventtitle").setValue(a);
                                                                mDatabase.child(post_key).child("event").setValue(b);

                                                                Toast.makeText(events_page.this,"Updated",Toast.LENGTH_LONG ).show();



                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(events_page.this,"empty fields are not considered", Toast.LENGTH_LONG).show();
                                                            }






                                                        }
                                                    });

                                                    b4.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });


                                                    titlebox.setText(titleeventfinal);
                                                    descbox.setText(eventfinal);
                                                    b4.show();

                                                }
                                                else
                                                {
                                                    finish();
                                                    Toast.makeText(events_page.this,"wrong password", Toast.LENGTH_LONG).show();
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
                                        final AlertDialog.Builder b5 = new AlertDialog.Builder(events_page.this);
                                        edit=new EditText(events_page.this);
                                        b5.setTitle("If you are the author of this blog, enter your password");
                                        b5.setView(edit);

                                        b5.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String s= String.valueOf(edit.getText());
                                                if(s.equals(passeventfinal))
                                                {

                                                    final AlertDialog.Builder b6 = new AlertDialog.Builder(events_page.this);

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
                                                    Toast.makeText(events_page.this,"wrong password", Toast.LENGTH_LONG).show();
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

                                        final AlertDialog.Builder b10 = new AlertDialog.Builder(events_page.this);

                                        b10.setMessage("Do you want to report this post?");
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
                                                emailIntent.putExtra(Intent.EXTRA_TEXT,"*Do not remove this data\nEvents "+post_key+".\nYour request will be confidential.\nWe will respond within 24 hours.\nPlease mention your college and your reason below.*\n\n");
                                                try{
                                                    startActivity(Intent.createChooser(emailIntent,"want to send mail..."));
                                                    finish();
                                                }catch (android.content.ActivityNotFoundException ex){
                                                    Toast.makeText(events_page.this, "Mail is not initialized", Toast.LENGTH_SHORT).show();
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


        meventlist.setAdapter(firebaseRecyclerAdapter1);
    }


    public static class eventViewHolder extends RecyclerView.ViewHolder {

        View fView;

        public eventViewHolder(View itemView) {
            super(itemView);
            fView = itemView;


        }

        public void setEventtitle(String title1) {

            TextView faqpost_title = (TextView) fView.findViewById(R.id.event_title);
            faqpost_title.setText(title1);
        }
        public void setEvent(String title1) {

            TextView faqpost_title = (TextView) fView.findViewById(R.id.eventdesc);
            faqpost_title.setText(title1);
        }



    }





    public void evButton() {
        ev_btn = (Button) findViewById(R.id.b_write_event);
        ev_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent event = new Intent("com.something.phanipriya.firstproject.inside_events");
                        startActivity(event);
                    }
                }
        );
    }

}

