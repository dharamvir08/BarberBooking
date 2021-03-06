package android.example.barberbooking.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.example.barberbooking.MainActivity;
import android.example.barberbooking.R;
import android.example.barberbooking.common.Common;
import android.example.barberbooking.model.VendorModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SubmitFormActivity extends AppCompatActivity {
    TextView name, sName, address, aadhar, city, phone, gender, servicesTxt, editBtn, refernceId, home;
    CheckBox agreeChkBx;
    Button submitBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    LinearLayout linearLayout1;


    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_form);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("vendordetails");


        initialize();
        getData();
        submit();
        edit();

    }

    private void edit() {

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.isEdit = true;

                Intent intent = new Intent(SubmitFormActivity.this, VendorDetail.class);
                startActivity(intent);
            }
        });


    }


    private void initialize() {
        name = findViewById(R.id.oNameSbmt);
        sName = findViewById(R.id.saloonNameSbmt);
        aadhar = findViewById(R.id.aadharSbmt);
        phone = findViewById(R.id.phoneSbmt);
        address = findViewById(R.id.addressSbmt);
        city = findViewById(R.id.citySbmt);
        gender = findViewById(R.id.genderSbmt);
        editBtn = findViewById(R.id.editBtn);
        servicesTxt = findViewById(R.id.f1);
        agreeChkBx = findViewById(R.id.agreeSubmitCheckBox);
        submitBtn = findViewById(R.id.submitFormBtn);
        refernceId = findViewById(R.id.refernceIdTxt);

        home = findViewById(R.id.returnHome);

        linearLayout1 = findViewById(R.id.linerarLayout1);

    }

    private void getData() {

        name.setText(Common.currentVendor.getOwnerName());
        sName.setText(Common.currentVendor.getSaloonName());
        address.setText(Common.currentVendor.getAddress());
        city.setText(Common.currentVendor.getCity());
        phone.setText(Common.currentVendor.getPhoneNo());
        aadhar.setText(Common.currentVendor.getAadharNo());

        gender.setText(Common.currentVendor.getGender());
        servicesTxt.setText(Common.currentVendor.getServiceList());

    }


    private void submit() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!agreeChkBx.isChecked()) {
                    agreeChkBx.requestFocus();
                    Toast.makeText(getApplicationContext(), "Please tick the box to continue", Toast.LENGTH_SHORT).show();
                } else {
                    uploadToFirebase();


                    submitBtn.setVisibility(View.GONE);
                    editBtn.setVisibility(View.GONE);
                    refernceId.setVisibility(View.VISIBLE);
                    agreeChkBx.setVisibility(View.GONE);
                    home();


                }
            }


        });
    }

    private void uploadToFirebase() {
        String id = String.valueOf(System.currentTimeMillis());
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        VendorModel vendorModel = Common.currentVendor;
        vendorModel.setTime(currentDateTimeString);
        vendorModel.setStatus("Pending");
        reference.child(id).setValue(vendorModel);
        refernceId.setText("Reference Id : " + id);
        Toast.makeText(getApplicationContext(), "Request Sent successfully with refernce id " + id, Toast.LENGTH_SHORT).show();

    }

    private void home() {
        home.setVisibility(View.VISIBLE);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitFormActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }


}


