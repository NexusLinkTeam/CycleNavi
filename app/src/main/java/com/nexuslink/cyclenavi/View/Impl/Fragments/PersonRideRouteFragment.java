package com.nexuslink.cyclenavi.View.Impl.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nexuslink.cyclenavi.Adapters.PersonRouteLineAdapter;
import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.GetRouteList;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.Util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by G150S on 2017/5/3.
 */

public class PersonRideRouteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = "PersonRouteFragment";
    private static final String ARG_TITLE = "title";
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PersonRouteLineAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<GetRouteList.RoutesBean> mRoutes = new ArrayList<>();
    private boolean isUpLoadingPersonInfo = false;

    public static PersonRideRouteFragment getInstance(String title)
    {
        PersonRideRouteFragment personRideRouteFragment = new PersonRideRouteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE,title);
        personRideRouteFragment.setArguments(bundle);

        return  personRideRouteFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString(ARG_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_route_info_fragment,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.person_route_swiftFresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.RED,R.color.Yellow
                ,R.color.colorPrimaryDark,R.color.colorAccent2,R.color.colorAccent3);
        recyclerView = (RecyclerView) view.findViewById(R.id.person_recycle_route_line);
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(getContext()));
        adapter = new PersonRouteLineAdapter(getContext(),mRoutes);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(true);
        isUpLoadingPersonInfo = true;
        getPersonRoutes();
        swipeRefreshLayout.setOnRefreshListener(PersonRideRouteFragment.this);

        return view;
    }
    private void getPersonRoutes()
    {
        RetrofitWrapper.getInstance().create(ICycleNaviService.class).getRoutes(SpUtils.getInt(getContext(),"uid"))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetRouteList>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG,"下拉刷新完成");
                        isUpLoadingPersonInfo = false;
                    }
                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        isUpLoadingPersonInfo = false;
                        Toast.makeText(getContext(),"下拉刷新请求失败",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(GetRouteList getRouteList) {
                       if (getRouteList.getCode() == 200)
                       {
                           Toast.makeText(getContext(),"下拉刷新成功",Toast.LENGTH_SHORT).show();
                           adapter.removeAll();
                           swipeRefreshLayout.setRefreshing(false);
                           adapter.loadMore(getRouteList.getRoutes());
                       }
                    }
                });
    }
    @Override
    public void onRefresh() {
        if (!isUpLoadingPersonInfo) {
            Log.e(TAG, "onRefresh");
            getPersonRoutes();
        }
    }
}
