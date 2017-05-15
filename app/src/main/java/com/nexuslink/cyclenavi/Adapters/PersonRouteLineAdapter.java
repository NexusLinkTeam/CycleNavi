package com.nexuslink.cyclenavi.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.nexuslink.cyclenavi.Model.JavaBean.GetRouteList;
import com.nexuslink.cyclenavi.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by G150S on 2017/5/3.
 */

public class PersonRouteLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int Flag_Route_NormalItem = 1;
    private static final int Flag_End = 2;
    private String TAG = "PersonRouteLineAdapter";
    private Context mContext;
    private List<GetRouteList.RoutesBean> mRoutes;

    public PersonRouteLineAdapter(Context context,List<GetRouteList.RoutesBean> routes)
    {
        mContext = context;
        mRoutes = routes;
    }

    @Override
    public int getItemViewType(int position) {
        return Flag_Route_NormalItem;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Flag_Route_NormalItem)
        {
            return  new PersonRouteLineAdapter.RouteInfoHolder(LayoutInflater.from(mContext).inflate(R.layout.route_info_items,parent,false));
        }/*else if (viewType == Flag_End){
            return new PersonRouteLineAdapter.BottomLoadHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bottom_load,parent,false));
        }*/
        return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PersonRouteLineAdapter.RouteInfoHolder)
        {
            ((RouteInfoHolder) holder).total_time.setText("TotalTime:"+mRoutes.get(position).getTotalTime());
            ((RouteInfoHolder) holder).route_data.setText("Date:"+mRoutes.get(position).getDate());
            Glide.with(mContext).load(mRoutes.get(position).getPicture()).placeholder(R.drawable.plactholder_picture).into(((RouteInfoHolder) holder).person_route_image);
            ((RouteInfoHolder) holder).tabLayout.setSelectedTabIndicatorHeight(0);
            ((RouteInfoHolder) holder).tabLayout.setTabTextColors(R.color.colorPrimaryDark,R.color.colorPrimary);
            if (((RouteInfoHolder) holder).tabLayout.getTabCount() == 0)
            {
                ((RouteInfoHolder) holder).tabLayout.addTab(((RouteInfoHolder) holder).tabLayout.newTab().setText("骑行照片"));
                ((RouteInfoHolder) holder).tabLayout.addTab(((RouteInfoHolder) holder).tabLayout.newTab().setText("骑行速度"));
                ((RouteInfoHolder) holder).tabLayout.addTab(((RouteInfoHolder) holder).tabLayout.newTab().setText("骑行海拔"));
            }
            ((RouteInfoHolder) holder).tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition())
                    {
                        case 0:
                            ((RouteInfoHolder) holder).heightChart.setVisibility(View.GONE);
                            ((RouteInfoHolder) holder).speedChart.setVisibility(View.GONE);
                            ((RouteInfoHolder) holder).person_route_image.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            ((RouteInfoHolder) holder).heightChart.setVisibility(View.GONE);
                            ((RouteInfoHolder) holder).person_route_image.setVisibility(View.GONE);
                            ((RouteInfoHolder) holder).speedChart.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            ((RouteInfoHolder) holder).speedChart.setVisibility(View.GONE);
                            ((RouteInfoHolder) holder).person_route_image.setVisibility(View.GONE);
                            ((RouteInfoHolder) holder).heightChart.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            String speedArray[] = convertToStringArray(mRoutes.get(position).getSpeedList());
            String heightArray[] = convertToStringArray(mRoutes.get(position).getHeightList());
            int totalX = Integer.parseInt(mRoutes.get(position).getTotalTime());
            List<Entry> entriesSpeed = new ArrayList<>();
            for (int i = 0;i<speedArray.length-1;i++)
            {
                entriesSpeed.add(new Entry((totalX/speedArray.length-1)*(i+1),Float.valueOf(speedArray[i])));
            }
            LineDataSet SpeedlineDataSet = new LineDataSet(entriesSpeed,"骑行速率曲线图");
            SpeedlineDataSet.setColor(Color.BLUE);
            LineData lineData = new LineData(SpeedlineDataSet);
            ((RouteInfoHolder) holder).speedChart.setData(lineData);
/*            ((RouteInfoHolder) holder).speedChart.setDrawGridBackground(false);
            ((RouteInfoHolder) holder).speedChart.setDrawBorders(false);*/
            ((RouteInfoHolder) holder).speedChart.invalidate();

            List<Entry> entriesHeight = new ArrayList<>();
            for (int i =0;i<heightArray.length-1;i++)
            {
                entriesHeight.add(new Entry((totalX/heightArray.length-1)*(i+1),Float.valueOf(heightArray[i])));
            }
            LineDataSet HeightDataSet = new LineDataSet(entriesHeight,"骑行海拔高度曲线图");
            HeightDataSet.setColors(Color.BLUE);
            LineData lineData1Height = new LineData(HeightDataSet);
            ((RouteInfoHolder) holder).heightChart.setData(lineData1Height);
            ((RouteInfoHolder) holder).heightChart.setBorderColor(R.color.colorPrimary);
           /* ((RouteInfoHolder) holder).heightChart.setDrawGridBackground(false);*/
            ((RouteInfoHolder) holder).heightChart.invalidate();
        }
    }

    @Override
    public int getItemCount() {
        return mRoutes.size() ;
    }

    public List<GetRouteList.RoutesBean> getmRoutes()
    {
        return mRoutes;
    }
    public void removeAll()
    {
        mRoutes.clear();
        Log.e(TAG,mRoutes.size()+"removeAll过后的数据量");
    }
    public void loadMore(List<GetRouteList.RoutesBean> routesBeanList)
    {
        mRoutes.addAll(routesBeanList);
        notifyDataSetChanged();
    }

    public class RouteInfoHolder extends RecyclerView.ViewHolder{
        private TextView total_time;
        private TextView route_data;
        private TabLayout tabLayout;
        private CircleImageView user_photo;
        private ImageView person_route_image;
        private LineChart speedChart;
        private LineChart heightChart;
        public RouteInfoHolder(View itmeView)
        {
            super(itmeView);
            total_time = (TextView)itmeView.findViewById(R.id.route_total_time);
            route_data = (TextView)itemView.findViewById(R.id.route_data);
            user_photo = (CircleImageView)itmeView.findViewById(R.id.user_route_photo);
            person_route_image = (ImageView)itmeView.findViewById(R.id.person_route_Image);
            speedChart = (LineChart)itmeView.findViewById(R.id.speedLineChart);
            heightChart = (LineChart)itemView.findViewById(R.id.heightLineChart);
            tabLayout = (TabLayout)itemView.findViewById(R.id.person_route_tag);
        }
    }

/*    public class BottomLoadHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView info;
        public BottomLoadHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.btom_load);
            info = (TextView) itemView.findViewById(R.id.btom_text);
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public TextView getInfo() {
            return info;
        }
    }*/
    private String[] convertToStringArray(String s)
    {
        String [] strArray = null;
        strArray = s.split(",");
        return  strArray;
    }
}
