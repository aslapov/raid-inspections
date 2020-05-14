package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidWithInspectors;

public class RaidListActivity extends AppCompatActivity
        implements RaidListFragment.OnRaidSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_RAID_ACTIVITY = 0;

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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment raidListFragment  = fm.findFragmentById(R.id.fragment_container);
        RaidFragment raidFragmentFromDualPane = (RaidFragment) fm.findFragmentById(R.id.detail_fragment_container);

        // Случай поворота экрана из горизонтального положения с открытым RaidFragment
        // в вертикальное положение. В таком случае необходимо открыть RaidActivity
        // и RaidFragment
        if (findViewById(R.id.detail_fragment_container) == null && raidFragmentFromDualPane != null) {
            UUID raidId = raidFragmentFromDualPane.getRaidId();
            fm.beginTransaction()
                    .remove(raidFragmentFromDualPane)
                    .commit();

            RaidActivity.start(this, raidId, REQUEST_CODE_RAID_ACTIVITY);
        } else if (raidListFragment == null) {
            raidListFragment = new RaidListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, raidListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = 0;
        switch (item.getItemId()) {
            case R.id.nav_all:
                i = 1;
                break;
            case R.id.nav_drafts:
                i = 2;
                break;
            default:
                i = 100;
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRaidSelected(RaidWithInspectors raid) {
        UUID raidId = UUID.fromString(raid.getRaidEntity().getId());
        if (findViewById(R.id.detail_fragment_container) == null) {
            RaidActivity.start(this, raidId, REQUEST_CODE_RAID_ACTIVITY);
        } else {
            showRaidFragment(raidId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Восстановление RaidFragment при повороте экрана в горизонтальное положение.
        // RESULT_OK нужен для отличия от события нажатия кнопки "назад", при котором
        // автоматически генерируется код результата RESULT_CANCELED
        if (requestCode == REQUEST_CODE_RAID_ACTIVITY && resultCode == RESULT_OK) {
            UUID raidId = RaidActivity.getRaidId(data);
            showRaidFragment(raidId);
        }
    }

    private void showRaidFragment(UUID raidId) {
        RaidFragment raidFragment = (RaidFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        if (raidFragment == null || raidFragment.getRaidId() != raidId) {
            raidFragment = RaidFragment.newInstance(raidId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, raidFragment)
                    .commit();
        }
    }
}
