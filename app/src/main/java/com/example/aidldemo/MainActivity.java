package com.example.aidldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;
/**
 * Created by didiwei on 2022/5/10
 * desc: AIDL的一个小实例，MainActivity扮演客户端的角色
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button connect;
    Button addBook;
    Button getBooks;

    IBookManager iBookManager;
    ServiceConnection serviceConnection;

    Boolean isBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isBind = false;

        connect = findViewById(R.id.btn_bindService);
        addBook = findViewById(R.id.btn_addBook);
        getBooks = findViewById(R.id.btn_getBooks);
        connect.setOnClickListener(this::onClick);
        addBook.setOnClickListener(this::onClick);
        getBooks.setOnClickListener(this::onClick);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v("ljh","这里是客户端，与Service连接成功");
                iBookManager = IBookManager.Stub.asInterface(service);

                isBind = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.v("ljh","这里是客户端，与Service断开连接");
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bindService:
                bindService(new Intent(MainActivity.this,MyService.class),serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_addBook:
                try {
                    iBookManager.addBook(new Book("新书",10));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_getBooks:
                try {
                    List<Book> bookList = iBookManager.getBookList();
                    Log.v("ljh","这里是客户端，从Service获取的书的集合为："+ bookList);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBind){
            unbindService(serviceConnection);
            isBind = false;
        }
    }
}