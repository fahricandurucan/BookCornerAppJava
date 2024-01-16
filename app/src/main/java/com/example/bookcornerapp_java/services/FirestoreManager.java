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

    private static final String COLLECTION_NAME = "books"; // colleciton name book on firebasefirestore
    private static final String CATEGORIES_COLLECTION_NAME = "categories"; //colleciton name category on firebasefirestore

    private FirebaseFirestore db;
    private CollectionReference booksCollection;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    public void uploadImage(Uri imageUri, String imageName, OnSuccessListener<UploadTask.TaskSnapshot> successListener, OnFailureListener failureListener) {
        StorageReference imageRef = storageReference.child("images/" + imageName);

        imageRef.putFile(imageUri)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }


    public FirestoreManager() {
        // Firestore bağlantısını başlattık
        db = FirebaseFirestore.getInstance();
        // Koleksiyon referansını aldık
        booksCollection = db.collection(COLLECTION_NAME);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    // add book to firebase
    public void addBook(Book book) {
        booksCollection.add(book)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    book.setId(documentId);
                    updateBook(book); // id ataması için güncelledik
                })
                .addOnFailureListener(e -> {
                    // if there is an error
                    Log.e("Error Firestore Book Add",e.toString());
                });
    }

    // update book from firebase (method)
    private void updateBook(Book book) {
        DocumentReference bookRef = booksCollection.document(String.valueOf(book.getId()));

        bookRef.set(book)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
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


    // Add category to firebase
    public void addCategory(Category category) {
        db.collection(CATEGORIES_COLLECTION_NAME)
                .add(category)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    category.setId(documentId);
                    updateCategory(category);
                })
                .addOnFailureListener(e -> {
                    Log.e("Error Firestore Category Add", e.toString());
                });
    }

    // update category method
    private void updateCategory(Category category) {
        DocumentReference categoryRef = db.collection(CATEGORIES_COLLECTION_NAME)
                .document(String.valueOf(category.getId()));

        categoryRef.set(category)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                    Log.e("Error Firestore Category Update", e.toString());
                });
    }

    // get category from firebase method
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


    // get books by categories method
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

    // get one book each category ( sabit metot)
    public void getOneBookPerCategory(OnBooksLoadedListener listener) {
        getCategories(new OnCategoriesLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> categoryList) {
                List<Book> oneBookPerCategoryList = new ArrayList<>();
                List<String> bookNameList = new ArrayList<>();
                bookNameList.add("Whole 30");
                bookNameList.add("The Elegant Universe");
                bookNameList.add("Orlando");
                bookNameList.add("Sapiens");
                bookNameList.add("Charlotte's Web");
                bookNameList.add("1984");


                getBooksByNames(bookNameList, new OnBooksLoadedListener() {
                    @Override
                    public void onBooksLoaded(List<Book> bookList) {
                        oneBookPerCategoryList.addAll(bookList);
                        listener.onBooksLoaded(oneBookPerCategoryList);
                    }

                    @Override
                    public void onBooksLoadFailed(String errorMessage) {
                        Log.e("FirestoreManager", "Kitaplar alınırken hata oluştu: " + errorMessage);
                        listener.onBooksLoadFailed(errorMessage);
                    }
                });
            }

            @Override
            public void onCategoriesLoadFailed(String errorMessage) {
                Log.e("FirestoreManager", "Kategoriler alınırken hata oluştu: " + errorMessage);
                listener.onBooksLoadFailed(errorMessage);
            }
        });
    }


    // get book by bookName from firebasefirestore
    private void getBooksByNames(List<String> bookNames, OnBooksLoadedListener listener) {
        CollectionReference booksCollection = FirebaseFirestore.getInstance().collection("books");
        booksCollection.whereIn("name", bookNames)
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


}


