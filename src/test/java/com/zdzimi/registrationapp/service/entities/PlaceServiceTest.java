package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.PlaceNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.repository.PlaceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlaceServiceTest {

    private static final String INSTITUTION_NAME = "institutionName";
    private static final String PLACE_NAME = "placeName";
    private static final long PLACE_ID = 1;

    private PlaceService placeService;
    private PlaceRepo placeRepo;

    @BeforeEach
    void setUp() {
        placeRepo = mock(PlaceRepo.class);
        initMocks(this);
        placeService = new PlaceService(placeRepo);
    }

    @Test
    void shouldFindByInstitutionAndPlaceName() {
        //  given
        Institution institution = new Institution();
        Place place = new Place();
        place.setPlaceId(PLACE_ID);
        place.setInstitution(institution);
        place.setPlaceName(PLACE_NAME);
        when(placeRepo.findByInstitutionAndPlaceName(institution, PLACE_NAME)).thenReturn(Optional.of(place));
        //  when
        Place result = placeService.findByInstitutionAndPlaceName(institution, PLACE_NAME);
        //  then
        assertEquals(1, result.getPlaceId());
        assertEquals(PLACE_NAME, result.getPlaceName());
        verify(placeRepo, times(1)).findByInstitutionAndPlaceName(institution, PLACE_NAME);
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void shouldThrowExceptionWhenPlaceNotFoundByInstitutionAndPlaceName() {
        //  given
        Institution institution = new Institution();
        when(placeRepo.findByInstitutionAndPlaceName(institution, PLACE_NAME)).thenReturn(Optional.empty());
        //  when
        PlaceNotFoundException exception = assertThrows(PlaceNotFoundException.class,
                () -> placeService.findByInstitutionAndPlaceName(institution, PLACE_NAME));
        //  then
        assertEquals("Could not find place: " + PLACE_NAME, exception.getMessage());
        verify(placeRepo, times(1)).findByInstitutionAndPlaceName(institution, PLACE_NAME);
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void shouldGetPlaceByInstitutionAndPlaceName() {
        //  given
        Institution institution = new Institution();
        Place place = new Place();
        place.setPlaceId(PLACE_ID);
        place.setInstitution(institution);
        place.setPlaceName(PLACE_NAME);
        when(placeRepo.findByInstitutionAndPlaceName(institution, PLACE_NAME)).thenReturn(Optional.of(place));
        //  when
        Optional<Place> result = placeService.getPlaceByInstitutionAndPlaceName(institution, PLACE_NAME);
        //  then
        assertEquals(PLACE_ID, result.get().getPlaceId());
        assertEquals(PLACE_NAME, result.get().getPlaceName());
        verify(placeRepo, times(1)).findByInstitutionAndPlaceName(institution, PLACE_NAME);
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void shouldFindByInstitution() {
        //  given
        Institution institution = new Institution();
        Place place = new Place();
        place.setInstitution(institution);
        when(placeRepo.findByInstitution(institution)).thenReturn(Arrays.asList(place));
        //  when
        List<Place> result = placeService.findByInstitution(institution);
        //  then
        assertEquals(1, result.size());
        assertEquals(place, result.get(0));
        verify(placeRepo, times(1)).findByInstitution(institution);
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void shouldSave() {
        //  given
        Place placeBeforeSave = new Place();
        Place placeAfterSave = new Place();
        placeAfterSave.setPlaceId(PLACE_ID);
        placeAfterSave.setPlaceName(PLACE_NAME);
        when(placeRepo.save(ArgumentMatchers.any(Place.class))).thenReturn(placeAfterSave);
        //  when
        Place result = placeService.save(placeBeforeSave);
        //  then
        assertEquals(placeAfterSave, result);
        verify(placeRepo, times(1)).save(placeBeforeSave);
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void shouldDelete() {
        //  given
        //  when
        //  then
        //  todo
    }

    @Test
    void shouldFindByInstitutionAndPlaceId() {
        //  given
        Institution institution = new Institution();
        Place place = new Place();
        place.setPlaceId(PLACE_ID);
        place.setInstitution(institution);
        place.setPlaceName(PLACE_NAME);
        when(placeRepo.findByInstitutionAndPlaceId(institution, PLACE_ID)).thenReturn(Optional.of(place));
        //  when
        Place result = placeService.findByInstitutionAndPlaceId(institution, PLACE_ID);
        //  then
        assertEquals(place, result);
        assertEquals(PLACE_ID, result.getPlaceId());
        assertEquals(PLACE_NAME, result.getPlaceName());
        verify(placeRepo, times(1)).findByInstitutionAndPlaceId(institution, PLACE_ID);
        verifyNoMoreInteractions(placeRepo);
    }

    @Test
    void shouldThrowExceptionWhenPlaceNotFoundByInstitutionAndPlaceId() {
        //  given
        Institution institution = new Institution();
        institution.setInstitutionName(INSTITUTION_NAME);
        when(placeRepo.findByInstitutionAndPlaceId(institution, 1)).thenReturn(Optional.empty());
        //  when
        PlaceNotFoundException exception = assertThrows(PlaceNotFoundException.class,
                () -> placeService.findByInstitutionAndPlaceId(institution, PLACE_ID));
        //  then
        assertEquals("Could not find place: " + PLACE_ID + " from " + INSTITUTION_NAME, exception.getMessage());
        verify(placeRepo, times(1)).findByInstitutionAndPlaceId(institution,PLACE_ID);
        verifyNoMoreInteractions(placeRepo);
    }
}