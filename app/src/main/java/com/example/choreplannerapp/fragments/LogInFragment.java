package com.example.choreplannerapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.interfaces.ChoreInterface;
import com.example.choreplannerapp.objects.Adult;
import com.example.choreplannerapp.objects.Child;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class LogInFragment extends Fragment{
    private static final String ARG_LOGIN = "ARG_LOGIN";
    ChoreInterface choreListener;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    int RC_SIGN_IN = 40;

    ImageView googleBtn;


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

        EditText emailEntry = view.findViewById(R.id.login_email);
        EditText passwordEntry =  view.findViewById(R.id.login_password);
        Button signInButton =  view.findViewById(R.id.login_signin_button);
        googleBtn =  view.findViewById(R.id.google_btn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Creating Account!");
        progressDialog.setMessage("Bear with us a moment while your account is being created!");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //THIS IS NOT AN ERROR, IT RUNS, DO NOT TOUCH IT, DO NOT FIX IT
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        googleBtn.setOnClickListener(v -> {
            signIn();
        });
    }

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.log_in_layout, container, false);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
                // User is logged in on google platform
                Log.v("GOOGLETAG", "Account token:" + account.getIdToken()); //NON-NLS
                // Signed in successfully, show authenticated UI.

            } catch (ApiException e) {
                Log.w("GOOGLETAG", "Google sign in failed", e); //NON-NLS

                String messageToDisplay = "Authentication failed.";
                switch (e.getStatusCode()) {
                    case CommonStatusCodes.API_NOT_CONNECTED: //17
                        messageToDisplay += "The client attempted to clearPreferences a method from an API that failed to connect.";
                        Log.i("GOOGLETAG", "" + messageToDisplay);
                        break;

                    case CommonStatusCodes.DEVELOPER_ERROR: //10
                        messageToDisplay += "The application is misconfigured.";
                        Log.i("GOOGLETAG", "" + messageToDisplay);
                        break;

                    case CommonStatusCodes.ERROR: //13
                        messageToDisplay += "The operation failed with no more detailed information.";
                        Log.i("GOOGLETAG", "" + messageToDisplay);
                        break;

                    case CommonStatusCodes.INTERNAL_ERROR: //8
                        messageToDisplay += "An internal error occurred.";
                        Log.i("GOOGLETAG", "" + messageToDisplay);
                        break;

                    case CommonStatusCodes.INVALID_ACCOUNT: //8
                        messageToDisplay += "Invalid account name specified.";
                        Log.i("GOOGLETAG", "" + messageToDisplay);
                        break;

                    case CommonStatusCodes.SIGN_IN_REQUIRED: //8
                        messageToDisplay += "Please Sign In to continue.";
                        Log.i("GOOGLETAG", "" + messageToDisplay);
                        break;
                }
            }
        }
    }

    private void firebaseAuth(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Adult adult = new Adult();
                        assert user != null;
                        adult.setUserId(user.getUid());
                        adult.setName(user.getDisplayName());
                        adult.setProfile(Objects.requireNonNull(user.getPhotoUrl()).toString());

//                        ArrayList<Child> children = new ArrayList<>();
//                        children.add(new Child("EMPTY", 10, 0));
//                        adult.setChildrenList(children);

                        database.getReference().child("Users").child(user.getUid()).setValue(adult);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.log_in_fragment_container, ProfileCreationFragment.newInstance())
                                .addToBackStack("create")
                                .commit();

                    } else {
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
