package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidActivity;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidFragment;

public class RaidListActivity extends AppCompatActivity {

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RaidListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment raidListFragment  = fm.findFragmentById(R.id.fragment_container);

        if (raidListFragment == null) {
            raidListFragment = new RaidListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, raidListFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void showRaidInfo(Raid raid) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            RaidActivity.start(this, raid.getId());
        } else {
            RaidFragment raidFragment = (RaidFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
            if (raidFragment == null || raidFragment.getShownIndex() != raid.getId()) {
                raidFragment = RaidFragment.newInstance(raid.getId());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, raidFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
            //raidFragment.showRaidInfo(raid);
        }
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_raid_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }*/
}
