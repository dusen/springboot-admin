/**
 * @(#)PaginationHolder.java
 *
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import lombok.Getter;
import lombok.Setter;

/**
 * A simple state holder for handling lists of objects, separating them into pages. Page numbering
 * starts with 0.
 *
 */
public class PaginationHolder {

    /** Default rows count number in a page(5). */
    public static final int DEFAULT_PAGE_SIZE = 5;

    /** Default max linked pages count number in a page(10). */
    public static final int DEFAULT_MAX_LINKED_PAGES = 10;

    /** Default linked pages group id prefix name with 'paginationLink'. */
    public static final String DEFAULT_PAGE_LINK_ID_PREFIX = "paginationLink";

    /** Show rows count number in a page. */
    @Getter
    private int pageSize = DEFAULT_PAGE_SIZE;

    /** The current page index. */
    @Getter
    private int page;

    /** Max linked pages count number in a page. */
    @Getter
    private int maxLinkedPages = DEFAULT_MAX_LINKED_PAGES;

    /** Total data rows number. */
    @Getter
    private int totalRows = 0;

    /** Total pages count. */
    @Getter
    private int pageCount = 0;

    /** True if the current page is the first one. */
    @Getter
    private boolean firstPage;

    /** True if the current page is the last one. */
    @Getter
    private boolean lastPage;

    /** True if the current page > 0. */
    @Getter
    private boolean canPreviousPage;

    /** True if the current page number < last page number. */
    @Getter
    private boolean canNextPage;

    /** The min number of page links. */
    @Getter
    private int firstLinkedPage;

    /** The max number of page links. */
    @Getter
    private int lastLinkedPage;

    /** The page links group id prefix name. */
    @Getter
    @Setter
    private String pageLinkIdPrefix = DEFAULT_PAGE_LINK_ID_PREFIX;

    /**
     * Create a new holder instance with the given total rows number and the current page index.
     * 
     * @param totalRows Total rows number.
     * @param page The current page index.
     */
    public PaginationHolder(int totalRows, int page) {
        this.totalRows = totalRows;
        this.page = page;

        initPaginationParameters();
    }

    /**
     * Create a new holder instance with the given total rows number and the current page index and
     * rows count number and max linked pages count number.
     * 
     * @param totalRows Total rows number.
     * @param page The current page index.
     * @param pageSize Rows count number in a page.
     * @param maxLinkedPages Max linked pages count number.
     */
    public PaginationHolder(int totalRows, int page, int pageSize, int maxLinkedPages) {
        this.totalRows = totalRows;
        this.page = page;
        this.pageSize = pageSize;
        this.maxLinkedPages = maxLinkedPages;
        initPaginationParameters();
    }

    /**
     * Initialize pagination parameters.
     */
    private void initPaginationParameters() {
        pageCount = Math.max((totalRows + pageSize - 1) / pageSize, 1);

        firstPage = page == 0;

        lastPage = page == (pageCount - 1);

        canPreviousPage = !firstPage;

        canNextPage = !lastPage;

        firstLinkedPage = Math.max(0, page - (maxLinkedPages / 2));

        lastLinkedPage = Math.min(firstLinkedPage + maxLinkedPages - 1, pageCount - 1);
    }
}
