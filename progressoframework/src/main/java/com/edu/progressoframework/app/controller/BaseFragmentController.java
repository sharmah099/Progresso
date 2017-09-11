package com.edu.progressoframework.app.controller;

import android.content.Intent;
import android.os.Bundle;

import com.edu.progressoframework.app.fragment.BaseFragment;

import org.androidannotations.annotations.EBean;

/**
 * The base class for all controllers of the application
 * where fragments are used.
 */
@EBean
public abstract class BaseFragmentController extends BaseController
{
    /**
     * The fragment associated with this controller.
     */
    protected BaseFragment fragment;

    public void setFragment(BaseFragment fragment)
    {
        this.fragment = fragment;
    }

    /**
     * Overrides BaseController's default behaviour of
     * making the current activity start the intended
     * activity and makes the fragment start it instead.
     */
    @Override
    protected void innerStartActivityForResult(Intent intent, int requestCode)
    {
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * Overrides BaseController's default behaviour of
     * making the current activity start the intended
     * activity and makes the fragment start it instead.
     */
    @Override
    protected void innerStartActivityForResult(Intent intent, int requestCode, Bundle bundle)
    {
        fragment.startActivityForResult(intent, requestCode, bundle);
    }
}
