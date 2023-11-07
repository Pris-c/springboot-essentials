package com.prisc.springboot.service;

import com.prisc.springboot.domain.Anime;
import com.prisc.springboot.exception.BadRequestException;
import com.prisc.springboot.repository.AnimeRepository;
import com.prisc.springboot.request.AnimePostRequestBody;
import com.prisc.springboot.util.AnimeCreator;
import com.prisc.springboot.util.AnimePostRequestBodyCreator;
import com.prisc.springboot.util.AnimePutRequestBodyCreator;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@ExtendWith(SpringExtension.class)
class AnimeServiceTest {


    @InjectMocks  //Para classe que está sendo testada
    private AnimeService animeService;

    @Mock // Para as classes que são usadas pela classe testada
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());


        BDDMockito.doNothing()
                .when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));



    }




    @Test
    @DisplayName("listAll returns list of Animes inside PageObject when successful")
    void listAll_returnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }




    @Test
    @DisplayName("listAllNotPageble returns list of Animes when successful")
    void listAllNotPageble_returnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.listAllNotPageble();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);


    }



    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
    void findByIdOrThrowBadRequestException_returnsAnime_WhenSuccessful(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1));

    }



    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when Anime is not found")
    void findByIdOrThrowBadRequestException_throwsBadRequestException_WhenAnimeIsNotFound(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findByIdOrThrowBadRequestException(1);

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

        List<Anime> animes = animeService.findByName("Any name");

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

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("Any name");

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();



    }




    @Test
    @DisplayName("save returns Anime when successful")
    void save_returnsAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        Anime anime = animeService.save(animePostRequestBody);

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());



    }




    @Test
    @DisplayName("replace updates Anime when successfull")
    void replace_updatesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();


    }




    @Test
    @DisplayName("delete removes Anime when successfull")
    void delete_removesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();

    }

}