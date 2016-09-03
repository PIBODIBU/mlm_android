package com.android.multilevelmarketing.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.multilevelmarketing.R;
import com.android.multilevelmarketing.ui.activity.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentRegisterBank extends Fragment {
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.root_view_info_bank)
    public CoordinatorLayout rootView;

    /**
     * ERROR STRINGS
     **/


    /**
     * INTEGERS
     **/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fm_register_bank, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((RegisterActivity) getActivity()).setStatusBarColor(R.color.colorAccentDark);
        ((RegisterActivity) getActivity()).setNavigationBarColor(R.color.colorAccentDark);
    }

    @OnClick(R.id.btn_next)
    public void moveNext() {
        ((RegisterActivity) getActivity()).moveNextPage();
    }

    @OnClick(R.id.btn_previous)
    public void movePrev() {
        ((RegisterActivity) getActivity()).movePreviousPage();
    }

    private void removeErrorFromTIL(TextInputLayout... layouts) {
        for (TextInputLayout textInputLayout : layouts) {
            textInputLayout.setError("");
        }
    }
}
