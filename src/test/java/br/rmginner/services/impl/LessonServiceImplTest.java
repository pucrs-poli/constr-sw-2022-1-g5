package br.rmginner.services.impl;

import br.rmginner.dtos.ContentDto;
import br.rmginner.dtos.LessonDto;
import br.rmginner.entities.Content;
import br.rmginner.entities.Lesson;
import br.rmginner.entities.enums.ContentType;
import br.rmginner.repositories.LessonsRepository;
import br.rmginner.services.LessonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class LessonServiceImplTest {

    private static final String TEST_NAME = "Test";

    private static final String TEST_ID = "102asd12nasd12e19e1";

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2022, 5, 4, 20, 13, 0);

    private static final String TEST_DATE_AS_STRING = "2022-05-04T20:13:00";

    @MockBean
    private LessonsRepository repository;

    @Autowired
    private LessonService service;

    @Test
    void shouldCreateLesson() {
        final var lessonToSave = getTestLesson();
        final var savedLesson = getTestLesson();

        savedLesson.setId(TEST_ID);

        Mockito.when(repository.save(lessonToSave))
                .thenReturn(savedLesson);

        final var createdLessonDto = service.create(getTestLessonDto());

        Assertions.assertEquals(TEST_ID, createdLessonDto.getId());
    }

    @Test
    void shouldGetAllLessons() {
        final var lessons = List.of(getTestLesson());
        final var lessonDtoListToReturn = List.of(getTestLessonDto());

        Mockito.when(repository.findAll())
                .thenReturn(lessons);

        final var lessonDtoList = service.getAll();

        Assertions.assertEquals(1, lessonDtoList.size());
        Assertions.assertEquals(lessonDtoListToReturn, lessonDtoList);
    }

    @Test
    void shouldGetLessonById() {
        final var lesson = getTestLesson();

        Mockito.when(repository.findById(TEST_ID))
                .thenReturn(Optional.of(lesson));

        final var lessonDtoOptional = service.getLessonById(TEST_ID);

        Assertions.assertTrue(lessonDtoOptional.isPresent());
    }

    private Lesson getTestLesson() {
        return Lesson.builder()
                .name(TEST_NAME)
                .date(TEST_DATE)
                .contents(
                        List.of(
                                Content.builder()
                                        .name(TEST_NAME)
                                        .type(ContentType.DOC)
                                        .build()
                        )
                )
                .build();
    }

    private LessonDto getTestLessonDto() {
        return LessonDto.builder()
                .name(TEST_NAME)
                .date(TEST_DATE)
                .contents(
                        List.of(
                                ContentDto.builder()
                                        .name(TEST_NAME)
                                        .type(ContentType.DOC)
                                        .build()
                        )
                )
                .build();
    }

}