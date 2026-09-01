package com.ecommerce.demo.features.UserProfile.page.response;

public class PageResponse<T> {
    private T data;
    private int totalPages;
    private int currentPage;
    private Long totalItems;
    private boolean isLast;

    public PageResponse(T data, int totalPages, int currentPage, Long totalItems, boolean isLast) {
        this.data = data;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.isLast = isLast;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
