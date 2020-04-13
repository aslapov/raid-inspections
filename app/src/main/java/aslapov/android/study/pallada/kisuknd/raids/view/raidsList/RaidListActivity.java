package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidActivity;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidFragment;

public class RaidListActivity extends AppCompatActivity implements RaidListFragment.OnRaidSelectedListener {

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RaidListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("log " + this.toString(), "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment raidListFragment  = fm.findFragmentById(R.id.fragment_container);
        RaidFragment raidFragmentFromDualPane = (RaidFragment) fm.findFragmentById(R.id.detail_fragment_container);

        // Случай поворота экрана из горизонтального положения с открытым RaidFragment
        // в вертикальное положение. В таком случае необходимо открыть RaidActivity
        // и RaidFragment
        if (findViewById(R.id.detail_fragment_container) == null && raidFragmentFromDualPane != null) {
            UUID raidId = raidFragmentFromDualPane.getShownIndex();
            fm.beginTransaction()
                    .remove(raidFragmentFromDualPane)
                    .commit();

            RaidActivity.start(this, raidId);
        } else if (raidListFragment == null) {
            raidListFragment = new RaidListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, raidListFragment)
                    .commit();
        }
    }

    @Override
    public void onRaidSelected(Raid raid) {
        Log.d("log " + this.toString(), "onRaidSelected");
        if (findViewById(R.id.detail_fragment_container) == null) {
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
        }
    }

    @Override
    protected void onPause() {
        Log.d("log " + this.toString(), "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("log " + this.toString(), "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("log " + this.toString(), "onDestroy");
        super.onDestroy();
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_raid_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }*/
}
