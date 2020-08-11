package com.nibemi.firebaseiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView txt_temperatura, txt_humo;
    Button btn_cerrar;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceHumo;
    private DatabaseReference mReferenceTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceHumo = mDatabase.getReference("humo");
        mReferenceTemp = mDatabase.getReference("temp");

        txt_temperatura = (TextView)findViewById(R.id.txt_temperatura);
        txt_humo = (TextView)findViewById(R.id.txt_humo);

        btn_cerrar = (Button)findViewById(R.id.btn_cerrar);
        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mReferenceHumo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valor_humo = dataSnapshot.getValue().toString();
                txt_humo.setText(valor_humo+" %");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                txt_humo.setText("0 %");
            }
        });

        mReferenceTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valor_temp = dataSnapshot.getValue().toString();
                txt_temperatura.setText(valor_temp+" °C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                txt_temperatura.setText("0 °C");
            }
        });
    };
}