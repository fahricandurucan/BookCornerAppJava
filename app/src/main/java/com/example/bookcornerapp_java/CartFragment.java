package com.example.bookcornerapp_java;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookcornerapp_java.adapters.CartAdapter;
import com.example.bookcornerapp_java.databinding.ActivityMainBinding;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.sharedPreferences.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalAmountTextView;
    private TextView subtotal;
    private Button orderNowBtn;
    public CartFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        View emptyView = inflater.inflate(R.layout.fragment_empty,container,false);

        TextView emptyCartTextView = emptyView.findViewById(R.id.textViewEmpty);
        ImageView emptyCartImage = emptyView.findViewById(R.id.emptyCartImage);

        // get data from SharedPreferences
        List<Book> cartItems = SharedPreferencesHelper.getCartItems(requireContext());
        recyclerView = view.findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (cartItems == null || cartItems.isEmpty()) {
            emptyCartTextView.setText("Your cart is empty!");
            emptyCartImage.setImageResource(R.drawable.empty_cart);
            emptyCartTextView.setGravity(Gravity.CENTER);
            emptyCartTextView.setVisibility(View.VISIBLE);
            Log.e("CartActivity", "Cart items are empty or null");
            return emptyView;
        } else {
            cartAdapter = new CartAdapter(cartItems,getContext(),this);
            recyclerView.setAdapter(cartAdapter);

            totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
            subtotal=view.findViewById(R.id.subtotal);
            updateTotalAmount();

            orderNowBtn = (Button) view.findViewById(R.id.orderNowBtn);
            orderNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(requireContext(), "Your order has been confirmed!", Toast.LENGTH_SHORT).show();

                    // clean order
                    clearCart();
                    recyclerView.setAdapter(cartAdapter);
                    goToHomeFragment();
                }
            });


            return view;
        }


    }


    private void goToHomeFragment() {
        // WaitingFragment'i göstermek için yazdık
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new WaitingCartFragment(R.drawable.shopping))
                .addToBackStack(null)
                .commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new CartFragment())
                        .commit();
            }
        }, 3000);

    }


    private void updateTotalAmount() {
        List<Book> cartItems = SharedPreferencesHelper.getCartItems(requireContext());

        double totalAmount = 0.0;
        for (Book book : cartItems) {
            totalAmount += book.getPrice();
        }

        totalAmountTextView.setText(totalAmount + "$");
        subtotal.setText(totalAmount+"$");
    }

    private void clearCart() {
        SharedPreferencesHelper.clearCart(requireContext());
    }
}