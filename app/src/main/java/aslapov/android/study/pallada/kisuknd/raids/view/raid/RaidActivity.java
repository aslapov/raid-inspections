package aslapov.android.study.pallada.kisuknd.raids.view.raid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidPresenter;

public class RaidActivity extends AppCompatActivity {
    public static final String EXTRA_RAID_ID = "aslapov.android.study.pallada.kisuknd.raids.view.raid_id";

    public static void start(@NonNull Activity activity, UUID raidId) {
        Intent intent = new Intent(activity, RaidActivity.class);
        intent.putExtra(EXTRA_RAID_ID, raidId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Если текущую активити перевели в горизонтальное положение,
        // то вернуться к предыдущей активити
        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }*/

        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            UUID raidId = (UUID) getIntent().getSerializableExtra(EXTRA_RAID_ID);
            RaidFragment raidFragment = RaidFragment.newInstance(raidId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, raidFragment)
                    .commit();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
