package com.example.choreplannerapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.objects.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    int RC_SIGN_IN = 40;

    ImageView googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);

        EditText emailEntry = findViewById(R.id.login_email);
        EditText passwordEntry = findViewById(R.id.login_password);
        Button signInButton = findViewById(R.id.login_signin_button);
        googleBtn = findViewById(R.id.google_btn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LogInActivity.this);
        progressDialog.setTitle("Creating Account!");
        progressDialog.setMessage("Bear with us a moment while your account is being created!");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //THIS IS NOT AN ERROR, IT RUNS, DO NOT TOUCH IT, DO NOT FIX IT
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(v -> {
            signIn();
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            assert user != null;
                            users.setUserId(user.getUid());
                            users.setName(user.getDisplayName());
                            users.setProfile(Objects.requireNonNull(user.getPhotoUrl()).toString());

                            database.getReference().child("Users").child(user.getUid()).setValue(users);

                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
