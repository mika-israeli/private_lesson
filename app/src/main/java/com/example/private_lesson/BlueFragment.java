
package com.example.private_lesson;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.private_lesson.databinding.FragmentBlueBinding;

public class BlueFragment extends Fragment {
    TextView titleTv;
    String title;
    String postID;

    FragmentBlueBinding binding;

    public static BlueFragment newInstance(String title){
        BlueFragment frag = new BlueFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            this.title = bundle.getString("TITLE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        postID = getArguments().getString("postID");
        binding = FragmentBlueBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        title= BlueFragmentArgs.fromBundle(getArguments()).getBlueTitle();
        TextView titleTv = view.findViewById(R.id.bluefrag_title_tv);


        if (title != null){
            titleTv.setText("");
        }

        View button = view.findViewById(R.id.bluefrag_back_btn);
        button.setOnClickListener((view1)->{
            Navigation.findNavController(view1).popBackStack();
        });

        binding.editPostBtn.setOnClickListener((view1)->{
            NavDirections action = BlueFragmentDirections.actionBlueFragmentToEditPostFragment(postID);
            Navigation.findNavController(view1).navigate(action);
        });


        return view;
    }

    public void setTitle(String title) {
        this.title = title;
        if (titleTv != null){
            titleTv.setText(title);
        }
    }
}