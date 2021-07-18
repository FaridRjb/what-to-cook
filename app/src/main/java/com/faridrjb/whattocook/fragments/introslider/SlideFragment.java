package com.faridrjb.whattocook.fragments.introslider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.faridrjb.whattocook.R;

public class SlideFragment extends Fragment {

    public static SlideFragment newSlide(int imgResId, String title, String description, @Nullable String btnNext) {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putInt("imgResId", imgResId);
        args.putString("title", title);
        args.putString("description", description);
        args.putString("btnNext", btnNext);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide, container, false);
        Button next = rootView.findViewById(R.id.btnNext102);
        if (getArguments() != null) {
            ((ImageView) rootView.findViewById(R.id.logoImage102)).setImageResource(getArguments().getInt("imgResId"));
            ((TextView) rootView.findViewById(R.id.tv103)).setText(getArguments().getString("title"));
            ((TextView) rootView.findViewById(R.id.tv104)).setText(getArguments().getString("description"));

            if (getArguments().getString("btnNext") != null) next.setText(getArguments().getString("btnNext"));
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideLogoFragment.CallBacks activity = (SlideLogoFragment.CallBacks) getActivity();
                activity.nextClicked(v.getId());
            }
        });
        return rootView;
    }
}
