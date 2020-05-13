package com.dlut.picturemaker.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.blankj.utilcode.util.IntentUtils;
import com.dlut.picturemaker.ViewModel.ImageViewModel;
import com.dlut.picturemaker.R;
import com.easytools.tools.BitmapUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class OutputFragment extends Fragment {
    private static final String TAG = "output";
    private ImageViewModel model;
    private ImageView imageViewThumb;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_people).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.clearPerson();
                NavHostFragment.findNavController(OutputFragment.this)
                        .navigateUp();
            }
        });
        view.findViewById(R.id.button_background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.clearPerson();

                NavHostFragment.findNavController(OutputFragment.this)
                        .navigate(R.id.action_outputFragment_to_FirstFragment);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = IntentUtils.getShareImageIntent("分享", model.getFinishedImageUri());
            startActivity(intent);

        });
        imageViewThumb = view.findViewById(R.id.image_thumb);
        imageViewThumb.setImageBitmap(BitmapUtils.getBitmapFromUri(getContext(),model.getFinishedImageUri()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(ImageViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_output, container, false);
    }

}
