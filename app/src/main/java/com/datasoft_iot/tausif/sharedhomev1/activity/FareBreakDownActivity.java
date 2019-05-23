package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.adapter.FareBreakDownAdapter;
import com.datasoft_iot.tausif.sharedhomev1.model.RoomAvailabilityResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FareBreakDownActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FareBreakDownAdapter adapter;
    String checkInDate,checkOutDate, room_uniq_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_break_down);

        Bundle bundle = getIntent().getExtras();
        checkInDate = bundle.getString("checkInDate");
        checkOutDate = bundle.getString("checkOutDate");
        room_uniq_id = bundle.getString("room_uniq_id");
        Log.e("checkInDate",checkInDate);
        Log.e("checkOutDate",checkOutDate);
//        Log.e("bed_uniq_id",bed_uniq_id);

        makeSearchRequest(checkInDate, checkOutDate, room_uniq_id);

    }


    private void makeSearchRequest(final String checkInDate, final String checkOutDate, String room_uniq_id) {
        Log.e("makeSearchRequest"," called");


        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api myApi = mRetrofit.create(Api.class);

        Call<List<RoomAvailabilityResponse>> call = myApi.getRoomAvailability(checkInDate, checkOutDate, room_uniq_id);

        call.enqueue(new Callback<List<RoomAvailabilityResponse>>() {
            @Override
            public void onResponse(Call<List<RoomAvailabilityResponse>> call, Response<List<RoomAvailabilityResponse>> response) {
                Log.e("Has ","response");

                if(!response.isSuccessful()){
                    Log.e("Code: ",Integer.toString(response.code()));
                    Toast.makeText(FareBreakDownActivity.this, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return; //to leave this method from here
                }

                for(RoomAvailabilityResponse dt : response.body()){
                    Log.e("seyam",dt.getDays_of_stay().toString());
                    Log.e("seyam",dt.getService_charge());
                    Log.e("seyam",dt.getCost());
                    Log.e("seyam",dt.getTotal_rent());
                    Log.e("seyam",dt.getTotal_cost());
                }
                Log.e("body",response.body().toString());

                if (response.body().toString() != null){
                    generateRoomList(response.body(), checkInDate, checkOutDate);
                }


            }

            @Override
            public void onFailure(Call<List<RoomAvailabilityResponse>> call, Throwable t) {
//                Log.e("onFailure "," Network Error!");
                Toast.makeText(getApplicationContext(), "Check your internet connectivity", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void generateRoomList(List<RoomAvailabilityResponse> data, String checkInDate, String checkOutDate) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_farebreakdown);


        //create a new constructor of RoomInfoAdapter class with required params
        adapter = new FareBreakDownAdapter(data, getApplicationContext(), checkInDate, checkOutDate);
        //create a new constructor of LinearLayoutManager class with required params
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
