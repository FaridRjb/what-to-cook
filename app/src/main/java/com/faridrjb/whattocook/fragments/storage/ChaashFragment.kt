package com.faridrjb.whattocook.fragments.storage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.recyclerviewadapters.StorageItemsAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ChaashFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_storage, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.itemList1);

        ArrayList<String> itemList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.chaashni_names)));
        Collator collator = Collator.getInstance(new Locale("fa", "IR"));
        collator.setStrength(Collator.PRIMARY);
        Collections.sort(itemList, collator);

        StorageItemsAdapter adapter = new StorageItemsAdapter(getContext(), itemList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return rootView;
    }
}
