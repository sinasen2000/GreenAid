package app.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RecommendationRepositoryTest {

    private RecommendationRepository recommendationRepository;

    @Before
    public void setUp() {
        recommendationRepository = new RecommendationRepository();
    }

    @After
    public void tearDown() {
        recommendationRepository = null;
    }

    @Test
    public void getEatRecommendations() {
        List<String> recommendations = recommendationRepository.getEatRecommendations();
        assertEquals(8, recommendations.size());
    }

    @Test
    public void getHouseholdRecommendations() {
        List<String> recommendations = recommendationRepository.getHouseholdRecommendations();
        assertEquals(6, recommendations.size());
    }

    @Test
    public void getTransportRecommendations() {
        List<String> recommendations = recommendationRepository.getTransportRecommendations();
        assertEquals(6, recommendations.size());
    }
}