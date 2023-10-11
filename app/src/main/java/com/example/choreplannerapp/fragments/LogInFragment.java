package com.example.choreplannerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.adapters.ChildWeekAdapter;
import com.example.choreplannerapp.adapters.HomeChoreBaseAdapter;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Chore;

import java.util.ArrayList;

public class LogInFragment extends Fragment{
    private static final String ARG_LOGIN = "ARG_LOGIN";
    ChoreInterface choreListener;

    public static LogInFragment newInstance() {

        Bundle args = new Bundle();

        LogInFragment fragment = new LogInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ChoreInterface){
            choreListener = (ChoreInterface) context;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.log_in_layout, container, false);
    }





    //        binding = ActivitySignInScreenBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//     //   firebaseAuth = FirebaseAuth.getInstance()
//        binding.SignInBtn.setOnClickListener {
//            val intent = Intent(this, SignUpScreen::class.java)
//            startActivity(intent)
//        }
//
//        binding.button.setOnClickListener {
//            val email = binding.emailEt.text.toString()
//            val pass = binding.passET.text.toString()
//
//            if (email.isNotEmpty() && pass.isNotEmpty()) {
//
////                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
////                    if (it.isSuccessful) {
////                        val intent = Intent(this, MainActivity::class.java)
////                        startActivity(intent)
////                    } else {
////                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
////
////                    }
//                }
//            } else {
//                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }
//
//override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//}





}
