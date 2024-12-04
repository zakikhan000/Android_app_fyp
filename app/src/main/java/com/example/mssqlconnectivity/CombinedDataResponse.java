package com.example.mssqlconnectivity;

import java.util.List;

public class CombinedDataResponse {
    private List<CombinedUser> combined_data;

    public List<CombinedUser> getCombinedData() {
        return combined_data;
    }

    public void setCombinedData(List<CombinedUser> combined_data) {
        this.combined_data = combined_data;
    }
}
