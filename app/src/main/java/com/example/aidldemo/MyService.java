package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyService extends Service {
    private CopyOnWriteArrayList<Book> bookList;//CopyOnWriteArrayList支持并发读写

    public MyService(){
        bookList = new CopyOnWriteArrayList<>();
    }

    private IBookManager.Stub stub = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            Log.v("ljh","这里是Service，新增的书为" + book);
            bookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}