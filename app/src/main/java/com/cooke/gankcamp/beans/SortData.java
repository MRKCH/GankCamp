package com.cooke.gankcamp.beans;

import java.util.List;

/**
 * Created by kch on 2017/12/7.
 */

public class SortData {

    private boolean error;

    private List<GankBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankBean> getResults() {
        return results;
    }

    public void setResults(List<GankBean> results) {
        this.results = results;
    }
}
