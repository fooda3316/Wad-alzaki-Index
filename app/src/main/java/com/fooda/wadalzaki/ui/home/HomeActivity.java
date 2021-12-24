package com.fooda.wadalzaki.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.adapters.ViewPagerAdapter;
import com.fooda.wadalzaki.helpers.SetUpImagesHelper;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.helpers.login.FacebookHelper;
import com.fooda.wadalzaki.helpers.login.LogoutHelper;
import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.more.MoreAppsActivity;
import com.fooda.wadalzaki.more.MoreAppsFragment;
import com.fooda.wadalzaki.themes.SetThemes;
import com.fooda.wadalzaki.ui.SearchActivity;
import com.fooda.wadalzaki.ui.login.LogInActivity;
import com.fooda.wadalzaki.utils.SharingConnection;
import com.facebook.GraphResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FacebookHelper.OnFbSignInListener {
    //private MaterialDialog logoutDialog, newLogoutDialog;
    private static final String TAG = "HomeActivity";
    private SharingConnection connection;
    private MenuItem nav_login;
    private LogoutHelper logoutHelper;
    private SharedPreferencesHelper preferencesHelper;
    private MaterialDialog logoutDialog, newLogoutDialog;
    private FacebookHelper facebookHelper;
    private CircleImageView circleImageView;
    private TextView headerTextViewName;
    private FirebaseFirestore db;
    private CollectionReference allUsersRef;
    public static Toolbar toolbar;
    public TabLayout tabLayout;
    //Fragments
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SetThemes.setThemes(this);
        setContentView(R.layout.activity_home);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        createTabIcons();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        centerToolbarTitle(toolbar);
        db = FirebaseFirestore.getInstance();
        allUsersRef = db.collection("UserList");


        //title_layout = (RelativeLayout) view.findViewById(R.id.title_layout);
//        textView=  view.findViewById(R.id.search_imb_title);
//        textView.setOnClickListener(this);
//        search_imb_title.setOnClickListener(this);
        //  new UploadDownloadHelper(this).saveValues("asa@s.com");
        //testUserLogin();
        findViewById(R.id.img_search).setOnClickListener(v -> {
            SearchActivity.actionStart(this);
        });
        connection = new SharingConnection(this);
        logoutHelper = new LogoutHelper(this, this);
        preferencesHelper = new SharedPreferencesHelper(this);
        facebookHelper = new FacebookHelper(this, this);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        SetThemes.ChangeNavigationItemTextColor(navigationView, this);
        //displaySelectedScreen(R.id.nav_home);
        SetThemes.ChangeToolbarColor(toolbar, this);
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_main);
        LinearLayout linearLayout = headerLayout.findViewById(R.id.nav_header_layout);
        SetThemes.ChangeNavigationHeaderColor(linearLayout, this);
        headerTextViewName = headerLayout.findViewById(R.id.tvTitle);
        circleImageView = headerLayout.findViewById(R.id.civProfile);
        navigationView.setNavigationItemSelectedListener(this);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_login = menu.findItem(R.id.nav_logout);

        if (preferencesHelper.isUserLogged()) {
            new SetUpImagesHelper(this).initiateUserData(circleImageView,headerTextViewName);

            nav_login.setTitle(getResources().getString(R.string.logout));
        } else {
            nav_login.setTitle(getResources().getString(R.string.login));
            circleImageView.setImageResource(R.drawable.app_logo);


        }

    }

    private void getAllUsersFromFireStore() {
        allUsersRef.orderBy("name").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    Log.e(TAG, "queryDocumentSnapshots is not empty: ");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        UserInfo userInfo = snapshot.toObject(UserInfo.class);
                        Log.e(TAG, "onEvent: Name " + userInfo.getName());
                        Log.e(TAG, "onEvent: Email " + userInfo.getEmail());
                    }

                    //
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    static void centerToolbarTitle(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(Color.parseColor("#ffffffff"));
            titleView.setTextSize(25);
            titleView.setGravity(Gravity.CENTER);
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
            //also you can use titleView for changing font: titleView.setTypeface(Typeface);
        }

    }


    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.exist_message))
                .positiveText(getResources().getString(R.string.yes))
                .negativeText(getResources().getString(R.string.no))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
                .build().show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //calling the method display selected screen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_profile:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_themes:
                startActivity(new Intent(HomeActivity.this, UserSettingActivity.class));
                break;
            case R.id.nav_rating:
                 connection.RateApp();
               // startActivity(new Intent(HomeActivity.this, LogInActivity.class));

                break;
            case R.id.nav_facebook:
                 connection.facebook();
                //startActivity(new Intent(HomeActivity.this, PhoneLoginActivity.class));

                break;
            case R.id.nav_whasapp:
                connection.whatsApp();
                break;
            case R.id.nav_github:
                connection.github();
                break;
            case R.id.nav_send_feedback:
                startActivity(new Intent(HomeActivity.this, SendFeedBackActivity.class));
                //  fragment = new SendFeedBackFragment();
                break;

            case R.id.nav_more_apps:
                //connection.MoreApp();
                startActivity(new Intent(HomeActivity.this, MoreAppsActivity.class));

                //fragment=new MoreAppsFragment();
                break;
            case R.id.nav_share:
                connection.ShareApp();
                break;
            case R.id.nav_about:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                break;
            case R.id.nav_logout:
                doLogout();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    private void doLogout() {
        if (preferencesHelper.isUserLogged()) {
            openLogoutDialog();
        } else {
            openNewLogoutDialog();
        }
    }




    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }


    private void openNewLogoutDialog() {
        newLogoutDialog = new MaterialDialog.Builder(HomeActivity.this)
                .title("تسجيل الخروج من التطبيق")
                .contentColor(Color.RED)
                .content("انت غير مُسجل اصلاً هل ترغب فعلا  تسجيل الدخول الى التطبيق؟")
                .positiveColor(Color.GREEN)
                .negativeColor(Color.BLUE)
                .positiveText("نعم")
                .negativeText("لا")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(HomeActivity.this, LogInActivity.class));
                    }
                })
                .show();
    }

    private void openLogoutDialog() {
        logoutDialog = new MaterialDialog.Builder(HomeActivity.this)
                .title("تسجيل الخروج من التطبيق")
                .contentColor(Color.RED)
                .content("هل ترغب فعلا في تسجيل الخروج من التطبيق؟ لن تكون قادراً على مشاهدة دليل الهواتف")
                .positiveColor(Color.GREEN)
                .negativeColor(Color.BLUE)
                .positiveText("نعم")
                .negativeText("لا")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        actualLogout();
                    }
                })
                .show();
    }

    @Override
    public void OnFbSuccess(GraphResponse graphResponse) {
        Log.d(TAG, "OnFbSuccess: ");
    }

    @Override
    public void OnFbError(String errorMessage) {
        Log.d(TAG, "OnFbError: errorMessage: " + errorMessage);
    }

    private void actualLogout() {
        preferencesHelper.saveIsUserLogin(false);
        //  logoutHelper.firebaseLogout();
        Log.d(TAG, "actualLogout type: " + preferencesHelper.getUserLoginType());
        switch (preferencesHelper.getUserLoginType()) {
            case "g":
                logoutHelper.googleLogout();
                break;
            case "f":
                logoutHelper.facebookLogout(facebookHelper);
                break;
            case "fr":
                logoutHelper.firebaseLogout();
                break;

        }
        nav_login.setTitle(getResources().getString(R.string.login));
        finish();
        startActivity(new Intent(this, LogInActivity.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferencesHelper.isUserLogged()) {
            allUsersRef.orderBy("name").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.e(TAG, "onEvent: " + e.toString());
                        return;
                    }


                    if (!queryDocumentSnapshots.isEmpty()) {
                        Log.e(TAG, "queryDocumentSnapshots is not empty: ");
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            UserInfo userInfo = snapshot.toObject(UserInfo.class);
                            Log.e(TAG, "onEvent: Name " + userInfo.getName());
                            Log.e(TAG, "onEvent: Email " + userInfo.getEmail());


                        }
                    }

                }
            });
        }


        //listenToChanges()
        if (!preferencesHelper.isUserLogged()) {
            startActivity(new Intent(this, LogInActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_search) {
            startActivity(new Intent(this, SearchActivity.class));
            //onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getString(R.string.contacts));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_person, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.favorite);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.phone));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_call, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        SetThemes.ChangeTabColor(tabLayout, this);
    }
    /*private void checkUserPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                //check if all permission are granted
                if (report.areAllPermissionsGranted()) {
                    initSocket();
                } else {
                    List<PermissionDeniedResponse> responses = report.getDeniedPermissionResponses();
                    StringBuilder permissionsDenied = new StringBuilder("Permissions denied: ");
                    for (PermissionDeniedResponse response : responses) {
                        permissionsDenied.append(response.getPermissionName()).append(" ") ;
                    }
                    showInfoMessageToast(permissionsDenied.toString());
                }

                if (report.isAnyPermissionPermanentlyDenied()) {
                    //permission is permanently denied navigate to user setting
                    AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("Need Permissions")
                            .setMessage("This application need to use some permissions, " +
                                    "you can grant them in the application settings.")
                            .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, 101);
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    dialog.show();

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        })
                .onSameThread()
                .check();

    }*/

}