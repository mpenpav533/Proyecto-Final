package com.example.songolee.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.songolee.R;
import com.example.songolee.Vista.GameActivity;
import com.example.songolee.databinding.ActivityInfoBinding;

public class InfoActivity extends AppCompatActivity {
    public ActivityInfoBinding binding;// Vincular la vista con la actividad
    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Recogemos la informacion del usuario que viene del registro
        bundle= this.getIntent().getExtras();
        binding.btnstartgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentR = getIntent();
                    Intent intentG = new Intent(getApplicationContext(), GameActivity.class);
                    intentG.putExtras(bundle);
                    startActivity(intentG);


            }
        });
    }
}