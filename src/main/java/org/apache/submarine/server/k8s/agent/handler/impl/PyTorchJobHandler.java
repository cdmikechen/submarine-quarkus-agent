package org.apache.submarine.server.k8s.agent.handler.impl;

import io.fabric8.kubernetes.client.Watcher;
import org.apache.submarine.server.k8s.agent.enums.CustomResourceType;
import org.apache.submarine.server.k8s.agent.handler.ModelHandler;
import org.apache.submarine.server.k8s.agent.model.training.resource.PyTorchJob;
import org.apache.submarine.server.k8s.agent.watcher.TrainingJobStatusWatcher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PyTorchJobHandler implements ModelHandler {

    @Inject
    TrainingJobStatusWatcher watcher;

    @Override
    public CustomResourceType resourceType() {
        return CustomResourceType.PyTorchJob;
    }

    @Override
    public Class<PyTorchJob> resourceClass() {
        return PyTorchJob.class;
    }

    @Override
    public String resourceLabel() {
        return "submarine-experiment-name";
    }

    @Override
    public Watcher watcher() {
        return watcher;
    }
}
