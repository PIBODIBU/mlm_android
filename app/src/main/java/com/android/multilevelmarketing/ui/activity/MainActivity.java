package com.android.multilevelmarketing.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.multilevelmarketing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseNavDrawerActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDrawer();
    }
}
