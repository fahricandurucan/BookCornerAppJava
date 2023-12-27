package com.example.bookcornerapp_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookcornerapp_java.model.Book;
=======
import android.view.View;
import android.widget.ImageView;
>>>>>>> 74c5d7829019c677c11f5e654342d6b4ade30fda

public class ProductDetailActivity extends AppCompatActivity {

    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
<<<<<<< HEAD

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_book")) {
            Book selectedBook = (Book) intent.getSerializableExtra("selected_book");

            // Burada seçilen kitap bilgilerini kullanabilirsiniz
            // Örnek olarak:
            TextView bookNameTextView = findViewById(R.id.bookNameTextView);
            TextView bookPriceTextView = findViewById(R.id.bookPriceTextView);
            TextView bookAuthorTextView = findViewById(R.id.bookAuthorTextView);
            TextView bookPublisherText = findViewById(R.id.bookPublisherText);
            ImageView bookImage = findViewById(R.id.bookImage);

            bookNameTextView.setText(selectedBook.getName());
            bookPriceTextView.setText("$" + selectedBook.getPrice());
            bookAuthorTextView.setText(selectedBook.getAuthor());
            bookPublisherText.setText(selectedBook.getPublisher());
            bookImage.setImageResource(selectedBook.getImage());

            // Diğer bilgileri de aynı şekilde set edebilirsiniz.
        }
=======
        btnBack=findViewById(R.id.backBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
>>>>>>> 74c5d7829019c677c11f5e654342d6b4ade30fda
    }

}