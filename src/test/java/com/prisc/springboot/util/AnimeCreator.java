package com.prisc.springboot.util;

import com.prisc.springboot.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Anime Test").
                build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Valid Anime Test")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime(){
        return Anime.builder()
                .name("Anime To Update")
                .id(1L)
                .build();
    }

}
