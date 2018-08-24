package com.example.student.coinrx;

import com.example.student.coinrx.Date.CryptoList;
import com.example.student.coinrx.Info.Info;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/v1/cryptocurrency/listings/latest?")
    Single<CryptoList> getMarketPairsLatest(@Query("limit") String limit);
    @GET("/v1/cryptocurrency/info")
    Single<Info> getCryptocurrencyInfo(@Query("symbol") String symbol);
}
