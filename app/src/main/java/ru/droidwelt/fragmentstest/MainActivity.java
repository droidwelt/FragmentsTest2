package ru.droidwelt.fragmentstest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    FragmentOne  fr1;
    FragmentTwo  fr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr2_Add();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fr2_Remove();
            }
        });

        Button sendMessageToFragment = (Button) findViewById(R.id.sendMessageToFragment);
        sendMessageToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageToFragment(view);
            }
        });

        fr1_Add();

    }

    public void fr1_Add() {
        FragmentManager fm = getFragmentManager();
        if (!(fr1 != null && fr1.isVisible())) {
            fr1=new FragmentOne();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment_place, fr1, "FR1");
          /*  fragmentTransaction.setCustomAnimations(
                    R.animator.slide_in_left,
                    R.animator.slide_in_right);*/
            fragmentTransaction.commit();
        }
    }


    public void fr2_Add() {
        FragmentManager fm = getFragmentManager();
        if (!(fr2 != null && fr2.isVisible())) {
            fr2=new FragmentTwo();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment_place, fr2, "FR2");
            fragmentTransaction.addToBackStack("FR2");
            fragmentTransaction.setTransition (TRANSIT_FRAGMENT_OPEN);
         //   fragmentTransaction.remove(fr1);
            fragmentTransaction.hide(fr1);
            fragmentTransaction.commit();
        }


    }

    public void fr2_Remove() {
        FragmentManager fm = getFragmentManager();
        if (fr2 != null && fr2.isVisible()) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.remove(fr2);
            fragmentTransaction.setTransition (FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
            fm.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void sendMessageToFragment(View view)  {
        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String s1 = String.valueOf(editText1.getText());
        String s2 = String.valueOf(editText2.getText());
        JSONObject rowObject = new JSONObject();
        try {
            rowObject.put("s1", s1);
            rowObject.put("s2", s2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(rowObject.toString());

        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    //  @Subscribe(threadMode = ThreadMode.MAIN)  // можно и так
    @Subscribe
    public void getMessage(Events.FragmentActivityMessage fragmentActivityMessage) {
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        String s = fragmentActivityMessage.getMessage();
        String s1="";
        String s2="";
        try {
            JSONObject rowObject = new JSONObject (s);
            s1 = rowObject.getString("s1");
            s2 = rowObject.getString("s2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView1.setText(s1);
        textView2.setText(s2);
        String sx = "Принято в MainActivity " + s;
        Toast.makeText(getApplicationContext(), sx, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
                return true;
            default:
                // Handle fragment menu items
                return super.onOptionsItemSelected(item);
        }
    }
}
