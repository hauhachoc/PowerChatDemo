package com.example.hautran.myapplication.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.example.hautran.myapplication.ChatApplication;
import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.utils.ShowLog;
import com.example.hautran.myapplication.utils.Utility;
import com.example.hautran.myapplication.views.BaseView;
import com.example.hautran.myapplication.widget.DialogHelper;

import butterknife.ButterKnife;

/**
 * Created by hautran on 16/08/17.
 */

public abstract class AbstractFragment extends Fragment implements BaseView {


    protected String TAG = "";

    protected AbstractActivity mActivity;

    protected int mContainerID = -1;

    protected View mRootView;

    protected boolean firstLoad = true;

    protected boolean isLoadLayout = true;
    /**
     * boolean true if was clicked on item and wait for excuse, don't allow click multiple times
     */
    protected boolean isClicked = false;
    protected DialogHelper dialogHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        ShowLog.showLogDebug(TAG + this.getClass().getSimpleName(), "=====onCreate=====");

        mActivity = (AbstractActivity) getActivity();
        dialogHelper = new DialogHelper(mActivity);
        setHasOptionsMenu(true);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowLog.showLogDebug(TAG, "=====onCreateView=====" + getClass().getSimpleName());
        if (isLoadLayout) {
            if (firstLoad) {
                if (mRootView == null) {
                    mRootView = inflater.inflate(getViewLayoutId(), container, false);
                }
                ShowLog.showLogDebug(TAG, "=====onCreateView===== FirstLoad");
                isClicked = false;
                ButterKnife.bind(this, mRootView);
            }
        }
        isLoadLayout = true;
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShowLog.showLogDebug(TAG, "=====onViewCreated=====");
        hideKeyboardUI(view);
        if (firstLoad) {
            ShowLog.showLogDebug(TAG, "=====onViewCreated===== firstLoad");
            mContainerID = mActivity.getViewContainerId();
            setOnBackPressListener();

            initViewFragment();
//            firstLoad = false;
        }
        initTittleBar();
    }

    public void setLoadLayout(boolean loadLayout) {
        isLoadLayout = loadLayout;
    }

    public void setFirstLoad(boolean firstLoad) {
        this.firstLoad = firstLoad;
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            if (view instanceof LinearLayout ||
                    view instanceof RelativeLayout ||
                    view instanceof FrameLayout) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideSoftKeyboard(v);
                    }
                });
            } else {
                view.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(v);
                        return false;
                    }

                });
            }

        }

        //If a item_spinner container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public void hideKeyboardUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
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

    public void hideSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();
    /*
     * If no view is focused, an NPE will be thrown
     *
     * Maxim Dmitriev
     */
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initTittleBar();
    }

    /**
     * init item_spinner for action bar.
     */
    protected abstract void initTittleBar();

    /**
     * for child fragment set item_spinner id.
     *
     * @return
     */
    protected abstract int getViewLayoutId();

    /**
     * function for extend fragment init view
     */
    protected abstract void initViewFragment();


    /* function use for activity call to fragment after activity complete other function */
    public void callbackFromActivity() {
    }

    ;


    @Override
    public void onPause() {
        super.onPause();
        ShowLog.showLogDebug(TAG, "=====onPause=====");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShowLog.showLogDebug(TAG, "=====onDestroy=====");
    }

    @Override
    public void onResume() {
        super.onResume();
        isClicked = false;
        ShowLog.showLogDebug(TAG, "=====onResume=====");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ShowLog.showLogDebug(TAG, "=====onDestroyView=====");

    }

    /**
     * ham back ve fragment/acitvity truoc do
     */
    public void backActivity() {
        Utility.hideInputKeyboard(mActivity);
        mActivity.backActivity();
    }

    public void pushFragment(Fragment fragment, boolean addBackStack) {
        pushFragment(fragment, addBackStack, true);
    }

    public void pushFragment(Fragment fragment, boolean addBackStack, boolean animation) {
        if (mActivity != null)
            mActivity.pushFragment(fragment, false, addBackStack, animation);
    }

    /**
     * ham replace sang mot fragment khac voi animation slide ngang
     *
     * @param fragment
     */
    public void addToBackStack(Fragment fragment) {
        addToBackStack(fragment, null);
    }

    /**
     * ham replace sang mot fragment khac voi animation slide ngang, truyen data bundle sang fragment moi tao
     *
     * @param fragment
     * @param args     data truyen di
     */
    public void addToBackStack(Fragment fragment, Bundle args) {
        if (args != null)
            fragment.setArguments(args);

        Utility.hideInputKeyboard(mActivity);
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.fade_out
                        , R.anim.fade_in, R.anim.slide_out_right)
                .replace(mActivity.getViewContainerId(), fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getName())
                .commitAllowingStateLoss();
    }


    public void addFragmentNotBackStack(Fragment fragment, Bundle args) {
        if (args != null)
            fragment.setArguments(args);

        Utility.hideInputKeyboard(mActivity);
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out
                        , R.anim.fade_in, R.anim.fade_out)
                .replace(mActivity.getViewContainerId(), fragment, fragment.getClass().getName())
                .commitAllowingStateLoss();
    }

    /**
     * ham replace sang mot fragment khac voi animation slide ngang
     *
     * @param fragment
     */
    protected void addToBackStackBottomToTop(Fragment fragment) {
        addToBackStackBottomToTop(fragment, null);
    }

    /**
     * ham replace sang mot fragment khac voi animation slide ngang, truyen data bundle sang fragment moi tao
     *
     * @param fragment
     * @param args     data truyen di
     */
    protected void addToBackStackBottomToTop(Fragment fragment, Bundle args) {
        if (args != null)
            fragment.setArguments(args);

        Utility.hideInputKeyboard(mActivity);
        if (getParentFragment() != null) {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out
                            , R.anim.fade_in, R.anim.slide_out_bottom)
                    .replace(mContainerID, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commitAllowingStateLoss();
        } else {
            mActivity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out
                            , R.anim.fade_in, R.anim.slide_out_bottom)
                    .replace(mContainerID, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commitAllowingStateLoss();
        }
    }


    /**
     * function replace fragment dont add to back stack, cannot back to this
     *
     * @param fragment
     * @param args
     */
    protected void replaceFragment(Fragment fragment, Bundle args) {
        if (args != null)
            fragment.setArguments(args);

        Utility.hideInputKeyboard(mActivity);
        if (getParentFragment() != null) {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out
                            , R.anim.fade_in, R.anim.fade_out)
                    .replace(mContainerID, fragment)
                    .commitAllowingStateLoss();
        } else {
            mActivity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(mContainerID, fragment)
                    .commitAllowingStateLoss();
        }
    }

    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, null);
    }


    /**
     * ham replace sang mot fragment khac voi animation tu tu hien ra
     *
     * @param fragment
     */
    protected void addToBackStackAnimAlpha(Fragment fragment) {
        Utility.hideInputKeyboard(mActivity);
        mActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out
                        , R.anim.fade_in, R.anim.fade_out)
                .replace(mContainerID, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    protected void clearBackStack() {

        while (mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            mActivity.getSupportFragmentManager().popBackStackImmediate();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void setOnBackPressListener() {
        mActivity.setOnBackPressedListener(new AbstractActivity.OnBackPressedListener() {
            @Override
            public void doBack() {
                backActivity();
            }
        });
    }

    @Override
    public void showProcessLoading() {
        mActivity.showProcessBar();
    }

    @Override
    public void hideProcessLoading() {
        mActivity.hideProcessBar();
    }

    public void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void hideKeyboard() {
        mActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public int getScreenOrientation() {
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
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
            switch (rotation) {
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



}
