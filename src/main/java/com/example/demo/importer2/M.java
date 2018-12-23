package com.example.demo.importer2;

import java.util.List;

public class M {
    int total;
    List<N> docs;
    int offset;
    String title;
    boolean isEnd;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<N> getDocs() {
        return docs;
    }

    public void setDocs(List<N> docs) {
        this.docs = docs;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
