package org.apache.submarine.server.k8s.agent.handler.impl;

import io.fabric8.kubernetes.client.Watcher;
import org.apache.submarine.server.k8s.agent.enums.CustomResourceType;
import org.apache.submarine.server.k8s.agent.handler.ModelHandler;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotebookHandler implements ModelHandler {

    @Override
    public CustomResourceType resourceType() {
        return CustomResourceType.Notebook;
    }

    @Override
    public Class resourceClass() {
        return null;
    }

    @Override
    public String resourceLabel() {
        return "notebook-id";
    }

    @Override
    public Watcher watcher() {
        return null;
    }
}
