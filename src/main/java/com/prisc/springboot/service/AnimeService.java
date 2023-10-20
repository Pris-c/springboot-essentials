package com.prisc.springboot.service;

import com.prisc.springboot.exception.BadRequestException;
import com.prisc.springboot.domain.Anime;
import com.prisc.springboot.mapper.AnimeMapper;
import com.prisc.springboot.repository.AnimeRepository;
import com.prisc.springboot.request.AnimePostRequestBody;
import com.prisc.springboot.request.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable){
        return animeRepository.findAll(pageable);
    }
    public List<Anime> findByName(String name){
            return animeRepository.findByName(name);
        }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found."));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        //System.out.println("animePostRequestBody " + animePostRequestBody.getName());
        //System.out.println("AnimeMapper" + AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody){
        System.out.println("animePutRequestBody " + animePutRequestBody.getName());
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}