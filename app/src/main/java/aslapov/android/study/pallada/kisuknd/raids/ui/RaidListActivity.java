package aslapov.android.study.pallada.kisuknd.raids.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

public class RaidListActivity extends AppCompatActivity
		implements OnRaidShowListener, NavigationView.OnNavigationItemSelectedListener {

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
		getSupportActionBar().setTitle(R.string.navigation_raids);

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
				R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		FragmentManager fm = getSupportFragmentManager();
		BaseRaidListFragment raidListFragment = (BaseRaidListFragment) fm.findFragmentById(R.id.fragment_container);
		ShowRaidFragment raidFragment = (ShowRaidFragment) fm.findFragmentById(R.id.detail_fragment_container);

		if (raidListFragment == null) {
			raidListFragment = RaidListFragment.newInstance();
			fm.beginTransaction()
					.add(R.id.fragment_container, raidListFragment)
					.commit();
		}

		// Случай поворота экрана из горизонтального положения с открытым ShowRaidFragment
		// в вертикальное положение. В таком случае необходимо открыть ShowRaidActivity
		// и ShowRaidFragment
		if (findViewById(R.id.detail_fragment_container) == null && raidFragment != null && raidListFragment.isItemSelected()) {
			UUID raidId = raidFragment.getRaidId();
			ShowRaidActivity.startForResult(this, raidId, REQUEST_CODE_SHOW_RAID_ACTIVITY);
		}

		mEmptyRaidImage = (ImageView) findViewById(R.id.raid_empty);
		if (findViewById(R.id.detail_fragment_container) != null) {
			if (raidFragment == null)
				mEmptyRaidImage.setVisibility(View.VISIBLE);
			else
				mEmptyRaidImage.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Fragment raidListFragment;
		FragmentManager fm = getSupportFragmentManager();
		ActionBar actionBar = getSupportActionBar();

		switch (item.getItemId()) {
			case R.id.nav_drafts:
				actionBar.setTitle(R.string.navigation_drafts);
				raidListFragment = RaidDraftListFragment.newInstance();
				break;
			case R.id.nav_outgoing:
				actionBar.setTitle(R.string.navigation_outgoing);
				raidListFragment = RaidOutgoingListFragment.newInstance();
				break;
			case R.id.nav_trash:
				actionBar.setTitle(R.string.navigation_trash);
				raidListFragment = RaidTrashListFragment.newInstance();
				break;
			default:
				actionBar.setTitle(R.string.navigation_raids);
				raidListFragment = RaidListFragment.newInstance();
		}

		Fragment raidFragment = fm.findFragmentById(R.id.detail_fragment_container);

		FragmentTransaction transaction = fm.beginTransaction().replace(R.id.fragment_container, raidListFragment);
		if (raidFragment != null)
			transaction = transaction.remove(raidFragment);
		transaction.commit();

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		if (findViewById(R.id.detail_fragment_container) != null)
			mEmptyRaidImage.setVisibility(View.VISIBLE);

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
	public void onRaidEmpty() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment raidFragment = fm.findFragmentById(R.id.detail_fragment_container);

		if (raidFragment != null) {
			mEmptyRaidImage.setVisibility(View.VISIBLE);
			fm.beginTransaction()
					.remove(raidFragment)
					.commit();
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
		if (raidFragment == null || !raidId.equals(raidFragment.getRaidId())) {
			raidFragment = ShowRaidFragment.newInstance(raidId);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.detail_fragment_container, raidFragment)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
					.commit();
		}
		mEmptyRaidImage.setVisibility(View.GONE);
	}
}
