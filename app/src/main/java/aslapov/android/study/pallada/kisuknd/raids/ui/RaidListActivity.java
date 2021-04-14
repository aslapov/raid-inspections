package aslapov.android.study.pallada.kisuknd.raids.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.RaidApplication;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.ui.auth.AuthActivity;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.AuthViewModelFactory;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidLisActivityViewModel;

public class RaidListActivity extends AppCompatActivity
		implements OnRaidShowListener, NavigationView.OnNavigationItemSelectedListener {

	private static final int REQUEST_CODE_SHOW_RAID_ACTIVITY = 0;

	private ImageView mEmptyRaidImage;

	private RaidLisActivityViewModel mViewModel;

	public static void start(@NonNull Activity activity) {
		Intent intent = new Intent(activity, RaidListActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RaidApplication application = (RaidApplication) getApplication();
		mViewModel = new ViewModelProvider(this, new AuthViewModelFactory(application.getAuthRepository()))
				.get(RaidLisActivityViewModel.class);

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

		View headerLayout = navigationView.getHeaderView(0);
		TextView userDisplayNameTextView = headerLayout.findViewById(R.id.user_display);
		userDisplayNameTextView.setText(mViewModel.getUserDisplayName());
		TextView userDepartmentTextView = headerLayout.findViewById(R.id.user_department);
		List<String> userDepartments = mViewModel.getUserDepartments();
		userDepartmentTextView.setText(userDepartments != null ? userDepartments.get(0) : null);

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

		mViewModel.getLogoutResult().observe(this, isLoggedOut -> {
			if (isLoggedOut) {
				closeAndShowAuthActivity();
			} else {
				Toast.makeText(this, R.string.logout_error, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Fragment raidListFragment = null;
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
			case R.id.nav_logout:
				mViewModel.logout();;
				break;
			default:
				actionBar.setTitle(R.string.navigation_raids);
				raidListFragment = RaidListFragment.newInstance();
		}

		if (raidListFragment != null)
			openRaidList(raidListFragment);

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

	private void openRaidList(Fragment raidListFragment) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment raidFragment = fm.findFragmentById(R.id.detail_fragment_container);

		FragmentTransaction transaction = fm.beginTransaction().replace(R.id.fragment_container, raidListFragment);
		if (raidFragment != null)
			transaction = transaction.remove(raidFragment);
		transaction.commit();

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		if (findViewById(R.id.detail_fragment_container) != null)
			mEmptyRaidImage.setVisibility(View.VISIBLE);
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

	private void closeAndShowAuthActivity() {
		Intent intent = new Intent(this, AuthActivity.class);
		startActivity(intent);

		setResult(RESULT_OK);
		finish();
	}
}
