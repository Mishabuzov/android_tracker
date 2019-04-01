package com.example.tom.itistracker.screens.sprints_and_stories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.SprintLocalModel;
import com.example.tom.itistracker.models.network.NotificationType;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.base.fragments.BaseFragment;
import com.example.tom.itistracker.screens.sprints_and_stories.sprints.SprintsFragment;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

//TODO: Create presenters for listeners to reduce code in this fragment
public class SprintsStoriesSwitchFragment extends BaseFragment implements SprintsFragment.StoriesListener,
        StoriesFragment.SprintsListener {

    private static final int STORIES_PAGE = 0;

    private static final int SPRINTS_PAGE = 1;

    @BindString(R.string.user_stories_title) String mStoriesTitle;

    @BindString(R.string.sprints_title) String mSprintsTitle;

    @BindView(R.id.search_tabs) TabLayout mTabs;

    @BindView(R.id.search_pager) ViewPager mPager;

    private SprintsFragment mSprintsFragment;

    private StoriesFragment mStoriesFragment;

    private NotificationType mNotificationType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_switch_sprints, container, false);
        ButterKnife.bind(this, view);
        initFragments();
        setupViewPager();
        return view;
    }

    public void setNotificationType(@NonNull final NotificationType notificationType) {
        mNotificationType = notificationType;
    }

    private void initFragments() {
        mSprintsFragment = new SprintsFragment();
        mSprintsFragment.setStoriesListener(this);

        mStoriesFragment = new StoriesFragment();
        mStoriesFragment.setSprintsListener(this);
    }

    private void setupViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(mStoriesFragment, getString(R.string.user_stories_title));
        pagerAdapter.addFragment(mSprintsFragment, getString(R.string.sprints_title));
        mPager.setAdapter(pagerAdapter);
        mTabs.setVisibility(View.VISIBLE);
        mTabs.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case STORIES_PAGE:
                        setToolbarTitle(mStoriesTitle);
                        break;
                    case SPRINTS_PAGE:
                        setToolbarTitle(mSprintsTitle);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        checkNotification();
    }

    private void checkNotification() {
        if (mNotificationType != null) {
            switch (mNotificationType) {
                case OVERDUE_SPRINT:
                case DEADLINE_IS_CLOSE:
                case BLOCKED_TASKS:
                    mPager.setCurrentItem(SPRINTS_PAGE);
                    break;
            }
        }
    }

    @Override
    public List<UserStory> getUnassignedStories() {
        return mStoriesFragment.getUnassignedStories();
    }

    @Override
    public void removeStoryAfterAttaching(final int storyPosition) {
        mStoriesFragment.removeStoryAfterAttaching(storyPosition);
    }

    @Override
    public void addStoryAfterDetaching(@NonNull final UserStory userStory) {
        mStoriesFragment.addStoryAfterDetaching(userStory);
    }

    @Override
    public List<SprintLocalModel> getSprints() {
        return mSprintsFragment.getSprints();
    }

    @Override
    public void refreshSprintScreen() {
        mSprintsFragment.refreshData();
    }

    private static class PagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String fragmentTitle) {
            mFragments.add(fragment);
            mFragmentTitles.add(fragmentTitle);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

}
