package org.apache.submarine.server.k8s.agent.handler;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.client.Watcher;
import org.apache.submarine.server.k8s.agent.enums.CustomResourceType;

public interface ModelHandler<T extends CustomResource> {

    CustomResourceType resourceType();

    Class<T> resourceClass();

    String resourceLabel();

    Watcher watcher();

}
