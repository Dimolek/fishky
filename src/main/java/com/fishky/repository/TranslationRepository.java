package com.fishky.repository;

import com.fishky.model.TranslationEntity;

import java.util.List;

public interface TranslationRepository {
    TranslationEntity save(TranslationEntity translation);
    List<Long> saveMany(List<TranslationEntity> translations);

    //consider later
    TranslationEntity read();

    TranslationEntity modify(TranslationEntity translation);
    Boolean delete(Long id);

}
