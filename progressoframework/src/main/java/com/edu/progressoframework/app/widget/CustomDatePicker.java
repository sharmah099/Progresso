package com.edu.progressoframework.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.DatePicker;

/**
 * Custom class used for the date piker for 5.0 Lollipop
 * device to handle the scroll for date picker
 */
public class CustomDatePicker extends DatePicker
{
    /**
     * Constructor
     */
    public CustomDatePicker(Context context)
    {
        super(context);
    }

    /**
     * Constructor
     */
    public CustomDatePicker(Context context, AttributeSet attrs)
    {
        super(context, attrs, android.R.attr.datePickerStyle);
    }

    /**
     * Function called to handle the touch events of datepicker view itself.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            ViewParent p = getParent();
            if (p != null) {
                p.requestDisallowInterceptTouchEvent(true);
            }
        }

        return false;
    }
}
