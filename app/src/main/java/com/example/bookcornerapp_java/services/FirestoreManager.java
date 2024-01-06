package com.example.bookcornerapp_java.services;

import android.util.Log;

import com.example.bookcornerapp_java.OnBooksLoadedListener;
import com.example.bookcornerapp_java.model.Book;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreManager {

    private static final String COLLECTION_NAME = "books"; // Firestore koleksiyon adı

    private FirebaseFirestore db;
    private CollectionReference booksCollection;

    public FirestoreManager() {
        // Firestore bağlantısını başlat
        db = FirebaseFirestore.getInstance();
        // Koleksiyon referansını al
        booksCollection = db.collection(COLLECTION_NAME);
    }

    // Firestore'a kitap eklemek için metot
    public void addBook(Book book) {
        // Yeni bir belge (document) oluştur ve verileri ekleyerek koleksiyona ekle
        booksCollection.add(book)
                .addOnSuccessListener(documentReference -> {
                    // Başarıyla eklendiğinde yapılacak işlemler
                    String documentId = documentReference.getId();
                    // Eklenen belgenin ID'sini kullanabilirsiniz
                    // Bu ID'yi Book sınıfındaki id özelliğine atayabilirsiniz
                    book.setId(documentId);

                    // Eklenen belgeyi güncelle
                    updateBook(book);
                })
                .addOnFailureListener(e -> {
                    // Eklenirken bir hata oluştuğunda yapılacak işlemler
                    // Hata mesajını loglayabilir veya kullanıcıya bir hata mesajı gösterebilirsiniz
                    Log.e("Error Firestore",e.toString());
                });
    }

    // Firestore'da belgeyi güncellemek için metot
    private void updateBook(Book book) {
        // Güncellenecek belgenin referansını al
        DocumentReference bookRef = booksCollection.document(String.valueOf(book.getId()));

        // Belgeyi güncelle
        bookRef.set(book)
                .addOnSuccessListener(aVoid -> {
                    // Belge başarıyla güncellendiğinde yapılacak işlemler
                })
                .addOnFailureListener(e -> {
                    // Güncellenirken bir hata oluştuğunda yapılacak işlemler
                    // Hata mesajını loglayabilir veya kullanıcıya bir hata mesajı gösterebilirsiniz
                    Log.e("Error Firestore",e.toString());
                });
    }


    public void getBooks(OnBooksLoadedListener listener) {
        CollectionReference booksCollection = FirebaseFirestore.getInstance().collection("books");

        booksCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Book> bookList = new ArrayList<>();

                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Book book = document.toObject(Book.class);
                        if (book != null) {
                            bookList.add(book);
                        }
                    }

                    listener.onBooksLoaded(bookList);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreManager", "Kitaplar alınırken hata oluştu: " + e.toString());
                    listener.onBooksLoadFailed("Veri çekme hatası: " + e.toString());
                });
    }
}


