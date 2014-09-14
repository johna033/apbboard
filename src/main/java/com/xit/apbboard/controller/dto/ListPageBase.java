package com.xit.apbboard.controller.dto;

import java.io.Serializable;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
public class ListPageBase implements Serializable {
    public int itemsPerPage;
    public int itemsCount;
    public int offset;
    public int size;
}
