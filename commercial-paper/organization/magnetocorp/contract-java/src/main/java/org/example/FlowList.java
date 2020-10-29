package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.ledgerapi.StateList;
import org.hyperledger.fabric.contract.Context;

public class FlowList {
    private StateList stateList;

    private final static Logger LOG = Logger.getLogger(FlowList.class.getName());

    public FlowList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, FlowList.class.getSimpleName(), Flow::deserialize);
    }

    public FlowList addFlow(Flow flow) {
        LOG.severe("adding state");
        stateList.addState(flow);

        return this;
    }

    public Flow getFlow(String flowKey) {
        return (Flow) this.stateList.getState(flowKey);
    }

    public String getAFlow() {
        return this.stateList.getAllStates();
    }

    public FlowList updatePaper(Flow flow) {
        this.stateList.updateState(flow);
        return this;
    }

}
