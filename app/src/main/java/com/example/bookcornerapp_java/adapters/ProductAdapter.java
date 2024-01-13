package com.example.bookcornerapp_java.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookcornerapp_java.ProductDetailActivity;
import com.example.bookcornerapp_java.R;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.FavoriteBook;
import com.example.bookcornerapp_java.model.FavoriteBookManager;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    Context context;
    List<Book> bookList;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_card, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.bookImage);
        TextView textView = convertView.findViewById(R.id.bookName);
        TextView priceTextView = convertView.findViewById(R.id.bookPrice);
        ImageView favoriteIcon = convertView.findViewById(R.id.favoriteIcon);  // Favori ikonu

        Book book = bookList.get(position);

        int drawableResourceId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        imageView.setImageResource(drawableResourceId);
        textView.setText(book.getName());
        priceTextView.setText("$"+book.getPrice()); // Assuming Book has a method getPrice()

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("selected_book", book);
            context.startActivity(intent);
        });



        // Favori ikonuna tıklama durumu
        // Favori ikonuna tıklama durumu
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFavoriteClick(book, favoriteIcon); // Favori ikonunu parametre olarak gönder
            }

        });
        for(int i = 0;i<FavoriteBookManager.getFavoriteBooks().size();i++){

            if(FavoriteBookManager.getFavoriteBooks().get(i).getName().equals(textView.getText().toString())){
                Log.e("if","if");
                updateFavoriteIcon(favoriteIcon, true);
            }

        }
        return convertView;
    }
    private void handleFavoriteClick(Book book, ImageView favoriteIcon) {
        String bookId = book.getId();

        if (FavoriteBookManager.isBookFavorited(bookId)) {
            // Eğer kitap favorilerde ise, favorilerden çıkar
            FavoriteBookManager.removeFavoriteBook(findFavoriteBookById(bookId));
            updateFavoriteIcon(favoriteIcon, false);
        } else {
            // Eğer kitap favorilerde değilse, favorilere ekle
            FavoriteBook favoriteBook = createFavoriteBook(book);
            FavoriteBookManager.addFavoriteBook(favoriteBook);
            updateFavoriteIcon(favoriteIcon, true);
        }
    }
    // Kitabın favori durumunu kontrol etmek için
    private FavoriteBook findFavoriteBookById(String bookId) {
        for (FavoriteBook favoriteBook : FavoriteBookManager.getFavoriteBooks()) {
            System.out.print(favoriteBook.getName());
            if (favoriteBook.getId().equals(bookId)) {
                return favoriteBook;
            }
        }
        return null;
    }

    // Favori ikonunu güncellemek için
    private void updateFavoriteIcon(ImageView favoriteIcon, boolean isFavorited) {
        // Favori ikonunun rengini güncelle (favoriye eklenmişse, rengi değiştir)
        favoriteIcon.setImageResource(isFavorited ? R.drawable.favorite : R.drawable.unfavorite);
    }

    // Favori kitap oluşturmak için
    private FavoriteBook createFavoriteBook(Book book) {
        return new FavoriteBook(book.getId(), book.getName(), book.getPrice(), book.getPublisher(), book.getDescription(), book.getImage());
    }

}
