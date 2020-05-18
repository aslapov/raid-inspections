package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;

public class ShowRaidActivity extends AppCompatActivity {
    public static final String EXTRA_RAID_ID = "aslapov.android.study.pallada.kisuknd.raids.view.raid_id";

    public static void startForResult(@NonNull Activity activity, UUID raidId, int requestCode) {
        Intent intent = new Intent(activity, ShowRaidActivity.class);
        intent.putExtra(EXTRA_RAID_ID, raidId);
        activity.startActivityForResult(intent, requestCode);
    }

    // Возврат в родительский Activity ID рейдового осмотра ТС
    public static UUID getRaidId(Intent result) {
        return (UUID) result.getSerializableExtra(EXTRA_RAID_ID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID raidId = (UUID) getIntent().getSerializableExtra(EXTRA_RAID_ID);

        FragmentManager fm = getSupportFragmentManager();
        ShowRaidFragment raidFragment = (ShowRaidFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        // Если текущую активити перевели в горизонтальное положение,
        // то вернуться к предыдущей активити
        if (getResources().getConfiguration().screenWidthDp >= 900) {
            if (raidFragment != null) {
                fm.beginTransaction()
                        .remove(raidFragment)
                        .commit();
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_RAID_ID, raidId);
            setResult(RESULT_OK, data);

            finish();
            return;
        }

        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null) {
            raidFragment = ShowRaidFragment.newInstance(raidId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, raidFragment)
                    .commit();
        }
    }
}
