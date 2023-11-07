package com.prisc.springboot.util;

import com.prisc.springboot.domain.Anime;
import com.prisc.springboot.request.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody(){
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createValidAnime().getName())
                .build();
    }

}
