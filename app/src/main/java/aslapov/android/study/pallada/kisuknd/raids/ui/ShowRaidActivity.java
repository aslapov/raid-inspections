package aslapov.android.study.pallada.kisuknd.raids.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;

public class ShowRaidActivity extends AppCompatActivity {
    public static final String EXTRA_RAID_ID = "aslapov.android.study.pallada.kisuknd.raids.ui.raid_id";

    // result_code для Activity, возвращающееся при изменении конфигурации (вращение экрана)
    public static final int RESULT_CONFIGURATION_CHANGE = 1;

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
            Intent data = new Intent();
            data.putExtra(EXTRA_RAID_ID, raidId);
            setResult(RESULT_CONFIGURATION_CHANGE, data);

            finish();
            return;
        }

        setContentView(R.layout.activity_fragment_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        if (savedInstanceState == null && raidFragment == null) {
            raidFragment = ShowRaidFragment.newInstance(raidId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, raidFragment)
                    .commit();
        }
    }
}
