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
package net.rptools.clientserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.rptools.clientserver.hessian.AbstractMethodHandler;
import net.rptools.clientserver.hessian.client.ClientConnection;
import net.rptools.clientserver.hessian.server.ServerConnection;

import com.caucho.hessian.io.HessianOutput;

/**
 * @author drice
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Test01 {

	public static void main(String[] args) throws Exception {
		ServerConnection server = new ServerConnection(4444);
		server.addMessageHandler(new ServerHandler());

		ClientConnection client = new ClientConnection("192.168.1.102", 4444, "Testing");
		client.addMessageHandler(new ClientHandler());

		for (int i = 0; i < 1000; i++) {
			if (i % 3 == 0) {
				client.callMethod("fromClient", "arg1", "arg2");
			}
			server.broadcastCallMethod("fromServer", "arg1");
			Thread.sleep(1000);
		}

		client.close();
		server.close();

	}

	private static byte[] getOutput(String method) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		HessianOutput hout = new HessianOutput(bout);

		hout.call(method, new Object[0]);

		return bout.toByteArray();
	}

	private static class ServerHandler extends AbstractMethodHandler {

		public void handleMethod(String id, String method, Object... parameters) {
			System.out.println("Server received: " + method + " from " + id + " args=" + parameters.length);
			for (Object param : parameters) {
				System.out.println("\t" + param);
			}
		}
	}

	private static class ClientHandler extends AbstractMethodHandler {
		public void handleMethod(String id, String method, Object... parameters) {
			System.out.println("Client received: " + method + " from " + id + " args=" + parameters.length);
			for (Object param : parameters) {
				System.out.println("\t" + param);
			}
		}
	}
}
