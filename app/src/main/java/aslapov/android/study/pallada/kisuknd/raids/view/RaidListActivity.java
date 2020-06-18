package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

public class RaidListActivity extends AppCompatActivity
        implements OnRaidSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_SHOW_RAID_ACTIVITY = 0;

    private ImageView mEmptyRaidImage;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RaidListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment raidListFragment  = fm.findFragmentById(R.id.fragment_container);
        ShowRaidFragment raidFragmentFromDualPane = (ShowRaidFragment) fm.findFragmentById(R.id.detail_fragment_container);

        // Случай поворота экрана из горизонтального положения с открытым ShowRaidFragment
        // в вертикальное положение. В таком случае необходимо открыть ShowRaidActivity
        // и ShowRaidFragment
        if (findViewById(R.id.detail_fragment_container) == null && raidFragmentFromDualPane != null) {
            UUID raidId = raidFragmentFromDualPane.getRaidId();
            fm.beginTransaction()
                    .remove(raidFragmentFromDualPane)
                    .commit();

            ShowRaidActivity.startForResult(this, raidId, REQUEST_CODE_SHOW_RAID_ACTIVITY);
        } else if (raidListFragment == null) {
            raidListFragment = RaidListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, raidListFragment)
                    .commit();
        }

        if (findViewById(R.id.detail_fragment_container) != null) {
            mEmptyRaidImage = (ImageView) findViewById(R.id.raid_empty);
            mEmptyRaidImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment raidListFragment;

        switch (item.getItemId()) {
            case R.id.nav_drafts:
                raidListFragment = RaidDraftListFragment.newInstance();
                break;
            case R.id.nav_outgoing:
                raidListFragment = RaidOutgoingListFragment.newInstance();
                break;
            default:
                raidListFragment = RaidListFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, raidListFragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRaidSelected(RaidWithInspectors raid) {
        UUID raidId = UUID.fromString(raid.getRaid().getId());
        if (findViewById(R.id.detail_fragment_container) == null) {
            ShowRaidActivity.startForResult(this, raidId, REQUEST_CODE_SHOW_RAID_ACTIVITY);
        } else {
            showRaidFragment(raidId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Восстановление ShowRaidFragment при повороте экрана в горизонтальное положение
        if (requestCode == REQUEST_CODE_SHOW_RAID_ACTIVITY &&
                resultCode == ShowRaidActivity.RESULT_CONFIGURATION_CHANGE) {
            UUID raidId = ShowRaidActivity.getRaidId(data);
            showRaidFragment(raidId);
        }
    }

    private void showRaidFragment(UUID raidId) {
        ShowRaidFragment raidFragment = (ShowRaidFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        if (raidFragment == null || raidFragment.getRaidId() != raidId) {
            mEmptyRaidImage.setVisibility(View.GONE);
            raidFragment = ShowRaidFragment.newInstance(raidId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, raidFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }
}
