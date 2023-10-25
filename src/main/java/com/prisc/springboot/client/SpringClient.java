package com.prisc.springboot.client;

import com.prisc.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args){

        // getForEntity() retorna o objeto e outras informações
        ResponseEntity<Anime> entity = new RestTemplate()
                                       .getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(entity);

        // getForObject() retorna apenas o objeto
        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 12);
        log.info(object);

        // getForObject() retornando lista de objetos
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        // exchange: permite definir formato do retorno. Neste caso, uma List
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
        log.info(exchange.getBody());

        /*
        // RestTemplate POST: postForObject
        Anime kingdon = Anime.builder().name("Kingdon").build();
        Anime kingdonSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdon, Anime.class);
        log.info("saved anime {} ", kingdonSaved);
        */

        // RestTemplate POST: exchange
        Anime sakura = Anime.builder().name("Sakura").build();
        ResponseEntity<Anime> sakuraSaved = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.POST,
                        new HttpEntity<>(sakura, createJsonHeader()),
                        Anime.class);
        log.info("saved anime {} ", sakuraSaved);


        // RestTemplate PUT: exchange
        Anime animeToBeUpdated = sakuraSaved.getBody();
        animeToBeUpdated.setName("Sakura Cards");
        ResponseEntity<Void> sakuraUpdated = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                    HttpMethod.PUT,
                    new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                    Void.class);
        log.info("anime updated {} ", sakuraUpdated);


        // RestTemplate DELETE: exchange
        ResponseEntity<Void> sakuraDeleted = new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        animeToBeUpdated.getId());
        log.info("anime deleted {} ", sakuraDeleted);

    }



    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
