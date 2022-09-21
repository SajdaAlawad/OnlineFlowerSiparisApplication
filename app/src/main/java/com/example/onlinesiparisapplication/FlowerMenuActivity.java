package com.example.onlinesiparisapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinesiparisapplication.adaptor.MenuListAdapter;
import com.example.onlinesiparisapplication.model.Menu;
import com.example.onlinesiparisapplication.model.OnlineSiparisModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FlowerMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    private List<Menu> menuList = null;
    private MenuListAdapter menuListAdapter;
    private List<Menu> itemInCartList;
    private int totalItemInCart = 0;
    private TextView buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_menu);

        OnlineSiparisModel onlineSiparisModel = getIntent().getParcelableExtra("OnlineSiparisModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(onlineSiparisModel.getName());
        //actionBar.setSubtitle(onlineSiparisModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);


        menuList = onlineSiparisModel.getMenus();
        initRecyclerView();

        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              if(itemInCartList != null && itemInCartList.size() <= 0){
                  Toast.makeText(FlowerMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                  return;
              }
              onlineSiparisModel.setMenus(itemInCartList);
              Intent i = new Intent(FlowerMenuActivity.this,PlaceYourOrderActivity.class);
              i.putExtra("OnlineSiparisModel", onlineSiparisModel);
              startActivityForResult(i,1000);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);

    }

    @Override
    public void onAddToCartClick(Menu menu) {
        if(itemInCartList == null){
            itemInCartList = new ArrayList<>();
        }
        itemInCartList.add(menu);
         totalItemInCart = 0;

         for(Menu m : itemInCartList){
             totalItemInCart = totalItemInCart + m.getTotalInCart();
         }
         buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
      if(itemInCartList.contains(menu)) {
          int index = itemInCartList.indexOf(menu);
          itemInCartList.remove(index);
          itemInCartList.add(index,menu);

          totalItemInCart = 0;

          for(Menu m : itemInCartList){
              totalItemInCart = totalItemInCart + m.getTotalInCart();
          }
          buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
      }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if(itemInCartList.contains(menu)){
            itemInCartList.remove(menu);
            totalItemInCart = 0;

            for(Menu m : itemInCartList){
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }
    // if request code is true it will go to flower categories
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK);
        //
        finish();
    }
}
