package org.techtown.mediclock;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Search_List extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == R.id.gotohome) {
            Intent homeIntent = new Intent(this, Mainmenu.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_search_list);
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff006aff));
        getSupportActionBar().setTitle("약정보목록");
        //액션바 배경색 변경#368AFF

        String ITEM_NAME = getIntent().getStringExtra("item_name");
        TextView textView = (TextView)findViewById(R.id.item_name);
        textView.setText(ITEM_NAME);

//        String ud = getIntent().getStringExtra("UD_DOC_DATA");
//        Log.d("ud", ud);

        Button button_search = findViewById(R.id.result_btn1);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_search = new Intent(getApplicationContext(), BasicinfoActivity.class);

                intent_search.putExtra("item_name", getIntent().getStringExtra("item_name"));
                intent_search.putExtra("entp_name", getIntent().getStringExtra("entp_name"));
                intent_search.putExtra("etc_otc_code", getIntent().getStringExtra("etc_otc_code"));
                intent_search.putExtra("item_permit_date", getIntent().getStringExtra("item_permit_date"));
                intent_search.putExtra("ENTP_NO", getIntent().getStringExtra("ENTP_NO"));
                intent_search.putExtra("bar_code", getIntent().getStringExtra("bar_code"));
                intent_search.putExtra("item_seq", getIntent().getStringExtra("item_seq"));
                intent_search.putExtra("chart", getIntent().getStringExtra("chart"));
                intent_search.putExtra("PACK_UNIT", getIntent().getStringExtra("PACK_UNIT"));
                intent_search.putExtra("PERMIT_KIND_NAME", getIntent().getStringExtra("PERMIT_KIND_NAME"));
                intent_search.putExtra("CANCEL_DATE", getIntent().getStringExtra("CANCEL_DATE"));
                intent_search.putExtra("MAKE_MATERIAL_FLAG", getIntent().getStringExtra("MAKE_MATERIAL_FLAG"));
                intent_search.putExtra("INDUTY_TYPE", getIntent().getStringExtra("INDUTY_TYPE"));
                intent_search.putExtra("CHANGE_DATE", getIntent().getStringExtra("CHANGE_DATE"));
                intent_search.putExtra("INGR_NAME", getIntent().getStringExtra("INGR_NAME"));

                startActivity(intent_search);
            }
        });

        Button result_btn2 = findViewById(R.id.result_btn2);
        result_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_btn2 = new Intent(getApplicationContext(), EffectinfoActivity.class);
                intent_btn2.putExtra("EE_DOC_DATA", getIntent().getStringExtra("EE_DOC_DATA"));
                startActivity(intent_btn2);
            }
        });



    }
}
