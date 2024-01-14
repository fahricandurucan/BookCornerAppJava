package com.example.bookcornerapp_java;

import static com.google.common.reflect.Reflection.getPackageName;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookcornerapp_java.adapters.CategoryAdapter;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.Category;
import com.example.bookcornerapp_java.model.FavoriteBook;
import com.example.bookcornerapp_java.model.FavoriteBookManager;
import com.example.bookcornerapp_java.myInterfaces.OnBooksLoadedListener;
import com.example.bookcornerapp_java.myInterfaces.OnCategoriesLoadedListener;
import com.example.bookcornerapp_java.services.FirestoreManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirestoreManager firestoreManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // FirestoreManager sınıfını oluşturun
        firestoreManager = new FirestoreManager();


        ImageView navigateCategories = view.findViewById(R.id.categories);

        navigateCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        firestoreManager.getBooks(new OnBooksLoadedListener() {
            @Override
            public void onBooksLoaded(List<Book> bookList) {
                GridLayout bookContainer = view.findViewById(R.id.bookContainer);
                createBookCards(bookList, bookContainer);
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                // Veri çekme işleminde hata oluştuğunda yapılacak işlemler
                Log.e("HomeFragment", errorMessage);
            }
        });

//        // Kitap verilerini oluştur
//        List<Book> bookList = firestoreManager.getBooks();
//        Log.e("weşlkdjewş","wkeljmklwe");
//        // Kitap kartlarını oluştur ve layouta ekle
//        GridLayout bookContainer = view.findViewById(R.id.bookContainer);
//        createBookCards(bookList, bookContainer);

//        CardView cardView1 = view.findViewById(R.id.cardview);
//
//        cardView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ProductDetailActivity.class);
//                startActivity(intent);
//            }
//        });

        TextView txtSeeAll = view.findViewById(R.id.txtSeeAll);

        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProductActivity.class);
                startActivity(intent);
            }
        });


        // FirestoreManager sınıfını oluşturun
        FirestoreManager firestoreManager = new FirestoreManager();


//         Eklemek istediğiniz kitap nesnesini oluşturun
        Book newBook = new Book("4",
                "The Enchanted Garden",
                9.99,
                "Jane Smith",
                "Magical Tales Publishing",
                "Jane Smith takes children on a magical journey into a whimsical garden, where imagination comes to life. With vibrant storytelling and captivating illustrations, this book sparks the creativity and wonder of young minds, inviting them into a world of enchantment and exploration.",
                "child6",
                "Children's Books");

        ImageView bell_icon = view.findViewById(R.id.bell_icon);

        bell_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bell icno");
                // Firestore'a kitabı ekleyin
                firestoreManager.getOneBookPerCategory(new OnBooksLoadedListener() {

                    @Override
                    public void onBooksLoaded(List<Book> bookList) {
                        System.out.println(bookList.size());
                        for (Book book : bookList) {
                            System.out.println(book.getName());
                        }
                    }

                    @Override
                    public void onBooksLoadFailed(String errorMessage) {
                        // Kitaplar alınırken hata oluştuğunda yapılacak işlemler
                        Log.e("HomeFragment", "Kitaplar alınırken hata oluştu: " + errorMessage);
                    }
                });

                System.out.println("bell icon sonu");
            }
        });
        LinearLayout childrenLayout= view.findViewById(R.id.childrenLayout);
        LinearLayout healthLayout= view.findViewById(R.id.healthLayout);
        LinearLayout improLayout= view.findViewById(R.id.improLayout);
        LinearLayout politicLayout= view.findViewById(R.id.politicLayout);

        childrenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BooksForCategoryActivity.class);
                intent.putExtra("selectedCategory","Children's Books");
                startActivity(intent);
            }
        });
        healthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BooksForCategoryActivity.class);
                intent.putExtra("selectedCategory","Health-Medicine");
                startActivity(intent);
            }
        });
        improLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BooksForCategoryActivity.class);
                intent.putExtra("selectedCategory","Self-improvement");
                startActivity(intent);
            }
        });
        politicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BooksForCategoryActivity.class);
                intent.putExtra("selectedCategory","Politics");
                startActivity(intent);
            }
        });

        return view;
    }



    // Örnek kitap verileri oluştur
//    private List<Book> createBookList() {
//        List<Book> bookList = new ArrayList<>();
//
//        bookList.add(new Book("1","War and Peace", 15.99,"xxxx", "Publisher 1", "Description 1", R.drawable.item3));
//        bookList.add(new Book("2","The Little Prince", 10.99, "xxxx","Publisher 2", "Description 2", R.drawable.book1));
//        bookList.add(new Book("3","Animal Farm", 12.49, "xxxx","Publisher 3", "Description 3", R.drawable.item7));
//        bookList.add(new Book("4","1984", 9.99, "xxxx","Publisher 4", "Description 4", R.drawable.item6));
//
//        return bookList;
//    }
    // Kitap kartlarını dinamik olarak oluştur ve layouta ekle
    private void createBookCards(List<Book> bookList, GridLayout  bookContainer) {
        Log.e("FONKSŞYON", String.valueOf(bookList.size()));

        for (Book book : bookList) {
            Log.e("FONKSŞYON2",book.getName());
            View bookCard = getLayoutInflater().inflate(R.layout.book_card, null);

            ImageView bookImage = bookCard.findViewById(R.id.bookImage);
            TextView bookName = bookCard.findViewById(R.id.bookName);
            TextView bookPrice = bookCard.findViewById(R.id.bookPrice);
            CardView cardView = bookCard.findViewById(R.id.cardView);
            if(!FavoriteBookManager.getFavoriteBooks().isEmpty()){
                Log.e("Listem", FavoriteBookManager.getFavoriteBooks().get(0).getName());
            }

            ImageView favoriteIcon = bookCard.findViewById(R.id.favoriteIcon);  // Favori ikonu


            // Favori ikonuna tıklama durumu
            // Favori ikonuna tıklama durumu
            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleFavoriteClick(book, favoriteIcon); // Favori ikonunu parametre olarak gönder
                }

            });


            // Assuming your image is in the "drawable" folder with the filename "your_image_name"
            int drawableResourceId = getResources().getIdentifier(book.getImage(), "drawable", requireContext().getPackageName());

            // Set the image resource to the ImageView
            bookImage.setImageResource(drawableResourceId);


//            bookImage.setImageResource(book.getImage());

            bookName.setText(book.getName());
            bookPrice.setText("$" + book.getPrice());

            // Kitap kartına tıklandığında detay sayfasına geçiş yap
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Kitap bilgilerini intent ile ProductDetailActivity'e aktar
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("selected_book", book);
                    startActivity(intent);
                }
            });

            // GridLayout.LayoutParams ekleyerek iki sütunlu düzenleme yapalım
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f); // Bir satır oluştur
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f); // İki sütunu eşit olarak doldur
            // Margin değerlerini belirle
            int marginInDp = 16; // İstediğiniz margin değeri
            int marginInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginInDp, getResources().getDisplayMetrics());
            params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx);
            bookCard.setLayoutParams(params);

            bookContainer.addView(bookCard);

            for(int i = 0;i<FavoriteBookManager.getFavoriteBooks().size();i++){

                if(FavoriteBookManager.getFavoriteBooks().get(i).getName().equals(bookName.getText().toString())){
                    Log.e("if","if");
                    updateFavoriteIcon(favoriteIcon, true);
                }

            }
        }
    }

    // Favori ikonuna tıklama durumu
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

    @Override
    public void onResume() {
        super.onResume();
    }
}