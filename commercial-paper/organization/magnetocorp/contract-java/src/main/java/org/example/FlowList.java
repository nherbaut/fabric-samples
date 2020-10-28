package org.example;

import org.example.ledgerapi.StateList;
import org.hyperledger.fabric.contract.Context;

public class FlowList {
    private StateList stateList;

    public FlowList(Context ctx) {
        this.stateList = StateList.getStateList(ctx, FlowList.class.getSimpleName(), Flow::deserialize);
    }

    public FlowList addFlow(Flow flow) {
        stateList.addState(flow);
        return this;
    }

    public Flow getFlow(String flowKey) {
        return (Flow) this.stateList.getState(flowKey);
    }

    public FlowList updatePaper(Flow flow) {
        this.stateList.updateState(flow);
        return this;
    }

}
