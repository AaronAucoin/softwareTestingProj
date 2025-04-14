package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;

public class MemberTest {
    private Library library;
    private Book book;
    private Member member;

    @BeforeEach
    void setup() {
        library = mock(Library.class);
        book = mock(Book.class);
        member = new Member("Reed, Steven", "sreed43@lsu.edu", 0);
    }

    // Test getting member ID and name
    @Test
    void getMemberIdAndName() {
        assertEquals(0, member.getMemberId());
        assertEquals("Reed, Steven", member.getName());
    }

    // Testing adding a book to the member's borrowedBooksList
    @Test
    void addBook() {
        List<Book> borrowedList = member.addBorrowedBook(book);
        assertEquals(1, borrowedList.size());
        assertEquals(book, borrowedList.get(0));
    }

    // Testing
}
