package com.something.phanipriya.firstproject;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


public class MainActivity extends AppCompatActivity {
   private static Button login_btn;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton();
        addItemsonspinner();
    }

private boolean isNetworkAvailable()
{
    ConnectivityManager connectivityManager=(ConnectivityManager)this
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo= connectivityManager
            .getActiveNetworkInfo();
    return activeNetworkInfo!=null;

}
    public void LoginButton() {
        login_btn = (Button) findViewById(R.id.b_login);
        if (!isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Internet Connection Required").setCancelable(false).setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent internet = getIntent();
                            finish();
                            startActivity(internet);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            login_btn.setOnClickListener(new View.OnClickListener() {

                                             public void onClick(View v) {

                                                 String text = spinner.getSelectedItem().toString();
                                                 if (text.equals("MVSR")) {

                                                     FirebaseOptions.Builder builder1 = new FirebaseOptions.Builder()
                                                             .setApplicationId("1:447553466688:android:5e34dbcaae7b2b06")
                                                             .setApiKey("AIzaSyCTehD02pDsAPrXfDoDTbYgshpMXvAaUac")
                                                             .setDatabaseUrl("https://collegediaries2.firebaseio.com")
                                                             .setStorageBucket("collegediaries2.appspot.com");
                                                     FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), builder1.build());
                                                     Intent intent = new Intent("com.something.phanipriya.firstproject.userpage");
                                                     startActivity(intent);
                                                 }

                                                  else if (text.equals("VCE")) {

                                                     FirebaseOptions.Builder builder2 = new FirebaseOptions.Builder()
                                                             .setApplicationId("1:423844547819:android:5e34dbcaae7b2b06")
                                                             .setApiKey("AIzaSyBjBzk57fqMv-tDEWU-9pnnjgoEuMjOq6w")
                                                             .setDatabaseUrl("https://collegediaries-4a49f.firebaseio.com")
                                                             .setStorageBucket("collegediaries-4a49f.appspot.com");

                                                     FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), builder2.build());
                                                     Intent intent = new Intent("com.something.phanipriya.firstproject.userpage");
                                                     startActivity(intent);
                                                 }

                                                 else
                                                 {
                                                     final AlertDialog.Builder b10 = new AlertDialog.Builder(MainActivity.this);

                                                     b10.setMessage("You need to register your college by mailing us. Do you want to proceed?");
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
                                                             emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Registering our college");
                                                             emailIntent.putExtra(Intent.EXTRA_TEXT,"*Please mention your college below.*\n\n");
                                                             try{
                                                                 startActivity(Intent.createChooser(emailIntent,"want to send mail..."));
                                                                 finish();
                                                             }catch (android.content.ActivityNotFoundException ex){
                                                                 Toast.makeText(MainActivity.this, "Mail is not initialized", Toast.LENGTH_SHORT).show();
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

                                                 }


                                             }
                                         }
            );
        }
    }

    public void addItemsonspinner()
    {
        spinner=(Spinner)findViewById(R.id.spinner_college_selection);
        String[] items= new String[]{"VCE","MVSR","Other College"};
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
    }
}
