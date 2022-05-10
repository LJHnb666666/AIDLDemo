// IBookManager.aidl
package com.example.aidldemo;

import com.example.aidldemo.Book;

interface IBookManager {
    void addBook(in Book book);
    List<Book> getBookList();
}