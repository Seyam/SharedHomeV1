package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.adapter.EmployeeAdapter;
import com.datasoft_iot.tausif.sharedhomev1.model.ApartmentListResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationSearchActivityV2 extends AppCompatActivity {


    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    private EmployeeAdapter myAdapter;
    private  RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

        list = new ArrayList<>();
        list.add("tokyo");
        list.add("osaka");
        list.add("kyoto");




//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                if(list.contains(query.toLowerCase())){
                    adapter.getFilter().filter(query);
                    Toast.makeText(getApplicationContext(), "Matched",Toast.LENGTH_LONG).show();

                    makeSearchRequest(query.toLowerCase());
                }else{
                    Toast.makeText(getApplicationContext(), "City not found!",Toast.LENGTH_LONG).show();
                }


//                for(String city : list){
//                    if (city.startsWith(query.toLowerCase()) && query.length() > 0)  {
//                        Log.e("MatchingWith",city);
//                    }
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                for(String city : list){
//                    if (city.startsWith(newText.toLowerCase()) && newText.length() > 0)  {
//                        Log.e("MatchingWith",city);
//                    }
//                }
                return false;
            }
        });

    }

    private void makeSearchRequest(String city) {

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api myApi = mRetrofit.create(Api.class);

        Call<List<ApartmentListResponse>> call = myApi.getApartmentList(city);

        call.enqueue(new Callback<List<ApartmentListResponse>>() {
            @Override
            public void onResponse(Call<List<ApartmentListResponse>> call, Response<List<ApartmentListResponse>> response) {

                Log.e("Has ","response");

                if(!response.isSuccessful()){
                    Log.e("Code: ",Integer.toString(response.code()));
                    Toast.makeText(getApplicationContext(), "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return; //to leave this method from here
                }
                generateApartmentDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ApartmentListResponse>> call, Throwable t) {

            }
        });
    }

    private void generateApartmentDataList(List<ApartmentListResponse> body) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_apartment_list);


        //create a new constructor of EmployeeAdapter class with required params
        myAdapter = new EmployeeAdapter(body, getApplicationContext());
        //create a new constructor of LinearLayoutManager class with required params
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }


}
