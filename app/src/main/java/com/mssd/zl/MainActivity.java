package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.mfragment.Discover;
import com.mssd.mfragment.Experience;
import com.mssd.mfragment.Exploration;
import com.mssd.mfragment.Mine;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AutoLayoutActivity {
    @BindView(R.id.main_Fragment)
    LinearLayout mainFragment;
    @BindView(R.id.main_exploration)
    RelativeLayout mainExploration;
    @BindView(R.id.main_discover)
    RelativeLayout mainDiscover;
    @BindView(R.id.main_experience)
    RelativeLayout mainExperience;
    @BindView(R.id.main_mine)
    RelativeLayout mainMine;
    @BindView(R.id.exploration_icon)
    ImageView explorationIcon;
    @BindView(R.id.exploration_name)
    TextView explorationName;
    @BindView(R.id.discover_icon)
    ImageView discoverIcon;
    @BindView(R.id.discover_name)
    TextView discoverName;
    @BindView(R.id.experience_icon)
    ImageView experienceIcon;
    @BindView(R.id.experience_name)
    TextView experienceName;
    @BindView(R.id.mine_icon)
    ImageView mineIcon;
    @BindView(R.id.mine_name)
    TextView mineName;
    private Unbinder unbinder;
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private Fragment fragment = new Fragment();
    private List<Fragment> list = new ArrayList<>();
    private int currentIndex = 0;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        changeFont();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);

            list.removeAll(list);
            list.add(fragmentManager.findFragmentByTag(0 + ""));
            list.add(fragmentManager.findFragmentByTag(1 + ""));
            list.add(fragmentManager.findFragmentByTag(2 + ""));
            list.add(fragmentManager.findFragmentByTag(3 + ""));
            restoreFragment();
        } else {
            list.add(new Exploration());
            list.add(new Discover());
            list.add(new Experience());
            list.add(new Mine());

            showFragment();
        }
    }
    private void changeFont(){
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        experienceName.setTypeface(typeface);
        explorationName.setTypeface(typeface);
        discoverName.setTypeface(typeface);
        mineName.setTypeface(typeface);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex);
        super.onSaveInstanceState(outState);
    }

    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!list.get(currentIndex).isAdded()) {
            transaction
                    .hide(fragment)
                    .add(R.id.main_Fragment, list.get(currentIndex), "" + currentIndex);
        } else {
            transaction
                    .hide(fragment)
                    .show(list.get(currentIndex));
        }
        fragment = list.get(currentIndex);
        transaction.commit();
    }

    private void restoreFragment() {
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < list.size(); i++) {

            if (i == currentIndex) {
                mBeginTreansaction.show(list.get(i));
            } else {
                mBeginTreansaction.hide(list.get(i));
            }
        }
        mBeginTreansaction.commit();
        fragment = list.get(currentIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.main_exploration, R.id.main_discover, R.id.main_experience, R.id.main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_exploration:
                currentIndex = 0;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                break;
            case R.id.main_discover:
                currentIndex = 1;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                break;
            case R.id.main_experience:
                currentIndex = 2;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                break;
            case R.id.main_mine:
                currentIndex = 3;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainChecked));
                break;
        }
    }
}
