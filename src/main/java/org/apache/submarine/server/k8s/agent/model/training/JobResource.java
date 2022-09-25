package org.apache.submarine.server.k8s.agent.model.training;


import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import org.apache.submarine.server.k8s.agent.model.training.status.JobStatus;

public class JobResource extends CustomResource<Void, JobStatus> implements Namespaced {
}
