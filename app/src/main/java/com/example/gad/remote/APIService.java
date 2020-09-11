package com.example.gad.remote;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    final String PATH = "1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse";
    @POST (PATH)
    @FormUrlEncoded
    Call<Path> savePost(@Field("entry.1877115667") String firstName,
                        @Field("entry.2006916086") String lastName,
                        @Field("entry.1824927963") String emailAddress,
                        @Field("entry.284483984") String projectLink
    );
//    Call<Void?>
}
