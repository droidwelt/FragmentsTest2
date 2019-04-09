package ru.droidwelt.fragmentstest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentTwo extends Fragment {

    private  View vx;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        vx= v;
        GlobalBus.getBus().register(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener(view);
    }

    public void setClickListener(final View view) {
        Button sendMessage = (Button) view.findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = (EditText) view.findViewById(R.id.editText1);
                EditText editText2 = (EditText) view.findViewById(R.id.editText2);
                String s1 = String.valueOf(editText1.getText());
                String s2 = String.valueOf(editText2.getText());
                JSONObject rowObject = new JSONObject();
                try {
                    rowObject.put("s1", s1);
                    rowObject.put("s2", s2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Events.FragmentActivityMessage fragmentActivityMessageEvent =
                        new Events.FragmentActivityMessage(rowObject.toString());
                GlobalBus.getBus().post(fragmentActivityMessageEvent);

                Events.FragmentFragmentMessage fragmentfragmentMessageEvent =
                        new Events.FragmentFragmentMessage(rowObject.toString());
                GlobalBus.getBus().post(fragmentfragmentMessageEvent);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        View v = getView().findViewById(R.id.frament2);
        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        String s = activityFragmentMessage.getMessage();
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
        String sx = "Принято во Фрагменте 2 " + s;
        Toast.makeText(((MainActivity) getActivity()).getBaseContext(), sx, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fr2, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fr2_item21:
                Toast.makeText(getActivity(), "Item 21", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_fr2_item22:
                Toast.makeText(getActivity(), "Item 22", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
