package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class ListTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Object> data = Lists.newArrayList();

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
