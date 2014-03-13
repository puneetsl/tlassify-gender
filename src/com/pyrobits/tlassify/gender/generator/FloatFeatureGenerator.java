package com.pyrobits.tlassify.gender.generator;

import java.util.HashMap;

public abstract class FloatFeatureGenerator {
	private HashMap<Integer, Float> featureValues = new HashMap<>();

	public void setFeatureValue(Integer key, Float value)
	{
		featureValues.put(key, value);
	}
	public Float getFeatureValue(Integer key) {
		return featureValues.get(key);
	}
}
