package org.apache.submarine.server.k8s.agent;

import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.quarkus.runtime.StartupEvent;
import org.apache.submarine.server.k8s.agent.config.SubmarineConfig;
import org.apache.submarine.server.k8s.agent.handler.ModelHandler;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class SubmarineAgentListener {

    private static final Logger LOGGER = Logger.getLogger(SubmarineAgentListener.class);

    @Inject
    KubernetesClient client;

    @Inject
    SubmarineConfig config;

    @Inject
    Instance<ModelHandler> handlers;

    public void init(@Observes StartupEvent event) {
        // filter resource
        var handlerOpt = handlers.stream()
                .filter(handler -> handler.resourceType() == config.resourceType())
                .findFirst();
        handlerOpt.ifPresentOrElse(handler -> {
            LOGGER.infov("watching {0} ...", config.resourceId());
            // get resource
            MixedOperation<?, KubernetesResourceList<?>, Resource<?>> resource = client.resources(handler.resourceClass());
            // list option
            var listOption = new ListOptionsBuilder()
                    .withLabelSelector(String.format("submarine-experiment-name=%s", config.resourceName()))
                    .withFieldSelector(String.format("metadata.name=%s", config.resourceId()))
                    .build();
            // check namespace
            resource.inNamespace(config.namespace().orElse(client.getNamespace()))
                    // watch
                    .watch(listOption, handler.watcher());
        }, () -> LOGGER.errorv("can not find resource type {0} implement!", config.resourceType()));
    }
}
