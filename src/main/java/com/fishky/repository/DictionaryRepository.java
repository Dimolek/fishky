package com.fishky.repository;

import com.fishky.model.DictionaryEntity;

import java.util.List;

public interface DictionaryRepository {
    DictionaryEntity save(DictionaryEntity dictionary);
    DictionaryEntity readWithFetch(Long id);
    DictionaryEntity read(Long id);
    List<DictionaryEntity> readUsersDictionaries(Long userId);
    DictionaryEntity modify(DictionaryEntity dictionary);
    Boolean delete(Long id);

}
