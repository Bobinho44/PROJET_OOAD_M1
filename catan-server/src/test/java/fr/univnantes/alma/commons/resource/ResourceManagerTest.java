package fr.univnantes.alma.commons.resource;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import fr.univnantes.alma.commons.resource.type.OreResource;
import fr.univnantes.alma.commons.resource.type.WheatResource;
import fr.univnantes.alma.core.resource.IResource;
import fr.univnantes.alma.core.resource.IResourceJSON;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests of the resource manager
 */
class ResourceManagerTest {

    static {
        ((Logger) LoggerFactory.getLogger("org.reflections")).setLevel(Level.OFF);
    }

    /**
     * Fields
     */
    private final ResourceManager resourceManager = new ResourceManager();
    private final IResource resource1 = new WheatResource().amount(3);
    private final IResource resource2 = new OreResource().amount(5);
    private final IResourceJSON resourceJSON1 = new ResourceJSON("Wheat", 3);
    private final IResourceJSON resourceJSON2 = new ResourceJSON("Ore", 5);

    @Test
    public void generateResourceTest() {
        assertEquals(resource1, resourceManager.generateResource(resourceJSON1));
    }

    @Test
    public void generateResourcesTest() {
        List<IResourceJSON> resourceJSONS = List.of(resourceJSON1, resourceJSON2);
        IResource[] resources = {resource1, resource2};

        assertArrayEquals(resources, resourceManager.generateResources(resourceJSONS).toArray());
    }

    @Test
    public void hasResourceTest() {
        assertTrue(resourceManager.hasResource(resource1));
        assertFalse(resourceManager.hasResource(new OreResource().amount(20)));
    }

    @Test
    public void hasResourcesTest() {
        assertTrue(resourceManager.hasResources(List.of(resource1, resource2)));
        assertFalse(resourceManager.hasResources(List.of(new OreResource().amount(20))));
    }

    @Test
    public void addResourceTest() {
        resourceManager.addResource(new OreResource().amount(10));

        assertTrue(resourceManager.hasResource(new OreResource().amount(29)));
    }

    @Test
    public void addResourcesTest() {
        resourceManager.addResources(List.of(new OreResource().amount(10), new WheatResource().amount(31)));

        assertTrue(resourceManager.hasResources(List.of(new OreResource().amount(29), new WheatResource().amount(50))));
    }

    @Test
    public void removeResourceTest() {
        resourceManager.removeResource(new OreResource().amount(10));

        assertFalse(resourceManager.hasResource(new OreResource().amount(10)));
        assertTrue(resourceManager.hasResource(new OreResource().amount(9)));
    }

    @Test
    public void removeResourcesTest() {
        resourceManager.removeResources(List.of(new OreResource().amount(10), new WheatResource().amount(1)));

        assertTrue(resourceManager.hasResources(List.of(new OreResource().amount(9))));
        assertFalse(resourceManager.hasResources(List.of(new OreResource().amount(10))));
        assertTrue(resourceManager.hasResources(List.of(new WheatResource().amount(18))));
        assertFalse(resourceManager.hasResources(List.of(new WheatResource().amount(19))));
    }

}