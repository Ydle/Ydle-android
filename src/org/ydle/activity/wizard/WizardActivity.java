package org.ydle.activity.wizard;

import java.util.List;
import java.util.Vector;

import org.ydle.R;
import org.ydle.activity.BaseActivity;
import org.ydle.adapter.FragmentPagerAdapter;
import org.ydle.fragment.settings.ExtraFragment;
import org.ydle.fragment.settings.HostDetailFragment;
import org.ydle.layout.ViewPager;

import roboguice.inject.InjectView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WizardActivity extends BaseActivity {

	@InjectView(R.id.pager)
	private ViewPager mPager;
	@InjectView(R.id.btn_next)
	private Button mNextButton;
	@InjectView(R.id.btn_previous)
	private Button mPrevButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wizard);

		List<Fragment> fragments = new Vector<Fragment>();

        // Ajout des Fragments dans la liste
        fragments.add(Fragment.instantiate(this,HostDetailFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,ExtraFragment.class.getName()));
		
        
        mPager.setAdapter(new WizardAdapter(getFragmentManager(), fragments));
		mPrevButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	public class WizardAdapter extends FragmentPagerAdapter {
		private final List<Fragment> fragments;

        //On fournit à l'adapter la liste des fragments à afficher
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
