package com.android.multilevelmarketing.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.data.sharedprefs.SharedPrefUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class BaseNavDrawerActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private DrawerBuilder drawerBuilder = null;
    private Drawer drawer = null;
    private InputMethodManager inputMethodManager = null;

    private boolean wasInputActive = false;
    private View savedFocus = null;

    public BaseNavDrawerActivity() {
    }

    /**
     * Base public methods
     */

    public Drawer getDrawer() {
        createDrawerBuilder(createToolbar(false));
        createDrawer();
        getCurrentSelection();

        return drawer;
    }

    public Drawer getDrawer(
            boolean withToolbar,
            boolean withToolbarBackArrow
    ) {
        createDrawerBuilder(withToolbar ? createToolbar(withToolbarBackArrow) : null);
        createDrawer();
        getCurrentSelection();

        return drawer;
    }

    /**
     * Support methods
     */

    protected void removeToolbarTitle() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
    }

    protected void setToolbarTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    protected void getCurrentSelection() {
        String basename = BaseNavDrawerActivity.this.getClass().getSimpleName();

        if (drawer == null) {
            return;
        }

        if (basename.equals(MainActivity.class.getSimpleName())) {
            drawer.setSelection(DrawerItems.MainActivity.ordinal());
        } else {
            drawer.setSelection(-1);
        }
    }

    /**
     * Base private methods
     */

    private void createDrawer() {
        if (drawerBuilder != null) {
            drawer = drawerBuilder.build(); // Building Drawer
            drawer.getRecyclerView().setVerticalScrollBarEnabled(false); // remove ScrollBar from RecyclerView
        } else {
            Log.e(TAG, "createDrawer() -> DrawerBuilder is null");
        }
    }

    private Toolbar createToolbar(boolean withBackArrowEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(withBackArrowEnabled);

        return toolbar;
    }

    private void createDrawerBuilder(Toolbar toolbar) {
        final PrimaryDrawerItem main = new PrimaryDrawerItem()
                .withName(getResources().getString(R.string.drawer_main_activity))
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withIdentifier(DrawerItems.MainActivity.ordinal());

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withProfileImagesVisible(false)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("NAME")
                                .withEmail("EMAIL"))
                .build();

        /**
         * Implementing DrawerBuilder
         */
        drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(accountHeader)
                .withHeaderDivider(false)
                .withSliderBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
                .addDrawerItems(
                        main
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        if (inputMethodManager.isAcceptingText()) {
                            if (getCurrentFocus() != null) {
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                                wasInputActive = true;
                                savedFocus = getCurrentFocus();
                            }
                        } else {
                            wasInputActive = false;
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        if (wasInputActive) {
                            inputMethodManager.showSoftInput(getCurrentFocus(), 0);
                            if (savedFocus != null)
                                savedFocus.requestFocus();
                        }
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обработка клика
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        try {
                            String currentClass = BaseNavDrawerActivity.this.getClass().getSimpleName();
                            DrawerItems drawerItems = DrawerItems.values()[(int) drawerItem.getIdentifier()];

                            switch (drawerItems) {
                                case MainActivity: {
                                    if (currentClass.equals(MainActivity.class.getSimpleName())) {
                                        break;
                                    } else {
                                        finish();

                                        startActivity(new Intent(BaseNavDrawerActivity.this, MainActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        break;
                                    }
                                }
                                default: {
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return false;
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            switch (keyCode) {
                case KeyEvent.KEYCODE_MENU: {
                    if (drawer.isDrawerOpen())
                        drawer.closeDrawer();
                    else
                        drawer.openDrawer();
                    break;
                }
                case KeyEvent.KEYCODE_BACK: {
                    if (drawer.isDrawerOpen()) { // Check if Drawer is opened
                        drawer.closeDrawer();
                    } else {
                        super.onBackPressed();
                    }

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    protected void onResume() {
        getCurrentSelection();
        super.onResume();
    }

    private enum DrawerItems {
        MainActivity
    }
}
