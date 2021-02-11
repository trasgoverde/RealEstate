package com.dihouse.app.web.rest;

import com.dihouse.app.RealEstateApp;
import com.dihouse.app.domain.Property;
import com.dihouse.app.repository.PropertyRepository;
import com.dihouse.app.repository.search.PropertySearchRepository;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dihouse.app.domain.enumeration.BuildingType;
import com.dihouse.app.domain.enumeration.ServiceType;
/**
 * Integration tests for the {@link PropertyResource} REST controller.
 */
@SpringBootTest(classes = RealEstateApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PropertyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BuildingType DEFAULT_BUILDING_TYPE = BuildingType.HOUSE;
    private static final BuildingType UPDATED_BUILDING_TYPE = BuildingType.FLAT;

    private static final ServiceType DEFAULT_SERVICE_TYPE = ServiceType.RENT;
    private static final ServiceType UPDATED_SERVICE_TYPE = ServiceType.SALE;

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final Boolean DEFAULT_SOLD = false;
    private static final Boolean UPDATED_SOLD = true;

    private static final Boolean DEFAULT_TERRACE = false;
    private static final Boolean UPDATED_TERRACE = true;

    private static final Integer DEFAULT_M_2 = 1;
    private static final Integer UPDATED_M_2 = 2;

    private static final Integer DEFAULT_NUMBER_BEDROOM = 1;
    private static final Integer UPDATED_NUMBER_BEDROOM = 2;

    private static final Boolean DEFAULT_ELEVATOR = false;
    private static final Boolean UPDATED_ELEVATOR = true;

    private static final Boolean DEFAULT_FURNISHED = false;
    private static final Boolean UPDATED_FURNISHED = true;

    private static final Boolean DEFAULT_POOL = false;
    private static final Boolean UPDATED_POOL = true;

    private static final Boolean DEFAULT_GARAGE = false;
    private static final Boolean UPDATED_GARAGE = true;

    private static final Integer DEFAULT_NUMBER_WC = 1;
    private static final Integer UPDATED_NUMBER_WC = 2;

    private static final Boolean DEFAULT_AC = false;
    private static final Boolean UPDATED_AC = true;

    @Autowired
    private PropertyRepository propertyRepository;

    /**
     * This repository is mocked in the com.dihouse.app.repository.search test package.
     *
     * @see com.dihouse.app.repository.search.PropertySearchRepositoryMockConfiguration
     */
    @Autowired
    private PropertySearchRepository mockPropertySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPropertyMockMvc;

    private Property property;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .buildingType(DEFAULT_BUILDING_TYPE)
            .serviceType(DEFAULT_SERVICE_TYPE)
            .ref(DEFAULT_REF)
            .visible(DEFAULT_VISIBLE)
            .sold(DEFAULT_SOLD)
            .terrace(DEFAULT_TERRACE)
            .m2(DEFAULT_M_2)
            .numberBedroom(DEFAULT_NUMBER_BEDROOM)
            .elevator(DEFAULT_ELEVATOR)
            .furnished(DEFAULT_FURNISHED)
            .pool(DEFAULT_POOL)
            .garage(DEFAULT_GARAGE)
            .numberWC(DEFAULT_NUMBER_WC)
            .ac(DEFAULT_AC);
        return property;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createUpdatedEntity(EntityManager em) {
        Property property = new Property()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .buildingType(UPDATED_BUILDING_TYPE)
            .serviceType(UPDATED_SERVICE_TYPE)
            .ref(UPDATED_REF)
            .visible(UPDATED_VISIBLE)
            .sold(UPDATED_SOLD)
            .terrace(UPDATED_TERRACE)
            .m2(UPDATED_M_2)
            .numberBedroom(UPDATED_NUMBER_BEDROOM)
            .elevator(UPDATED_ELEVATOR)
            .furnished(UPDATED_FURNISHED)
            .pool(UPDATED_POOL)
            .garage(UPDATED_GARAGE)
            .numberWC(UPDATED_NUMBER_WC)
            .ac(UPDATED_AC);
        return property;
    }

    @BeforeEach
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();
        // Create the Property
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProperty.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProperty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProperty.getBuildingType()).isEqualTo(DEFAULT_BUILDING_TYPE);
        assertThat(testProperty.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testProperty.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testProperty.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testProperty.isSold()).isEqualTo(DEFAULT_SOLD);
        assertThat(testProperty.isTerrace()).isEqualTo(DEFAULT_TERRACE);
        assertThat(testProperty.getm2()).isEqualTo(DEFAULT_M_2);
        assertThat(testProperty.getNumberBedroom()).isEqualTo(DEFAULT_NUMBER_BEDROOM);
        assertThat(testProperty.isElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testProperty.isFurnished()).isEqualTo(DEFAULT_FURNISHED);
        assertThat(testProperty.isPool()).isEqualTo(DEFAULT_POOL);
        assertThat(testProperty.isGarage()).isEqualTo(DEFAULT_GARAGE);
        assertThat(testProperty.getNumberWC()).isEqualTo(DEFAULT_NUMBER_WC);
        assertThat(testProperty.isAc()).isEqualTo(DEFAULT_AC);

        // Validate the Property in Elasticsearch
        verify(mockPropertySearchRepository, times(1)).save(testProperty);
    }

    @Test
    @Transactional
    public void createPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property with an existing ID
        property.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Property in Elasticsearch
        verify(mockPropertySearchRepository, times(0)).save(property);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setName(null);

        // Create the Property, which fails.


        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setRef(null);

        // Create the Property, which fails.


        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].buildingType").value(hasItem(DEFAULT_BUILDING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].sold").value(hasItem(DEFAULT_SOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE.booleanValue())))
            .andExpect(jsonPath("$.[*].m2").value(hasItem(DEFAULT_M_2)))
            .andExpect(jsonPath("$.[*].numberBedroom").value(hasItem(DEFAULT_NUMBER_BEDROOM)))
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].furnished").value(hasItem(DEFAULT_FURNISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.booleanValue())))
            .andExpect(jsonPath("$.[*].garage").value(hasItem(DEFAULT_GARAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].numberWC").value(hasItem(DEFAULT_NUMBER_WC)))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.buildingType").value(DEFAULT_BUILDING_TYPE.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.sold").value(DEFAULT_SOLD.booleanValue()))
            .andExpect(jsonPath("$.terrace").value(DEFAULT_TERRACE.booleanValue()))
            .andExpect(jsonPath("$.m2").value(DEFAULT_M_2))
            .andExpect(jsonPath("$.numberBedroom").value(DEFAULT_NUMBER_BEDROOM))
            .andExpect(jsonPath("$.elevator").value(DEFAULT_ELEVATOR.booleanValue()))
            .andExpect(jsonPath("$.furnished").value(DEFAULT_FURNISHED.booleanValue()))
            .andExpect(jsonPath("$.pool").value(DEFAULT_POOL.booleanValue()))
            .andExpect(jsonPath("$.garage").value(DEFAULT_GARAGE.booleanValue()))
            .andExpect(jsonPath("$.numberWC").value(DEFAULT_NUMBER_WC))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).get();
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .buildingType(UPDATED_BUILDING_TYPE)
            .serviceType(UPDATED_SERVICE_TYPE)
            .ref(UPDATED_REF)
            .visible(UPDATED_VISIBLE)
            .sold(UPDATED_SOLD)
            .terrace(UPDATED_TERRACE)
            .m2(UPDATED_M_2)
            .numberBedroom(UPDATED_NUMBER_BEDROOM)
            .elevator(UPDATED_ELEVATOR)
            .furnished(UPDATED_FURNISHED)
            .pool(UPDATED_POOL)
            .garage(UPDATED_GARAGE)
            .numberWC(UPDATED_NUMBER_WC)
            .ac(UPDATED_AC);

        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProperty)))
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProperty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProperty.getBuildingType()).isEqualTo(UPDATED_BUILDING_TYPE);
        assertThat(testProperty.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testProperty.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testProperty.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testProperty.isSold()).isEqualTo(UPDATED_SOLD);
        assertThat(testProperty.isTerrace()).isEqualTo(UPDATED_TERRACE);
        assertThat(testProperty.getm2()).isEqualTo(UPDATED_M_2);
        assertThat(testProperty.getNumberBedroom()).isEqualTo(UPDATED_NUMBER_BEDROOM);
        assertThat(testProperty.isElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testProperty.isFurnished()).isEqualTo(UPDATED_FURNISHED);
        assertThat(testProperty.isPool()).isEqualTo(UPDATED_POOL);
        assertThat(testProperty.isGarage()).isEqualTo(UPDATED_GARAGE);
        assertThat(testProperty.getNumberWC()).isEqualTo(UPDATED_NUMBER_WC);
        assertThat(testProperty.isAc()).isEqualTo(UPDATED_AC);

        // Validate the Property in Elasticsearch
        verify(mockPropertySearchRepository, times(1)).save(testProperty);
    }

    @Test
    @Transactional
    public void updateNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Property in Elasticsearch
        verify(mockPropertySearchRepository, times(0)).save(property);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Delete the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Property in Elasticsearch
        verify(mockPropertySearchRepository, times(1)).deleteById(property.getId());
    }

    @Test
    @Transactional
    public void searchProperty() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        when(mockPropertySearchRepository.search(queryStringQuery("id:" + property.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(property), PageRequest.of(0, 1), 1));

        // Search the property
        restPropertyMockMvc.perform(get("/api/_search/properties?query=id:" + property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].buildingType").value(hasItem(DEFAULT_BUILDING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].sold").value(hasItem(DEFAULT_SOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE.booleanValue())))
            .andExpect(jsonPath("$.[*].m2").value(hasItem(DEFAULT_M_2)))
            .andExpect(jsonPath("$.[*].numberBedroom").value(hasItem(DEFAULT_NUMBER_BEDROOM)))
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].furnished").value(hasItem(DEFAULT_FURNISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.booleanValue())))
            .andExpect(jsonPath("$.[*].garage").value(hasItem(DEFAULT_GARAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].numberWC").value(hasItem(DEFAULT_NUMBER_WC)))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.booleanValue())));
    }
}
