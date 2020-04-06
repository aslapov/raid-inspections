package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import java.util.UUID;
import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidListPresenter;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidActivity;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidFragment;
import aslapov.android.study.pallada.kisuknd.raids.view.SingleFragmentActivity;

public class RaidListActivity extends AppCompatActivity implements IRaidListView {

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RaidListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new RaidListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void showRaidInfo(UUID raidId) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            RaidActivity.start(this, raidId);
        } else {
            RaidFragment raidFragment = (RaidFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
            if (raidFragment == null || raidFragment.getShownIndex() != raidId) {
                raidFragment = RaidFragment.newInstance(raidId);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, raidFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
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
