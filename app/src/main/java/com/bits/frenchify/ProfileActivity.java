package com.bits.frenchify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextInputEditText etFirstName,etLastName,etNationality,etOccupation,etAge,etEmail;

    Toolbar toolbar;
    Button btnUpdateProfile;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    FirebaseUser firebaseUser;
    Boolean guestBro=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        readFireStore();
        updateProfile();
    }

    private void updateProfile() {

        btnUpdateProfile.setOnClickListener(view -> {

            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String nationality = etNationality.getText().toString();
            String occupation = etOccupation.getText().toString();
            String age = etAge.getText().toString();
            String email = etEmail.getText().toString();

            if (TextUtils.isEmpty(firstName)) {
                Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(lastName)) {
                Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(nationality)) {
                Toast.makeText(this, "Please enter nationality", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(occupation)) {
                Toast.makeText(this, "Please enter occupation", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(age)) {
                Toast.makeText(this, "Please enter age", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setMessage("Updating profile....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            DocumentReference documentReference= fireStore.collection("users").document(firebaseUser.getUid());

            Map<String , Object> user = new HashMap<>();
            user.put("UserName", firstName);
            user.put("FullName", lastName);
            user.put("Nationality", nationality);
            user.put("Occupation", occupation);
            user.put("Age", age);
            user.put("email", email);
            documentReference.set(user).addOnCompleteListener(this, results -> {
                if (results.isSuccessful()){
                    Toast.makeText(this, "User profile have been updated ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Something went wrong ! try again later ", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            });
        });
    }

    private void initViews() {

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etNationality = findViewById(R.id.et_nationality);
        etOccupation = findViewById(R.id.et_occupation);
        etAge = findViewById(R.id.et_age);
        etEmail = findViewById(R.id.et_email);
        toolbar=findViewById(R.id.toolbar);

        toolbar.setTitle("Update your Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,LearningOrAssessment.class);
                intent.putExtra("guestBro",guestBro);
                intent.putExtra("UserName",etFirstName.getText().toString());
                startActivity(intent);
                finish();
            }
        });


        btnUpdateProfile = findViewById(R.id.btn_update_profile);
    }
    @Override
    public void onBackPressed() {

       Intent intent = new Intent(ProfileActivity.this,LearningOrAssessment.class);
       intent.putExtra("guestBro",guestBro);
       intent.putExtra("UserName",etFirstName.getText().toString());
       startActivity(intent);
       finish();


    }

    public void readFireStore() {

        progressDialog.setMessage("Fetching details....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        DocumentReference docRef = fireStore.collection("users").document(firebaseUser.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    etFirstName.setText(doc.get("UserName").toString());

                    if(doc.contains("FullName")){
                         etLastName.setText(doc.get("FullName").toString());
                    }
                    if(doc.contains("Nationality")){
                        etNationality.setText(doc.get("Nationality").toString());
                    }
                    if(doc.contains("Occupation")){
                        etOccupation.setText(doc.get("Occupation").toString());
                    }
                    if(doc.contains("Age")){
                        etAge.setText(doc.get("Age").toString());
                    }



                    //etNationality.setText(doc.get("Nationality").toString());
                    //etOccupation.setText(doc.get("Occupation").toString());
                    //etAge.setText(doc.get("Age").toString());
                    etEmail.setText(doc.get("email").toString());
                }
            }
            progressDialog.dismiss();
        });
    }
}