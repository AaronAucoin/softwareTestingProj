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
        // its just as easy for me to use a real book object
        book = new Book(
                "The Lion, The Witch, and The Wardrobe",
                "CS Lewis",
                1950,
                "9780001831803",
                "Fantasy",
                library
        );
        member = new Member("Reed, Steven", "sreed43@lsu.edu", 0);
    }

    // Test getting member ID and name
    @Test
    void getMemberIdAndName() {
        assertEquals(0, member.getMemberId());
        assertEquals("Reed, Steven", member.getName());
    }

    // Testing adding and removing a book from the member's borrowedBooksList
    @Test
    void addAndRemoveBook() {
        List<Book> borrowedList = member.addBorrowedBook(book);
        assertEquals(1, borrowedList.size());
        assertEquals(book, borrowedList.get(0));
    }

    // Testing removing a book from a member's borrowedBookList
    @Test
    void removeBook() {
        List<Book> borrowedList = member.addBorrowedBook(book);
        assertEquals(1, borrowedList.size());
        member.removeBorrowedBook(book);
        assertEquals(0, borrowedList.size());
    }

    // Testing updateMemberInfo

}
