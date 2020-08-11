package com.nibemi.firebaseiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    Button btn_cerrar, btn_actuador1, btn_actuador2;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceHumo;
    private DatabaseReference mReferenceTemp;
    private DatabaseReference mReferenceAct1;
    private DatabaseReference mReferenceAct2;

    private int estado_actuador1, estado_actuador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceHumo = mDatabase.getReference("humo");
        mReferenceTemp = mDatabase.getReference("temp");
        mReferenceAct1 = mDatabase.getReference("act1");
        mReferenceAct2 = mDatabase.getReference("act2");

        txt_temperatura = (TextView)findViewById(R.id.txt_temperatura);
        txt_humo = (TextView)findViewById(R.id.txt_humo);

        btn_cerrar = (Button)findViewById(R.id.btn_cerrar);
        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_actuador1 = (Button)findViewById(R.id.btn_actuador1);
        btn_actuador1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReferenceAct1.setValue(Math.abs(estado_actuador1 -1));
            }
        });

        btn_actuador2 = (Button)findViewById(R.id.btn_actuador2);
        btn_actuador2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReferenceAct2.setValue(Math.abs(estado_actuador2 -1));
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

        mReferenceAct1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int estado = Integer.parseInt(dataSnapshot.getValue().toString());
                estado_actuador1 = estado;
                if (estado_actuador1==0){
                    btn_actuador1.setText("PRENDER ACTUADOR 1");
                }else{
                    btn_actuador1.setText("APAGAR ACTUADOR 1");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                estado_actuador1 = 0;
            }
        });

        mReferenceAct2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int estado = Integer.parseInt(dataSnapshot.getValue().toString());
                estado_actuador2 = estado;
                if (estado_actuador2==0){
                    btn_actuador2.setText("PRENDER ACTUADOR 2");
                }else{
                    btn_actuador2.setText("APAGAR ACTUADOR 2");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                estado_actuador2 = 0;
            }
        });
    };
}