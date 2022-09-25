package org.apache.submarine.server.k8s.agent.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.apache.submarine.server.k8s.agent.entity.Experiment;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Date;

@ApplicationScoped
public class ExperimentRepository implements PanacheRepository<Experiment> {

    @Transactional
    public Experiment findById(String id) {
        return find("id=?1", id).firstResult();
    }

    @Transactional
    public void created(String id, Date startTime) {
        update("experimentStatus='Created', acceptedTime=?1, updateTime=?2 where id=?3",
                startTime, new Date(), id);
    }

    @Transactional
    public void succeed(String id, Date startTime) {
        update("experimentStatus='Succeeded', finishedTime=?1, updateTime=?2 where id=?3",
                startTime, new Date(), id);
    }

    @Transactional
    public void failed(String id, Date startTime) {
        update("experimentStatus='Failed', finishedTime=?1, updateTime=?2 where id=?3",
                startTime, new Date(), id);
    }

    @Transactional
    public void running(String id, Date startTime) {
        update("experimentStatus='Running', runningTime=?1, updateTime=?2 where id=?3",
                startTime, new Date(), id);
    }

}
