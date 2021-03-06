package com.fishky.service;

import com.fishky.dto.IdDto;
import com.fishky.dto.NameDto;
import com.fishky.dto.dictionary.DictionaryCreateRequestDto;
import com.fishky.dto.dictionary.DictionaryDto;
import com.fishky.dto.dictionary.DictionaryResponseDto;
import com.fishky.model.DictionaryEntity;
import com.fishky.model.UserEntity;
import com.fishky.repository.DictionaryRepository;
import com.fishky.repository.UserRepository;
import com.fishky.security.config.AccountRoles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DictionaryServiceTest {

    @InjectMocks
    private DictionaryService dictionaryService;

    @Mock
    private DictionaryRepository dictionaryRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void add_whenCorrectDictionaryContentIsProvided_thenNewDictionaryResponseDtoIsReturned() {
        //given
        final DictionaryCreateRequestDto dictionaryDto = DictionaryCreateRequestDto.of("Unit2", "German");
        final UserEntity userEntity = new UserEntity(12L, "TestUser", "TestPassword", Timestamp.valueOf(LocalDateTime.now()), AccountRoles.USER);
        final DictionaryEntity entity = new DictionaryEntity(16L,"Unit2", "German", userEntity);

        when(dictionaryRepository.save(any())).thenReturn(entity);
        when(userRepository.readByUsername("TestUser")).thenReturn(userEntity);

        //when
        DictionaryResponseDto dictionaryResponseDto = dictionaryService.add(dictionaryDto, NameDto.of("TestUser"));
        //then
        assertAll("Should return correct dictionary data",
                () -> assertEquals(16L, dictionaryResponseDto.getId()),
                () -> assertEquals("Unit2", dictionaryResponseDto.getName()),
                () -> assertEquals("German", dictionaryResponseDto.getLanguage())
        );
    }

    @Test
    public void add_whenIncorrectUserContentIsProvided_thenNullPointerExceptionIsThrown() {
        //given
        final DictionaryCreateRequestDto dictionaryDto = DictionaryCreateRequestDto.of("User1", null);

        //when
        when(dictionaryRepository.save(any())).thenThrow(new NullPointerException());

        //then
        assertThrows(NullPointerException.class, () -> dictionaryService.add(dictionaryDto, NameDto.of("User1")));

        // only mock is tested, need to implement validation methods
    }

    @Test
    void read_whenExistingDictionaryIdIsProvided_thenRetrievedDictionaryContentIsCorrect() {
        //given
        final IdDto dto = IdDto.of(70L);

        final UserEntity userEntity = new UserEntity(
                18L, "TestUser", "TestPassword", Timestamp.valueOf(LocalDateTime.now()), AccountRoles.USER);
        final DictionaryEntity dictionaryEntity = new DictionaryEntity(
                70L, "Unit4", "Mandarin", userEntity);

        Mockito.when(dictionaryRepository.read(dto.getId())).thenReturn(dictionaryEntity);

        //when
        DictionaryResponseDto resultDictionary = dictionaryService.read(dto);

        //then
        assertAll("Should return correct dictionary data",
                () -> assertEquals(70L, resultDictionary.getId()),
                () -> assertEquals("Mandarin", resultDictionary.getLanguage()),
                () -> assertEquals("Unit4", resultDictionary.getName())
        );
    }

    @Test
    public void read_whenDictionaryIdDoesNotExist_thenNullPointerExceptionIsThrown() {
        //given
        final IdDto dto = IdDto.of(62000L);

        //when
        when(dictionaryRepository.read(dto.getId())).thenReturn(null);

        //then
        assertThrows(NullPointerException.class, () -> dictionaryService.read(dto));
    }

    @Test
    void readUsersDictionaries() {
        //given
        final NameDto dto = NameDto.of("User123");
        final UserEntity userEntity = new UserEntity(1L, "User123", "StrongPassword123", Timestamp.valueOf(LocalDateTime.now()), AccountRoles.USER);
        final List<DictionaryEntity> dictionaryEntityList = new ArrayList<>();

        dictionaryEntityList.add(new DictionaryEntity(1L, "Unit 1", "English", userEntity));
        dictionaryEntityList.add(new DictionaryEntity(2L, "Kapitel zwei", "Deutsch", userEntity));
        dictionaryEntityList.add(new DictionaryEntity(3L, "Kapitel drei", "Deutsch", userEntity));
        when(dictionaryRepository.readUsersDictionaries(dto.getName())).thenReturn(dictionaryEntityList);

        //when
        List<DictionaryDto> usersDictionaries = dictionaryService.readUsersDictionaries(dto);
        //then
        assertEquals(3, usersDictionaries.size());
        assertAll("Should return correct data of first dictionary",
                () -> assertEquals("English", usersDictionaries.get(0).getLanguage()),
                () -> assertEquals("Unit 1", usersDictionaries.get(0).getName())
        );
        assertAll("Should return correct data of second dictionary",
                () -> assertEquals("Deutsch", usersDictionaries.get(1).getLanguage()),
                () -> assertEquals("Kapitel zwei", usersDictionaries.get(1).getName())
        );
        assertAll("Should return correct data of third dictionary",
                () -> assertEquals("Deutsch", usersDictionaries.get(2).getLanguage()),
                () -> assertEquals("Kapitel drei", usersDictionaries.get(2).getName())
        );
    }

    @Test
    void modify_whenCorrectDictionaryContentIsProvided_thenModifiedDictionaryIsReturned() {
        //given
        final DictionaryDto dictionaryDto = DictionaryDto.of(39L,"Unit21", "English");
        final UserEntity userEntity = new UserEntity(12L, "TestUser", "TestPassword", Timestamp.valueOf(LocalDateTime.now()), AccountRoles.USER);
        final DictionaryEntity dictionaryEntity = new DictionaryEntity(39L, "Unit21", "English", userEntity);

        when(dictionaryRepository.modify(any())).thenReturn(dictionaryEntity);
        when(userRepository.readByUsername("Unit21")).thenReturn(userEntity);

        //when
        DictionaryDto resultDictionary = dictionaryService.modify(dictionaryDto, NameDto.of("Unit21"));

        //then
        assertAll("Should return correct, modified dictionary data",
                () -> assertEquals(39L, resultDictionary.getId()),
                () -> assertEquals("Unit21", resultDictionary.getName()),
                () -> assertEquals("English", resultDictionary.getLanguage())
        );
    }

    @Test
    void delete() {
        // only mock is tested, need to implement validation methods
    }
}