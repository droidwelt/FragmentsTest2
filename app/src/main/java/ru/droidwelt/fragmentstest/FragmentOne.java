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
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

public class FragmentOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        //Inflate the layout for this fragment
        GlobalBus.getBus().register(this);

        Button buttonStart = v.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).fr2_Add();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        String s = activityFragmentMessage.getMessage();
        String sx = s;
        Toast.makeText(((MainActivity) getActivity()).getBaseContext(),
                "Принято во Фрагменте 1 от MainActivity " + sx,
                Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void getMessage(Events.FragmentFragmentMessage fragmentFragmentMessage) {
        String s = fragmentFragmentMessage.getMessage();
        String sx = s;
        Toast.makeText(((MainActivity) getActivity()).getBaseContext(),
                "Принято во Фрагменте 1 от другого фрагмента " + sx,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fr1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fr1_item1:
                Toast.makeText(getActivity(), "Item 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_fr1_item2:
                Toast.makeText(getActivity(), "Item 2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


 /*   View.OnClickListener oclBtnOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {

                case R.id.buttonStart:
                    ((MainActivity) getActivity()).fr2_Add();
                    break;

                default:
                    break;
            }
        }
    };  */


}
