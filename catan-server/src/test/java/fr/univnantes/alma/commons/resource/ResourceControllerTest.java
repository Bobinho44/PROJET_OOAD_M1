package fr.univnantes.alma.commons.resource;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.core.resource.Resource;
import fr.univnantes.alma.core.resource.ResourceJSON;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the resource controller
 */
class ResourceControllerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final ResourceController resourceController = new ResourceController();
    private final Resource resource1 = new WheatResource().amount(3);
    private final Resource resource2 = new OreResource().amount(5);
    private final ResourceJSON resourceJSON1 = new ResourceJSONImpl("Wheat", 3);
    private final ResourceJSON resourceJSON2 = new ResourceJSONImpl("Ore", 5);

    @Test
    public void generateResourceTest() {
        assertEquals(resource1, resourceController.generateResource(resourceJSON1));
    }

    @Test
    public void generateResourcesTest() {
        List<ResourceJSON> resourceJSONS = List.of(resourceJSON1, resourceJSON2);
        Resource[] resources = {resource1, resource2};

        assertArrayEquals(resources, resourceController.generateResources(resourceJSONS).toArray());
    }

    @Test
    public void hasResourceTest() {
        assertTrue(resourceController.hasResource(resource1));
        assertFalse(resourceController.hasResource(new OreResource().amount(20)));
    }

    @Test
    public void hasResourcesTest() {
        assertTrue(resourceController.hasResources(List.of(resource1, resource2)));
        assertFalse(resourceController.hasResources(List.of(new OreResource().amount(20))));
    }

    @Test
    public void addResourceTest() {
        resourceController.addResource(new OreResource().amount(10));

        assertTrue(resourceController.hasResource(new OreResource().amount(29)));
    }

    @Test
    public void addResourcesTest() {
        resourceController.addResources(List.of(new OreResource().amount(10), new WheatResource().amount(31)));

        assertTrue(resourceController.hasResources(List.of(new OreResource().amount(29), new WheatResource().amount(50))));
    }

    @Test
    public void removeResourceTest() {
        resourceController.removeResource(new OreResource().amount(10));

        assertFalse(resourceController.hasResource(new OreResource().amount(10)));
        assertTrue(resourceController.hasResource(new OreResource().amount(9)));
    }

    @Test
    public void removeResourcesTest() {
        resourceController.removeResources(List.of(new OreResource().amount(10), new WheatResource().amount(1)));

        assertTrue(resourceController.hasResources(List.of(new OreResource().amount(9))));
        assertFalse(resourceController.hasResources(List.of(new OreResource().amount(10))));
        assertTrue(resourceController.hasResources(List.of(new WheatResource().amount(18))));
        assertFalse(resourceController.hasResources(List.of(new WheatResource().amount(19))));
    }

}