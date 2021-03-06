package com.fishky.repository.implemenetation;

import com.fishky.model.TranslationEntity;
import com.fishky.repository.TranslationRepository;
import com.fishky.repository.orm.TranslationOrmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TranslationRepositoryImpl implements TranslationRepository {

    @Autowired
    private TranslationOrmRepository ormRepository;

    @Override
    public TranslationEntity save(final TranslationEntity translation) {

        return ormRepository.saveAndFlush(translation);
    }

    @Override
    public List<Long> saveMany(List<TranslationEntity> translations) {

        return ormRepository.saveAll(translations)
                .stream()
                .map(TranslationEntity::getIdTranslation)
                .collect(Collectors.toList());
    }

    @Override
    public TranslationEntity read() {
        return null;
    }

    @Override
    public TranslationEntity modify(TranslationEntity translation) {
        //Possible 'exists' validation
        return ormRepository.saveAndFlush(translation);
    }

    @Override
    public Boolean delete(Long id) {
        //Possible 'exists' validation
        ormRepository.deleteById(id);
        return !ormRepository.existsById(id);
    }

}
