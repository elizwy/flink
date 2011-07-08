package eu.stratosphere.sopremo.pact;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.stub.Collector;
import eu.stratosphere.pact.common.stub.MapStub;
import eu.stratosphere.sopremo.EvaluationContext;

public abstract class SopremoMap<IK extends PactJsonObject.Key, IV extends PactJsonObject, OK extends PactJsonObject.Key, OV extends PactJsonObject>
		extends MapStub<PactJsonObject.Key, PactJsonObject, PactJsonObject.Key, PactJsonObject> {
	private EvaluationContext context;

	@Override
	public void configure(Configuration parameters) {
		this.context = SopremoUtil.deserialize(parameters, "context", EvaluationContext.class);
	}

	protected EvaluationContext getContext() {
		return this.context;
	}

	protected abstract void map(JsonNode key, JsonNode value, JsonCollector out);

	public void map(PactJsonObject.Key key, PactJsonObject value, Collector<PactJsonObject.Key, PactJsonObject> out) {
		map(key.getValue(), value.getValue(), new JsonCollector(out));
	};
}
