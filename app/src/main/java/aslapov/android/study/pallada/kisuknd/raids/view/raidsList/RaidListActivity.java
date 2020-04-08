package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidListPresenter;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidActivity;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidFragment;

public class RaidListActivity extends AppCompatActivity implements IRaidListActivityView {

    RaidListPresenter mPresenter;

    RaidListFragment mRaidListFragment;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RaidListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_fragment);
        else
            setContentView(R.layout.activity_twopane);

        FragmentManager fm = getSupportFragmentManager();
        mRaidListFragment = (RaidListFragment) fm.findFragmentById(R.id.fragment_container);

        if (mRaidListFragment == null) {
            mRaidListFragment = new RaidListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mRaidListFragment)
                    .commit();
        }

        mPresenter = new RaidListPresenter();
        mPresenter.attachView(this);
        mPresenter.init();
    }

    @Override
    public void showRaids(List<Raid> raids) {
        mRaidListFragment.showRaids(raids);
    }

    @Override
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

    @Override
    protected void onDestroy() {
        mRaidListFragment = null;
        mPresenter.detachView();
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
