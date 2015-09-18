/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.rptools.clientserver.hessian.server;

import java.io.IOException;

import net.rptools.clientserver.hessian.HessianUtils;

/**
 * @author drice
 */
public class ServerConnection extends net.rptools.clientserver.simple.server.ServerConnection {
	public ServerConnection(int port) throws IOException {
		super(port);
	}

	public void broadcastCallMethod(String method, Object... parameters) {
		broadcastMessage(HessianUtils.methodToBytesGZ(method, parameters));
	}

	public void broadcastCallMethod(String[] exclude, String method, Object... parameters) {
		byte[] data = HessianUtils.methodToBytesGZ(method, parameters);
		broadcastMessage(exclude, data);
	}

	public void callMethod(String id, String method, Object... parameters) {
		byte[] data = HessianUtils.methodToBytesGZ(method, parameters);
		sendMessage(id, null, data);
	}

	public void callMethod(String id, Object channel, String method, Object... parameters) {
		byte[] data = HessianUtils.methodToBytesGZ(method, parameters);
		sendMessage(id, channel, data);
	}
}
