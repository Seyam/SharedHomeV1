package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.adapter.RoomInfoAdapter;
import com.datasoft_iot.tausif.sharedhomev1.model.RoomInfoResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomInfoAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        String apartment_id = getIntent().getStringExtra("apartment_id");

        Log.e("apartment_id", apartment_id);


        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api myApi = mRetrofit.create(Api.class);

        Call<List<RoomInfoResponse>> call = myApi.getRoomInfo(apartment_id);

        call.enqueue(new Callback<List<RoomInfoResponse>>() {
            @Override
            public void onResponse(Call<List<RoomInfoResponse>> call, Response<List<RoomInfoResponse>> response) {

                Log.e("Has","response: "+response.body());

                if(!response.isSuccessful()){
                    Log.e("rescode",Integer.toString(response.code()));
                    Toast.makeText(RoomInfoActivity.this, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return; //to leave this method from here
                }

                for(RoomInfoResponse dt : response.body()){
                    Log.e("roomInfo" , dt.getRoom_kitchen_no() );
                    Log.e("roomInfo" , dt.getRoom());
                    Log.e("roomInfo" , dt.getImage_url());
                    Log.e("roomInfo" , dt.getNo_of_bed_available().toString() );
                    Log.e("roomInfo" , dt.getFacility());
                }



                if (response.body().toString()=="[]"){
                    Toast.makeText(RoomInfoActivity.this, "No data available!", Toast.LENGTH_SHORT).show();
                }
                else {
                    generateRoomList(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<RoomInfoResponse>> call, Throwable t) {
                Log.e("onFailure" , "Network error");
                Toast.makeText(RoomInfoActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void generateRoomList(List<RoomInfoResponse> data) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_room_info);


        //create a new constructor of RoomInfoAdapter class with required params
        adapter = new RoomInfoAdapter(data, getApplicationContext());
        //create a new constructor of LinearLayoutManager class with required params
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
