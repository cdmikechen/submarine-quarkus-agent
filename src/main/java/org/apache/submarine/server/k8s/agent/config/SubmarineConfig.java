package org.apache.submarine.server.k8s.agent.config;

import io.smallrye.config.ConfigMapping;
import org.apache.submarine.server.k8s.agent.enums.CustomResourceType;

import java.util.Optional;

@ConfigMapping(prefix = "submarine")
public interface SubmarineConfig {

    Optional<String> namespace();

    CustomResourceType resourceType();

    String resourceName();

    String resourceId();

}
