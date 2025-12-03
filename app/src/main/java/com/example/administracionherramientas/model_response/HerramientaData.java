package com.example.administracionherramientas.model_response;

import com.example.administracionherramientas.models.Herramienta;

import java.util.List;

public class HerramientaData {
    private List<Herramienta> data;
    private int totalRecords;
    private int page;
    private int pageSize;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    public HerramientaData(){};
    public HerramientaData(List<Herramienta> data, int totalRecords, int page, int pageSize, int totalPages, boolean hasNextPage, boolean hasPreviousPage) {
        this.data = data;
        this.totalRecords = totalRecords;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.hasNextPage = hasNextPage;
        this.hasPreviousPage = hasPreviousPage;
    }

    public List<Herramienta> getData() {
        return data;
    }

    public void setData(List<Herramienta> data) {
        this.data = data;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }
}
