package com.arextest.schedule.model;

import com.arextest.schedule.common.SendSemaphoreLimiter;
import com.arextest.schedule.model.bizlog.BizLog;
import com.arextest.schedule.model.dao.mongodb.ReplayPlanCollection;
import com.arextest.schedule.model.plan.BuildReplayPlanType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

/**
 * @author jmo
 * @see ReplayPlanCollection
 * @since 2021/9/15
 */
@Data
@ToString(of = {"id", "appId", "sourceEnv", "sourceHost", "targetEnv", "targetHost"})
public class ReplayPlan {
    private String id;
    private String appId;
    private Integer replaySendMaxQps;
    @JsonIgnore
    private String planName;
    // @JsonIgnore
    private String sourceEnv;
    // @JsonIgnore
    private String targetEnv;
    @JsonIgnore
    private String sourceHost;
    @JsonIgnore
    private String targetHost;
    @JsonIgnore
    private String targetImageId;
    @JsonIgnore
    private String targetImageName;
    @JsonIgnore
    private Date caseSourceFrom;
    @JsonIgnore
    private Date caseSourceTo;
    @JsonIgnore
    private Date planCreateTime;
    @JsonIgnore
    private Date planFinishTime;
    @JsonIgnore
    private String operator;
    @JsonIgnore
    private String arexCordVersion;
    @JsonIgnore
    private String arexExtVersion;
    @JsonIgnore
    private String caseRecordVersion;
    private int caseTotalCount;
    /**
     * see {@link CaseSourceEnvType}
     */
    @JsonIgnore
    private int caseSourceType;
    /**
     * @see BuildReplayPlanType
     */
    @JsonIgnore
    private int replayPlanType;
    @JsonIgnore
    private List<ReplayActionItem> replayActionItemList;
    @JsonIgnore
    private List<PlanExecutionContext> executionContexts;
    @JsonIgnore
    private String appName;
    @JsonIgnore
    private int caseCountLimit;
    @JsonIgnore
    private String errorMessage;
    private transient long planCreateMillis;

    private boolean resumed;

    // Min(targetInstanceCount || Int.MAX, sourceInstanceCount || Int.MAX)
    @JsonIgnore
    private int minInstanceCount;

    @JsonIgnore
    private ExecutionStatus planStatus;
    @JsonIgnore
    private SendSemaphoreLimiter limiter;

    @JsonIgnore
    private long lastLogTime = System.currentTimeMillis();

    @JsonIgnore
    private BlockingQueue<BizLog> bizLogs = new LinkedBlockingQueue<>();

    public void enqueueBizLog(BizLog log) {
        this.bizLogs.add(log);
    }

    @JsonIgnore
    private ScheduledFuture<?> monitorFuture;
}