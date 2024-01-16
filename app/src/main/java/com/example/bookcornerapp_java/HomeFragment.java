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
        // we create FirestoreManager class
        firestoreManager = new FirestoreManager();


        ImageView navigateCategories = view.findViewById(R.id.categories);

        navigateCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        firestoreManager.getOneBookPerCategory(new OnBooksLoadedListener() {

            @Override
            public void onBooksLoaded(List<Book> bookList) {
                    GridLayout bookContainer = view.findViewById(R.id.bookContainer);
                    createBookCards(bookList, bookContainer);
            }

            @Override
            public void onBooksLoadFailed(String errorMessage) {
                // if there is an error
                Log.e("HomeFragment", "Kitaplar alınırken hata oluştu: " + errorMessage);
            }
        });


        TextView txtSeeAll = view.findViewById(R.id.txtSeeAll);

        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProductActivity.class);
                startActivity(intent);
            }
        });




//         add book
//        Book newBook = new Book("4",
//                "The Enchanted Garden",
//                9.99,
//                "Jane Smith",
//                "Magical Tales Publishing",
//                "Jane Smith takes children on a magical journey into a whimsical garden, where imagination comes to life. With vibrant storytelling and captivating illustrations, this book sparks the creativity and wonder of young minds, inviting them into a world of enchantment and exploration.",
//                "child6",
//                "Children's Books");
//
//        ImageView bell_icon = view.findViewById(R.id.bell_icon);
//
//        bell_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("bell icno");
//                // Firestore'a kitabı ekleyin
//                firestoreManager.getOneBookPerCategory(new OnBooksLoadedListener() {
//
//                    @Override
//                    public void onBooksLoaded(List<Book> bookList) {
//                        System.out.println(bookList.size());
//                        for (Book book : bookList) {
//                            System.out.println(book.getName());
//                        }
//                    }
//
//                    @Override
//                    public void onBooksLoadFailed(String errorMessage) {
//                        // Kitaplar alınırken hata oluştuğunda yapılacak işlemler
//                        Log.e("HomeFragment", "Kitaplar alınırken hata oluştu: " + errorMessage);
//                    }
//                });
//
//                System.out.println("bell icon sonu");
//            }
//        });


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

            ImageView favoriteIcon = bookCard.findViewById(R.id.favoriteIcon);  // Favori icon

            //click favorite icon and add favoritelist
            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleFavoriteClick(book, favoriteIcon);
                }

            });


            int drawableResourceId = getResources().getIdentifier(book.getImage(), "drawable", requireContext().getPackageName());
            bookImage.setImageResource(drawableResourceId);

            bookName.setText(book.getName());
            bookPrice.setText("$" + book.getPrice());

            // navigation
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // navigation and pass data
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("selected_book", book);
                    startActivity(intent);
                }
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
            int marginInDp = 16;
            int marginInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginInDp, getResources().getDisplayMetrics());
            params.setMargins(marginInPx, marginInPx, marginInPx, marginInPx);
            bookCard.setLayoutParams(params);

            bookContainer.addView(bookCard);

            for(int i = 0;i<FavoriteBookManager.getFavoriteBooks().size();i++){

                if(FavoriteBookManager.getFavoriteBooks().get(i).getName().equals(bookName.getText().toString())){
                    updateFavoriteIcon(favoriteIcon, true);
                }

            }
        }
    }

    private void handleFavoriteClick(Book book, ImageView favoriteIcon) {
        String bookId = book.getId();

        if (FavoriteBookManager.isBookFavorited(bookId)) {
            // if book is on favorite , remove book on favorite
            FavoriteBookManager.removeFavoriteBook(findFavoriteBookById(bookId));
            updateFavoriteIcon(favoriteIcon, false);
        } else {
            // Eğer kitap favorilerde değilse, favorilere ekle
            FavoriteBook favoriteBook = createFavoriteBook(book);
            FavoriteBookManager.addFavoriteBook(favoriteBook);
            updateFavoriteIcon(favoriteIcon, true);
        }
    }
    private FavoriteBook findFavoriteBookById(String bookId) {
        for (FavoriteBook favoriteBook : FavoriteBookManager.getFavoriteBooks()) {
            System.out.print(favoriteBook.getName());
            if (favoriteBook.getId().equals(bookId)) {
                return favoriteBook;
            }
        }
        return null;
    }

    private void updateFavoriteIcon(ImageView favoriteIcon, boolean isFavorited) {
        favoriteIcon.setImageResource(isFavorited ? R.drawable.favorite : R.drawable.unfavorite);
    }

    private FavoriteBook createFavoriteBook(Book book) {
        return new FavoriteBook(book.getId(), book.getName(), book.getPrice(), book.getPublisher(), book.getDescription(), book.getImage());
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}