package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import aslapov.android.study.pallada.kisuknd.raids.R;

public class CreateRaidActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CreateRaidActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            CreateRaidFragment createRaidFragment = new CreateRaidFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, createRaidFragment)
                    .commit();
        }
    }
}
