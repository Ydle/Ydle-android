package org.ydle.activity.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ydle.R;
import org.ydle.activity.BaseActivity;
import org.ydle.adapter.FragmentPagerAdapter;
import org.ydle.fragment.settings.ExtraFragment;
import org.ydle.fragment.settings.FramgmentValidator;
import org.ydle.fragment.settings.HostDetailFragment;
import org.ydle.layout.ViewPager;
import org.ydle.layout.ViewPager.OnPageChangeListener;

import roboguice.inject.InjectView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WizardActivity extends BaseActivity {

	protected static final String TAG = "Ydle.WizardActivity";
	@InjectView(R.id.pager)
	private ViewPager mPager;
	@InjectView(R.id.btn_next)
	private Button mNextButton;
	@InjectView(R.id.btn_previous)
	private Button mPrevButton;

	private List<FramgmentValidator> framgementValidators = new ArrayList<FramgmentValidator>();
	private List<Fragment> fragments = new Vector<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wizard);

		// Ajout des Fragments dans la liste
		addFragment(Fragment.instantiate(this,
				HostDetailFragment.class.getName()));
		addFragment(Fragment.instantiate(this, ExtraFragment.class.getName()));

		mPager.setAdapter(new WizardAdapter(getFragmentManager(), fragments));
		mPrevButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPager.getCurrentItem() == 0) {
					finish();
				} else {
					mPager.setCurrentItem(mPager.getCurrentItem() - 1);
				}
			}
		});

		mNextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// si la page courante n'est pas valide on reste sur la page
				if (framgementValidators.get(mPager.getCurrentItem())
						.isValide()) {
					mPager.setCurrentItem(mPager.getCurrentItem() + 1);
				}
				
				if (mPager.getCurrentItem()+1 == fragments.size()) {
					// sauvegarder en conf que l'on a passé le wizard
					Editor editor = prefs.edit().putBoolean("pref_firstStart", false);
					editor.apply();
					finish();
				}
			}
		});

		mPager.setOnPageChangeListener(pageListener);

	}

	private void addFragment(Fragment fragment) {
		fragments.add(fragment);
		framgementValidators.add((FramgmentValidator) fragment);
	}

	private OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			FramgmentValidator validator = framgementValidators.get(position);
			// si la page courante n'est pas valide on reste sur la page
			if (!validator.isValide()) {
				Toast.makeText(WizardActivity.this, validator.getError(), Toast.LENGTH_SHORT)
						.show();
				mPager.setCurrentItem(position);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (mPager.getCurrentItem() == 0) {
				mPrevButton.setText(R.string.cancel);
			} else {
				mPrevButton.setText(R.string.previous);
			}
			
			if (mPager.getCurrentItem()+1 == fragments.size()) {
				mNextButton.setText(R.string.terminer);
			} else {
				mNextButton.setText(R.string.next);
			}
		}
	};

	public class WizardAdapter extends FragmentPagerAdapter {
		private final List<Fragment> fragments;

		// On fournit à l'adapter la liste des fragments à afficher
		public WizardAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}
}
