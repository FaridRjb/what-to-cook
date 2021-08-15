package com.faridrjb.whattocook.fragments.introslider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.faridrjb.whattocook.R;

public class SlideLogoFragment extends Fragment {

    private CallBacks activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logo_slide, container, false);
        Button next = rootView.findViewById(R.id.btnNext101);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.nextClicked(v.getId());
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CallBacks) getActivity();
    }

    public interface CallBacks {
        void nextClicked(int nextBtnId);
    }
}
