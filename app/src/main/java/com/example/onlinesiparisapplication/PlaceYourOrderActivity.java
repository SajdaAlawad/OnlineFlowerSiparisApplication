package com.example.onlinesiparisapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlinesiparisapplication.adaptor.PlaceYourOrderAdapter;
import com.example.onlinesiparisapplication.model.Menu;
import com.example.onlinesiparisapplication.model.OnlineSiparisModel;

public class PlaceYourOrderActivity extends AppCompatActivity {
   private EditText inputName,inputAddress,inputCity, inputState;
   private RecyclerView cartItemsRecyclerView;
   private TextView tvSubtotalAmount, tvDeliveryChargeAmount,tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;;
   private boolean isDeliveryOn;
   private PlaceYourOrderAdapter placeYourOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        OnlineSiparisModel onlineSiparisModel = getIntent().getParcelableExtra("OnlineSiparisModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(onlineSiparisModel.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(onlineSiparisModel);

            }
        });
        calculateTotalAmount(onlineSiparisModel);
    }

    private void calculateTotalAmount(OnlineSiparisModel onlineSiparisModel) {
        float subTotalAmount = 0f;

        for(Menu m : onlineSiparisModel.getMenus()){
            subTotalAmount +=m.getPrice() * m.getTotalInCart();
        }
         tvSubtotalAmount.setText("tl"+String.format("%.2f", subTotalAmount));

            tvDeliveryChargeAmount.setText("tl"+String.format("%.2f",onlineSiparisModel.getDelivery_charge()));
            subTotalAmount +=onlineSiparisModel.getDelivery_charge();

        tvTotalAmount.setText("tl"+String.format("%.2f",subTotalAmount));
    }
    private void onPlaceOrderButtonClick(OnlineSiparisModel onlineSiparisModel){
        if(TextUtils.isEmpty(inputName.getText().toString())){
            inputName.setError("Please enter name");
            return;
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())){
            inputAddress.setError("Please enter address");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())){
            inputCity.setError("Please enter city");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter zip");
            return;
        }
        //start sucess activity..
        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSuccessActivity.class);
        i.putExtra("OnlineSiparisModel", onlineSiparisModel);
        startActivityForResult(i,1000);
    }
   private void initRecyclerView(OnlineSiparisModel onlineSiparisModel){
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       placeYourOrderAdapter = new PlaceYourOrderAdapter(onlineSiparisModel.getMenus());
      cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1000){
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}