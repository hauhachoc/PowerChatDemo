package com.example.hautran.myapplication.presentation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.hautran.myapplication.ChatApplication;
import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.utils.ShowLog;
import com.example.hautran.myapplication.widget.CustomProgressHorizontalView;

import butterknife.ButterKnife;

/**
 * Created by hautran on 16/08/17.
 */

public abstract class AbstractActivity extends AppCompatActivity {

    protected String TAG = "cAbstractActivity";

    private CustomProgressHorizontalView progressView;

    public interface OnBackPressedListener {
        public void doBack();
    }

    /** listener call to fragment when press back device */
    private OnBackPressedListener onBackPressedListener;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        ActivityHistoryManager.addNewActivity(this);
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        setContentView(getViewLayoutId());
        getViewContainerId();
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initView(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    protected abstract int getViewLayoutId();

    public abstract int getViewContainerId();

    protected void initView(final Bundle savedInstanceState) {

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ShowLog.showLogInfo(TAG, "onPause ===== cAbstractActivity ======= " + this.getClass().getSimpleName());
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        ShowLog.showLogMemory(TAG, " onResume " + this.getClass().getSimpleName() + " == ");
        ShowLog.showLogInfo(TAG, "onResume ====== cAbstractActivity =========" + this.getClass().getSimpleName());
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowLog.showLogInfo(TAG, " onDestroy ============= " + this.getClass().getSimpleName());
        ActivityHistoryManager.removeFromActivityHistory(this);

        if(progressView != null) {
            progressView.dismiss();
            progressView = null;
        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        ShowLog.showLogInfo(TAG, " onStop ============= " + this.getClass().getSimpleName());
//        ChatApplication.getFirebaseUserInstance().delete();
    }

    @Override
    public void startActivity(final Intent intent) {
        // TODO Auto-generated method stub
        ShowLog.showLogInfo(TAG, "startActivity : " + this.getClass().getName());
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
        ShowLog.showLogInfo(TAG, "startActivityFromFragment : " + this.getClass().getName());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        // TODO Auto-generated method stub
        ShowLog.showLogInfo(TAG, "startActivityForResult : " + this.getClass().getName());
        super.startActivityForResult(intent, requestCode);
    }

    public void backActivity() {
        ShowLog.showLogInfo(TAG, "========== backActivity ==========");
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        } else if (getFragmentManager().getBackStackEntryCount() >= 1) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
        overridePendingTransition(0, R.anim.slide_out_right);
    }


    @Override
    public void onBackPressed() {
        if(onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            backActivity();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        ShowLog.showLogInfo(TAG, "gc " + getClass().getSimpleName() + "; onConfigurationChanged " + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShowLog.showLogInfo(TAG, "========== onActivityResult;requestCode = "
                + requestCode + "; resultCode : " + resultCode+ " ==========");
    }

    /**
     * function show progress horizontal view
     */
    public void showProcessBar() {
        if(progressView == null){
            progressView = new CustomProgressHorizontalView(this);
        }
        if (!isFinishing()) {
            progressView.show();
        }
    }

    /**
     * function hid progress horizontal view
     */
    public void hideProcessBar() {
        if(progressView != null) {
            progressView.dismiss();
            progressView = null;
        }
    }

    /**
     * push fragment
     * @param fragment
     * @param addBackStack
     * @param animation
     */
    public void pushFragment(Fragment fragment, boolean addBackStack, boolean animation) {
        pushFragment(fragment, false, addBackStack, animation);
    }

    /**
     * push fragment
     * @param fragment
     * @param clearStack
     * @param addToStack
     * @param animation
     */
    public void pushFragment(Fragment fragment,
                             Boolean clearStack,
                             Boolean addToStack,
                             boolean animation) {
        if (clearStack) {
            clearAllFragments();
        }

        if (fragment != null) {
            replaceFragment(fragment, addToStack, animation);
        }

    }

    /**
     * get fragment manager
     * @return
     */
    private FragmentManager fragmentManager(){
        return getSupportFragmentManager();
    }

    /**
     * replace fragment
     * @param fragment
     * @param addToBackStack
     * @param animation
     */
    protected void replaceFragment(Fragment fragment, Boolean addToBackStack, boolean animation) {

        int idContainer = getViewContainerId();
        if(idContainer<=0)
            return;

        FragmentManager fm = fragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //ft.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_left);
        if (animation) {
//            ft.setCustomAnimations(R.anim.slide_in_right,
//                    R.anim.slide_out_left,
//                    R.anim.slide_in_left,
//                    R.anim.slide_out_right);

            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.fade_out
                        , R.anim.fade_in, R.anim.slide_out_right);
        } else {
            /*ft.setCustomAnimations(R.anim.fade_in,
                    R.anim.fade_out);*/
            //ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        }

        ft.replace(idContainer, fragment, fragment.getClass().getName());

        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }

        ft.commitAllowingStateLoss();
    }

    /**
     * get current fragment
     * @return
     */
    public Fragment getCurrentFragment() {
        int idContainer = getViewContainerId();
        if(idContainer<=0)
            return null;

        FragmentManager fragmentManager = fragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(idContainer);
        return fragment;
    }

    public void clearAllFragments() {
        FragmentManager fm = fragmentManager();
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
            fm.beginTransaction().commitAllowingStateLoss();
        }
    }

    public void clearAllDetailFragments() {
        FragmentManager fm = fragmentManager();
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
            fm.beginTransaction().commitAllowingStateLoss();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void hideKeyboardUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AbstractActivity.this);
                    return false;
                }
            });
        }

        //If a item_spinner container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardUI(innerView);
            }
        }
    }

    private int getScreenOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    Log.e(TAG, "Unknown screen orientation. Defaulting to " +
                            "portrait.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    Log.e(TAG, "Unknown screen orientation. Defaulting to " +
                            "landscape.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    private View mTouchOutsideView;

    private OnTouchOutsideViewListener mOnTouchOutsideViewListener;


    public void setOnTouchOutsideViewListener(View view, OnTouchOutsideViewListener onTouchOutsideViewListener) {
        mTouchOutsideView = view;
        mOnTouchOutsideViewListener = onTouchOutsideViewListener;
    }

    public OnTouchOutsideViewListener getOnTouchOutsideViewListener() {
        return mOnTouchOutsideViewListener;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // Notify touch outside listener if user tapped outside a given view
            if (mOnTouchOutsideViewListener != null && mTouchOutsideView != null
                    && mTouchOutsideView.getVisibility() == View.VISIBLE) {
                Rect viewRect = new Rect();
                mTouchOutsideView.getGlobalVisibleRect(viewRect);
                if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    mOnTouchOutsideViewListener.onTouchOutside(mTouchOutsideView, ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Interface definition for a callback to be invoked when a touch event has occurred outside a formerly specified
     * view. See {@link #setOnTouchOutsideViewListener(View, OnTouchOutsideViewListener).}
     */
    public interface OnTouchOutsideViewListener {

        /**
         * Called when a touch event has occurred outside a given view.
         *
         * @param view  The view that has not been touched.
         * @param event The MotionEvent object containing full information about the event.
         */
        public void onTouchOutside(View view, MotionEvent event);
    }


}
