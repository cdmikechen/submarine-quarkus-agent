package org.apache.submarine.server.k8s.agent.enums;

public enum CustomResourceType {

    TFJob("TFJob"), PyTorchJob("PyTorchJob"), XGBoost("XGBoost"), Notebook("Notebook");

    private final String customResourceType;

    CustomResourceType(String customResourceType) {
        this.customResourceType = customResourceType;
    }

    public String getCustomResourceType() {
        return this.customResourceType;
    }
}