package com.prisc.springboot.client;

import com.prisc.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args){

        // getForEntity() retorna o objeto e outras informações
        ResponseEntity<Anime> entity = new RestTemplate()
                                       .getForEntity("http://localhost:8080/animes/2", Anime.class);
        log.info(entity);

        // getForObject() retorna apenas o objeto
        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object);



    }
}
