package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidActivity;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.RaidFragment;

public class RaidListActivity extends AppCompatActivity implements RaidListFragment.OnRaidSelectedListener {
    private static final int REQUEST_CODE_RAID = 1;

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
        RaidFragment raidFragmentFromDualPane = (RaidFragment) fm.findFragmentById(R.id.detail_fragment_container);

        // Случай поворота экрана из горизонтального положения с открытым RaidFragment
        // в вертикальное положение. В таком случае необходимо открыть RaidActivity
        // и RaidFragment
        if (findViewById(R.id.detail_fragment_container) == null && raidFragmentFromDualPane != null) {
            UUID raidId = raidFragmentFromDualPane.getRaidId();
            fm.beginTransaction()
                    .remove(raidFragmentFromDualPane)
                    .commit();

            RaidActivity.start(this, raidId, REQUEST_CODE_RAID);
        } else if (raidListFragment == null) {
            raidListFragment = new RaidListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, raidListFragment)
                    .commit();
        }
    }

    @Override
    public void onRaidSelected(Raid raid) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            RaidActivity.start(this, raid.getId(), REQUEST_CODE_RAID);
        } else {
            showRaidFragment(raid.getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Восстановление RaidFragment при повороте экрана в горизонтальное положение
        // RESULT_OK нужен для отличия от события нажатия кнопки "назад", при котором
        // автоматически генерируется результат RESULT_CANCELED
        if (requestCode == REQUEST_CODE_RAID && resultCode == RESULT_OK) {
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

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_raid_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }*/
}
