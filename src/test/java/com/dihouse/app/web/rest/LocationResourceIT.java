package com.dihouse.app.web.rest;

import com.dihouse.app.RealEstateApp;
import com.dihouse.app.domain.Location;
import com.dihouse.app.repository.LocationRepository;
import com.dihouse.app.repository.search.LocationSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dihouse.app.domain.enumeration.RoadType;
/**
 * Integration tests for the {@link LocationResource} REST controller.
 */
@SpringBootTest(classes = RealEstateApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocationResourceIT {

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_TOWN = "BBBBBBBBBB";

    private static final String DEFAULT_CP = "AAAAAAAAAA";
    private static final String UPDATED_CP = "BBBBBBBBBB";

    private static final RoadType DEFAULT_TYPE_OF_ROAD = RoadType.STREET;
    private static final RoadType UPDATED_TYPE_OF_ROAD = RoadType.AVENUE;

    private static final String DEFAULT_NAME_ROAD = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ROAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_APARTMENT = 1;
    private static final Integer UPDATED_APARTMENT = 2;

    private static final Integer DEFAULT_BUILDING = 1;
    private static final Integer UPDATED_BUILDING = 2;

    private static final Integer DEFAULT_DOOR = 1;
    private static final Integer UPDATED_DOOR = 2;

    private static final String DEFAULT_STAIR = "AAAAAAAAAA";
    private static final String UPDATED_STAIR = "BBBBBBBBBB";

    private static final String DEFAULT_URLGMAPS = "AAAAAAAAAA";
    private static final String UPDATED_URLGMAPS = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private LocationRepository locationRepository;

    /**
     * This repository is mocked in the com.dihouse.app.repository.search test package.
     *
     * @see com.dihouse.app.repository.search.LocationSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocationSearchRepository mockLocationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .ref(DEFAULT_REF)
            .province(DEFAULT_PROVINCE)
            .town(DEFAULT_TOWN)
            .cp(DEFAULT_CP)
            .typeOfRoad(DEFAULT_TYPE_OF_ROAD)
            .nameRoad(DEFAULT_NAME_ROAD)
            .number(DEFAULT_NUMBER)
            .apartment(DEFAULT_APARTMENT)
            .building(DEFAULT_BUILDING)
            .door(DEFAULT_DOOR)
            .stair(DEFAULT_STAIR)
            .urlgmaps(DEFAULT_URLGMAPS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return location;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .ref(UPDATED_REF)
            .province(UPDATED_PROVINCE)
            .town(UPDATED_TOWN)
            .cp(UPDATED_CP)
            .typeOfRoad(UPDATED_TYPE_OF_ROAD)
            .nameRoad(UPDATED_NAME_ROAD)
            .number(UPDATED_NUMBER)
            .apartment(UPDATED_APARTMENT)
            .building(UPDATED_BUILDING)
            .door(UPDATED_DOOR)
            .stair(UPDATED_STAIR)
            .urlgmaps(UPDATED_URLGMAPS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return location;
    }

    @BeforeEach
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();
        // Create the Location
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testLocation.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testLocation.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testLocation.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testLocation.getTypeOfRoad()).isEqualTo(DEFAULT_TYPE_OF_ROAD);
        assertThat(testLocation.getNameRoad()).isEqualTo(DEFAULT_NAME_ROAD);
        assertThat(testLocation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testLocation.getApartment()).isEqualTo(DEFAULT_APARTMENT);
        assertThat(testLocation.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testLocation.getDoor()).isEqualTo(DEFAULT_DOOR);
        assertThat(testLocation.getStair()).isEqualTo(DEFAULT_STAIR);
        assertThat(testLocation.getUrlgmaps()).isEqualTo(DEFAULT_URLGMAPS);
        assertThat(testLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);

        // Validate the Location in Elasticsearch
        verify(mockLocationSearchRepository, times(1)).save(testLocation);
    }

    @Test
    @Transactional
    public void createLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location with an existing ID
        location.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Location in Elasticsearch
        verify(mockLocationSearchRepository, times(0)).save(location);
    }


    @Test
    @Transactional
    public void checkProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setProvince(null);

        // Create the Location, which fails.


        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTownIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setTown(null);

        // Create the Location, which fails.


        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setCp(null);

        // Create the Location, which fails.


        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeOfRoadIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setTypeOfRoad(null);

        // Create the Location, which fails.


        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameRoadIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setNameRoad(null);

        // Create the Location, which fails.


        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setNumber(null);

        // Create the Location, which fails.


        restLocationMockMvc.perform(post("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].typeOfRoad").value(hasItem(DEFAULT_TYPE_OF_ROAD.toString())))
            .andExpect(jsonPath("$.[*].nameRoad").value(hasItem(DEFAULT_NAME_ROAD)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].apartment").value(hasItem(DEFAULT_APARTMENT)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].door").value(hasItem(DEFAULT_DOOR)))
            .andExpect(jsonPath("$.[*].stair").value(hasItem(DEFAULT_STAIR)))
            .andExpect(jsonPath("$.[*].urlgmaps").value(hasItem(DEFAULT_URLGMAPS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP))
            .andExpect(jsonPath("$.typeOfRoad").value(DEFAULT_TYPE_OF_ROAD.toString()))
            .andExpect(jsonPath("$.nameRoad").value(DEFAULT_NAME_ROAD))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.apartment").value(DEFAULT_APARTMENT))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING))
            .andExpect(jsonPath("$.door").value(DEFAULT_DOOR))
            .andExpect(jsonPath("$.stair").value(DEFAULT_STAIR))
            .andExpect(jsonPath("$.urlgmaps").value(DEFAULT_URLGMAPS))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).get();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .ref(UPDATED_REF)
            .province(UPDATED_PROVINCE)
            .town(UPDATED_TOWN)
            .cp(UPDATED_CP)
            .typeOfRoad(UPDATED_TYPE_OF_ROAD)
            .nameRoad(UPDATED_NAME_ROAD)
            .number(UPDATED_NUMBER)
            .apartment(UPDATED_APARTMENT)
            .building(UPDATED_BUILDING)
            .door(UPDATED_DOOR)
            .stair(UPDATED_STAIR)
            .urlgmaps(UPDATED_URLGMAPS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restLocationMockMvc.perform(put("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocation)))
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testLocation.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testLocation.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testLocation.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testLocation.getTypeOfRoad()).isEqualTo(UPDATED_TYPE_OF_ROAD);
        assertThat(testLocation.getNameRoad()).isEqualTo(UPDATED_NAME_ROAD);
        assertThat(testLocation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testLocation.getApartment()).isEqualTo(UPDATED_APARTMENT);
        assertThat(testLocation.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testLocation.getDoor()).isEqualTo(UPDATED_DOOR);
        assertThat(testLocation.getStair()).isEqualTo(UPDATED_STAIR);
        assertThat(testLocation.getUrlgmaps()).isEqualTo(UPDATED_URLGMAPS);
        assertThat(testLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);

        // Validate the Location in Elasticsearch
        verify(mockLocationSearchRepository, times(1)).save(testLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc.perform(put("/api/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Location in Elasticsearch
        verify(mockLocationSearchRepository, times(0)).save(location);
    }

    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Delete the location
        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Location in Elasticsearch
        verify(mockLocationSearchRepository, times(1)).deleteById(location.getId());
    }

    @Test
    @Transactional
    public void searchLocation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        locationRepository.saveAndFlush(location);
        when(mockLocationSearchRepository.search(queryStringQuery("id:" + location.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(location), PageRequest.of(0, 1), 1));

        // Search the location
        restLocationMockMvc.perform(get("/api/_search/locations?query=id:" + location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].typeOfRoad").value(hasItem(DEFAULT_TYPE_OF_ROAD.toString())))
            .andExpect(jsonPath("$.[*].nameRoad").value(hasItem(DEFAULT_NAME_ROAD)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].apartment").value(hasItem(DEFAULT_APARTMENT)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].door").value(hasItem(DEFAULT_DOOR)))
            .andExpect(jsonPath("$.[*].stair").value(hasItem(DEFAULT_STAIR)))
            .andExpect(jsonPath("$.[*].urlgmaps").value(hasItem(DEFAULT_URLGMAPS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
}
