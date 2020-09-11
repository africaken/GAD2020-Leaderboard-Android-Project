package com.example.gad.remote.data;

import com.example.gad.remote.APIService;
import com.example.gad.remote.RetrofitClient;

public class ApiUtils {
   private ApiUtils() {}
   public static final String BASE_URL = " https://docs.google.com/forms/d/e/";
   public static APIService getAPIService() {
      return RetrofitClient.getClient(BASE_URL).create(APIService.class);
   }

}
