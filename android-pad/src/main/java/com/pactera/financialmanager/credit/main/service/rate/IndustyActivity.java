package com.pactera.financialmanager.credit.main.service.rate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dysen.common_res.common.utils.LogUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.rate.adapter.IndustyAdapter;
import com.pactera.financialmanager.credit.common.bean.rate.Industry;

import java.util.List;


public class IndustyActivity extends AppCompatActivity {
    private static final String TAG = "IndustyActivity";
    private Spinner spinner1, spinner2, spinner3, spinner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industy);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        //获取第一级数据
        loadIndutry("", spinner1);
    }

    public void loadIndutry(String itemno, Spinner spinner) {
        Data data = new Data();
        List<Industry> industryList =  data.getIndustry(itemno);
        if(industryList == null){//假如  没数据 就加载数据
            data.insertData();
            industryList = data.getIndustry(itemno);
        }
        for(Industry industry : industryList){
            LogUtils.d("db", "clickSelData: "+industry.getEtemname());
        }
        industryList.add(0, new Industry("","","请选择"));
        IndustyAdapter industyAdapter = new IndustyAdapter(this, industryList);
        spinner.setAdapter(industyAdapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "getTypeName: "+bussType.get(position).getTypeName());
                //Log.d(TAG, "getitemno: "+bussType.get(position).getitemno());
                //Log.d(TAG, "length: "+bussType.get(position).getitemno().length());
                Log.d(TAG, "id: "+id);
                Log.d(TAG, "position: "+position);
                Log.d(TAG, "view: "+parent.getItemAtPosition(position).toString());
                Industry industry = (Industry) parent.getItemAtPosition(position);
                String itemno = industry.getItemno();
                if(0 != id && itemno.length() == 1){
                    loadIndutry(itemno , spinner2);
                }else if(0 != id && itemno.length() == 3){
                    loadIndutry(itemno , spinner3);
                }else if(0 != id && itemno.length() == 4){
                    loadIndutry(itemno , spinner4);
                }
                //选择第一级隐藏后面几级下拉框
                if(itemno.length() == 1 ){
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                }else if(itemno.length() == 3){
                    spinner4.setVisibility(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
