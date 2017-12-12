package br.com.silas.letusknow.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.AppCompatRadioButton;

public class LetUsKnowRadioButton extends AppCompatRadioButton {

    @SuppressLint("RestrictedApi")
    public LetUsKnowRadioButton(Context context) {
        super(context);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Color.LTGRAY,
                        Color.rgb(48, 63, 159),
                }
        );
        super.setSupportButtonTintList(colorStateList);
        super.setTextColor(Color.BLACK);
        super.setTextSize(20);
    }

}
