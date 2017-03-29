package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.History;
import com.nexuslink.cyclenavi.Util.MyHistoryManager;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, Inputtips.InputtipsListener {
    private EditText edit;
    private RecyclerView recyclerViewHistory;
    private RecyclerView recyclerViewResult;
    private TextView textHistory;
    private TextView textResult;
    private CardView cardHistory;
    private CardView cardResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       /* ImageView search = (ImageView) findViewById(R.id.search_image);*/
        ImageView back = (ImageView) findViewById(R.id.left_back);
        textHistory = (TextView) findViewById(R.id.text_history);
        textResult = (TextView) findViewById(R.id.text_result);
        cardHistory = (CardView) findViewById(R.id.card_history);
        cardResult = (CardView) findViewById(R.id.card_result);
        List<History> histories = MyHistoryManager.QueryHistory(this);
        recyclerViewResult = (RecyclerView) findViewById(R.id.recycle_result);
        recyclerViewHistory = (RecyclerView) findViewById(R.id.recycle_history);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(new HistoryAdapter(histories));
        edit = (EditText) findViewById(R.id.search_edit);
        back.setOnClickListener(this);
       /* search.setOnClickListener(this);*/
        edit.setOnClickListener(this);
        edit.addTextChangedListener(this);

        /*edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Intent intent = new Intent();
                intent.putExtra("DES",edit.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                return true;
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.search_image:
                Intent intent = new Intent();
                intent.putExtra("DES",edit.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;*/
            case R.id.left_back:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        InputtipsQuery inputtipsQuery = new InputtipsQuery(edit.getText().toString(),"");//全国范围搜索
        Inputtips inputtips = new Inputtips(this,inputtipsQuery);
        inputtips.setInputtipsListener(this);
        inputtips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        textHistory.setVisibility(View.GONE);
        cardHistory.setVisibility(View.GONE);
        textResult.setVisibility(View.VISIBLE);
        cardResult.setVisibility(View.VISIBLE);
        if(list != null){
            recyclerViewResult.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            recyclerViewResult.setAdapter(new ResultAdapter(list));
        }
    }

    /*@Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        List<GeocodeAddress> addresses = geocodeResult.getGeocodeAddressList();
        textHistory.setVisibility(View.GONE);
        cardHistory.setVisibility(View.GONE);
        textResult.setVisibility(View.VISIBLE);
        cardResult.setVisibility(View.VISIBLE);
        recyclerViewResult.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerViewResult.setAdapter(new ResultAdapter(addresses));
    }*/

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
        private List<History> histories;
        public HistoryAdapter(List<History> histories) {
            this.histories = histories;
        }

        @Override
        public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HistoryViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_history,parent,false));
        }

        @Override
        public void onBindViewHolder(HistoryViewHolder holder, final int position) {
            holder.detail.setText(histories.get(position).getName());
            holder.around.setText(histories.get(position).getDistrict());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("DES",histories.get(position).getName());
                    double latitude = histories.get(position).getLatitude();
                    double longitude = histories.get(position).getLongitude();
                    intent.putExtra("POINT_LATITUDE",latitude);
                    intent.putExtra("POINT_LONGITUDE",longitude);
                    intent.putExtra("DETAIL",histories.get(position).getName());
                    intent.putExtra("AROUND",histories.get(position).getDistrict());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            if(histories.size() > 5){
                return 5;
            }
            return histories.size();
        }
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private TextView detail;
        private TextView around;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linear_history);
            detail = (TextView) itemView.findViewById(R.id.history_detail);
            around = (TextView) itemView.findViewById(R.id.history_around);
        }

    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {
        private List<Tip> tips;
        public ResultAdapter(List<Tip> tips) {
            this.tips = tips;
        }

        @Override
        public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResultViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_result,parent,false));
        }

        @Override
        public void onBindViewHolder(ResultViewHolder holder, final int position) {
            holder.detail.setText(tips.get(position).getName());
            holder.around.setText(tips.get(position).getDistrict());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("DES",tips.get(position).getName());
                    if(tips.get(position).getPoint() != null){
                        double latitude = tips.get(position).getPoint().getLatitude();
                        double longitude = tips.get(position).getPoint().getLongitude();
                        intent.putExtra("POINT_LATITUDE",latitude);
                        intent.putExtra("POINT_LONGITUDE",longitude);
                        intent.putExtra("DETAIL",tips.get(position).getName());
                        intent.putExtra("AROUND",tips.get(position).getDistrict());
                        Log.d("TAG123",789+"");
                        savePositionAsHistory(latitude,longitude,
                                tips.get(position).getName(),
                                tips.get(position).getDistrict());
                    }
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

        private void savePositionAsHistory(double latitude, double longitude, String name, String district) {
            MyHistoryManager.saveHistory(SearchActivity.this,latitude,longitude,name,district);
        }

        @Override
        public int getItemCount() {
            if(tips.size() > 5){
                return 5;
            }
            return tips.size();
        }
    }

    private class ResultViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private TextView detail;
        private TextView around;
        public ResultViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linear_result);
            detail = (TextView) itemView.findViewById(R.id.result_detail);
            around = (TextView) itemView.findViewById(R.id.result_around);
        }
    }
}
