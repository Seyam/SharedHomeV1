package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.adapter.EmployeeAdapter;
import com.datasoft_iot.tausif.sharedhomev1.model.ApartmentListResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.rgb;
//Add an import statement for the client library.


public class LocationSearchActivity extends AppCompatActivity {
// String TAG = "placeautocomplete";
// TextView txtView;


// String[] arr = { "Tokyo,Japan", "PA,United States","Parana,Brazil",
// "Padua,Italy", "Pasadena,CA,United States"};

    String[] arr = { "Tokyo", "Kyoto","Sapporo", "Kobe"};

    AutoCompleteTextView autocomplete;
    private  RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        autocomplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        frameLayout = findViewById(R.id.frameLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
        autocomplete.setHint("Where you want to go?");
        autocomplete.setHintTextColor(rgb(0,0,0));

//        autocomplete.setTextColor();




        autocomplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH){
//                    Log.e("city", autocomplete.getText().toString());
                    AppConfig.hideKeyboard(LocationSearchActivity.this);
                    makeSearchRequest(autocomplete.getText().toString());
//                    Toast.makeText(LocationSearchActivity.this, "Entered", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("========>>", autocomplete.getText().toString());
//                Toast.makeText(LocationSearchActivity.this, autocomplete.getText().toString(), Toast.LENGTH_SHORT).show();
                AppConfig.hideKeyboard(LocationSearchActivity.this);

                makeSearchRequest(autocomplete.getText().toString());

            }


        });


// txtView = findViewById(R.id.txtView);
//
// // Initialize Places.
// Places.initialize(getApplicationContext(), "AIzaSyA2FGwIbDDK1rmsvGMXkE3OnSMM-bxmVPo");
// // Create a new Places client instance.
// PlacesClient placesClient = Places.createClient(this);
//
// // Initialize the AutocompleteSupportFragment.
// AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
// getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
// // Specify the types of place data to return.
// autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
// // Set up a PlaceSelectionListener to handle the response.
// autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
// @Override
// public void onPlaceSelected(Place place) {
// // TODO: Get info about the selected place.
// txtView.setText(place.getName()+","+place.getId());
// Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
// }
//
// @Override
// public void onError(Status status) {
// // TODO: Handle the error.
// Log.i(TAG, "An error occurred: " + status);
// }
// });
    }

    private void makeSearchRequest(String city) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .build();


        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
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

                for(ApartmentListResponse dt : response.body()){
                    Log.e("apartment", dt.getDescription());
                }

                generateApartmentDataList(response.body());


            }

            @Override
            public void onFailure(Call<List<ApartmentListResponse>> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(frameLayout, "Check your internet connection and try again!",Snackbar.LENGTH_LONG);
                snackbar.show();
//                Toast.makeText(getApplicationContext(), "Request failed due to invalid input or poor connectivity", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void generateApartmentDataList(List<ApartmentListResponse> empDataList) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_apartment_list);


        //create a new constructor of EmployeeAdapter class with required params
        adapter = new EmployeeAdapter(empDataList, getApplicationContext());
        //create a new constructor of LinearLayoutManager class with required params
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}