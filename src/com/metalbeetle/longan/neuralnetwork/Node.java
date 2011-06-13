package com.metalbeetle.longan.neuralnetwork;

/*
 * Copyright 2011 David Stark
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static com.metalbeetle.longan.neuralnetwork.Util.*;
import java.util.ArrayList;

public class Node {
	public final String name;
	public double activation;
	public double delta;
	public final ArrayList<Connection> incoming = new ArrayList<Connection>();
	public final ArrayList<Connection> outgoing = new ArrayList<Connection>();

	public Node(String name) {
		this.name = name;
	}
	
	public void update() {
		if (incoming.isEmpty()) { return; }
		double sum = 0.0;
		for (Connection in : incoming) {
			for (Node input : in.inputs) {
				sum += input.activation * in.weight.value / in.inputs.size();
			}
		}
		activation = sigmoid(sum);
	}

	void calculateDelta() {
		double error = 0.0;
		for (Connection out : outgoing) {
			error += out.output.delta * out.weight.value; // qqDPS Scale by size?
		}
		delta = dSigmoid(activation) * error;
	}
}
