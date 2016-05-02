package gsihome.reyst.y2t.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import gsihome.reyst.y2t.R;
import gsihome.reyst.y2t.adapters.PagerAdapter;
import gsihome.reyst.y2t.data.State;
import gsihome.reyst.y2t.fragments.ListViewFragment;
import gsihome.reyst.y2t.fragments.RecyclerViewFragment;
// DataUtil.java: 
// Line 29: No need to create new object of Date, just use System.getCurrentTimeMillis()
// Line 33: replace @SuppressLint to make it work for String.format
// 	Line 45, 50: icon2, icon1 - bad naming - not describes the meaning
// 	Line 64: Hardcoded text
// nav_header_main.xml: format code, no need in relative layout as there is only one child,
// And if don’t use features of complicated layouts use frame layout 
// ImageGalleryAdapter.java : 
// Line 67: read lint warnings
// ListViewFragment:
// Remove unused imports
// Line 34: lint warning
// Line 146-150: include view class in field naming like mTvSomething or mSomethingTextView
// Line 83: move adapter class  from fragment  
// MainActiviy: 
// 	Line 31: remove unused field
// 	Line 82: you can replace with tablayout.setupWithViewPager(viewPager), add   getPageTitle in PagerAdapter in that case. 
// DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); -  used two times . make a field
// Remove NullPointerExeption warnings
// http://prntscr.com/axiy7b - replace with switch
//     6	RecyclerViewFragment, ListViewFragment - make animation of fab through behavior, something like here http://www.sitepoint.com/animating-android-floating-action-button/.
// The way you do it is dangerous because frgment can be attached to the activity without fab  
//    7. 	Use instead of deprecated  mContext.getResources().getDrawable
// ContextCompat.getDrawable() (IssueListAdapter, ListViewFragment)
//    8. 	List_item_card.xml - 	need to rebuild layout, too many relative layouts where it can be changed to linear. Use LinearLayouts , weight attribute can help you, at least in base cardview layout  . Prevent text overlap. For example in like counter



	


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FloatingActionButtonOwner {

    private List<Fragment> mFragments;
    private List<String> mFragmentNames;
    private DrawerLayout mDrawer;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.all_appeals);
        }
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.toolbar_icon);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initFragments();
        initViewPager();
        initFab();

    }

    private void initFab() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initFragments() {

        mFragments = new ArrayList<>(3);
        mFragmentNames = new ArrayList<>(3);

        mFragments.add(RecyclerViewFragment.getInstance(State.IN_WORK));
        mFragmentNames.add(getString(R.string.first_tab));

        mFragments.add(RecyclerViewFragment.getInstance(State.DONE));
        mFragmentNames.add(getString(R.string.second_tab));

        mFragments.add(ListViewFragment.getInstance(State.WAIT));
        mFragmentNames.add(getString(R.string.third_tab));

    }

    private void initViewPager() {

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mFragments, mFragmentNames);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        if (tabLayout != null && viewPager != null) {
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }

    private void initDrawer(Toolbar toolbar) {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (mDrawer != null) {
            mDrawer.addDrawerListener(toggle);
        }

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_all_appeals:
                break;
            case R.id.nav_appeals_on_map:
                break;
            case R.id.nav_login:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public FloatingActionButton getFloatingActionButton() {
        return mFab;
    }
}
