package org.techtown.mediclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TextresultActivity extends AppCompatActivity implements RecyclerviewAdapter.onItemListener, TextWatcher {

    public String requestUrl;
    Recent bus = null;
    public String key="BZAkHyL1OvsaKk4INUgYd1ra39ts5cl%2BDojvvOH%2BQkW3FCIifva%2FTa5ZTKvrIt03W97NKmFMZH4Oq%2B6jIwy5bA%3D%3D";
    private RecyclerviewAdapter adapter;
    ArrayList<Recent> mList;
    public String barcodeinfo;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textresult);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        editText = (EditText) findViewById(R.id.editText);
        if(getIntent().getStringExtra("BAR_CODE_info") != null) {
            barcodeinfo = getIntent().getStringExtra("BAR_CODE_info");
            Log.e("barcodeinfo", barcodeinfo);
            editText.setText(barcodeinfo);
        }
        editText.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);

    }

    @Override
    public void afterTextChanged(Editable s) {
        String filterText = s.toString() ;
        adapter.getFilter().filter(filterText);
    }

    //@Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "클릭" + position, Toast.LENGTH_SHORT).show();
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        while (parser.next() != XmlPullParser.END_TAG) {
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String start = parser.getName();
                Log.d("start1", start);

                if (start.equals("SECTION")) {
                    if (parser.getAttributeValue(0) != null) {
                        String sec = parser.getAttributeValue(0);
                        Log.d("sec", sec);
                        link = link + sec;
                    }
                } else if (start.equals("ARTICLE")) {
                    String ti = parser.getAttributeValue(0);//ARTICLE first line
                    Log.d("test", ti);
                    link = link + ti;

                    //1번만 하려면 이렇게 해야 함
                    parser.nextTag();//ARTICLE
                    String check = parser.getName();
                    Log.d("check", check);

                    if (check.equals("PARAGRAPH")) {
                        String tag2 = parser.nextText();//paragraph 내용
                        Log.d("art para", tag2);
                        link = link + tag2;

                        parser.nextTag();
                        String tag3 = parser.getName();
                        Log.d("tag3", tag3);

                        if (tag3.equals("PARAGRAPH")) {
                            String tag4 = parser.nextText();//paragraph 내용
                            Log.d("art para para", tag4);
                            link = link + tag4;

                            parser.nextTag();
                            String check2 = parser.getName();
                            Log.d("check2", check2);

                        }
                    }
                }
            }
        }
        return link;
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            requestUrl = "http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?"+"pageNo=1&numOfRows=10&"+"ServiceKey=" + key;
            try {
                boolean b_ITEM_NAME = false;
                boolean b_ENTP_NAME = false;
                boolean b_ETC_OTC_CODE = false;
                boolean b_ITEM_PERMIT_DATE = false;
                boolean b_ENTP_NO = false;
                boolean b_BAR_CODE = false;
                boolean b_ITEM_SEQ = false;
                boolean b_CHART = false;
                boolean b_MATERIAL_NAME = false;
                boolean b_PACK_UNIT = false;
                boolean b_PERMIT_KIND_NAME = false;
                boolean b_CANCEL_DATE = false;
                boolean b_MAKE_MATERIAL_FLAG = false;
                boolean b_INDUTY_TYPE = false;
                boolean b_CHANGE_DATE = false;
                boolean b_INGR_NAME = false;

                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                String tag;
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            mList = new ArrayList<Recent>();
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item") && bus != null) {
                                mList.add(bus);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("item")) {
                                bus = new Recent();
                            }
                            if (parser.getName().equals("ITEM_NAME")) b_ITEM_NAME = true;
                            if (parser.getName().equals("ENTP_NAME")) b_ENTP_NAME = true;
                            if (parser.getName().equals("ETC_OTC_CODE")) b_ETC_OTC_CODE = true;
                            if (parser.getName().equals("ITEM_PERMIT_DATE")) b_ITEM_PERMIT_DATE = true;
                            if (parser.getName().equals("ENTP_NO")) b_ENTP_NO = true;
                            if (parser.getName().equals("BAR_CODE")) b_BAR_CODE = true;
                            if (parser.getName().equals("ITEM_SEQ")) b_ITEM_SEQ = true;
                            if (parser.getName().equals("CHART")) b_CHART = true;
                            if (parser.getName().equals("MATERIAL_NAME")) b_MATERIAL_NAME = true;
                            if (parser.getName().equals("PACK_UNIT")) b_PACK_UNIT = true;
                            if (parser.getName().equals("PERMIT_KIND_NAME")) b_PERMIT_KIND_NAME = true;
                            if (parser.getName().equals("CANCEL_DATE")) b_CANCEL_DATE = true;
                            if (parser.getName().equals("MAKE_MATERIAL_FLAG")) b_MAKE_MATERIAL_FLAG = true;
                            if (parser.getName().equals("INDUTY_TYPE")) b_INDUTY_TYPE = true;
                            if (parser.getName().equals("CHANGE_DATE")) b_CHANGE_DATE = true;
                            if (parser.getName().equals("INGR_NAME")) b_INGR_NAME = true;
                            if (parser.getName().equals("EE_DOC_DATA")) bus.setEE_DOC_DATA(readLink(parser));
                            //if (parser.getName().equals("UD_DOC_DATA")) bus.setUD_DOC_DATA(readLink(parser));

                            break;

                        case XmlPullParser.TEXT:
                            if (b_ITEM_NAME) {
                                bus.setITEM_NAME(parser.getText());
                                b_ITEM_NAME = false;
                            } else if (b_ENTP_NAME) {
                                bus.setENTP_NAME(parser.getText());
                                b_ENTP_NAME = false;
                            } else if (b_ETC_OTC_CODE) {
                                bus.setETC_OTC_CODE(parser.getText());
                                b_ETC_OTC_CODE = false;
                            } else if (b_ITEM_PERMIT_DATE) {
                                bus.setITEM_PERMIT_DATE(parser.getText());
                                b_ITEM_PERMIT_DATE = false;
                            } else if (b_ENTP_NO) {
                                bus.setENTP_NO(parser.getText());
                                b_ENTP_NO = false;
                            } else if (b_BAR_CODE) {
                                bus.setBAR_CODE(parser.getText());
                                b_BAR_CODE = false;
                            } else if (b_ITEM_SEQ) {
                                bus.setITEM_SEQ(parser.getText());
                                b_ITEM_SEQ = false;
                            } else if (b_CHART) {
                                bus.setCHART(parser.getText());
                                b_CHART = false;
                            } else if (b_MATERIAL_NAME) {
                                bus.setMATERIAL_NAME(parser.getText());
                                b_MATERIAL_NAME = false;
                            } else if (b_PACK_UNIT) {
                                bus.setPACK_UNIT(parser.getText());
                                b_PACK_UNIT = false;
                            } else if (b_PERMIT_KIND_NAME) {
                                bus.setPERMIT_KIND_NAME(parser.getText());
                                b_PERMIT_KIND_NAME = false;
                            } else if (b_CANCEL_DATE) {
                                bus.setCANCEL_DATE(parser.getText());
                                b_CANCEL_DATE = false;
                            } else if (b_MAKE_MATERIAL_FLAG) {
                                bus.setMAKE_MATERIAL_FLAG(parser.getText());
                                b_MAKE_MATERIAL_FLAG = false;
                            } else if (b_INDUTY_TYPE) {
                                bus.setINDUTY_TYPE(parser.getText());
                                b_INDUTY_TYPE = false;
                            } else if (b_CHANGE_DATE) {
                                bus.setCHANGE_DATE(parser.getText());
                                b_CHANGE_DATE = false;
                            } else if (b_INGR_NAME) {
                                bus.setINGR_NAME(parser.getText());
                                b_INGR_NAME = false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            RecyclerView recyclerView = findViewById(R.id.recyclerview_medlist);
            adapter = new RecyclerviewAdapter(getApplicationContext(), (ArrayList<Recent>) mList);
            recyclerView.setAdapter(adapter);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}
