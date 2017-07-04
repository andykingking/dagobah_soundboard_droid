package com.andrewsking.dagobahsoundboard;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

public class TextViewColorAssertion implements ViewAssertion {

    private int color;

    public TextViewColorAssertion(int colorId) {
        Context context = InstrumentationRegistry.getTargetContext();
        this.color = ContextCompat.getColor(context, colorId);
    }
    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (!(view instanceof TextView)) throw new AssertionError("View is not a TextView");

        int foundColor = ((TextView) view).getCurrentTextColor();
        if (foundColor != this.color) throw new AssertionError(String.format("Expected color to be %d, found %d", this.color, foundColor));
    }
}