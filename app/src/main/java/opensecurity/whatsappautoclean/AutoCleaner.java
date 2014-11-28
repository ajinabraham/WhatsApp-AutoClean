package opensecurity.whatsappautoclean;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import java.io.File;


public class AutoCleaner extends ActionBarActivity {
    ImageView image;
    private  AutoCleanReceiver alarm;
    public EditText minutes;
    public ToggleButton t;
    Button dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auto_cleaner);
      try {

          alarm = new AutoCleanReceiver();
          minutes=(EditText) findViewById(R.id.editText);
          t = (ToggleButton) findViewById(R.id.toggleButton);
          dis =(Button) findViewById(R.id.button);
          dis.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (t.isChecked())
                  {

                      t.setChecked(false);
                       Context context = getApplicationContext();
                      if (alarm != null) {
                      alarm.CancelAlarm(context);
                    }
                     else {
                            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                            }
                   }
                  File wpath = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/");
                  Restore(wpath);
                  Toast.makeText(getApplicationContext(), "WhatsApp AutoClean Stopped. WhatsApp Media will be shown in Gallery", Toast.LENGTH_SHORT).show();

                  Log.d("WhatsAppAutoClean", "WhatsApp AutoClean Disabled");


              }

});
        t.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View view)
              {
                  try {

                      String minu=minutes.getText().toString();
                      Context context = getApplicationContext();
                      if (t.isChecked()) {
                          if (minu.matches(""))
                          {
                              Toast.makeText(context, "Please Enter Minute", Toast.LENGTH_SHORT).show();
                                t.setChecked(false);
                          } else {

                              int min = Integer.parseInt(minu);
                              int seconds = min * 60;

                              if (alarm != null) {
                                  Toast.makeText(context, "Clean WhatsApp Media every " + min + "  minutes", Toast.LENGTH_SHORT).show();
                                  alarm.SetAlarm(context, seconds);
                              } else {
                                  Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                              }
                          }

                      } else {
                          if (alarm != null) {


                              Toast.makeText(context, "WhatsApp AutoClean Stopped", Toast.LENGTH_SHORT).show();
                              alarm.CancelAlarm(context);
                          } else {
                              Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                          }
                      }
                  }

                  catch (Exception e)
                  {
                      Log.e("WhatsAppAutoClean", "exception", e);

                  }
              }
          });

            image = (ImageView) findViewById(R.id.imageView1);
            image.setImageResource(R.drawable.home);
            image.setOnClickListener(new OnClickListener() {

              public void onClick(View view) {
                  startService(new Intent(getBaseContext(), AutoCleanService.class));

                  Toast.makeText(getApplicationContext(), "WhatsApp Media Directory Cleaned",Toast.LENGTH_LONG).show();



                //Go to Home Screen
                 Intent intent = new Intent(Intent.ACTION_MAIN);
                 intent.addCategory(Intent.CATEGORY_HOME);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

              }
          });



        }
            catch (Exception e)
            {
                Log.e("WhatsAppAutoClean", "exception", e);

        }

    }

    public static void Restore(File dir)
    {
        File med = new File (dir.getAbsolutePath() + "/Media");
        med.mkdirs();
        File file = new File(med, ".nomedia");
        if (file.exists()) {
            try {
                file.delete();
            }
            catch (Exception e) {
                Log.e("WhatsAppAutoClean", "exception", e);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.auto_cleaner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
