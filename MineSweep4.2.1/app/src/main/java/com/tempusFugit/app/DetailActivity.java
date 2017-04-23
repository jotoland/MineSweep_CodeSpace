package com.tempusFugit.app;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] members = resources.getStringArray(R.array.member_names);
        collapsingToolbar.setTitle(members[postion % members.length]);

        String[] memberDetails = resources.getStringArray(R.array.member_major);
        TextView memDetails = (TextView) findViewById(R.id.major);
        memDetails.setText(memberDetails[postion % memberDetails.length]);

        String[] memberGrad = resources.getStringArray(R.array.member_grad);
        TextView memGrad = (TextView) findViewById(R.id.grad);
        memGrad.setText(memberGrad[postion % memberGrad.length]);

        String[] memBio = resources.getStringArray(R.array.member_bio);
        TextView memDesc = (TextView) findViewById(R.id.desc);
        memDesc.setText(memBio[postion % memBio.length]);

        String[] memEmail = resources.getStringArray(R.array.member_emails);
        TextView memberEmail =  (TextView) findViewById(R.id.email);
        memberEmail.setText(memEmail[postion % memEmail.length]);

        TypedArray backgroundPic = resources.obtainTypedArray(R.array.background_picture);
        ImageView bPic = (ImageView) findViewById(R.id.image);
        bPic.setImageDrawable(backgroundPic.getDrawable(postion % backgroundPic.length()));

        backgroundPic.recycle();
    }
}
