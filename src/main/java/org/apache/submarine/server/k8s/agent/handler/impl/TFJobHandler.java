package org.apache.submarine.server.k8s.agent.handler.impl;

import io.fabric8.kubernetes.client.Watcher;
import org.apache.submarine.server.k8s.agent.enums.CustomResourceType;
import org.apache.submarine.server.k8s.agent.handler.ModelHandler;
import org.apache.submarine.server.k8s.agent.model.training.resource.TFJob;
import org.apache.submarine.server.k8s.agent.watcher.TrainingJobStatusWatcher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TFJobHandler implements ModelHandler {

    @Inject
    TrainingJobStatusWatcher watcher;

    @Override
    public CustomResourceType resourceType() {
        return CustomResourceType.TFJob;
    }

    @Override
    public Class<TFJob> resourceClass() {
        return TFJob.class;
    }

    @Override
    public Watcher watcher() {
        return watcher;
    }
}
