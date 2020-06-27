package org.techtown.mediclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EffectinfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effectinfo);

        String EE_DOC_DATA = getIntent().getStringExtra("EE_DOC_DATA"); //효능효과
        Log.e("EE_DOC_DATA", EE_DOC_DATA);

        TextView textView36 = (TextView)findViewById(R.id.textView36);
        textView36.setText(EE_DOC_DATA);
    }
}
