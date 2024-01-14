package com.example.bookcornerapp_java.services;

import android.net.Uri;
import android.util.Log;

import com.example.bookcornerapp_java.myInterfaces.OnBooksLoadedListener;
import com.example.bookcornerapp_java.model.Book;
import com.example.bookcornerapp_java.model.Category;
import com.example.bookcornerapp_java.myInterfaces.OnCategoriesLoadedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class FirestoreManager {

    private static final String COLLECTION_NAME = "books"; // Firestore koleksiyon adı
    private static final String CATEGORIES_COLLECTION_NAME = "categories"; // Yeni koleksiyon adı

    private FirebaseFirestore db;
    private CollectionReference booksCollection;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    public void uploadImage(Uri imageUri, String imageName, OnSuccessListener<UploadTask.TaskSnapshot> successListener, OnFailureListener failureListener) {
        StorageReference imageRef = storageReference.child("images/" + imageName);

        // Resmi yükle
        imageRef.putFile(imageUri)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }


    public FirestoreManager() {
        // Firestore bağlantısını başlat
        db = FirebaseFirestore.getInstance();
        // Koleksiyon referansını al
        booksCollection = db.collection(COLLECTION_NAME);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
                    Log.e("Error Firestore Book Add",e.toString());
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
                    Log.e("Error Firestore Book Update",e.toString());
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


    // Firestore'a kategori eklemek için metot
    public void addCategory(Category category) {
        // Yeni bir belge oluştur ve verileri ekleyerek koleksiyona ekle
        db.collection(CATEGORIES_COLLECTION_NAME)
                .add(category)
                .addOnSuccessListener(documentReference -> {
                    // Başarıyla eklendiğinde yapılacak işlemler
                    String documentId = documentReference.getId();
                    category.setId(documentId);
                    updateCategory(category);
                })
                .addOnFailureListener(e -> {
                    // Eklenirken bir hata oluştuğunda yapılacak işlemler
                    Log.e("Error Firestore Category Add", e.toString());
                });
    }

    // Firestore'da kategoriyi güncellemek için metot
    private void updateCategory(Category category) {
        // Güncellenecek belgenin referansını al
        DocumentReference categoryRef = db.collection(CATEGORIES_COLLECTION_NAME)
                .document(String.valueOf(category.getId()));

        // Belgeyi güncelle
        categoryRef.set(category)
                .addOnSuccessListener(aVoid -> {
                    // Belge başarıyla güncellendiğinde yapılacak işlemler
                })
                .addOnFailureListener(e -> {
                    // Güncellenirken bir hata oluştuğunda yapılacak işlemler
                    Log.e("Error Firestore Category Update", e.toString());
                });
    }

    // Firestore'dan kategorileri getirmek için metot
    public void getCategories(OnCategoriesLoadedListener listener) {
        CollectionReference categoriesCollection = db.collection(CATEGORIES_COLLECTION_NAME);

        categoriesCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Category> categoryList = new ArrayList<>();

                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Category category = document.toObject(Category.class);
                        if (category != null) {
                            categoryList.add(category);
                        }
                    }

                    listener.onCategoriesLoaded(categoryList);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreManager", "Kategoriler alınırken hata oluştu: " + e.toString());
                    listener.onCategoriesLoadFailed("Veri çekme hatası: " + e.toString());
                });
    }


    // Firestore'dan belirli bir kategoriye ait kitapları getirmek için metot
    public void getBooksByCategory(String category, OnBooksLoadedListener listener) {
        CollectionReference booksCollection = FirebaseFirestore.getInstance().collection("books");

        booksCollection.whereEqualTo("category", category)
                .get()
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

    public void getOneBookPerCategory(OnBooksLoadedListener listener) {
        // Kategorileri getir
        getCategories(new OnCategoriesLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> categoryList) {
                List<Book> oneBookPerCategoryList = new ArrayList<>();

                // Her kategori için
                for (Category category : categoryList) {
                    // Belirli bir kategoriden bir kitap getir
                    getBooksByCategory(category.getName(), new OnBooksLoadedListener() {
                        @Override
                        public void onBooksLoaded(List<Book> bookList) {
                            // Eğer kitaplar varsa, ilk kitabı al ve listeye ekle
                            if (!bookList.isEmpty()) {
                                oneBookPerCategoryList.add(bookList.get(0));
                            }

                            // Eğer tüm kategorilere baktıysak, sonucu döndür
                            if (oneBookPerCategoryList.size() == categoryList.size()) {
                                listener.onBooksLoaded(oneBookPerCategoryList);
                            }
                        }
                        @Override
                        public void onBooksLoadFailed(String errorMessage) {
                            // Kitaplar alınırken hata oluştuğunda yapılacak işlemler
                            Log.e("FirestoreManager", "Kitaplar alınırken hata oluştu: " + errorMessage);
                        }
                    });
                }
            }

            @Override
            public void onCategoriesLoadFailed(String errorMessage) {
                // Kategoriler alınırken hata oluştuğunda yapılacak işlemler
                Log.e("FirestoreManager", "Kategoriler alınırken hata oluştu: " + errorMessage);
            }
        });
    }






}


