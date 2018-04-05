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

import static com.something.phanipriya.firstproject.R.id;
import static com.something.phanipriya.firstproject.R.layout;

public class blog_page extends AppCompatActivity {
    Button writeblogs_btn;
    RecyclerView mBloglist;
     DatabaseReference mDatabase;
     EditText edit,edit1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        setContentView(layout.activity_blog_page);
        mBloglist = (RecyclerView) findViewById(id.blog_list);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mBloglist.setLayoutManager(mLayoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        wblogButton();
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


        final FirebaseRecyclerAdapter<Blog, com.something.phanipriya.firstproject.blog_page.BlogViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Blog, com.something.phanipriya.firstproject.blog_page.BlogViewHolder>(
                Blog.class,
                layout.blog_row,
                com.something.phanipriya.firstproject.blog_page.BlogViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(final com.something.phanipriya.firstproject.blog_page.BlogViewHolder viewHolder, final Blog model, int position) {

                final String post_key= getRef(position).getKey();


                viewHolder.setTitle(model.getTitle());
                final String titlefinal=model.getTitle();
                final String descriptionfinal=model.getDescription();
                final String namefinal=model.getName();
                final String branchfinal=model.getBranch();
                final String rollfinal=model.getRollnumber();
                final String passfinal=model.getPassword();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent read_blogIntent=new Intent(com.something.phanipriya.firstproject.blog_page.this, read_blog.class);
                        read_blogIntent.putExtra("blog_id",post_key);
                        startActivity(read_blogIntent);


                    }
                });

                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        PopupMenu popup = new PopupMenu(blog_page.this,v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.popup_menu, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.editmenu:

                                        final AlertDialog.Builder b3 = new AlertDialog.Builder(blog_page.this);
                                        edit=new EditText(blog_page.this);

                                        b3.setTitle("If you are the author of this blog, enter your password");
                                        b3.setView(edit);

                                        b3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String s= String.valueOf(edit.getText());
                                                if(s.equals(passfinal))
                                                {
                                                    LinearLayout layout=new LinearLayout(blog_page.this);
                                                    final AlertDialog.Builder b4 = new AlertDialog.Builder(blog_page.this);
                                                    layout.setOrientation(LinearLayout.VERTICAL);

                                                    TextView test1=new TextView(blog_page.this);
                                                    test1.setText("Title");
                                                    layout.addView(test1);

                                                    final EditText titlebox=new EditText(blog_page.this);
                                                    layout.addView(titlebox);

                                                    TextView test2=new TextView(blog_page.this);
                                                    test2.setText("Description");
                                                    layout.addView(test2);

                                                    final EditText descbox=new EditText(blog_page.this);
                                                    layout.addView(descbox);

                                                    TextView test3=new TextView(blog_page.this);
                                                    test3.setText("Name");
                                                    layout.addView(test3);

                                                    final EditText namebox=new EditText(blog_page.this);
                                                    layout.addView(namebox);

                                                    TextView test4=new TextView(blog_page.this);
                                                    test4.setText("Branch");
                                                    layout.addView(test4);

                                                    final EditText branchbox=new EditText(blog_page.this);
                                                    layout.addView(branchbox);

                                                    TextView test5=new TextView(blog_page.this);
                                                    test5.setText("Roll Number");
                                                    layout.addView(test5);

                                                    final EditText rollbox=new EditText(blog_page.this);
                                                    layout.addView(rollbox);

                                                    b4.setView(layout);

                                                    b4.setPositiveButton("Save Details", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            String a= String.valueOf(titlebox.getText());
                                                            String b= String.valueOf(descbox.getText());
                                                            String c= String.valueOf(namebox.getText());
                                                            String d= String.valueOf(branchbox.getText());
                                                            String e= String.valueOf(rollbox.getText());
                                                            if(!TextUtils.isEmpty(a) & !TextUtils.isEmpty(b) & !TextUtils.isEmpty(c) & !TextUtils.isEmpty(d) & !TextUtils.isEmpty(e)){
                                                                mDatabase.child(post_key).child("title").setValue(a);
                                                                mDatabase.child(post_key).child("description").setValue(b);
                                                                mDatabase.child(post_key).child("name").setValue(c);
                                                                mDatabase.child(post_key).child("branch").setValue(d);
                                                                mDatabase.child(post_key).child("rollnumber").setValue(e);
                                                                Toast.makeText(blog_page.this,"Updated",Toast.LENGTH_LONG ).show();



                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(blog_page.this,"empty fields are not considered", Toast.LENGTH_LONG).show();
                                                            }






                                                        }
                                                    });

                                                    b4.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });


                                                    titlebox.setText(titlefinal);
                                                    descbox.setText(descriptionfinal);
                                                    namebox.setText(namefinal);
                                                    branchbox.setText(branchfinal);
                                                    rollbox.setText(rollfinal);

                                                        b4.show();

                                                }
                                                else
                                                {
                                                    finish();
                                                    Toast.makeText(blog_page.this,"wrong password", Toast.LENGTH_LONG).show();
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
                                        final AlertDialog.Builder b5 = new AlertDialog.Builder(blog_page.this);
                                        edit=new EditText(blog_page.this);
                                        b5.setTitle("If you are the author of this blog, enter your password");
                                        b5.setView(edit);

                                        b5.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                String s= String.valueOf(edit.getText());
                                                if(s.equals(passfinal))
                                                {

                                                    final AlertDialog.Builder b6 = new AlertDialog.Builder(blog_page.this);

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
                                                    Toast.makeText(blog_page.this,"wrong password", Toast.LENGTH_LONG).show();
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
                                    case id.reportmenu:

                                        final AlertDialog.Builder b10 = new AlertDialog.Builder(blog_page.this);

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
                                                        emailIntent.putExtra(Intent.EXTRA_TEXT,"*Do not remove this data\nBlogs "+post_key+".\nYour request will be confidential.\nWe will respond within 24 hours.\nPlease mention your college and your reason below.*\n\n");
                                                        try{
                                                            startActivity(Intent.createChooser(emailIntent,"want to send mail..."));
                                                            finish();
                                                        }catch (android.content.ActivityNotFoundException ex){
                                                            Toast.makeText(blog_page.this, "Mail is not initialized", Toast.LENGTH_SHORT).show();
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
        mBloglist.setAdapter(firebaseRecyclerAdapter);
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;


        }
        public void setTitle(String title){

            TextView post_title=(TextView) mView.findViewById(id.post_title);
            post_title.setText(title);
        }

    }
    public void wblogButton() {
        writeblogs_btn = (Button) findViewById(id.b_write_blog);
        writeblogs_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent("com.something.phanipriya.firstproject.inside_blogpage");
                        startActivity(i);
                    }
                }
        );
    }


}
