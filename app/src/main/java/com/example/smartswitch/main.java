package com.example.smartswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class main extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button jixie=findViewById(R.id.jixie);
        Button smart=findViewById(R.id.smart);
        final Intent intent2=new Intent(this,BluetoothClientActivity_mytimer_auto.class);
        final Intent intent1=new Intent(this,BluetoothClientActivity_mytimer_machinery.class);
        jixie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(intent1);
               finish();
            }
        });
        smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
                finish();
            }
        });

    }
}
