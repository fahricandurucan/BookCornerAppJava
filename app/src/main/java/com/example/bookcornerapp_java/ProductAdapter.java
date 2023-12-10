package com.example.bookcornerapp_java;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {

    Context context;
    String[] productName;
    int[] image;

    LayoutInflater inflater;

    public ProductAdapter(Context context, String[] productName, int[] image) {
        this.context = context;
        this.productName = productName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return productName.length;
    }

    @Override
    public Object getItem(int i) {
        return productName[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.product_item,null);
        }

        ImageView imageView = view.findViewById(R.id.imageProduct);
        TextView textView = view.findViewById(R.id.productName);

        imageView.setImageResource(image[i]);
        textView.setText(productName[i]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tıklanılan öğenin pozisyonuna göre bir işlem yap
                // Örneğin, yeni bir aktiviteye geçiş yapabilirsiniz.
                Intent intent = new Intent(context, ProductDetailActivity.class);
                // İlgili bilgileri intent'e ekleyebilirsiniz (örneğin, ürün adı, resim vs.)
//                intent.putExtra("productName", productName[i]);
//                intent.putExtra("productImage", image[i]);
                context.startActivity(intent);
            }
        });


        return view;
    }
}
