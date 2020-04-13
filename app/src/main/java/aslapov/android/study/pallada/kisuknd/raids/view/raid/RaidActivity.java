package aslapov.android.study.pallada.kisuknd.raids.view.raid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
        Log.d("log " + this.toString(), "onCreate");
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        RaidFragment raidFragment = (RaidFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        // Если текущую активити перевели в горизонтальное положение,
        // то вернуться к предыдущей активити
        // TODO передача raidId в RaidListActivity (startActivityForResult)
        if (getResources().getConfiguration().screenWidthDp >= 600) {
            if (raidFragment != null) {
                fm.beginTransaction()
                        .remove(raidFragment)
                        .commit();
            }

            finish();
            return;
        }

        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            UUID raidId = (UUID) getIntent().getSerializableExtra(EXTRA_RAID_ID);
            raidFragment = RaidFragment.newInstance(raidId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, raidFragment)
                    .commit();
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
}
