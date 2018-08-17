/**
 * @(#)UriUtility.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fastretailing.dcp.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

/**
 * URI utility class.
 */
@Slf4j
public class UriUtility {

    /** Separator for URI path. */
    public static final String URI_PATH_SEPARATOR = "/";

    /** Separator for query parameters. */
    public static final String QUERY_PARAMETERS_SEPARATOR = "?";

    /** Common path for screen applications. */
    private static final String SCREEN_COMMON_PATH = "/screen/";

    /** Regex pattern for six-digits store code. */
    private static final Pattern REGEX_STORE_CODE_PATTERN = Pattern.compile("\\d{6}");

    /**
     * Create URI information from input URI and return it.
     * 
     * @param inputUri the input URI string.
     * @return the URI information.
     */
    public static UriInformation getUriInformation(String inputUri) {

        log.debug("Input URI={}", inputUri);

        UriInformation uriInformation = new UriInformation();

        if (inputUri != null) {
            URI uri = createURI(inputUri);

            String queryParameters = uri.getQuery();
            uriInformation.setQuery(queryParameters);

            String fragment = uri.getFragment();
            uriInformation.setFragment(fragment);

            uriInformation.setBasePath(getBasePath(uri));
        }

        return uriInformation;

    }

    /**
     * Returns URI instance from URI string.
     * 
     * @param inputUri the input URI string.
     * @return the URI instance.
     */
    private static URI createURI(String inputUri) {
        try {
            return new URI(inputUri);
        } catch (URISyntaxException e) {
            throw new SystemException("The URI is in an abnormal format. URI=" + inputUri, e);
        }
    }

    /**
     * Returns the base path after removing the query parameters and fragments from the URI.
     * 
     * @param inputUri the input URI string.
     * @return the base path after removing the query parameters and fragments.
     */
    public static String getBasePath(String inputUri) {
        log.debug("Input URI={}", inputUri);
        if (inputUri != null) {
            return getBasePath(createURI(inputUri));
        } else {
            return null;
        }
    }

    /**
     * Returns the base path after removing the query parameters and fragments from the URI.
     * 
     * @param uri the URI instance.
     * @return the base path after removing the query parameters and fragments.
     */
    private static String getBasePath(URI uri) {
        URI basePathUri;
        try {
            basePathUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    uri.getPath(), null, null);
        } catch (URISyntaxException e) {
            throw new SystemException("Failed to convert to base path.", e);
        }
        return basePathUri.toString();
    }

    /**
     * Gets brand and region from the saved request URL and sets it as
     * {@link RequestPathVariableHolder}.
     * 
     * @param serverContextPath the context path defined in the application.yml file.
     */
    public static void setRequestPathVariable(String serverContextPath) {

        // Processing instead of RequestPathVariableInterceptor.
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, null);
        String originalUrl = savedRequest.getRedirectUrl();
        log.debug("originalUrl={}", originalUrl);

        String controllerPath = getControllerPath(originalUrl, serverContextPath);

        String[] paths = controllerPath.split(UriUtility.URI_PATH_SEPARATOR);
        if (paths.length >= 5) {
            Map<String, String> pathVariableMap = new HashMap<>();
            pathVariableMap.put(StoreRequestPathVariableHolder.KEY_BRAND, getBrand(paths));
            pathVariableMap.put(StoreRequestPathVariableHolder.KEY_REGION, getRegion(paths));
            pathVariableMap.put(StoreRequestPathVariableHolder.KEY_STORECODE, getStoreCode(paths));
            StoreRequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);
        } else {
            throw new SystemException(
                    "The layer of the URL does not conform to API regulations. controllerPath="
                            + controllerPath);
        }

    }

    /**
     * Removes the context path from the URI and returns the partial path for the controller.
     * 
     * @param uri the URI.
     * @param serverContextPath the server context path.
     * @return the partial path for the controller.
     */
    private static String getControllerPath(String uri, String serverContextPath) {

        if (uri != null) {
            String[] uriParts = uri.split(serverContextPath);
            if (uriParts.length == 2) {
                String controllerPath = uriParts[1];
                log.debug("Controller path={}", controllerPath);
                return controllerPath;
            } else {
                throw new SystemException(
                        "The URI does not conform to API regulations. URI=" + uri);
            }
        } else {
            throw new SystemException("The URI is null. URI=" + uri);
        }

    }

    /**
     * Returns brand from path list.
     * 
     * @param paths the path list.
     * @return brand.
     */
    private static String getBrand(String[] paths) {
        String brand = paths[1];
        log.debug("paths[1](brand)={}", brand);
        return brand;
    }

    /**
     * Returns region from path list.
     * 
     * @param paths the path list.
     * @return region.
     */
    private static String getRegion(String[] paths) {
        String region = paths[2];
        log.debug("paths[2](region)={}", region);
        return region;
    }

    /**
     * If the fifth path is a six-digit store code, return the store code from path list. Otherwise
     * it returns null.
     * 
     * @param paths the path list.
     * @return store code.
     */
    private static String getStoreCode(String[] paths) {
        String storeCodePath = paths[4];
        String storeCode = null;

        if (!StringUtils.isEmpty(storeCodePath)
                && REGEX_STORE_CODE_PATTERN.matcher(storeCodePath).matches()) {
            storeCode = storeCodePath;
            log.debug("paths[4](store code)={}", storeCodePath);
        } else {
            log.debug("paths[4](not store code)={}", storeCodePath);
        }

        return storeCode;
    }

    /**
     * Creates possible path list that may be access-managed from the request URL.
     * 
     * Basically, the path after the common path for the screen is targeted. However, since there
     * may be a store code layer, deals with another step deeper.
     * 
     * @param requestPath request URL path.
     * @return path list that may be access-managed.
     */
    public static List<String> createPossiblePathList(String requestPath) {

        // Remove query parameters if present.
        String targetRequestPath = getBasePath(requestPath);

        List<String> targetPathList = new ArrayList<>();

        if (targetRequestPath != null) {
            int firstScreenIndex = targetRequestPath.indexOf(SCREEN_COMMON_PATH);
            if (firstScreenIndex != -1) {

                // The part after the screen common path.
                String targetPath = targetRequestPath
                        .substring(firstScreenIndex + SCREEN_COMMON_PATH.length() - 1);
                targetPathList.add(targetPath);

                // Measures against URI that may have store code layers.
                int firstSeparatorIndex = targetPath.indexOf(URI_PATH_SEPARATOR);
                if (firstSeparatorIndex != targetPath.lastIndexOf(URI_PATH_SEPARATOR)) {
                    String noStoreCodePath = targetPath.substring(
                            targetPath.indexOf(URI_PATH_SEPARATOR, firstSeparatorIndex + 1));
                    targetPathList.add(noStoreCodePath);
                }

            }
        }

        return targetPathList;

    }

}
