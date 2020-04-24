package com.ria.ait.drv_database.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ItemJsonResponse {
    private String sort;
    private int totalNumberOfElements;
    private int requestedPageSize;
    private int actualPageSize;
    private int currentPage;
    private List<ItemObject> content;
    private boolean isFirstPage;
    private boolean isLastPage;
    private Integer pageCount;

    public ItemJsonResponse(){

    }

    public  ItemJsonResponse(Page<Item> items, List<ItemObject> items2){
        this.sort = items.getPageable().getSort().toString().replace(":",",");
        this.totalNumberOfElements = (int)items.getTotalElements();
        this.content = items2;
        this.requestedPageSize = items.getPageable().getPageSize();
        this.actualPageSize = items.getContent().size();
        this.currentPage = items.getPageable().getPageNumber();
        if(this.totalNumberOfElements < this.requestedPageSize){
            this.isFirstPage = true;
            this.isLastPage = true;
        }
        else if(((this.currentPage + 1) * requestedPageSize ) >= this.totalNumberOfElements){
            this.isLastPage = true;
        }
        else if((this.actualPageSize + currentPage) <= this.requestedPageSize){
            this.isFirstPage = true;
        }
        else{
            this.isFirstPage = false;
            this.isLastPage = false;
        }
        this.pageCount = (int)Math.ceil(((double)this.totalNumberOfElements)/((double)this.requestedPageSize));
    }
}
