/**
 * @(#)OpenSamlResourceLoaderTest.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication.saml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opensaml.util.resource.ClasspathResource;
import org.opensaml.util.resource.FilesystemResource;
import org.opensaml.util.resource.Resource;
import org.opensaml.util.resource.ResourceException;

/**
 * OpenSamlResourceLoader test class.
 */
public class OpenSamlResourceLoaderTest {

    /** File system. */
    private FileSystem fileSystem = FileSystems.getDefault();

    /** Exception assertion. */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource1() throws ResourceException {

        // input argument.
        String location = "/" + getAbsolutePath("/testresources/testGetResource1.txt");

        // execute test method.
        Resource actualResource = OpenSamlResourceLoader.getResource(location);

        // assert output.
        assertEquals(FilesystemResource.class, actualResource.getClass());
        assertTrue(actualResource.exists());
        Path expectedPath =
                fileSystem.getPath(getAbsolutePath("/testresources/testGetResource1.txt"));
        assertEquals(expectedPath.toFile().getAbsolutePath(), actualResource.getLocation());

    }

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource2() throws ResourceException {

        // input argument.
        String location = getAbsolutePath("/testresources/testGetResource2.txt");

        // execute test method.
        Resource actualResource = OpenSamlResourceLoader.getResource(location);

        // assert output.
        assertEquals(FilesystemResource.class, actualResource.getClass());
        assertTrue(actualResource.exists());
        Path expectedPath =
                fileSystem.getPath(getAbsolutePath("/testresources/testGetResource2.txt"));
        assertEquals(expectedPath.toFile().getAbsolutePath(), actualResource.getLocation());

    }

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource3() throws ResourceException {

        // input argument.
        String location = "file:/" + getAbsolutePath("/testresources/testGetResource3.txt");

        // execute test method.
        Resource actualResource = OpenSamlResourceLoader.getResource(location);

        // assert output.
        assertEquals(FilesystemResource.class, actualResource.getClass());
        assertTrue(actualResource.exists());
        Path expectedPath =
                fileSystem.getPath(getAbsolutePath("/testresources/testGetResource3.txt"));
        assertEquals(expectedPath.toFile().getAbsolutePath(), actualResource.getLocation());

    }

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource4() throws ResourceException {

        // input argument.
        String location = "classpath:/testresources/testGetResource4.txt";

        // execute test method.
        Resource actualResource = OpenSamlResourceLoader.getResource(location);

        // assert output.
        assertEquals(ClasspathResource.class, actualResource.getClass());
        assertTrue(actualResource.exists());
        assertEquals("file:/" + getAbsolutePath("/testresources/testGetResource4.txt"),
                actualResource.getLocation());

    }

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource5() throws ResourceException {

        // input argument.
        String location = null;

        // Expect throwing an exception.
        thrown.expect(IllegalArgumentException.class);

        // execute test method.
        OpenSamlResourceLoader.getResource(location);

    }

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource6() throws ResourceException {

        // input argument.
        String location = "";

        // Expect throwing an exception.
        thrown.expect(ResourceException.class);

        // execute test method.
        OpenSamlResourceLoader.getResource(location);

    }

    /**
     * @throws ResourceException Test method for
     *         {@link com.fastretailing.dcp.storecommon.screen.authentication.saml.OpenSamlResourceLoader#getResource(java.lang.String)}.
     */
    @Test
    public void testGetResource7() throws ResourceException {

        // input argument.
        String location = "classpath:/testresources/testGetResource7.txt";

        // Expect throwing an exception.
        thrown.expect(ResourceException.class);

        // execute test method.
        OpenSamlResourceLoader.getResource(location);

    }

    /**
     * Returns the absolute path from the relative path.
     * 
     * @param relativePath the relative path.
     * @return the absolute path.
     */
    private String getAbsolutePath(String relativePath) {
        URL resourceUrl = getClass().getResource(relativePath);
        String resourcePath = resourceUrl.getPath().substring(1);
        return resourcePath;
    }

}
