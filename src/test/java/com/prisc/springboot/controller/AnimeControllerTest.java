package com.prisc.springboot.controller;

import com.prisc.springboot.domain.Anime;
import com.prisc.springboot.request.AnimePostRequestBody;
import com.prisc.springboot.request.AnimePutRequestBody;
import com.prisc.springboot.service.AnimeService;
import com.prisc.springboot.util.AnimeCreator;
import com.prisc.springboot.util.AnimePostRequestBodyCreator;
import com.prisc.springboot.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks  //Para classe que está sendo testada
    private AnimeController animeController;

    @Mock // Para as classes que são usadas pela classe testada
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNotPageble())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing()
                .when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing()
                .when(animeServiceMock).delete(ArgumentMatchers.anyLong());



    }




    @Test
    @DisplayName("List returns list of Animes inside PageObject when successful")
    void list_returnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null)
                .getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }




    @Test
    @DisplayName("List returns list of Animes when successful")
    void listAll_returnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);


    }



    @Test
    @DisplayName("findById returns anime when successful")
    void findById_returnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);


    }



    @Test
    @DisplayName("findByName returns list of Animes when successful")
    void findByName_returnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.findByName("Any name").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName())
                .isNotNull()
                .isEqualTo(expectedName);


    }



    @Test
    @DisplayName("findByName returns an empty list of Animes when anime is not found")
    void findByName_returnsEmptyListOfAnimes_WhenAnimeIsNotFound(){

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("Any name").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();



    }




    @Test
    @DisplayName("save returns Anime when successful")
    void save_returnsAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        Anime anime = animeController.save(animePostRequestBody).getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());



    }




    @Test
    @DisplayName("replace updates Anime when successfull")
    void replace_updatesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                        .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }




    @Test
    @DisplayName("delete removes Anime when successfull")
    void delete_removesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() -> animeController.delete(1))
                        .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }

}