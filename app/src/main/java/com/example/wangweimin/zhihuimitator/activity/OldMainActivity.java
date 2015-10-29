//package com.example.wangweimin.zhihuimitator.activity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.ShareActionProvider;
//import android.support.v7.widget.Toolbar;
//import android.util.TypedValue;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.astuetz.PagerSlidingTabStrip;
//
//import com.example.wangweimin.zhihuimitator.R;
//import com.example.wangweimin.zhihuimitator.adapter.MyPagerAdapter;
//
//public class MainActivity extends AppCompatActivity {
//
//    private CharSequence mTitle;
//    private String[] mTitleArray = {"Section 1", "Section 2", "Section 3"};
//    private Toolbar mToolbar;
//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private PagerSlidingTabStrip mPagerSlidingTabStrip;
//    private ViewPager mViewPager;
//    private ShareActionProvider mShareActionProvider;
//    private ListView mListView;
//    private ArrayAdapter mArrayAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle("Wayne");
//
//        setSupportActionBar(mToolbar);
//
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.ab_search:
//                        Toast.makeText(MainActivity.this, "Search Action", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.action_share:
//                        Toast.makeText(MainActivity.this, "Share Action", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.action_settings:
//                        Toast.makeText(MainActivity.this, "Setting Action", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        mDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        mViewPager = (ViewPager) findViewById(R.id.pages);
//        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        mPagerSlidingTabStrip.setViewPager(mViewPager);
//
//        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        initTabsValue();
//
//        mListView = (ListView) findViewById(R.id.drawer_list);
//        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mTitleArray);
//        mListView.setAdapter(mArrayAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String mChildCount =""+mPagerSlidingTabStrip.getChildCount();
//                Toast.makeText(MainActivity.this, mChildCount, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /**
//     * mPagerSlidingTabStrip默认值配置
//     */
//    private void initTabsValue() {
//        // 底部游标颜色
//        mPagerSlidingTabStrip.setIndicatorColor(Color.BLUE);
//        // tab的分割线颜色
//        mPagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
//        // tab背景
//        mPagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#4876FF"));
//        // tab底线高度
//        mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                1, getResources().getDisplayMetrics()));
//        // 游标高度
//        mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                5, getResources().getDisplayMetrics()));
//        // 正常文字颜色
//        mPagerSlidingTabStrip.setTextColor(Color.WHITE);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        /* ShareActionProvider配置 */
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu
//                .findItem(R.id.action_share));
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/*");
//        mShareActionProvider.setShareIntent(intent);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}
