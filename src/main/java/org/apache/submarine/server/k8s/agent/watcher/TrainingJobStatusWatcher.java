package org.apache.submarine.server.k8s.agent.watcher;

import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import org.apache.submarine.server.k8s.agent.dao.ExperimentRepository;
import org.apache.submarine.server.k8s.agent.model.training.JobResource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TrainingJobStatusWatcher implements Watcher {

    private static final Logger LOGGER = Logger.getLogger(TrainingJobStatusWatcher.class);

    private static final List<String> FINISHED_TYPES = List.of("Succeeded", "Failed");

    @Inject
    ExperimentRepository er;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void eventReceived(Action action, Object resource) {
        var jobResource = (JobResource) resource;

        // get conditions
        var conditions = jobResource.getStatus().getConditions();
        if (conditions == null || conditions.isEmpty()) {
            LOGGER.warn("conditions is empty, skip...");
            return;
        }
        // get condition and update experiment
        var lastCondition = conditions.get(conditions.size() - 1);

        // find experiment name/experiment_id
        var name = jobResource.getMetadata().getName();
        // The reason value can refer to https://github.com/kubeflow/common/blob/master/pkg/util/status.go
        var reason = Objects.requireNonNull(lastCondition.getReason());
        // The type value can refer to https://github.com/kubeflow/common/blob/master/pkg/apis/common/v1/types.go#L112
        var type = Objects.requireNonNull(lastCondition.getType());
        // time
        var zdt = ZonedDateTime.parse(lastCondition.getLastTransitionTime(), DTF);
        var date = Date.from(zdt.toInstant());
        LOGGER.infov("receiving type: {0}", type);
        LOGGER.infov("current status/reason of {0} is {1}/{2}", name, lastCondition.getStatus(), reason);
        switch (type) {
            case "Created" -> er.created(name, date);
            case "Restarting", "Running" -> er.running(name, date);
            case "Succeeded" -> er.succeed(name, date);
            case "Failed" -> er.failed(name, date);
        }
        // finish agent
        if (FINISHED_TYPES.contains(type)) {
            LOGGER.infov("finish job ...");
            System.exit(-1);
        }
    }

    @Override
    public void onClose(WatcherException cause) {

    }
}
