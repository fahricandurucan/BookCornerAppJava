package com.example.bookcornerapp_java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookcornerapp_java.adapters.FavoriteAdapter;
import com.example.bookcornerapp_java.model.FavoriteBook;
import com.example.bookcornerapp_java.model.FavoriteBookManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter; // Favori kitapları göstermek için uygun bir adapter kullanın


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        View emptyView = inflater.inflate(R.layout.fragment_empty,container,false);

        TextView emptyCartTextView = emptyView.findViewById(R.id.textViewEmpty);
        ImageView emptyCartImage = emptyView.findViewById(R.id.emptyCartImage);
        // Favori kitapları içeren bir liste oluşturun (örnek olarak)
        List<FavoriteBook> favoriteBookList = FavoriteBookManager.getFavoriteBooks();
        // Sepet boşsa uygun bir mesaj göster
        if (favoriteBookList == null || favoriteBookList.isEmpty()) {
            emptyCartTextView.setText("Not yet favorite book!");
            emptyCartImage.setImageResource(R.drawable.empty_favorite);
            Log.e("FavoriActivity", "Favori items are empty or null");
            return emptyView;
        } else {
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            // Favori kitapları göstermek için uygun bir adapter oluşturun
            favoriteAdapter = new FavoriteAdapter(favoriteBookList,getContext());

            // RecyclerView'e adapter'i ayarlayın
            recyclerView.setAdapter(favoriteAdapter);

            return view;
        }



    }
}